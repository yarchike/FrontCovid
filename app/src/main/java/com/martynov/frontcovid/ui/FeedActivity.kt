package com.martynov.frontcovid.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.martynov.frontcovid.API_SHARED_FILE
import com.martynov.frontcovid.AUTHENTICATED_SHARED_KEY
import com.martynov.frontcovid.App
import com.martynov.frontcovid.R
import kotlinx.coroutines.launch

class FeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        lifecycleScope.launch {
            val loadMeasurements = getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getString(AUTHENTICATED_SHARED_KEY, "")
                ?.let { App.repository.getMeasurements(it) }
        }
    }
}