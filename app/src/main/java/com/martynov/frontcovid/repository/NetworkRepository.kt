package com.martynov.frontcovid.repository

import com.martynov.frontcovid.api.Api
import com.martynov.frontcovid.dto.*
import retrofit2.Response


class NetworkRepository(private val api: Api) : Repository {
    override suspend fun authenticate(userRequest: UserRequest): Response<UserResponse> =
        api.auth(userRequest)

    override suspend fun getMeasurements(id: String): Response<List<MeasurementsResponse>> =
        api.getMeasurements(id)

    override suspend fun setMeasurements(measurementsRequest: MeasurementsRequest): Response<com.martynov.frontcovid.dto.Response> =
        api.setMeasurements(measurementsRequest)


    override suspend fun getContactFromGroup(contactsRequest: ContactsRequest): Response<ContactResponse> =
        api.getContactFromGroup(contactsRequest)

    override suspend fun editContact(contactEditingRequest: ContactEditingRequest): Response<com.martynov.frontcovid.dto.Response> =
        api.editContact(contactEditingRequest)


}