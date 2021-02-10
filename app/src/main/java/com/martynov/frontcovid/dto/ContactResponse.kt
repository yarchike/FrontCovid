package com.martynov.frontcovid.dto

class ContactResponse(
        val success: Boolean,
        val total: Int,
        val page: Int,
        val data: ArrayList<ContactModel>
)