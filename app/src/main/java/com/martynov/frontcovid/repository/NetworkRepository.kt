package com.martynov.frontcovid.repository

import com.martynov.frontcovid.api.Api
import com.martynov.frontcovid.dto.MeasurementsResponse
import com.martynov.frontcovid.dto.UserRequest
import com.martynov.frontcovid.dto.UserResponse
import retrofit2.Response


class NetworkRepository(private val api: Api) : Repository {
    override suspend fun authenticate(userRequest: UserRequest): Response<UserResponse> =
        api.auth(userRequest)

    override suspend fun getMeasurements(id: String): Response<List<MeasurementsResponse>> =
        api.getMeasurements(id)

}