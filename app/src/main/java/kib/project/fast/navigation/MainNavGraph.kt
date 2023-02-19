package kib.project.fast.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kib.project.fast.ui.bottom_bar_screens.home.HomeScreen
import kib.project.fast.ui.bottom_bar_screens.settings.SettingsScreen
import kib.project.fast.ui.splash.SplashScreen

@Composable
fun MainNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        route = Graph.MAIN,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navHostController = navHostController)
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen(navHostController = navHostController)
        }
    }
}

fun NavGraphBuilder.splashNavGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.SPLASH,
        startDestination = "SPLASH"
    ) {
        composable(route = "SPLASH") {
            SplashScreen(navHostController = navHostController)
        }
    }
}