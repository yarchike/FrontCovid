package com.martynov.frontcovid.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
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
    var listData = ArrayList<MeasurementsResponse>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        supportActionBar!!.setSubtitle(getString(R.string.measurements))


        fab.setOnClickListener {
            navigateToCreate()
        }
        swipeContainer.setOnRefreshListener {
            loadmeas()
        }
    }

    override fun onStart() {
        super.onStart()
        loadmeas()

    }


    private fun loadmeas() {
        list.clear()
        lifecycleScope.launch {
            try {
                val loadMeasurements =
                        getSharedPreferences(API_SHARED_FILE, MODE_PRIVATE).getString(
                                AUTHENTICATED_SHARED_KEY,
                                ""
                        )
                                ?.let { App.repository.getMeasurements(it) }
                for (iteam in loadMeasurements?.body()!!) {
                    list.add(getMessasureIteam(iteam))
                }
                listData = loadMeasurements?.body() as ArrayList<MeasurementsResponse>



                items_container.adapter = GroupAdapter<GroupieViewHolder>().apply { addAll(list) }
                swipeContainer.isRefreshing = false
            } catch (e: IOException) {
                swipeContainer.isRefreshing = false
                Toast.makeText(this@FeedActivity, getString(R.string.falien_connect), Toast.LENGTH_LONG).show()
            }

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
            R.id.settings -> navigateToSettings()
        }
        return super.onOptionsItemSelected(item);
    }

    private fun getMessasureIteam(measurementsResponse: MeasurementsResponse): Item {
        return MainCardContainer(measurementsResponse.date, measurementsResponse.contacts.size.toString(), ::onItemClick, getItemTemperatur(measurementsResponse.temperats))
    }


    fun onItemClick(position: Int) {
        val item = listData[position]
        val stringGson = Gson().toJson(item)
        val intent = Intent(this@FeedActivity, CreateActivity::class.java)
        intent.putExtra("item", stringGson)
        startActivity(intent)
    }

    private fun getItemTemperatur(list: ArrayList<TemperatsResponse>): List<Item> {
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

    private fun navigateToSettings() {
        val intent = Intent(this@FeedActivity, SettingsActivity::class.java)
        startActivity(intent)
    }

}