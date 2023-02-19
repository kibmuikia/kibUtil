package kib.project.fast.di

import kib.project.fast.main_activity.viewmodels.MainActivityViewModel
import kib.project.fast.ui.bottom_bar_screens.home.HomeScreenViewModel
import kib.project.fast.ui.bottom_bar_screens.settings.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val viewModelModule: Module = module {
    viewModel { MainActivityViewModel(get()) }
    viewModel { SplashScreenViewModel(get()) }
    viewModel { HomeScreenViewModel() }
}

val appModules: List<Module> = listOf(
    viewModelModule,
)