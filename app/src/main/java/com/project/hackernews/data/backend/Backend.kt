package com.project.hackernews.data.backend

import android.annotation.SuppressLint
import android.content.Context
import com.project.hackernews.R
import com.project.hackernews.data.model.ApiResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Backend {

    private var context: Context? = null
    private var retrofit: Retrofit? = null
    private fun initInstance(context: Context) {
        this.context = context
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val request = chain.request()
                val newRequest = request.newBuilder()
                    .removeHeader("Content-Type")
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(newRequest)
            })
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.endpoint_url))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    suspend fun loadNews(): ApiResponse {
        return retrofit!!.create(BackendService::class.java).loadNews()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        val instance = Backend()
        fun init(appContext: Context) {
            instance.initInstance(appContext)
        }
    }
}