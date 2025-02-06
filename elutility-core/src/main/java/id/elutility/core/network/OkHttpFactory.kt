package id.elutility.core.network

import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object OkHttpFactory {

    private fun builder(
        connectTimeout: Long = 30L,
        readTimeout: Long = 30L,
        writeTimeout: Long = 30L
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            connectTimeout(connectTimeout, TimeUnit.SECONDS)
            readTimeout(readTimeout, TimeUnit.SECONDS)
            writeTimeout(writeTimeout, TimeUnit.SECONDS)
        }
    }

    fun create(
        connectTimeout: Long = 30L,
        readTimeout: Long = 30L,
        writeTimeout: Long = 30L,
        interceptors: List<Interceptor> = emptyList()
    ): OkHttpClient {
        return builder(connectTimeout, readTimeout, writeTimeout).apply {
            interceptors.forEach {
                addInterceptor(it)
            }
        }.build()
    }

    fun create(
        connectTimeout: Long = 30L,
        readTimeout: Long = 30L,
        writeTimeout: Long = 30L,
        interceptors: List<Interceptor> = emptyList(),
        pinnedDomains: Map<String, List<String>> = emptyMap()
    ): OkHttpClient {
        return builder(connectTimeout, readTimeout, writeTimeout).apply {
            interceptors.forEach {
                addInterceptor(it)
            }
            if (pinnedDomains.isNotEmpty()) {
                val certificatePinner = CertificatePinner.Builder().apply {
                    pinnedDomains.forEach { (key, hashes) ->
                        hashes.forEach { pin ->
                            add(key, pin)
                        }
                    }
                }.build()
                certificatePinner(certificatePinner)
            }
        }.build()
    }
}