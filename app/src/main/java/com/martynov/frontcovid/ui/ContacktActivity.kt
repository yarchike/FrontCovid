package com.martynov.frontcovid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.martynov.frontcovid.R
import kotlinx.android.synthetic.main.activity_contackt.*

class ContacktActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contackt)
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        supportActionBar!!.setSubtitle(getString(R.string.contacts))
        val clickListener = View.OnClickListener {
            when(it.id){
                R.id.btnWork ->{

                }
                R.id.btnFamily ->{

                }
                R.id.btnFriends ->{

                }
                R.id.btnOther -> {

                }
            }
        }
        btnWork.setOnClickListener(clickListener)
        btnFamily.setOnClickListener(clickListener)
        btnFriends.setOnClickListener(clickListener)
        btnOther.setOnClickListener(clickListener)
    }
}