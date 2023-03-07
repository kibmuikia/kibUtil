package kib.project.data.di

import kib.project.data.database.AppDatabase
import kib.project.data.database.repositories.UserRepository
import kib.project.data.database.repositories.UserRepositoryImpl
import kib.project.data.database.usecases.UserUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val dbModule: Module = module {
    single { AppDatabase.create(androidContext()) }
}

private val daoModule: Module = module {
    single { get<AppDatabase>().userDao() }
}

private val repositoryModule: Module = module {
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
}

private val useCaseModule: Module = module {
    single { UserUseCase(get()) }
}

val dataModule: List<Module> = listOf(
    dbModule,
    daoModule,
    repositoryModule,
    useCaseModule
)