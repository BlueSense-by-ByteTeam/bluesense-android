package com.byteteam.bluesense

import android.util.Log
import android.webkit.URLUtil
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.byteteam.bluesense.core.domain.model.DeviceEntity
import com.byteteam.bluesense.core.helper.Screens
import com.byteteam.bluesense.core.helper.Topbars
import com.byteteam.bluesense.core.helper.bottomNavigationItems
import com.byteteam.bluesense.core.presentation.views.device.add.AddScreen
import com.byteteam.bluesense.core.presentation.views.device.add_form.AddDeviceFormScreen
import com.byteteam.bluesense.core.presentation.views.device.add_form.AddDeviceFormViewModel
import com.byteteam.bluesense.core.presentation.views.device.add_form.AddDeviceScreenData
import com.byteteam.bluesense.core.presentation.views.device.add_form.AddDeviceViewModel
import com.byteteam.bluesense.core.presentation.views.device.detail.DetailDeviceScreen
import com.byteteam.bluesense.core.presentation.views.device.detail.DetailDeviceViewModel
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
import com.byteteam.bluesense.core.presentation.views.store.detail.DetailProductScreen
import com.byteteam.bluesense.core.presentation.views.store.main.StoreScreen
import com.byteteam.bluesense.core.presentation.views.store.support_items.SupportItemsScreen
import com.byteteam.bluesense.core.presentation.views.store.water_supplier.WaterSupplierScreen
import com.byteteam.bluesense.core.presentation.widgets.BottomBar

@Composable
fun App(
    currentRoute: String?,
    navController: NavHostController,
    signInGoogle: () -> Unit,
    callbackOnSuccessSignIn: () -> Unit,
    callbackOnConnected: (DeviceEntity) -> Unit,
    scanViewModel: ScanViewModel,
    authViewModel: AuthViewModel,
    onBoardViewModel: OnBoardViewModel,
    registerViewModel: RegisterViewModel,
    addDeviceViewModel: AddDeviceViewModel,
    homeViewModel: HomeViewModel,
    addDeviceFormViewModel: AddDeviceFormViewModel,
    detailDeviceViewModel: DetailDeviceViewModel,

    ) {

    Scaffold(
        topBar = {
            Topbars(
                route = currentRoute ?: "", actions = mapOf(
                    Screens.DetailDevice.route to { detailDeviceViewModel.openDeleteModal() }
                ), navHostController = navController
            )
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
                            navController.navigate(Screens.SignIn.route) {
                                popUpTo(Screens.GetStarted.route) {
                                    inclusive = true
                                }
                            }
                        },
                        navigateSignUp = {
                            navController.navigate(Screens.SignUp.route) {
                                popUpTo(Screens.GetStarted.route) {
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
                    var openSuccessDialog by rememberSaveable { mutableStateOf(false) }

                    DisposableEffect(Unit) {
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
                    StoreScreen(navController)
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

                    DetailDeviceScreen(
                        statusDevice = detailDeviceViewModel.isConnected,
                        sensorData = detailDeviceViewModel.data,
                        closeDeleteModal = { detailDeviceViewModel.closeDeleteModal() },
                        onDeleteDevice = {
                            detailDeviceViewModel.deleteDevice()
                        },
                        isDeleteDialogOpen = detailDeviceViewModel.isDialogDeleteOpen.collectAsState().value,
                        waterQualityHistory = null,
                        waterQualityRealtime = detailDeviceViewModel.waterQuality,
                        waterStatusRealtime = detailDeviceViewModel.waterStatus
                    )
                }
                composable(Screens.WaterSupplierRecommendation.route){
                    WaterSupplierScreen()
                }
                composable(Screens.FilterRecommendation.route){
                    val id = it.arguments?.getString("id")

                    Log.d("navigation", "App: $id")

                    if(id == "{id}"){//default value when no id is given is {id}
                        SupportItemsScreen(navController)
                    }else{
                        DetailProductScreen()
                    }
                }
            }
        }
    }
}