package com.martynov.frontcovid.api

import com.martynov.frontcovid.dto.*
import retrofit2.Response
import retrofit2.http.*


interface Api {
    @POST("/api/Auth")
    suspend fun auth(@Body userRequest: UserRequest): Response<UserResponse>

    @GET("/api/Repconts/{id}")
    suspend fun getMeasurements(@Path("id") id: String): Response<List<MeasurementsResponse>>

    @POST("/api/Repconts")
    suspend fun setMeasurements(@Body measurementsRequest: MeasurementsRequest): Response<com.martynov.frontcovid.dto.Response>

    @POST("/api/Spisconts")
    suspend fun getContactFromGroup(@Body contactRequest: ContactRequest):Response<ContactResponse>
    @PUT("/api/Conts")
    suspend fun editContact(@Body contactEditingRequest: ContactEditingRequest):Response<com.martynov.frontcovid.dto.Response>
}