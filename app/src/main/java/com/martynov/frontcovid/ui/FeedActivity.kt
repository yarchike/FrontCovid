package com.martynov.frontcovid.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.martynov.frontcovid.API_SHARED_FILE
import com.martynov.frontcovid.AUTHENTICATED_SHARED_KEY
import com.martynov.frontcovid.App
import com.martynov.frontcovid.R
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
    //    items_container.adapter = GroupAdapter<GroupieViewHolder>().apply { addAll(listOf(getPopularMovies())) }


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

}