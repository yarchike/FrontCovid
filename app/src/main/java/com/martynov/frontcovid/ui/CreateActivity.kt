package com.martynov.frontcovid.ui

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.martynov.frontcovid.API_SHARED_FILE
import com.martynov.frontcovid.AUTHENTICATED_SHARED_KEY
import com.martynov.frontcovid.App
import com.martynov.frontcovid.R
import com.martynov.frontcovid.adapter.MyPagerAdapter
import com.martynov.frontcovid.dto.ContactsRequest
import com.martynov.frontcovid.dto.MeasurementsRequest
import com.martynov.frontcovid.dto.TemperatsRequest
import com.martynov.frontcovid.dto.TemperatsResponse
import com.martynov.frontcovid.repository.OnDataPass
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.item_measurements.*
import kotlinx.coroutines.launch
import ru.androidschool.groupiesample.items.TemperatsItem
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CreateActivity : AppCompatActivity(), OnDataPass {
    val list = ArrayList<Item>()
    val listTemaperature = ArrayList<TemperatsRequest>()
    val listContacts = ArrayList<ContactsRequest>()
    var dateAndTime = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        val empiId = getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getString(
            AUTHENTICATED_SHARED_KEY, ""
        )
        val bundle = Bundle()
        bundle.putString(
            "empiId", empiId
        )


        calendarBtn.setOnClickListener {
            setTime()
            setDate()
            setDateTime()
        }
        btnAdd.setOnClickListener {
            addTemperature()
        }
        viewpager.adapter = MyPagerAdapter(supportFragmentManager, bundle)
        tabs.setupWithViewPager(viewpager)


        fabSave.setOnClickListener {
            lifecycleScope.launch {
                try{
                    val measurementsRequest = MeasurementsRequest(empiId.toString(), "", listContacts, listTemaperature.get(0).time,listTemaperature)
                    val result = App.repository.setMeasurements( measurementsRequest)
                    if(result.body()?.success == true){
                        navigateToFeed()
                    }else{
                        Toast.makeText(this@CreateActivity, getString(R.string.falien_connect), Toast.LENGTH_LONG).show()
                    }
                }catch (e: Exception){
                    Toast.makeText(this@CreateActivity, getString(R.string.falien_connect), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun addTemperature() {
        try{
            val temperatsRequest = TemperatsRequest(
                editTextTextTemperature.text.toString().toDouble(), dateText.text.toString()
            )
            listTemaperature.add(temperatsRequest)
            list.add(
                TemperatsItem(
                    TemperatsResponse(temperatsRequest.temperat, temperatsRequest.time)
                )
            )

            items_container_create.adapter =
                GroupAdapter<GroupieViewHolder>().apply { addAll(list) }
        }catch (e :Exception){
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
        }


    }


    private fun setDate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DatePickerDialog(
                this@CreateActivity, OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    dateAndTime.set(Calendar.YEAR, year)
                    dateAndTime.set(Calendar.MONTH, monthOfYear)
                    dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)
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

    override fun onDataPass(data: ContactsRequest?) {
        if (data != null) {
            listContacts.add(data)
        }
    }
    private fun navigateToFeed() {
        val intent = Intent(this@CreateActivity, FeedActivity::class.java)
        startActivity(intent)
        finish()
    }
}


