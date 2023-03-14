package kib.project.fast.utils

import android.os.Build
import kib.project.fast.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.util.UUID

class AuthInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var chainRequest = chain.request()
        chainRequest =
            chainRequest.newBuilder()
                .header("Authorization", "Bearer ${BuildConfig.API_KEY}")
                .header("device", Build.MANUFACTURER + " : " + Build.MODEL)
                .header("device-os", Build.VERSION.SDK_INT.toString() + " " + Build.VERSION.RELEASE)
                .header("unique-request-id", UUID.randomUUID().toString())
                .build()
        return chain.proceed(chainRequest)
    }

}