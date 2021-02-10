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
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.martynov.frontcovid.*
import com.martynov.frontcovid.adapter.MyPagerAdapter
import com.martynov.frontcovid.dto.*
import com.martynov.frontcovid.repository.OnDataPass
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.item_measurements.*
import kotlinx.coroutines.launch
import ru.androidschool.groupiesample.items.TemperatsItem
import java.util.*
import kotlin.collections.ArrayList


class CreateActivity : AppCompatActivity(), OnDataPass {
    val list = ArrayList<Item>()
    val listTemaperature = ArrayList<TemperatsRequest>()
    val listContacts = ArrayList<ContactsRequest>()
    var dateAndTime = Calendar.getInstance()
    val bundle = Bundle()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        supportActionBar!!.subtitle = getString(R.string.measurement_creation)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        editMeasure()
        dateText.text = "${dateAndTime.get(Calendar.DAY_OF_MONTH)}  ${convertMount(dateAndTime.get(Calendar.MONTH))}"

        val empiId = getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getString(
                AUTHENTICATED_SHARED_KEY, ""
        )

        bundle.putString(
                "empiId", empiId
        )


        calendarBtn.setOnClickListener {
            setDate()
            setDateText()
        }
        btnClock.setOnClickListener {
            setTime()
            setTimeText()
        }

        btnAdd.setOnClickListener {
            addTemperature()
        }
        viewpager.adapter = MyPagerAdapter(supportFragmentManager, bundle)
        tabs.setupWithViewPager(viewpager)


        fabSave.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val measurementsRequest = MeasurementsRequest(empiId.toString(), "", listContacts, listTemaperature.get(0).time, listTemaperature)
                    val result = App.repository.setMeasurements(measurementsRequest)
                    if (result.body()?.success == true) {
                        navigateToFeed()
                    } else {
                        Toast.makeText(this@CreateActivity, getString(R.string.falien_connect), Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@CreateActivity, getString(R.string.falien_connect), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun editMeasure() {
        var itemMeasure: MeasurementsResponse? = null
        if (intent.extras?.getString("item") != null) {
            try {
                val StringGson = intent.extras?.getString("item")
                Log.d("My", StringGson.toString())
                val type = object : TypeToken<MeasurementsResponse>() {}.type
                itemMeasure = Gson().fromJson(StringGson, type)
                dateAndTime = itemMeasure?.date?.let { convecrStringToDate(it) }
                textClock.text = "${dateAndTime.get(Calendar.HOUR_OF_DAY)}: ${dateAndTime.get(Calendar.MINUTE)}"
                for (item in itemMeasure!!.temperats) {
                    val temperatsRequest = TemperatsRequest(item.temperat, item.time)
                    listTemaperature.add(temperatsRequest)
                    list.add(
                            TemperatsItem(
                                    TemperatsResponse(temperatsRequest.temperat, temperatsRequest.time)
                            )

                    )
                    items_container_create.adapter =
                            GroupAdapter<GroupieViewHolder>().apply { addAll(list) }


                }
                val gsonContactsResponse = Gson().toJson(itemMeasure.contacts)
                bundle.putString("contackt", gsonContactsResponse)

            } catch (e: java.lang.Exception) {
                Toast.makeText(this@CreateActivity, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun addTemperature() {
        try {
            val temperatsRequest = TemperatsRequest(
                    editTextTextTemperature.text.toString().toDouble(), convecrDateToString(dateAndTime)
            )
            listTemaperature.add(temperatsRequest)
            list.add(
                    TemperatsItem(
                            TemperatsResponse(temperatsRequest.temperat, temperatsRequest.time)
                    )
            )

            items_container_create.adapter =
                    GroupAdapter<GroupieViewHolder>().apply { addAll(list) }
        } catch (e: Exception) {
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

    private fun setTime() {
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

    private fun setDateText() {
        dateText.text = "${dateAndTime.get(Calendar.DAY_OF_MONTH)}  ${convertMount(dateAndTime.get(Calendar.MONTH))}"
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

    private fun setTimeText() {
        textClock.text = "${dateAndTime.get(Calendar.HOUR_OF_DAY)}: ${dateAndTime.get(Calendar.MINUTE)}"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish();
                return true;
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }

        }
    }
}


