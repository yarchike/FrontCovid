package com.martynov.frontcovid.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.martynov.frontcovid.API_SHARED_FILE
import com.martynov.frontcovid.AUTHENTICATED_SHARED_KEY
import com.martynov.frontcovid.App
import com.martynov.frontcovid.R
import com.martynov.frontcovid.adapter.MeasurementsAdapter
import com.martynov.frontcovid.databinding.ActivityFeedBinding
import com.martynov.frontcovid.dto.MeasurementsResponse
import kotlinx.coroutines.launch

class FeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            val loadMeasurements = getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getString(AUTHENTICATED_SHARED_KEY, "")
                ?.let { App.repository.getMeasurements(it) }
            binding.container.layoutManager = LinearLayoutManager(this@FeedActivity)
            binding.container.adapter = MeasurementsAdapter(loadMeasurements?.body() as ArrayList<MeasurementsResponse>, LinearLayoutManager(this@FeedActivity))
        }
    }

}