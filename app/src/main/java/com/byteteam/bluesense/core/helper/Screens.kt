package com.byteteam.bluesense.core.helper

sealed class Screens(val route: String) {
    object SignIn : Screens(route = "signin")
    object SignUp : Screens(route = "signup")
    object Home : Screens(route = "home")
    object Profile : Screens(route = "profil")
    object AddDevice : Screens(route = "add_device")
    object GetStartedScan : Screens(route = "get_started_scan")
    object DetailDevice : Screens(route = "detail_device")
    object History : Screens(route = "history")
    object Store : Screens(route = "store")
    object FilterRecommendation : Screens(route = "filter_recommendation")
    object DetailFilterDevice : Screens(route = "filter_recommendation/{id}"){
        fun createRoute(id: String) = "filter_recommendation/$id"
    }
    object WaterSupplier : Screens(route = "water_supplier")
    object Notification : Screens(route = "notification")
    object OnBoarding : Screens(route = "onboarding")
    object GetStarted : Screens(route = "get_started")
}
