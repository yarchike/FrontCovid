package com.martynov.frontcovid.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.martynov.frontcovid.API_SHARED_FILE
import com.martynov.frontcovid.AUTHENTICATED_SHARED_KEY
import com.martynov.frontcovid.App
import com.martynov.frontcovid.R
import com.martynov.frontcovid.dto.UserRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var dialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        supportActionBar!!.setSubtitle(R.string.authorization)
        if (isAuthenticated()) {
            navigateToFeed()
            return
        }
        btn_login.setOnClickListener {
            lifecycleScope.launch {
                dialog = ProgressDialog(this@MainActivity).apply {
                    setMessage(getString(R.string.please_wait))
                    setTitle(getString(R.string.loading_data))
                    show()
                    setCancelable(false)
                }
                val username =login_text.text?.toString().orEmpty()
                val password = password_text.text?.toString().orEmpty()
                try {
                    val loadUser = App.repository.authenticate(UserRequest(password, username))
                    dialog?.dismiss()
                    if (loadUser.isSuccessful) {
                        if(loadUser.body()?.success == true){
                            loadUser.body()?.empid?.let { it1 -> setUserAuth(it1) }
                            navigateToFeed()
                        }else{
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.authorisation_Error),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.authorisation_Error),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: IOException) {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.falien_connect),
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("My", e.toString())
                    dialog?.dismiss()

                }


            }

        }


    }

    private fun navigateToFeed() {
        val intent = Intent(this@MainActivity, FeedActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setUserAuth(empid: String) =
        getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE)
            .edit()
            .putString(AUTHENTICATED_SHARED_KEY, empid)
            .apply()


    private fun isAuthenticated(): Boolean =
        getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getString(
            AUTHENTICATED_SHARED_KEY, ""
        )?.isNotEmpty() ?: false
}