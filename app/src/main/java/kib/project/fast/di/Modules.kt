package kib.project.fast.di

import kib.project.fast.main_activity.viewmodels.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val viewModelModule: Module = module {
    viewModel { MainActivityViewModel(get()) }
}

val appModules: List<Module> = listOf(
    viewModelModule,
)