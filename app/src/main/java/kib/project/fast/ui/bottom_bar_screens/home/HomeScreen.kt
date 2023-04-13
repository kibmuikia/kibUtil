@file:OptIn(ExperimentalMaterial3Api::class)

package kib.project.fast.ui.bottom_bar_screens.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kib.project.fast.R
import kib.project.fast.ui.component.MpesaTextMessage
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.getViewModel


@Composable
fun HomeScreen(navHostController: NavHostController) {
    val viewModel: HomeScreenViewModel = getViewModel()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()

    /*LaunchedEffect(Unit) {
        coroutineScope.launch {
            viewModel.sampleFetchMovieGenresListFromApi()
        }
    }*/

    HomeScreenContent(context = context, viewModel = viewModel, coroutineScope = coroutineScope)
}

@Composable
fun HomeScreenContent(
    context: Context,
    viewModel: HomeScreenViewModel,
    coroutineScope: CoroutineScope
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.title_home),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        )

        Spacer(modifier = Modifier.height(12.dp))

        MpesaTextMessage(context = context)
    }
}

@Preview
@Composable
fun HomeScreenContentPreview() {
    HomeScreenContent(
        context = LocalContext.current,
        viewModel = getViewModel(),
        coroutineScope = rememberCoroutineScope()
    )
}