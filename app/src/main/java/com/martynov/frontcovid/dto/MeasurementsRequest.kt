package com.martynov.frontcovid.dto

data class MeasurementsRequest (
    val empid: String,
    val Comment: String,
    val Contacts: ArrayList<ContactsRequest>,
    val Repcontdate: String,
    val temperats: ArrayList<TemperatsRequest>
        )