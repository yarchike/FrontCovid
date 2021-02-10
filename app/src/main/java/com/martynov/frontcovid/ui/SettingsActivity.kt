package com.martynov.frontcovid.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.martynov.frontcovid.API_SHARED_FILE
import com.martynov.frontcovid.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        supportActionBar!!.setSubtitle(getString(R.string.settings))
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        btnExit.setOnClickListener {
            clearData()
            navigateToMain()
        }

    }

    private fun clearData() {
        getSharedPreferences(API_SHARED_FILE, MODE_PRIVATE)
                .edit()
                .clear()
                .apply()

    }

    private fun navigateToMain() {
        val intent = Intent(this@SettingsActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
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