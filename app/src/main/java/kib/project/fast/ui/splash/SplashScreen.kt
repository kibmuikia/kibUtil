package kib.project.fast.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kib.project.fast.R
import kib.project.fast.navigation.Graph
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreen(navHostController: NavHostController) {
    val viewModel: SplashScreenViewModel = getViewModel()

    LaunchedEffect(key1 = true) {
        val route = if (viewModel.isUserFirstVisit.value) Graph.ON_BOARDING else Graph.MAIN
        delay(1500L)
        navHostController.navigate(route) {
            popUpTo(Graph.SPLASH) {
                inclusive = true
            }
        }
    }
    SplashScreenContent()
}

@Composable
fun SplashScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Fast",
            fontSize = 60.sp,
            color = MaterialTheme.colorScheme.onSecondary,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_splat),
            contentDescription = "Splash Logo"
        )
    }
}

@Preview
@Composable
fun SplashScreenContentPreview() {
    SplashScreenContent()
}