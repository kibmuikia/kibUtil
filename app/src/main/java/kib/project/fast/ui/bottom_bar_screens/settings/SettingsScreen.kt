@file:OptIn(ExperimentalMaterial3Api::class)

package kib.project.fast.ui.bottom_bar_screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import kib.project.fast.R
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navHostController: NavHostController) {
    val viewModel: SettingsScreenViewModel = getViewModel()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
}

@Composable
fun SettingsScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onSecondaryContainer)
    ) {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.title_settings))
            },
            modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
        )
    }
}

@Preview
@Composable
fun SettingsScreenContentPreview() {
    SettingsScreenContent()
}