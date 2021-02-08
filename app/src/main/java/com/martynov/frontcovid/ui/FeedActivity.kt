package com.martynov.frontcovid.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
import java.io.IOException


class FeedActivity : AppCompatActivity() {
    var list: ArrayList<Item> = ArrayList<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        supportActionBar!!.setSubtitle(getString(R.string.measurements))

        lifecycleScope.launch {
            try {
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
            } catch (e: IOException) {
                Toast.makeText(this@FeedActivity, getString(R.string.falien_connect), Toast.LENGTH_LONG).show()
            }

        }
        fab.setOnClickListener {
            navigateToCreate()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.friends -> navigateToFriends()
        }
        return super.onOptionsItemSelected(item);
    }

    fun getMessasureIteam(measurementsResponse: MeasurementsResponse): Item {
        return MainCardContainer(measurementsResponse.date, measurementsResponse.contacts.size.toString(), ::onItemClick, getItemTemperatur(measurementsResponse.temperats))
    }


    fun onItemClick(url: String) {
        Log.d("My", "act $url")
    }

    fun getItemTemperatur(list: ArrayList<TemperatsResponse>): List<Item> {
        var listIteam = ArrayList<Item>()
        for (item in list) {
            listIteam.add(TemperatsItem(item))
        }
        return listIteam
    }

    private fun navigateToCreate() {
        val intent = Intent(this@FeedActivity, CreateActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToFriends() {
        val intent = Intent(this@FeedActivity, ContacktActivity::class.java)
        startActivity(intent)
    }

}