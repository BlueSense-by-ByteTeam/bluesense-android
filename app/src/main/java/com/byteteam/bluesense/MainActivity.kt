package com.byteteam.bluesense

import android.Manifest
import MqttClientHelper
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.core.domain.model.DeviceEntity
import com.byteteam.bluesense.core.domain.model.SensorData
import com.byteteam.bluesense.core.helper.Screens
import com.byteteam.bluesense.core.presentation.helper.GoogleSignInClientHelper
import com.byteteam.bluesense.core.presentation.views.device.add_form.AddDeviceFormViewModel
import com.byteteam.bluesense.core.presentation.views.device.add_form.AddDeviceViewModel
import com.byteteam.bluesense.core.presentation.views.device.detail.DetailDeviceViewModel
import com.byteteam.bluesense.core.presentation.views.device.scan.ScanViewModel
import com.byteteam.bluesense.core.presentation.views.home.HomeViewModel
import com.byteteam.bluesense.core.presentation.views.onboard.OnBoardViewModel
import com.byteteam.bluesense.core.presentation.views.reset_password.ResetPasswordViewModel
import com.byteteam.bluesense.core.presentation.views.signin.AuthViewModel
import com.byteteam.bluesense.core.presentation.views.signup.RegisterViewModel
import com.byteteam.bluesense.core.presentation.views.statistic.StatisticHistoryViewModel
import com.byteteam.bluesense.core.presentation.views.store.StoreViewModel
import com.byteteam.bluesense.core.services.FCMService
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import javax.inject.Inject
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val scanViewModel: ScanViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val onBoardViewModel: OnBoardViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val addDeviceViewModel: AddDeviceViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val addDeviceFormViewModel: AddDeviceFormViewModel by viewModels()
    private val detailDeviceViewModel: DetailDeviceViewModel by viewModels()
    private val storeViewModel: StoreViewModel by viewModels()
    private val resetPasswordViewModel: ResetPasswordViewModel by viewModels()
    private val historyViewModel: StatisticHistoryViewModel by viewModels()

    @Inject
    lateinit var googleAuthUiClient: GoogleSignInClientHelper

    var mqttClientHelper: MqttClientHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        installSplashScreen()

        if (Build.VERSION.SDK_INT >= 33) {
            requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        FCMService.subscribeTopic(
            this,
            "it_should_device_id"
        )// TODO: the topic should get from REST server data

        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.apply {
            this.userName = BuildConfig.MQTT_USERNAME
            this.password = BuildConfig.MQTT_PASSWORD.toCharArray()
        }

        setContent {
            val navController: NavHostController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            fun callbackOnSuccessSignIn() =
                navController.navigate(Screens.Home.route) {
                    popUpTo(Screens.SignIn.route) {
                        inclusive = true
                    }
                }


            val googleSignInLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult(),
                onResult = { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        val task: Task<GoogleSignInAccount> =
                            GoogleSignIn.getSignedInAccountFromIntent(result.data)
                        try {
                            lifecycleScope.launch {
                                val signInResult = googleAuthUiClient.getSignInResult(task)
                                signInResult.data?.let {
                                    registerViewModel.googleSignup(it)
                                }
                                authViewModel.getCurrentUser()
                                homeViewModel.getDevices()
                                storeViewModel.getWaterFilters()
                                storeViewModel.getWaterSuppliers()
                                storeViewModel.getFeaturedWaterFilters()
                                callbackOnSuccessSignIn()
                            }

                        } catch (e: ApiException) {
                            lifecycleScope.launch {
                                authViewModel.setErrorSignIn(e.message)
                            }
                        } finally {
                            authViewModel.enableGoogleSigninButton()
                        }
                    }
                }
            )

            fun newGoogleSignIn() = lifecycleScope.launch {
                authViewModel.disableGoogleSigninButton()
                googleSignInLauncher.launch(
                    googleAuthUiClient.client.signInIntent ?: return@launch
                )
            }

            fun callbackOnConnected(data: DeviceEntity) {
                val brokerUrl = "ssl://${data.mqttBaseUrl}:8883"
                val mqttTopic = data.mqttTopic
                initMqtt(
                    context = this,
                    mqttConnectOptions = mqttConnectOptions,
                    brokerUrl = brokerUrl,
                    mqttTopic = mqttTopic,
                    cbStatusConnection = { detailDeviceViewModel.updateDeviceStatus(it) },
                    cbOnMessage = { detailDeviceViewModel.updateDeviceSensorValue(it) },
                )
            }

            BlueSenseTheme {
                App(
                    currentRoute = currentRoute,
                    navController = navController,
                    signInGoogle = { newGoogleSignIn() },
                    callbackOnSuccessSignIn = { callbackOnSuccessSignIn() },
                    callbackOnConnected = { callbackOnConnected(it) },
                    scanViewModel = scanViewModel,
                    authViewModel = authViewModel,
                    onBoardViewModel = onBoardViewModel,
                    registerViewModel = registerViewModel,
                    addDeviceViewModel = addDeviceViewModel,
                    homeViewModel = homeViewModel,
                    addDeviceFormViewModel = addDeviceFormViewModel,
                    detailDeviceViewModel = detailDeviceViewModel,
                    storeViewModel = storeViewModel,
                    resetPasswordViewModel = resetPasswordViewModel,
                    historyViewModel = historyViewModel,
                )
            }
        }
    }

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
            }
        }

    private fun initMqtt(
        context: Context,
        mqttConnectOptions: MqttConnectOptions?,
        brokerUrl: String,
        mqttTopic: String,
        cbStatusConnection: (Boolean) -> Unit,
        cbOnMessage: (SensorData) -> Unit,
    ) {
        if (mqttClientHelper == null) {
            mqttClientHelper = MqttClientHelper(
                context = context,
                broker = brokerUrl
            )

            mqttClientHelper?.init(
                cbOnConnected = {
                    cbStatusConnection(true)
//                    Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show()
                },
                cbOnLostConnection = {
                    cbStatusConnection(false)
//                    Toast.makeText(context, "Lost connection", Toast.LENGTH_SHORT).show()
                },
                cbOnMessage = {
                    val data = Gson()?.fromJson(it, SensorData::class.java)
                    data?.let(cbOnMessage)
//                    Log.d("message", "on message cb: $data")
//                    Toast.makeText(context, "message: $it", Toast.LENGTH_SHORT).show()
                },
            )
            mqttConnectOptions?.let {
                mqttClientHelper?.connect(mqttConnectOptions = it, topic = mqttTopic)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mqttClientHelper = null
    }
}