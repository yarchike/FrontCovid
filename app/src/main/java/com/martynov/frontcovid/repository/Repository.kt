package com.martynov.frontcovid.repository

import com.martynov.frontcovid.dto.MeasurementsResponse
import com.martynov.frontcovid.dto.UserRequest
import com.martynov.frontcovid.dto.UserResponse
import retrofit2.Response

interface Repository {
    suspend fun authenticate(userRequest: UserRequest): Response<UserResponse>
    suspend fun getMeasurements(id: String): Response<List<MeasurementsResponse>>
}