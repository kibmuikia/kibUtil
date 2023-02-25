package kib.project.fast.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kib.project.fast.R
import kib.project.fast.navigation.Graph
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreen(navHostController: NavHostController) {
    val viewModel: SplashScreenViewModel = getViewModel()

    LaunchedEffect(key1 = true) {
        val route = if (viewModel.isUserFirstVisit.value) Graph.ON_BOARDING else Graph.MAIN
        delay(2000L)
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
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_meditating_rabbit))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        reverseOnRepeat = true,
        speed = 1.5F,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Image(
        //  painter = painterResource(id = R.drawable.ic_splat),
        //  contentDescription = "Splash Logo"
        // )
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.fillMaxSize(0.8F),
            contentScale = ContentScale.Fit
        )
    }
}

@Preview
@Composable
fun SplashScreenContentPreview() {
    SplashScreenContent()
}