@file:OptIn(ExperimentalMaterial3Api::class)

package kib.project.fast.ui.bottom_bar_screens.settings

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kib.project.core.utils.showToast
import kib.project.fast.BuildConfig
import kib.project.fast.R
import kib.project.fast.ui.component.SettingThemeItem
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navHostController: NavHostController) {
    val viewModel: SettingsScreenViewModel = getViewModel()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val themeSettingList = stringArrayResource(id = R.array.theme_settings).toList()
    val themeState = viewModel.themeState.collectAsState().value

    SettingsScreenContent(
        themeSettingList = themeSettingList,
        themeState = themeState,
        onSettingThemeItemClicked = {
            viewModel.setThemeState(it)
        },
        context = context
    )
}

@Composable
fun SettingsScreenContent(
    themeSettingList: List<String>,
    themeState: Int,
    onSettingThemeItemClicked: (Int) -> Unit,
    context: Context,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondary)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.title_settings),
                    color = MaterialTheme.colorScheme.onTertiary
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
            actions = {
                IconButton(onClick = {
                    val message = "v${BuildConfig.VERSION_NAME} :: ${BuildConfig.VERSION_CODE}"
                    showToast(context = context, message = message)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_info),
                        contentDescription = stringResource(id = R.string.ic_info_content_description)
                    )
                }
            }
        )
        LazyColumn(
            contentPadding = PaddingValues(12.dp)
        ) {
            item {
                Text(
                    text = stringResource(id = R.string.title_theme_settings),
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onTertiary,
                )
            }
            itemsIndexed(items = themeSettingList) { index, item ->
                SettingThemeItem(
                    title = item,
                    isEnable = themeState == index
                ) {
                    onSettingThemeItemClicked(index)
                }
            }
        }
    }
}

@Preview
@Composable
fun SettingsScreenContentPreview() {
    SettingsScreenContent(
        themeSettingList = stringArrayResource(id = R.array.theme_settings).toList(),
        themeState = 1,
        onSettingThemeItemClicked = {},
        context = LocalContext.current,
    )
}