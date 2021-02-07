package com.martynov.frontcovid.ui

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.martynov.frontcovid.R
import com.martynov.frontcovid.dto.TemperatsResponse
import com.martynov.frontcovid.fragmetn.FamilyFragment
import com.martynov.frontcovid.fragmetn.JobFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.item_measurements.*
import ru.androidschool.groupiesample.items.TemperatsItem
import java.text.SimpleDateFormat
import java.util.*


class CreateActivity : AppCompatActivity() {
    val list = ArrayList<Item>()
    var dateAndTime = Calendar.getInstance()
    var fragJob: JobFragment? = null
    var fragFamaly: FamilyFragment? = null
    var fTrans: FragmentTransaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        fragFamaly = FamilyFragment()
        fTrans = supportFragmentManager.beginTransaction()


        calendarBtn.setOnClickListener {
            setTime()
            setDate()
            setDateTime()
        }
        btnAdd.setOnClickListener {
            addTemperature()
        }
        btnJob.setOnClickListener {
            fragJob = JobFragment()
            fTrans?.add(R.id.groupLayouts, fragJob!!)
            fTrans?.commit()

        }
        btnFamily.setOnClickListener {
            if (fragJob != null) {
                fTrans?.remove(fragJob!!)
            }
            fTrans?.replace(R.id.groupLayouts, fragFamaly!!)
            fTrans?.commit()
        }
    }

    private fun addTemperature() {
        list.add(
            TemperatsItem(
                TemperatsResponse(
                    editTextTextTemperature.text.toString().toDouble(), dateText.text.toString()
                )
            )
        )
        items_container_create.adapter =
            GroupAdapter<GroupieViewHolder>().apply { addAll(list) }
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
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        dateText.setText(dateFormat.format(dateAndTime.timeInMillis))
    }
}


