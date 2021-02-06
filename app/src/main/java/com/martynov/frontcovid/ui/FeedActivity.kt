package com.martynov.frontcovid.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.martynov.frontcovid.*
import com.martynov.frontcovid.dto.MeasurementsResponse
import com.martynov.frontcovid.dto.TemperatsResponse
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.coroutines.launch
import ru.androidschool.groupiesample.items.*


class FeedActivity : AppCompatActivity() {
    var list: ArrayList<Item> = ArrayList<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        lifecycleScope.launch {
            val loadMeasurements =
                    getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getString(
                            AUTHENTICATED_SHARED_KEY,
                            ""
                    )
                            ?.let { App.repository.getMeasurements(it) }
            for (iteam in loadMeasurements?.body()!!) {
                list.add(getMessasureIteam(iteam))
            }



            items_container.adapter = GroupAdapter<GroupieViewHolder>().apply { addAll(list) }

        }
        fab.setOnClickListener {
            navigateToCreate()
        }

    }

    fun getMessasureIteam(measurementsResponse: MeasurementsResponse): Item {
        return MainCardContainer(measurementsResponse.date, measurementsResponse.contacts.size.toString(), ::onItemClick, getItemTemperatur(measurementsResponse.temperats))
    }


    fun onItemClick(url: String) {
        Log.d("My","act $url")
    }

    fun getItemTemperatur(list: ArrayList<TemperatsResponse>): List<Item>{
        var listIteam = ArrayList<Item>()
        for (item in list) {
            listIteam.add(TemperatsItem(item))
        }
        return listIteam
    }
    private fun navigateToCreate() {
        val intent = Intent(this@FeedActivity, CreateActivity::class.java)
        startActivity(intent)
        finish()
    }

}