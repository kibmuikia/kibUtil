package kib.project.fast.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kib.project.fast.ui.MainScreen

@ExperimentalMaterial3Api
@Composable
fun RootNavigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = Graph.SPLASH
    ) {
        splashNavGraph(navHostController = navHostController)
        composable(route = Graph.MAIN) {
             MainScreen()
        }
    }
}