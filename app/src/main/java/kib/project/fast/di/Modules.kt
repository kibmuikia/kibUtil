package kib.project.fast.di

import kib.project.core.utils.provideChuckerInterceptor
import kib.project.data.api.interfaces.SampleApi
import kib.project.fast.main_activity.viewmodels.MainActivityViewModel
import kib.project.fast.ui.bottom_bar_screens.home.HomeScreenViewModel
import kib.project.fast.ui.bottom_bar_screens.settings.SettingsScreenViewModel
import kib.project.fast.ui.component.viewmodels.AppProgressDialogViewModel
import kib.project.fast.ui.component.viewmodels.ExpandableListViewModel
import kib.project.fast.ui.splash.SplashScreenViewModel
import kib.project.fast.utils.BASEURL
import okhttp3.OkHttpClient
import okhttp3.Protocol
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit

private val viewModelModule: Module = module {
    viewModel { MainActivityViewModel(get()) }
    viewModel { SplashScreenViewModel(get()) }
    viewModel { HomeScreenViewModel() }
    viewModel { SettingsScreenViewModel(get()) }
    viewModel { AppProgressDialogViewModel() }
    viewModel { ExpandableListViewModel() }
}

private val networkingModule: Module = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(provideChuckerInterceptor(androidContext()))
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BASEURL)
            .client(get())
            .build()
    }
}

private val apiModule: Module = module {
    single<SampleApi> { get<Retrofit>().create() }
}

val appModules: List<Module> = listOf(
    viewModelModule,
    networkingModule,
    apiModule
)