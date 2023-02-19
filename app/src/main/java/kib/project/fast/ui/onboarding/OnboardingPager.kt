@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package kib.project.fast.ui.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kib.project.fast.R
import kib.project.fast.navigation.Graph
import kib.project.fast.ui.bottom_bar_screens.settings.SplashScreenViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingPager(navHostController: NavHostController) {
    val viewModel: SplashScreenViewModel = getViewModel()
    val pagerState = rememberPagerState(0)
    val map = mapOf(
        "One" to stringResource(id = R.string.generic_sentence),
        "Two" to stringResource(id = R.string.generic_sentence),
        "Three" to stringResource(id = R.string.generic_sentence)
    ).toList()
    val images = listOf(
        R.drawable.ic_baseline_settings_24,
        R.drawable.ic_baseline_settings_24,
        R.drawable.ic_baseline_settings_24,
    )

    OnboardingPagerContent(
        pagerState = pagerState,
        pageData = map,
        pageImages = images,
        onClickGetStarted = {
            viewModel.setIsUserFirstVisit(false)
            navHostController.navigate(Graph.MAIN) {
                popUpTo(route = Graph.ON_BOARDING) { inclusive = true }
            }
        })
}

@Composable
fun OnboardingPagerContent(
    pagerState: PagerState,
    pageData: List<Pair<String, String>>,
    pageImages: List<Int>,
    onClickGetStarted: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        HorizontalPager(
            pageCount = 3,
            state = pagerState,
            contentPadding = PaddingValues(5.dp),
            modifier = Modifier
                .fillMaxSize()
                .weight(2.5f)
        ) { index ->
            Pager(
                title = pageData[index].first,
                description = pageData[index].second,
                image = pageImages[index]
            )
        }
        OnboardingFooter(pagerState = pagerState, onClickGetStarted = { onClickGetStarted() })
    }
}

@Preview
@Composable
fun OnboardingPagerContentPreview() {
    OnboardingPagerContent(
        pagerState = rememberPagerState(0),
        pageData = mapOf(
            "One" to "Test One",
            "Two" to "Test Two",
        ).toList(),
        pageImages = listOf(
            R.drawable.ic_baseline_settings_24,
            R.drawable.ic_baseline_settings_24,
            R.drawable.ic_baseline_settings_24,
        ),
        onClickGetStarted = {}
    )
}

@Composable
fun Pager(
    title: String,
    description: String,
    image: Int
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            painter = painterResource(id = image),
            contentDescription = "$title-image"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            text = title,
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSecondary,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            text = description,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSecondary,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PagerPreview() {
    Pager(
        title = "Title",
        description = "The Description",
        image = R.drawable.ic_baseline_settings_24
    )
}

@Composable
fun OnboardingFooter(pagerState: PagerState, onClickGetStarted: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp, top = 12.dp),
        contentAlignment = Center
    ) {
        ElevatedButton(
            onClick = {
                if (pagerState.currentPage != 2) {
                    coroutineScope.launch {
                        pagerState.scrollToPage(3)
                    }
                } else {
                    onClickGetStarted()
                }
            },
            modifier = Modifier.size(height = 55.dp, width = 280.dp),
            colors = ButtonDefaults.elevatedButtonColors(containerColor = MaterialTheme.colorScheme.onSecondary)
        ) {
            Text(
                text = if (pagerState.currentPage != 2) {
                    "Skip"
                } else {
                    stringResource(id = R.string.text_get_started)
                },
                fontSize = 20.sp,
                color = Color.Black
            )
        }
    }
}

@Preview
@Composable
fun OnboardingFooterPreview() {
    OnboardingFooter(pagerState = rememberPagerState(0), onClickGetStarted = {})
}