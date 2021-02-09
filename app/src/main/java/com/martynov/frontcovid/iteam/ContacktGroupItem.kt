package com.martynov.frontcovid.iteam

import android.graphics.Color
import android.util.Log
import android.view.View
import com.martynov.frontcovid.R
import com.martynov.frontcovid.dto.ContactModel
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_contact_group.*

class ContacktGroupItem(private val contackt: ContactModel,
                        private val onClick: (position: Int, view: View) -> Unit) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.textFIOGroup.text = contackt.fiocont
        viewHolder.textStateGroup.text = contackt.status
        if(contackt.status == "Здоров"){
            viewHolder.textStateGroup.setTextColor(Color.GREEN)
        }else{
            viewHolder.textStateGroup.setTextColor(Color.RED)
        }
        viewHolder.btnEditContactGroup.setOnClickListener {
            onClick(position,it)
        }
    }

    override fun getLayout(): Int = R.layout.item_contact_group

}