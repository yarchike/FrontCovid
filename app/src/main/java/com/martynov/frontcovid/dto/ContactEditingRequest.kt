package com.martynov.frontcovid.dto

data class ContactEditingRequest (
    val Empid: String,
    val contid: String,
    val  fiocont: String,
    val group: Int,
    val status: String
        )