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
import kotlinx.android.synthetic.main.fragment_friends.view.*


class FriendsFragment : Fragment() {
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
        val viewFriends = inflater.inflate(R.layout.fragment_friends, null)
        val bundle = this.arguments
        val empiId = bundle?.getString("empiId")
        viewFriends.btnAddContactFriends.setOnClickListener {
            val fio = viewFriends.editTextFIOFriends.text.toString()
            val whereDidYouContact = viewFriends.editTextWhereDidYouContactFriends.text.toString()
            val contact = ContactsRequest(empiId.toString(), fio, "Здоров", whereDidYouContact, 3)
            mDataPasser?.onDataPass(contact)
            list.add(
                ContactJobItem(
                    contact
                )
            )
            viewFriends.items_container_friends.adapter =
                GroupAdapter<GroupieViewHolder>().apply { addAll(list) }
            viewFriends.editTextFIOFriends.text.clear()
            viewFriends.editTextWhereDidYouContactFriends.text.clear()
        }
        return viewFriends
    }
}