package com.martynov.frontcovid.dto

data class MeasurementsResponse(
        val success: Boolean,
        val date: String,
        val repid: String,
        val temperat: Double,
        val contacts: ArrayList<ContactsResponse>,
        val temperats: ArrayList<TemperatsResponse>
)