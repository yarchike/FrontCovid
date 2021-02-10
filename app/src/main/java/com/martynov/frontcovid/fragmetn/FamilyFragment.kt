package com.martynov.frontcovid.fragmetn

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.martynov.frontcovid.R
import com.martynov.frontcovid.dto.ContactsRequest
import com.martynov.frontcovid.dto.ContactsResponse
import com.martynov.frontcovid.iteam.ContactJobItem
import com.martynov.frontcovid.repository.OnDataPass
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.fragment_family.view.*

class FamilyFragment : Fragment() {
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
        val viewFamaly = inflater.inflate(R.layout.fragment_family, null)
        val bundle = this.arguments
        val empiId = bundle?.getString("empiId")
        if (bundle?.getString("contackt") != null) {
            val stringGson = bundle.getString("contackt")
            val type = object : TypeToken<List<ContactsResponse>>() {}.type
            val listContackt = Gson().fromJson<List<ContactsResponse>>(stringGson, type)
            for (iteam in listContackt) {
                if (iteam.group == 2) {
                    list.add(ContactJobItem(ContactsRequest(empiId.toString(), iteam.fiocont, iteam.status, iteam.placecont, iteam.group)))
                }

            }
            viewFamaly.items_container_family.adapter =
                    GroupAdapter<GroupieViewHolder>().apply { addAll(list) }
        }
        var state = getString(R.string.healthy)
        viewFamaly.checkBoxStateFamaly.isChecked = true
        viewFamaly.btnAddContactFamaly.setOnClickListener {
            if (!viewFamaly.checkBoxStateFamaly.isChecked) {
                state = getString(R.string.sick)
            }
            val fio = viewFamaly.editTextFIOFamaly.text.toString()
            val whereDidYouContact = viewFamaly.editTextWhereDidYouContactFamaly.text.toString()
            val contact = ContactsRequest(empiId.toString(), fio, state, whereDidYouContact, 2)
            mDataPasser?.onDataPass(contact)
            list.add(
                    ContactJobItem(
                            contact
                    )
            )
            viewFamaly.items_container_family.adapter =
                    GroupAdapter<GroupieViewHolder>().apply { addAll(list) }
            viewFamaly.editTextFIOFamaly.text.clear()
            viewFamaly.editTextWhereDidYouContactFamaly.text.clear()
        }
        return viewFamaly
    }
}