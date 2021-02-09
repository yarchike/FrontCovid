package com.martynov.frontcovid.repository

interface OnData {
    fun onDataPass(contid: String, fio: String, status: String)
}