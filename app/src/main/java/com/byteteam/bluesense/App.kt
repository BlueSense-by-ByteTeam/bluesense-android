package com.byteteam.bluesense

import DetailWaterSupplier
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.ChatEntity
import com.byteteam.bluesense.core.domain.model.DeviceEntity
import com.byteteam.bluesense.core.helper.Screens
import com.byteteam.bluesense.core.helper.Topbars
import com.byteteam.bluesense.core.helper.bottomNavigationItems
import com.byteteam.bluesense.core.presentation.views.chatbot.ChatBotScreen
import com.byteteam.bluesense.core.presentation.views.chatbot.ChatBotViewModel
import com.byteteam.bluesense.core.presentation.views.device.modules.add.AddScreen
import com.byteteam.bluesense.core.presentation.views.device.modules.add_form.AddDeviceFormScreen
import com.byteteam.bluesense.core.presentation.views.device.modules.add_form.AddDeviceFormViewModel
import com.byteteam.bluesense.core.presentation.views.device.modules.add_form.AddDeviceScreenData
import com.byteteam.bluesense.core.presentation.views.device.modules.add_form.AddDeviceViewModel
import com.byteteam.bluesense.core.presentation.views.device.modules.detail.DetailDeviceScreen
import com.byteteam.bluesense.core.presentation.views.device.modules.detail.DetailDeviceViewModel
import com.byteteam.bluesense.core.presentation.views.getstarted.GetStartedScreen
import com.byteteam.bluesense.core.presentation.views.home.HomeScreen
import com.byteteam.bluesense.core.presentation.views.home.HomeViewModel
import com.byteteam.bluesense.core.presentation.views.notification.NotificationScreen
import com.byteteam.bluesense.core.presentation.views.notification.NotificationViewModel
import com.byteteam.bluesense.core.presentation.views.onboard.OnBoardScreen
import com.byteteam.bluesense.core.presentation.views.onboard.OnBoardViewModel
import com.byteteam.bluesense.core.presentation.views.profile.ProfileScreen
import com.byteteam.bluesense.core.presentation.views.reset_password.ResetPasswordScreen
import com.byteteam.bluesense.core.presentation.views.reset_password.ResetPasswordViewModel
import com.byteteam.bluesense.core.presentation.views.scanner.ScannerPage
import com.byteteam.bluesense.core.presentation.views.signin.AuthViewModel
import com.byteteam.bluesense.core.presentation.views.signin.SignInData
import com.byteteam.bluesense.core.presentation.views.signin.SigninScreen
import com.byteteam.bluesense.core.presentation.views.signup.RegisterViewModel
import com.byteteam.bluesense.core.presentation.views.signup.SignupScreen
import com.byteteam.bluesense.core.presentation.views.signup.widgets.SignupScreenContentData
import com.byteteam.bluesense.core.presentation.views.statistic.StatisticHistoryViewModel
import com.byteteam.bluesense.core.presentation.views.statistic.StatisticScreen
import com.byteteam.bluesense.core.presentation.views.store.StoreViewModel
import com.byteteam.bluesense.core.presentation.views.store.detail.DetailProductScreen
import com.byteteam.bluesense.core.presentation.views.store.main.StoreScreen
import com.byteteam.bluesense.core.presentation.views.store.support_items.SupportItemsScreen
import com.byteteam.bluesense.core.presentation.views.store.water_supplier.WaterSupplierScreen
import com.byteteam.bluesense.core.presentation.views.success_reset_pass.SuccessResetPassScreen
import com.byteteam.bluesense.core.presentation.widgets.BottomBar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun App(
    currentRoute: String?,
    navController: NavHostController,
    signInGoogle: () -> Unit,
    callbackOnSuccessSignIn: () -> Unit,
    callbackOnConnected: (DeviceEntity) -> Unit,
    callbackOnDisconnectDevice: (String) -> Unit,
    authViewModel: AuthViewModel,
    onBoardViewModel: OnBoardViewModel,
    registerViewModel: RegisterViewModel,
    addDeviceViewModel: AddDeviceViewModel,
    homeViewModel: HomeViewModel,
    addDeviceFormViewModel: AddDeviceFormViewModel,
    detailDeviceViewModel: DetailDeviceViewModel,
    storeViewModel: StoreViewModel,
    resetPasswordViewModel: ResetPasswordViewModel,
    historyViewModel: StatisticHistoryViewModel,
    notificationViewModel: NotificationViewModel,
    chatBotViewModel: ChatBotViewModel,
    cbSuccessAddDevice: () -> Unit = {}
) {
    var mqttTopic by remember { mutableStateOf("") }
    val context = LocalContext.current
    Scaffold(topBar = {
        Topbars(
            route = currentRoute ?: "",
            actions = mapOf(
                Screens.DetailDevice.route to { detailDeviceViewModel.openDeleteModal() },
                Screens.Notification.route to { notificationViewModel.openDeleteModal() }),
            navHostController = navController
        )
    }, bottomBar = {
        if (bottomNavigationItems.map { it.route }.contains(currentRoute)) BottomBar(
            currentRoute = currentRoute ?: "", navHostController = navController
        )
    }) {
        Surface(
            modifier = Modifier
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            val isSigned = authViewModel.currentUser.collectAsState().value != null
            val isFirstTimeOpen = onBoardViewModel.isFirstTimeOpen.collectAsState().value
            var isPressRegisterFirst by remember { mutableStateOf(false) }

            NavHost(
                startDestination = if (isFirstTimeOpen) {
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
                    GetStartedScreen(onFinishOnBoarding = {
                        onBoardViewModel.finishOnBoarding()
                    }, navigateSignIn = {
                        navController.navigate(Screens.SignIn.route) {
                            popUpTo(Screens.GetStarted.route) {
                                inclusive = true
                            }
                        }
                    }, navigateSignUp = {
                        navController.navigate(Screens.SignUp.route) {
                            popUpTo(Screens.GetStarted.route) {
                                inclusive = true
                            }
                        }
                        isPressRegisterFirst = true
                    })
                }
                composable(Screens.SignIn.route) {
                    val signInData = SignInData(
                        email = authViewModel.email.collectAsState().value,
                        password = authViewModel.password.collectAsState().value,
                        onUpdateEmail = { authViewModel.updateEmail(it) },
                        onUpdatePassword = { authViewModel.updatePassword(it) },
                        onResetState = { authViewModel.resetState() },
                        onTapSignInEmailPassword = {
                            authViewModel.signInEmailPassword(callbackOnSuccess = {
                                homeViewModel.getDevices()
                                storeViewModel.getWaterFilters()
                                storeViewModel.getWaterSuppliers()
                                storeViewModel.getFeaturedWaterFilters()
                                callbackOnSuccessSignIn()
                            })
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
                        onResetState = { registerViewModel.resetState() },
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
                        signupScreenContentData = data, navHostController = navController
                    )
                }
                composable(Screens.Home.route) {
                    HomeScreen(
                        waterQualityRealtime = detailDeviceViewModel.waterQuality,
                        waterStatusRealtime = detailDeviceViewModel.waterStatus,
                        statusDevice = detailDeviceViewModel.isConnected,
                        cbOnDeviceConnected = {
                            callbackOnConnected(it)
                            mqttTopic = it.mqttTopic
                        },
                        devices = homeViewModel.devices,
                        detailDevice = homeViewModel.detailDeviceLatestInfo,
                        getDevices = { homeViewModel.getDevices() },
                        navHostController = navController
                    )
                }
                composable(Screens.Notification.route) {
                    NotificationScreen(
                        notificationState = notificationViewModel.notifications,
                        getNotification = { notificationViewModel.getNotificationsCurrentUser() },
                        deleteNotification = { notificationViewModel.deleteNotificationsCurrentUser() },
                        closeDeleteModal = { notificationViewModel.closeDeleteModal() },
                        isModalOpen = notificationViewModel.isDeleteModalOpen.collectAsState().value,
                        onDelete = notificationViewModel.onDelete.collectAsState().value,
                    )
                }
                composable(Screens.History.route) {
                    StatisticScreen(
                        getHistoryToday = { historyViewModel.getTodayHistory() },
                        getHistoryWeek = { historyViewModel.getWeekHistory() },
                        getHistoryMonth = { historyViewModel.getMonthHistory() },
                        getHistoryYear = { historyViewModel.getYearHistory() },
                        historyState = historyViewModel.historyLogs
                    )
                }
                composable(Screens.Store.route) {
                    StoreScreen(
                        featuredWaterFilterState = storeViewModel.featuredWaterFilter,
                        waterFiltersState = storeViewModel.waterFilters,
                        waterSuppliersState = storeViewModel.waterSuppliers,
                        getFeaturedWaterFilter = { storeViewModel.getFeaturedWaterFilters() },
                        getWaterFilter = { storeViewModel.getWaterFilters() },
                        getWaterSupplier = { storeViewModel.getWaterSuppliers() },
                        navHostController = navController
                    )
                }
                composable(Screens.Profile.route) {
                    fun callbackOnSuccessSignout() {
                        navController.navigate(Screens.SignIn.route) {
                            popUpTo(Screens.Home.route) {
                                inclusive = true
                            }
                        }
                    }
                    ProfileScreen(userData = authViewModel.currentUser.collectAsState().value,
                        onTapSignOut = {
                            authViewModel.signOut {
                                callbackOnSuccessSignout()
                            }
                        })
                }
                composable(Screens.AddDevice.route) {
                    var barcode: String? by remember {
                        mutableStateOf(null)
                    }

                    LaunchedEffect(barcode) {
                        if (barcode != null) navController.navigate(
                            Screens.AddDeviceForm.createRoute(
                                barcode
                            )
                        ) {
                            popUpTo(Screens.AddDevice.route) {
                                inclusive = true
                            }
                        }
                    }

                    AddScreen(navHostController = navController, navigateScanScreen = {
                        navController.navigate(Screens.Scan.route)
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
                            addDeviceFormViewModel.postDevice(callbackOnSuccess = {
                                homeViewModel.getDevices()
                                cbSuccessAddDevice()
                                navController.navigate(Screens.Home.route) {
                                    popUpTo(Screens.AddDeviceForm.route) {
                                        inclusive = true
                                    }
                                }
                            })
                        },
                    )

                    AddDeviceFormScreen(
                        resetFormState = { addDeviceFormViewModel.resetState() },
                        provinces = addDeviceViewModel.province.collectAsState().value,
                        cities = addDeviceViewModel.cities.collectAsState().value,
                        districs = addDeviceViewModel.districts.collectAsState().value,
                        getCitiesItem = { addDeviceViewModel.getCities(it) },
                        getDistrictItem = { provinceId, cityId ->
                            addDeviceViewModel.getDistricts(
                                provinceId, cityId
                            )
                        },
                        idDeviceFromUrl = id,
                        addDeviceScreenData = data
                    )
                }
                composable(Screens.DetailDevice.route) {
                    val id = it.arguments?.getString("id")
                    val mqttTopic = it.arguments?.getString("mqttTopic")
                    DetailDeviceScreen(
                        statusDevice = detailDeviceViewModel.isConnected,
                        sensorData = detailDeviceViewModel.data,
                        closeDeleteModal = { detailDeviceViewModel.closeDeleteModal() },
                        isOnDelete = detailDeviceViewModel.onDelete.collectAsState().value,
                        isDeleteDialogOpen = detailDeviceViewModel.isDialogDeleteOpen.collectAsState().value,
                        waterQualityHistory = detailDeviceViewModel.waterQuality.collectAsState().value,
                        waterQualityRealtime = detailDeviceViewModel.waterQuality,
                        waterStatusRealtime = detailDeviceViewModel.waterStatus,
                        onDeleteDevice = {
                            detailDeviceViewModel.deleteDevice(id ?: "", callbackOnSuccess = {
                                callbackOnDisconnectDevice(mqttTopic ?: "-")
                                navController.navigate(Screens.Home.route) {
                                    popUpTo(Screens.DetailDevice.createRoute(id ?: "")) {
                                        inclusive = true
                                    }
                                }
                                homeViewModel.getDevices()//reset by get new data
                            })
                        },
                    )
                }
                composable(Screens.WaterSupplierRecommendation.route) {
                    val id = it.arguments?.getString("id")
                    if (id == "{id}") {
                        WaterSupplierScreen(waterSuppliersState = storeViewModel.waterSuppliers,
                            getWaterSupplier = { storeViewModel.getWaterSuppliers() })
                    } else {
                        DetailWaterSupplier(
                            getWaterSupplierDetail = { storeViewModel.getDetailSupplier(id ?: "") },
                            waterSupplierState = storeViewModel.waterSupplierDetail,
                        )
                    }
                }
                composable(Screens.FilterRecommendation.route) {
                    val id = it.arguments?.getString("id")
                    if (id == "{id}") {
                        SupportItemsScreen(
                            waterFiltersState = storeViewModel.waterFilters,
                            getWaterFilters = { storeViewModel.getWaterFilters() },
                        )
                    } else {
                        DetailProductScreen(
                            getFeaturedWaterFilter = { storeViewModel.getFeaturedWaterFilters() },
                            waterFilterState = storeViewModel.featuredWaterFilter
                        )
                    }
                }
                composable(Screens.ResetPassword.route) {
                    ResetPasswordScreen(buttonEnabled = resetPasswordViewModel.buttonEnabled.collectAsState().value,
                        inputEmail = resetPasswordViewModel.inputEmail.collectAsState().value,
                        updateEmail = { resetPasswordViewModel.updateEmail(it) },
                        onResetState = { resetPasswordViewModel.resetState() },
                        eventMessage = resetPasswordViewModel.eventFlow,
                        sendResetEmail = {
                            resetPasswordViewModel.sendEmailResetPassword(callbackSuccess = {
                                navController.navigate(Screens.SuccessResetPassword.route) {
                                    popUpTo(Screens.ResetPassword.route) {
                                        inclusive = true
                                    }
                                }
                            })
                        })
                }
                composable(Screens.SuccessResetPassword.route) {
                    SuccessResetPassScreen(navHostController = navController)
                }
                composable(Screens.Scan.route) {
                    ScannerPage(
                        navHostController = navController,
                        cbOnScanSuccess = {
                            navController.navigate(Screens.AddDeviceForm.createRoute(it)) {
                                popUpTo(Screens.Scan.route) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
                composable(Screens.ChatBot.route) {
                    ChatBotScreen(
                        onCopyResult = { text ->
                            chatBotViewModel.copyToClipBoard(text, onSuccess = {
                                Toast.makeText(
                                    context,
                                    "Berhasil menyalin text chatbot🎉",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }, onFail = { error ->
                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                            })
                        },
                        onRetryPrompt = { chatIndex -> chatBotViewModel.retryPromptChatAt(chatIndex) },
                        onPostNewPrompt = { chatBotViewModel.postNewPrompt(it) },
                        chats = chatBotViewModel.chats,
                        newChatUiState = chatBotViewModel.newChat
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
val loadingNewChatUiState: StateFlow<Resource<ChatEntity>> = MutableStateFlow(
    Resource.Success(
        ChatEntity(
            isMe = true,
            text = "",
            created = SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().time)
        )
    )
)


@RequiresApi(Build.VERSION_CODES.O)
val withDataUiState: StateFlow<List<ChatEntity>> = MutableStateFlow(
    listOf(
        ChatEntity(
            text = "ini chatku",
            created = SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().time),
            isMe = true
        ),
        ChatEntity(
            text = "ini balasan chatku",
            created = SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().time),
            isMe = false
        ),
        ChatEntity(
            text = """  
# Sample  
* Markdown  panjang banget
* [Link](https://example.com)  
![Image](https://example.com/img.png)  
<a href="https://www.google.com/">Google</a>  
""", created = SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().time), isMe = false
        ),
    )
)