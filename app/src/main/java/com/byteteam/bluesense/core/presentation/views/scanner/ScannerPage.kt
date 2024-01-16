package com.byteteam.bluesense.core.presentation.views.scanner

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.presentation.views.scanner.widgets.BottomDecoration
import com.byteteam.bluesense.core.presentation.views.scanner.widgets.CenterRect
import com.byteteam.bluesense.core.presentation.views.scanner.widgets.ScannerActions
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@OptIn(ExperimentalGetImage::class)
@Composable
fun ScannerPage(
    cbOnScanSuccess: (String) -> Unit,
    navHostController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    val previewView: PreviewView = remember { PreviewView(context) }
    val cameraController = remember { LifecycleCameraController(context) }
    val lifecycleOwner = LocalLifecycleOwner.current
    cameraController.bindToLifecycle(lifecycleOwner)
    cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    previewView.controller = cameraController

    val executor = remember { Executors.newSingleThreadExecutor() }
    val options = BarcodeScannerOptions
        .Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .enableAllPotentialBarcodes()
        .build()
    val barcodeScanner = remember { BarcodeScanning.getClient(options) }

    var barcodeText by rememberSaveable { mutableStateOf("") }
    var isFlashOn by remember { mutableStateOf(false) }
    fun toggleFlash() {
        cameraController.enableTorch(!isFlashOn)
        isFlashOn = !isFlashOn
    }

    var permissionGranted by remember { mutableStateOf(true) }

    val permissions = arrayOf(
        Manifest.permission.CAMERA,
    )
    val launcherCameraPermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        permissionGranted = areGranted
    }


    LaunchedEffect(Unit) {
        checkAndRequestPermission(
            context,
            permissions,
            cbOnGranted = { permissionGranted = true },
            launcherCameraPermissions
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            // Release camera and other resources here
            cameraController.unbind()
            barcodeScanner.close()
            executor.shutdown()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (!permissionGranted) {
            Dialog(onDismissRequest = {}) {
                Card(Modifier.padding(24.dp)) {
                    Column(Modifier.padding(24.dp)) {
                        Text(text = stringResource(R.string.permission_camera_rejeected))
                        Button(modifier = Modifier.align(Alignment.End), onClick = { navHostController.popBackStack() }) {
                            Text(text = "Kembali")
                        }
                    }
                }
            }
        }

        AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())
        ScannerActions(
            isFlashOn = isFlashOn,
            onToggleFlash = { toggleFlash() },
            onNavBack = {
                navHostController.popBackStack()
            },
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )
        BottomDecoration(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
        CenterRect(
            modifier = Modifier
                .align(Alignment.Center)
        )

        cameraController.setImageAnalysisAnalyzer(executor) { imageProxy ->
            try {
                imageProxy.image?.let { image ->
                    val img = InputImage.fromMediaImage(
                        image,
                        imageProxy.imageInfo.rotationDegrees
                    )
                    // get image every frame then process it in barcode scanner
                    barcodeScanner
                        .process(img)
                        .addOnSuccessListener { barcodes ->
                            barcodes.forEach { barcode ->
                                barcode.rawValue?.let { barcodeValue ->
                                    barcodeText = barcodeValue
                                }
                            }
                        }
                        .addOnFailureListener {
                            Log.d("Scanner QR", "Error: ${it.message}")
                        }
                }
            } catch (e: Exception) {
                Log.d("Scanner QR", "Error: ${e.message}")
            } finally {
                imageProxy.close()
            }
        }
    }

    if (barcodeText.isNotEmpty()) {
        cbOnScanSuccess(barcodeText)
    }
}

fun checkAndRequestPermission(
    context: Context,
    permissions: Array<String>,
    cbOnGranted: () -> Unit,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
) {
    if (
        permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    ) {
        cbOnGranted()
    } else {
        launcher.launch(permissions)
    }
}