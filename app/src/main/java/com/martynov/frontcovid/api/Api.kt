package com.martynov.frontcovid.api

import com.martynov.frontcovid.dto.MeasurementsResponse
import com.martynov.frontcovid.dto.UserRequest
import com.martynov.frontcovid.dto.UserResponse
import retrofit2.Response
import retrofit2.http.*


interface Api {
    @POST("/api/Auth")
    suspend fun auth(@Body userRequest: UserRequest): Response<UserResponse>
    @GET("/api/Repconts/{id}")
    suspend fun getMeasurements(@Path("id") id: String): Response<List<MeasurementsResponse>>
}