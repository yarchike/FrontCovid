package com.martynov.frontcovid.ui

import android.content.Intent
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
                    navigateToGroup(1)
                }
                R.id.btnFamily ->{
                    navigateToGroup(2)
                }
                R.id.btnFriends ->{
                    navigateToGroup(3)
                }
                R.id.btnOther -> {
                    navigateToGroup(4)
                }
            }
        }
        btnWork.setOnClickListener(clickListener)
        btnFamily.setOnClickListener(clickListener)
        btnFriends.setOnClickListener(clickListener)
        btnOther.setOnClickListener(clickListener)
    }
    private fun navigateToGroup(id: Int) {
        val intent = Intent(this@ContacktActivity, ContacktGroupActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}