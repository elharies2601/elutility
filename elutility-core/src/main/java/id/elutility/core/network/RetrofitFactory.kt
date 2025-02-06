package id.elutility.core.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    fun createBuilder(baseUrl: String, okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
    }

    inline fun <reified T> createService(baseUrl: String, okHttpClient: OkHttpClient): T {
        return createBuilder(baseUrl, okHttpClient).build().create(T::class.java)
    }

    inline fun <reified T> createReactiveService(baseUrl: String, okHttpClient: OkHttpClient): T {
        return createBuilder(baseUrl, okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(T::class.java)
    }
}