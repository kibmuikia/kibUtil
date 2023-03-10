package kib.project.fast.navigation

import kib.project.fast.R

sealed class BottomBarScreen(
    val route: String,
    val label: String,
    val icon: Int,
) {
    object Home : BottomBarScreen(
        route = "home",
        label = "Home",
        icon = R.drawable.ic_home
    )

    object Settings : BottomBarScreen(
        route = "settings",
        label = "Settings",
        icon = R.drawable.ic_settings
    )

    object About : BottomBarScreen(
        route = "about",
        label = "About",
        icon = R.drawable.ic_info
    )
}
