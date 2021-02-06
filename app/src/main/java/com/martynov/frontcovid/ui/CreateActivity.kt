package com.martynov.frontcovid.ui

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.martynov.frontcovid.R
import kotlinx.android.synthetic.main.activity_create.*

import java.text.SimpleDateFormat
import java.util.*


class CreateActivity : AppCompatActivity() {
    var dateAndTime = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        calendarBtn.setOnClickListener {
            setTime()
            setDate()
            setDateTime()

        }
    }

    private fun setDate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DatePickerDialog(
                    this@CreateActivity, OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                dateAndTime.set(Calendar.YEAR, year)
                dateAndTime.set(Calendar.MONTH, monthOfYear)
                dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//                    dateAndTime.set(Calendar.HOUR_OF_DAY, 23)
//                    dateAndTime.set(Calendar.MINUTE, 59)
////                    val dateFormat = SimpleDateFormat("MM/dd/yyyy  HH:mm")
////                    dateText.setText(dateFormat.format(dateAndTime.timeInMillis))
            },
                    dateAndTime[Calendar.YEAR],
                    dateAndTime[Calendar.MONTH],
                    dateAndTime[Calendar.DAY_OF_MONTH]
            )
                    .show()
        }
    }

    fun setTime() {
        TimePickerDialog(
                this, OnTimeSetListener { view, hourOfDay, minute ->
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            dateAndTime.set(Calendar.MINUTE, minute)

        },
                dateAndTime[Calendar.HOUR_OF_DAY],
                dateAndTime[Calendar.MINUTE],
                true
        ).show()
    }

    fun setDateTime() {

        val dateFormat = SimpleDateFormat("MM/dd/yyyy  HH:mm")
        dateText.setText(dateFormat.format(dateAndTime.timeInMillis))

    }
}


