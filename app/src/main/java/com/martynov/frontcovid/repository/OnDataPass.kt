package com.martynov.frontcovid.repository

import com.martynov.frontcovid.dto.ContactsRequest

interface OnDataPass {
    fun onDataPass(data: ContactsRequest?)
}