package com.martynov.frontcovid

import android.app.Application
import com.martynov.frontcovid.api.Api
import com.martynov.frontcovid.repository.NetworkRepository
import com.martynov.frontcovid.repository.Repository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {
 companion object{
     lateinit var repository: Repository
         private set
 }

    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(Api::class.java)
        repository = NetworkRepository(api)



    }
}