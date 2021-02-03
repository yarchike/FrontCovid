package com.martynov.frontcovid.dto

data class ContactsResponse(
    val contid: String,
    val placecont: String,
    val fiocont: String,
    val status: String,
    val group: Int
)