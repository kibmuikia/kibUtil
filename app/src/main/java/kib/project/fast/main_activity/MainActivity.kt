package kib.project.fast.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import kib.project.fast.main_activity.screens.MainActivityScreen
import kib.project.fast.ui.theme.FastTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FastTheme {
                MainActivityScreen()
            }
        }
    }
}
