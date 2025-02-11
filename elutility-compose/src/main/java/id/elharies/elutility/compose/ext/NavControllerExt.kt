package id.elharies.elutility.compose.ext

import androidx.navigation.NavHostController

var lastTimeClick = 0L
fun NavHostController.safeNavigateSingleTop(route: String, delay: Long = 500L) {
    val currentTime = System.currentTimeMillis()
    if (currentTime - lastTimeClick > delay) {
        navigate(route) { launchSingleTop = true }
        lastTimeClick = currentTime
    }
}

fun NavHostController.navigateClear(route: String) {
    navigate(route) {
        popUpTo(0) {
            inclusive = true
        }
    }
}