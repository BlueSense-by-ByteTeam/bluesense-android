package com.byteteam.bluesense

import MqttClientHelper
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.webkit.URLUtil
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.core.domain.model.DeviceEntity
import com.byteteam.bluesense.core.domain.model.SensorData
import com.byteteam.bluesense.core.helper.Screens
import com.byteteam.bluesense.core.helper.Topbars
import com.byteteam.bluesense.core.helper.bottomNavigationItems
import com.byteteam.bluesense.core.presentation.helper.GoogleSignInClient
import com.byteteam.bluesense.core.presentation.views.device.add.AddScreen
import com.byteteam.bluesense.core.presentation.views.device.add_form.AddDeviceFormScreen
import com.byteteam.bluesense.core.presentation.views.device.add_form.AddDeviceFormViewModel
import com.byteteam.bluesense.core.presentation.views.device.add_form.AddDeviceScreenData
import com.byteteam.bluesense.core.presentation.views.device.add_form.AddDeviceViewModel
import com.byteteam.bluesense.core.presentation.views.device.detail.DetailDeviceViewModel
import com.byteteam.bluesense.core.presentation.views.device.detail.DetailScreen
import com.byteteam.bluesense.core.presentation.views.device.scan.ScanViewModel
import com.byteteam.bluesense.core.presentation.views.getstarted.GetStartedScreen
import com.byteteam.bluesense.core.presentation.views.home.HomeScreen
import com.byteteam.bluesense.core.presentation.views.home.HomeViewModel
import com.byteteam.bluesense.core.presentation.views.notification.NotificationScreen
import com.byteteam.bluesense.core.presentation.views.onboard.OnBoardScreen
import com.byteteam.bluesense.core.presentation.views.onboard.OnBoardViewModel
import com.byteteam.bluesense.core.presentation.views.profile.ProfileScreen
import com.byteteam.bluesense.core.presentation.views.signin.AuthViewModel
import com.byteteam.bluesense.core.presentation.views.signin.SignInData
import com.byteteam.bluesense.core.presentation.views.signin.SigninScreen
import com.byteteam.bluesense.core.presentation.views.signup.RegisterViewModel
import com.byteteam.bluesense.core.presentation.views.signup.SignupScreen
import com.byteteam.bluesense.core.presentation.views.signup.widgets.SignupScreenContentData
import com.byteteam.bluesense.core.presentation.views.statistic.StatisticScreen
import com.byteteam.bluesense.core.presentation.views.store.main.StoreScreen
import com.byteteam.bluesense.core.presentation.widgets.BottomBar
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import javax.inject.Inject

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

    @Inject
    lateinit var googleAuthUiClient: GoogleSignInClient

    var mqttClientHelper: MqttClientHelper? = null
//    private val mqttClient by lazy {
//        MqttHandler(this)
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        installSplashScreen()

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

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    Log.d("TAG", "onCreate: $result")
                    if (result.resultCode == RESULT_OK) {
                        lifecycleScope.launch {
                            val signInResult =
                                googleAuthUiClient.getSignInResultFromIntent(
                                    intent = result.data ?: return@launch
                                )
                            signInResult.data?.let {
                                registerViewModel.googleSignup(it)
                            }
                            authViewModel.getCurrentUser()
                            callbackOnSuccessSignIn()
                        }
                    }
                }
            )

            fun signInGoogle() = lifecycleScope.launch {
                authViewModel.disableGoogleSigninButton()
                val signInIntentSender = googleAuthUiClient.signIn()
                launcher.launch(
                    IntentSenderRequest.Builder(
                        signInIntentSender ?: return@launch
                    ).build()
                )
                authViewModel.enableGoogleSigninButton()
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
                Scaffold(
                    topBar = {
                        Topbars(route = currentRoute ?: "", navHostController = navController)
                    },
                    bottomBar = {
                        if (bottomNavigationItems.map { it.route }
                                .contains(currentRoute)) BottomBar(
                            currentRoute = currentRoute ?: "", navHostController = navController
                        )
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .padding(it)
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val isSigned = authViewModel.currentUser.collectAsState().value != null
                        val isFirstTimeOpen =
                            onBoardViewModel.isFirstTimeOpen.collectAsState().value
                        var isPressRegisterFirst by remember { mutableStateOf(false) }

                        NavHost(
                            startDestination =
                            if (isFirstTimeOpen) {
                                Screens.OnBoarding.route
                            } else {
                                if (isSigned) {
                                    Screens.Home.route
                                } else if (isPressRegisterFirst) {
                                    Screens.SignUp.route
                                } else {
                                    Screens.SignIn.route
                                }
                            },
                            navController = navController
                        ) {
                            composable(Screens.OnBoarding.route) {
                                OnBoardScreen(navController)
                            }
                            composable(Screens.GetStarted.route) {
                                GetStartedScreen(
                                    onFinishOnBoarding = {
                                        onBoardViewModel.finishOnBoarding()
                                    },
                                    navigateSignIn = {
                                        navController.navigate(Screens.SignIn.route){
                                            popUpTo(Screens.GetStarted.route){
                                                inclusive = true
                                            }
                                        }
                                    },
                                    navigateSignUp = {
                                        navController.navigate(Screens.SignUp.route){
                                            popUpTo(Screens.GetStarted.route){
                                                inclusive = true
                                            }
                                        }
                                        isPressRegisterFirst = true
                                    }
                                )
                            }
                            composable(Screens.SignIn.route) {
                                val signInData = SignInData(
                                    email = authViewModel.email.collectAsState().value,
                                    password = authViewModel.password.collectAsState().value,
                                    onUpdateEmail = { authViewModel.updateEmail(it) },
                                    onUpdatePassword = { authViewModel.updatePassword(it) },
                                    onTapSignInEmailPassword = {
                                        authViewModel.signInEmailPassword(
                                            callbackOnSuccess = { callbackOnSuccessSignIn() }
                                        )
                                    },
                                    onTapGoogleAuth = {
                                        signInGoogle()
                                    },
                                    enableButton = authViewModel.buttonEnabled.collectAsState().value,
                                    enableGooogleSigninButton = authViewModel.googleSigninEnabled.collectAsState().value,
                                    eventMessage = authViewModel.eventFlow,
                                )
                                SigninScreen(
                                    signInData,
                                    navHostController = navController,
                                )
                            }
                            composable(Screens.SignUp.route) {
                                var openSuccessDialog by rememberSaveable{  mutableStateOf(false) }

                                DisposableEffect(Unit){
                                    onDispose { openSuccessDialog = false }
                                }

                                val data = SignupScreenContentData(
                                    name = registerViewModel.name.collectAsState().value,
                                    email = registerViewModel.email.collectAsState().value,
                                    password = registerViewModel.password.collectAsState().value,
                                    confirmPassword = registerViewModel.confirmPassword.collectAsState().value,
                                    onUpdateName = { registerViewModel.updateName(it) },
                                    onUpdateEmail = { registerViewModel.updateEmail(it) },
                                    onUpdatePassword = { registerViewModel.updatePassword(it) },
                                    onUpdateConfirmPassword = {
                                        registerViewModel.updateConfirmPassword(
                                            it
                                        )
                                    },
                                    errorEventMsg = registerViewModel.errorEventFlow,
                                    onTapSignUpEmailPassword = {
                                        registerViewModel.register(callbackOnSuccess = {
                                            openSuccessDialog = true
                                        })
                                    },
                                    onTapSignUpGoogle = { signInGoogle() },
                                    onSuccessNavigateSignIn = {
                                        openSuccessDialog = false
                                        navController.navigate(Screens.SignIn.route) {
                                            popUpTo(Screens.SignUp.route) {
                                                inclusive = true
                                            }
                                        }
                                    },
                                    openSuccessDialog = openSuccessDialog,
                                    disableButton = !registerViewModel.buttonEnabled.collectAsState().value
                                )
                                SignupScreen(
                                    signupScreenContentData = data,
                                    navHostController = navController
                                )
                            }
                            composable(Screens.Home.route) {
                                val context = LocalContext.current

                                HomeScreen(
                                    waterQualityRealtime = detailDeviceViewModel.waterQuality,
                                    waterStatusRealtime = detailDeviceViewModel.waterStatus,
                                    statusDevice = detailDeviceViewModel.isConnected,
                                    cbOnDeviceConnected = { callbackOnConnected(it) },
                                    devices = homeViewModel.devices,
                                    detailDevice = homeViewModel.detailDeviceLatestInfo,
                                    getDevices = { homeViewModel.getDevices() },
                                    navHostController = navController
                                )
                            }
                            composable(Screens.Notification.route) {
                                NotificationScreen(navController)
                            }
                            composable(Screens.History.route) {
                                StatisticScreen()
                            }
                            composable(Screens.Store.route) {
                                StoreScreen()
                            }
                            composable(Screens.Profile.route) {
                                fun callbackOnSuccessSignout() {
                                    navController.navigate(Screens.SignIn.route) {
                                        popUpTo(Screens.Profile.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                                ProfileScreen(
                                    userData = authViewModel.currentUser.collectAsState().value,
                                    onTapSignOut = {
                                        authViewModel.signOut {
                                            callbackOnSuccessSignout()
                                        }
                                    })
                            }
                            composable(Screens.AddDevice.route) {
                                val context = LocalContext.current
                                var barcode: String? by remember {
                                    mutableStateOf(null)
                                }

                                LaunchedEffect(barcode) {
                                    if (barcode != null)
                                        navController.navigate(
                                            Screens.AddDeviceForm.createRoute(
                                                barcode
                                            )
                                        ) {
                                            popUpTo(Screens.AddDevice.route) {
                                                inclusive = true
                                            }
                                        }
                                }

                                AddScreen(
                                    navHostController = navController,
                                    startScanDevice = {
                                        scanViewModel.startScan(
                                            context = context,
                                            callBackOnSuccess = {
                                                if (!URLUtil.isValidUrl(it)) {
                                                    barcode = it
                                                } else {
                                                    Toast.makeText(
                                                        context,
                                                        "QR code tidak valid!",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            })
                                    })
                            }
                            composable(Screens.AddDeviceForm.route) {
                                val id = it.arguments?.getString("id")

                                val data = AddDeviceScreenData(
                                    name = addDeviceFormViewModel.name.collectAsState().value,
                                    id = addDeviceFormViewModel.id.collectAsState().value,
                                    province = addDeviceFormViewModel.province.collectAsState().value,
                                    city = addDeviceFormViewModel.city.collectAsState().value,
                                    district = addDeviceFormViewModel.district.collectAsState().value,
                                    address = addDeviceFormViewModel.address.collectAsState().value,
                                    waterSource = addDeviceFormViewModel.waterSource.collectAsState().value,
                                    buttonEnabled = addDeviceFormViewModel.buttonEnabled.collectAsState().value,
                                    updateId = { addDeviceFormViewModel.updateId(it) },
                                    updateName = { addDeviceFormViewModel.updateName(it) },
                                    updateProvince = { addDeviceFormViewModel.updateProvince(it) },
                                    updateCity = { addDeviceFormViewModel.updateCity(it) },
                                    updateDistrict = { addDeviceFormViewModel.updateDistrict(it) },
                                    updateAddress = { addDeviceFormViewModel.updateAddress(it) },
                                    updateWaterSource = {
                                        addDeviceFormViewModel.updateWaterSource(
                                            it
                                        )
                                    },
                                    eventMessage = addDeviceFormViewModel.eventFlow,
                                    postDevice = {
                                        addDeviceFormViewModel.postDevice(
                                            callbackOnSuccess = {
                                                navController.navigate(Screens.Home.route) {
                                                    popUpTo(Screens.AddDeviceForm.route) {
                                                        inclusive = true
                                                    }
                                                }
                                                homeViewModel.getDevices()
                                            }
                                        )
                                    },
                                )

                                AddDeviceFormScreen(
                                    provinces = addDeviceViewModel.province.collectAsState().value,
                                    cities = addDeviceViewModel.cities.collectAsState().value,
                                    districs = addDeviceViewModel.districts.collectAsState().value,
                                    getCitiesItem = { addDeviceViewModel.getCities(it) },
                                    getDistrictItem = { provinceId, cityId ->
                                        addDeviceViewModel.getDistricts(
                                            provinceId,
                                            cityId
                                        )
                                    },
                                    idDeviceFromUrl = id,
                                    addDeviceScreenData = data
                                )
                            }
                            composable(Screens.DetailDevice.route) {
                                val id = it.arguments?.getString("id")
                                DetailScreen(
                                    statusDevice = detailDeviceViewModel.isConnected,
                                    sensorData = detailDeviceViewModel.data,
                                    waterQualityHistory = null,
                                    waterQualityRealtime = detailDeviceViewModel.waterQuality
                                )
                            }
                        }
                    }
                }
            }
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

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        BlueSenseTheme {
            Greeting("Android")
        }
    }
}