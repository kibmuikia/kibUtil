package kib.project.fast.di

import kib.project.fast.main_activity.viewmodels.MainActivityViewModel
import kib.project.fast.ui.bottom_bar_screens.home.HomeScreenViewModel
import kib.project.fast.ui.bottom_bar_screens.settings.SettingsScreenViewModel
import kib.project.fast.ui.splash.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val viewModelModule: Module = module {
    viewModel { MainActivityViewModel(get()) }
    viewModel { SplashScreenViewModel(get()) }
    viewModel { HomeScreenViewModel() }
    viewModel { SettingsScreenViewModel(get()) }
}

val appModules: List<Module> = listOf(
    viewModelModule,
)