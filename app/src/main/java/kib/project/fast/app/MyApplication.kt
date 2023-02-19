package kib.project.fast.app

import android.app.Application
import androidx.annotation.Nullable
import kib.project.core.di.coreModules
import kib.project.fast.di.appModules
import org.jetbrains.annotations.NotNull
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException
import org.koin.core.logger.Level
import org.koin.core.module.Module
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initKoin()
    }

    private fun initKoin() {
        try {
            startKoin {
                androidLogger(Level.ERROR)
                androidContext(applicationContext)
                val moduleList = mutableListOf<Module>().apply {
                    addAll(coreModules)
                    addAll(appModules)
                }
                modules(moduleList)
            }
        } catch (errorAlreadyStarted: KoinAppAlreadyStartedException) {
            Timber.e(errorAlreadyStarted.localizedMessage)
        } catch (exception: Exception) {
            Timber.e(exception)
        }
    }

    private fun initTimber() =
        Timber.plant(object : Timber.DebugTree() {
            @Nullable
            override fun createStackElementTag(@NotNull element: StackTraceElement): String? {
                return super.createStackElementTag(element) + ":" + element.methodName + ":" + element.lineNumber
            }
        })

}