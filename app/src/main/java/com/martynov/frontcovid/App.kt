package com.martynov.frontcovid

import android.app.Application
import android.content.Context
import com.martynov.frontcovid.api.Api
import com.martynov.frontcovid.repository.NetworkRepository
import com.martynov.frontcovid.repository.Repository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {
 companion object{
     lateinit var repository: Repository
         private set
 }

    override fun onCreate() {
        super.onCreate()
        val httpLoggerInterceptor = HttpLoggingInterceptor()
        httpLoggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggerInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(Api::class.java)
        repository = NetworkRepository(api)



    }
}