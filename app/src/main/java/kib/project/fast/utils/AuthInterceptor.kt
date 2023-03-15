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
                .header("device", "${Build.MANUFACTURER} : model-${Build.MODEL}")
                .header("device-os", "sdk_int: ${Build.VERSION.SDK_INT}, version-release: ${Build.VERSION.RELEASE}")
                .header("app-build", "name-${BuildConfig.VERSION_NAME}, code-${BuildConfig.VERSION_CODE}, type-${BuildConfig.BUILD_TYPE}")
                .header("unique-request-id", UUID.randomUUID().toString())
                .build()
        return chain.proceed(chainRequest)
    }

}