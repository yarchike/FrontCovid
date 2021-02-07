package com.martynov.frontcovid.iteam

import android.util.Log
import com.martynov.frontcovid.R
import com.martynov.frontcovid.dto.ContactsRequest
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_contackts.*

class ContactJobItem(private val contackt: ContactsRequest): Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.textViewFIOItem.text = contackt.fiocont
        viewHolder.textViewWhereDidYouContactItem.text = contackt.placecont


    }

    override fun getLayout(): Int = R.layout.item_contackts
}