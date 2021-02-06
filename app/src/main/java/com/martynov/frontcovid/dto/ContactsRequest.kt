package com.martynov.frontcovid.dto

data class ContactsRequest (
    val Empid: String,
    val fiocont: String,
    val status: String,
    val placecont:String,
    val group: Int
        )