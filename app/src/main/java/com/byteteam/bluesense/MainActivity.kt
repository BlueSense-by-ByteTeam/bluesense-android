package com.byteteam.bluesense

import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.byteteam.bluesense.core.helper.MqttHandler
import com.byteteam.bluesense.core.helper.Screens
import com.byteteam.bluesense.core.helper.Topbars
import com.byteteam.bluesense.core.helper.bottomNavigationItems
import com.byteteam.bluesense.core.presentation.helper.GoogleSignInClient
import com.byteteam.bluesense.core.presentation.views.device.add.AddScreen
import com.byteteam.bluesense.core.presentation.views.device.add_form.AddDeviceFormScreen
import com.byteteam.bluesense.core.presentation.views.device.add_form.AddDeviceViewModel
import com.byteteam.bluesense.core.presentation.views.device.scan.ScanViewModel
import com.byteteam.bluesense.core.presentation.views.getstarted.GetStartedScreen
import com.byteteam.bluesense.core.presentation.views.home.HomeScreen
import com.byteteam.bluesense.core.presentation.views.notification.NotificationScreen
import com.byteteam.bluesense.core.presentation.views.onboard.OnBoardScreen
import com.byteteam.bluesense.core.presentation.views.onboard.OnBoardViewModel
import com.byteteam.bluesense.core.presentation.views.profile.ProfileScreen
import com.byteteam.bluesense.core.presentation.views.signin.AuthViewModel
import com.byteteam.bluesense.core.presentation.views.signin.SigninScreen
import com.byteteam.bluesense.core.presentation.views.signup.RegisterViewModel
import com.byteteam.bluesense.core.presentation.views.signup.SignupScreen
import com.byteteam.bluesense.core.presentation.views.signup.widgets.SignupScreenContentData
import com.byteteam.bluesense.core.presentation.views.statistic.StatisticScreen
import com.byteteam.bluesense.core.presentation.views.store.main.StoreScreen
import com.byteteam.bluesense.core.presentation.widgets.BottomBar
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val scanViewModel: ScanViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val onBoardViewModel: OnBoardViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val addDeviceViewModel: AddDeviceViewModel by viewModels()

    @Inject
    lateinit var mqttHandlerClient: MqttHandler

    @Inject
    lateinit var googleAuthUiClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        installSplashScreen()

        val mqttConnectOptions = MqttConnectOptions()
//        val mqttAndroidClient = MqttHandler()
        val persistence = MemoryPersistence()

        mqttConnectOptions.apply {
            this.userName = BuildConfig.MQTT_USERNAME
            this.password = BuildConfig.MQTT_PASSWORD.toCharArray()
        }

//        val scanner = GmsBarcodeScanning.getClient(this)
//
//        scanner.startScan()
//            .addOnSuccessListener { barcode ->
//                Log.d("TAG", "qr scan: ${barcode.rawValue}")
////                    callBackOnSuccess()
//            }
//            .addOnCanceledListener {
//                // Task canceled
//                Log.d("TAG", "qr scan canceled")
//            }
//            .addOnFailureListener { e ->
//                // Task failed with an exception
//                Log.d("TAG", "qr scan fail: ${e.message}")
//            }


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
                            authViewModel.getCurrentUser()
                            callbackOnSuccessSignIn()
                        }
                    }
                }
            )

            fun signInGoogle() = lifecycleScope.launch {
                val signInIntentSender = googleAuthUiClient.signIn()
                launcher.launch(
                    IntentSenderRequest.Builder(
                        signInIntentSender ?: return@launch
                    ).build()
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

                        NavHost(
                            startDestination =
                            if (isFirstTimeOpen) {
                                Screens.OnBoarding.route
                            } else {
                                if (isSigned) Screens.Home.route else Screens.SignIn.route
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
                                    navController
                                )
                            }
                            composable(Screens.SignIn.route) {

                                SigninScreen(
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
                                    navHostController = navController,
                                )
                            }
                            composable(Screens.SignUp.route) {
                                val data = SignupScreenContentData(
                                    name = registerViewModel.name.collectAsState().value,
                                    email = registerViewModel.email.collectAsState().value,
                                    password = registerViewModel.password.collectAsState().value,
                                    confirmPassword = registerViewModel.confirmPassword.collectAsState().value,
                                    onUpdateName = { registerViewModel.updateName(it) },
                                    onUpdateEmail = { registerViewModel.updateEmail(it) },
                                    onUpdatePassword = { registerViewModel.updatePassword(it) },
                                    onUpdateConfirmPassword = { registerViewModel.updateConfirmPassword(it) },
                                    onTapSignUpEmailPassword = {  registerViewModel.register(callbackOnSuccess = {
                                        authViewModel.getCurrentUser()
                                        navController.navigate(Screens.Home.route){
                                            popUpTo(Screens.SignUp.route){
                                                inclusive=true
                                            }
                                        }
                                    })  },
                                    onTapSignUpGoogle = { signInGoogle() },
                                    disableButton = !registerViewModel.buttonEnabled.collectAsState().value
                                )
                                SignupScreen(signupScreenContentData = data)
                            }
                            composable(Screens.Home.route) {
                                HomeScreen(
                                    memoryPersistence = persistence,
                                    mqttConnectOptions = mqttConnectOptions,
                                    mqttAndroidClient = mqttHandlerClient,
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
                                var barcode: String? by remember{
                                    mutableStateOf(null)
                                }

                                LaunchedEffect(barcode){
                                    if(barcode != null)
                                        navController.navigate(Screens.AddDeviceForm.route){
                                            popUpTo(Screens.AddDevice.route){
                                                inclusive = true
                                            }
                                        }
                                }

                                AddScreen(
                                    navHostController = navController,
                                    startScanDevice = { scanViewModel.startScan(context = context, callBackOnSuccess = {
                                       barcode = it
                                    }) })
                            }
                            composable(Screens.AddDeviceForm.route){
                                AddDeviceFormScreen(
                                    provinces = addDeviceViewModel.province.collectAsState().value,
                                    cities = addDeviceViewModel.cities.collectAsState().value,
                                    districs = addDeviceViewModel.districts.collectAsState().value,
                                    getCitiesItem = { addDeviceViewModel.getCities(it) },
                                    getDistrictItem = { provinceId, cityId-> addDeviceViewModel.getDistricts(provinceId, cityId) },
                                )
                            }
                        }
                    }
                }
            }
        }
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