package id.elutility.core.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object OkHttpFactory {
    fun create(connectTimeout: Long = 30L, readTimeout: Long = 30L, writeTimeout: Long = 30L, interceptors: List<Interceptor> = emptyList()): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(connectTimeout, TimeUnit.SECONDS)
            readTimeout(readTimeout, TimeUnit.SECONDS)
            writeTimeout(writeTimeout, TimeUnit.SECONDS)
            interceptors.forEach {
                addInterceptor(it)
            }
        }.build()
    }
}