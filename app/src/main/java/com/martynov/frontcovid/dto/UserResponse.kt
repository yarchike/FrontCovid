package com.martynov.frontcovid.dto

data class UserResponse(
        val fio: String? = null,
        val empid: String? = null,
        val role: String? = null,
        val success: Boolean

)