package kib.project.core.di

import kib.project.core.settings.general.GeneralSettingsManager
import kib.project.core.settings.general.GeneralSettingsManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val settingsModule: Module = module {
    single<GeneralSettingsManager> { GeneralSettingsManagerImpl(androidContext()) }
}

val coreModules: List<Module> = listOf(settingsModule)