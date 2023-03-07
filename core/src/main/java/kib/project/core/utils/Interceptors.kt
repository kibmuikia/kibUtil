package kib.project.core.utils

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.Interceptor

fun provideChuckerInterceptor(context: Context): Interceptor =
    ChuckerInterceptor.Builder(context)
        .collector(ChuckerCollector(context)).maxContentLength(250000L)
        .redactHeaders(emptySet()).alwaysReadResponseBody(true).build()