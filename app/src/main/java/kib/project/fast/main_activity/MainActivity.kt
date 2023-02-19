package kib.project.fast.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import kib.project.fast.main_activity.screens.MainActivityScreen
import kib.project.fast.main_activity.viewmodels.MainActivityViewModel
import kib.project.fast.ui.theme.FastTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FastTheme {
                MainActivityScreen(
                    viewModel = mainActivityViewModel
                )
            }
        }
    }
}
