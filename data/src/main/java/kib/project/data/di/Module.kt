package kib.project.data.di

import kib.project.data.api.interfaces.SampleApi
import kib.project.data.database.AppDatabase
import kib.project.data.database.repositories.MpesaSmsRepository
import kib.project.data.database.repositories.MpesaSmsRespositoryImpl
import kib.project.data.database.repositories.SampleRepository
import kib.project.data.database.repositories.SampleRepositoryImpl
import kib.project.data.database.repositories.UserRepository
import kib.project.data.database.repositories.UserRepositoryImpl
import kib.project.data.database.usecases.UserUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val dbModule: Module = module {
    single { AppDatabase.create(androidContext()) }
}

private val daoModule: Module = module {
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().appSmsMessageDao() }
    single { get<AppDatabase>().mpesaSmsDao() }
}

private val repositoryModule: Module = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<SampleRepository> { SampleRepositoryImpl(get()) }
    single<MpesaSmsRepository> { MpesaSmsRespositoryImpl(get()) }
}

private val useCaseModule: Module = module {
    single { UserUseCase(get()) }
}

private val apiModule: Module = module {
    single<SampleApi> { get<Retrofit>().create() }
}

val dataModule: List<Module> = listOf(
    dbModule,
    daoModule,
    repositoryModule,
    useCaseModule,
    apiModule
)