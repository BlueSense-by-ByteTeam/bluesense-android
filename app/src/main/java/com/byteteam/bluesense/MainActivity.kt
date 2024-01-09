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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.core.helper.Screens
import com.byteteam.bluesense.core.presentation.helper.GoogleSignInClient
import com.byteteam.bluesense.core.presentation.views.device.scan.ScanViewModel
import com.byteteam.bluesense.core.presentation.views.getstarted.GetStartedScreen
import com.byteteam.bluesense.core.presentation.views.home.HomeScreen
import com.byteteam.bluesense.core.presentation.views.notification.NotificationScreen
import com.byteteam.bluesense.core.presentation.views.onboard.OnBoardScreen
import com.byteteam.bluesense.core.presentation.views.onboard.OnBoardViewModel
import com.byteteam.bluesense.core.presentation.views.profile.ProfileScreen
import com.byteteam.bluesense.core.presentation.views.signin.AuthViewModel
import com.byteteam.bluesense.core.presentation.views.signin.SigninScreen
import com.byteteam.bluesense.core.presentation.views.signup.SignupScreen
import com.byteteam.bluesense.core.presentation.views.statistic.StatisticScreen
import com.byteteam.bluesense.core.presentation.views.store.main.StoreScreen
import com.byteteam.bluesense.core.presentation.widgets.BottomBar
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import com.google.android.gms.auth.api.identity.Identity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val scanViewModel: ScanViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val onBoardViewModel: OnBoardViewModel by viewModels()

    @Inject
    lateinit var googleAuthUiClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        installSplashScreen()
        setContent {
            val navController: NavHostController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            BlueSenseTheme {
                Scaffold(
                    bottomBar = {
                        BottomBar(currentRoute = currentRoute ?: "", navHostController = navController)
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
                        val isFirstTimeOpen = onBoardViewModel.isFirstTimeOpen.collectAsState().value

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
                                                navController.navigate(Screens.Home.route) {
                                                    popUpTo(Screens.SignIn.route) {
                                                        inclusive = true
                                                    }
                                                }
    //                                            Log.d("TAG", "onCreate: google sign in result ${signInResult.data}")
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

                                SigninScreen(onTapGoogleAuth = {
                                    signInGoogle()
                                })
                            }
                            composable(Screens.SignUp.route) {
                                SignupScreen()
                            }
                            composable(Screens.Home.route) {
                                HomeScreen(navController)
                            }
                            composable(Screens.Notification.route) {
                                NotificationScreen(navController)
                            }
                            composable(Screens.History.route){
                                StatisticScreen()
                            }
                            composable(Screens.Store.route){
                                StoreScreen()
                            }
                            composable(Screens.Profile.route){
                                ProfileScreen()
                            }
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