package com.martynov.frontcovid.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.martynov.frontcovid.API_SHARED_FILE
import com.martynov.frontcovid.AUTHENTICATED_SHARED_KEY
import com.martynov.frontcovid.App
import com.martynov.frontcovid.R
import com.martynov.frontcovid.dto.ContactEditingRequest
import com.martynov.frontcovid.dto.ContactModel
import com.martynov.frontcovid.dto.ContactRequest
import com.martynov.frontcovid.fragmetn.ContacktFragment
import com.martynov.frontcovid.iteam.ContacktGroupItem
import com.martynov.frontcovid.repository.OnData
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.activity_contackt_group.*
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.item_contact_group.*
import kotlinx.coroutines.launch
import java.io.IOException

class ContacktGroupActivity : AppCompatActivity(), OnData {
    val list = ArrayList<Item>()
    val listData = ArrayList<ContactModel>()
    var idGroup: Int? = null
    var empiId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contackt_group);
        val passedData = intent.extras;
        idGroup = passedData?.getInt("id");
        empiId = getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getString(
                AUTHENTICATED_SHARED_KEY, ""
        )
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        when (idGroup) {
            1 -> {
                supportActionBar!!.subtitle = getString(R.string.work)
            }
            2 -> {
                supportActionBar!!.subtitle = getString(R.string.family)
            }
            3 -> {
                supportActionBar!!.subtitle = getString(R.string.friends)
            }
            4 -> {
                supportActionBar!!.subtitle = getString(R.string.other)
            }
        }

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        swipeContainerContackt.setOnRefreshListener {
            loadBase()
        }

    }

    override fun onStart() {
        super.onStart()
        loadBase()
    }

    private fun loadBase() {
        lifecycleScope.launch {
            try {
                listData.clear()
                list.clear()
                val result = empiId?.let { idGroup?.let { it1 -> ContactRequest(it, it1) } }
                        ?.let { App.repository.getContactFromGroup(it) }
                if (result?.body()?.success == true) {
                    for (item in result?.body()?.data!!) {
                        listData.add(item)
                        list.add(ContacktGroupItem(item, ::onItemClick))
                    }
                    contactGroupContant.adapter =
                            GroupAdapter<GroupieViewHolder>().apply { addAll(list) }
                    swipeContainerContackt.isRefreshing = false
                } else {
                    Toast.makeText(
                            this@ContacktGroupActivity,
                            getString(R.string.falien_connect),
                            Toast.LENGTH_SHORT
                    ).show()
                    swipeContainerContackt.isRefreshing = false
                }
            } catch (e: IOException) {
                Toast.makeText(
                        this@ContacktGroupActivity,
                        getString(R.string.falien_connect),
                        Toast.LENGTH_SHORT
                ).show()
                swipeContainerContackt.isRefreshing = false
            }
        }
    }

    fun onItemClick(id: Int, view: View) {
        when (view.id) {
            R.id.btnEditContactGroup -> {
                val item = listData.get(id)
                val bundle = Bundle()
                bundle.putString(
                        "fio", item.fiocont
                )
                bundle.putString(
                        "status", item.status
                )
                bundle.putString("id", item.contid)
                val frt: FragmentTransaction = supportFragmentManager.beginTransaction()
                val fragment: ContacktFragment = ContacktFragment()
                fragment.arguments = bundle
                frt.addToBackStack(null)
                frt.add(R.id.itemFragmentContackt, fragment)
                frt.commit()

            }
        }
    }

    override fun onDataPass(contid: String, fio: String, status: String) {
        val index = listData.indexOfFirst { it.contid == contid }
        val item = listData[index]
        val request = ContactEditingRequest(empiId!!, contid, fio, item.group, status)
        lifecycleScope.launch {
            try {
                val result = App.repository.editContact(request)
                if (result.body()?.success == true) {
                    loadBase()
                    Toast.makeText(
                            this@ContacktGroupActivity,
                            getString(R.string.saved_successfully),
                            Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                            this@ContacktGroupActivity,
                            getString(R.string.falien_connect),
                            Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: IOException) {
                Toast.makeText(
                        this@ContacktGroupActivity,
                        getString(R.string.falien_connect),
                        Toast.LENGTH_SHORT
                ).show()
            }
        }

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