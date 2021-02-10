package com.martynov.frontcovid.repository

import com.martynov.frontcovid.dto.*
import retrofit2.Response


interface Repository {
    suspend fun authenticate(userRequest: UserRequest): Response<UserResponse>
    suspend fun getMeasurements(id: String): Response<List<MeasurementsResponse>>
    suspend fun setMeasurements(measurementsRequest: MeasurementsRequest): Response<com.martynov.frontcovid.dto.Response>
    suspend fun getContactFromGroup(contactRequest: ContactRequest): Response<ContactResponse>
    suspend fun editContact(contactEditingRequest: ContactEditingRequest): Response<com.martynov.frontcovid.dto.Response>
}