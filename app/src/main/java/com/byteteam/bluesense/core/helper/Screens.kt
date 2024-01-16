package com.byteteam.bluesense.core.helper

sealed class Screens(val route: String) {
    object SignIn : Screens(route = "signin")
    object SignUp : Screens(route = "signup")
    object Home : Screens(route = "home")
    object Profile : Screens(route = "profil")
    object AddDevice : Screens(route = "add_device")
    object AddDeviceForm : Screens(route = "add_device_form/{id}") {
        fun createRoute(id: String?) = "add_device_form/$id"
    }

    object GetStartedScan : Screens(route = "get_started_scan")
    object DetailDevice : Screens(route = "detail_device/{id}") {
        fun createRoute(id: String) = "detail_device/$id"
    }

    object History : Screens(route = "history")
    object Store : Screens(route = "store")
    object FilterRecommendation : Screens(route = "filter_recommendation/{id}"){
        fun createRoute(id: String) = "filter_recommendation/$id"
    }
    object DetailFilterDevice : Screens(route = "filter_recommendation/{id}") {
        fun createRoute(id: String) = "filter_recommendation/$id"
    }

    object WaterSupplierRecommendation : Screens(route = "water_supplier_recommendation")
    object Notification : Screens(route = "notification")
    object OnBoarding : Screens(route = "onboarding")
    object GetStarted : Screens(route = "get_started")
    object ResetPassword : Screens(route = "reset_password")
    object SuccessResetPassword: Screens(route = "success_reset_password")
    object Scan : Screens(route = "scan_qr")
}
