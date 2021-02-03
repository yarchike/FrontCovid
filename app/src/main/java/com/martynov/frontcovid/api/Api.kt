package com.martynov.frontcovid.api

import com.martynov.frontcovid.dto.UserRequest
import com.martynov.frontcovid.dto.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface Api {
    @POST("/api/Auth")
    suspend fun auth(@Body userRequest: UserRequest): Response<UserResponse>
}