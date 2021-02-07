package com.martynov.frontcovid.fragmetn

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.martynov.frontcovid.R
import com.martynov.frontcovid.dto.ContactsRequest
import com.martynov.frontcovid.iteam.ContactJobItem
import com.martynov.frontcovid.repository.OnDataPass
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.fragment_other.view.*

class OtherFragment : Fragment() {
    val list = ArrayList<Item>()
    var mDataPasser: OnDataPass? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mDataPasser = activity as OnDataPass?
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewOther = inflater.inflate(R.layout.fragment_other, null)
        val bundle = this.arguments
        val empiId = bundle?.getString("empiId")
        viewOther.btnAddContactOther.setOnClickListener {
            val fio = viewOther.editTextFIOOther.text.toString()
            val whereDidYouContact = viewOther.editTextWhereDidYouContactOther.text.toString()
            val contact = ContactsRequest(empiId.toString(), fio, "Здоров", whereDidYouContact, 4)
            mDataPasser?.onDataPass(contact)
            list.add(
                ContactJobItem(
                    contact
                )
            )
            viewOther.items_container_other.adapter =
                GroupAdapter<GroupieViewHolder>().apply { addAll(list) }
            viewOther.editTextFIOOther.text.clear()
            viewOther.editTextWhereDidYouContactOther.text.clear()
        }
        return viewOther
    }
}