package com.byteteam.bluesense.core.presentation.views.device.scan

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.launch

class ScanViewModel : ViewModel() {
    fun startScan(context: Context){
        viewModelScope.launch {

            val options = GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    Barcode.FORMAT_QR_CODE,
                    Barcode.FORMAT_AZTEC
                )
                .build()
            val scanner = GmsBarcodeScanning.getClient(context)

            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    Log.d("TAG", "qr scan: ${barcode.rawValue}")
                }
                .addOnCanceledListener {
                    // Task canceled
                    Log.d("TAG", "qr scan canceled")
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    Log.d("TAG", "qr scan fail: ${e.message}")
                }
        }
    }
}