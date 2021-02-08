package ru.androidschool.groupiesample.items

import com.martynov.frontcovid.R
import com.martynov.frontcovid.convecrStringToDate
import com.martynov.frontcovid.dto.TemperatsRequest
import com.martynov.frontcovid.dto.TemperatsResponse
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_temperature.*
import java.util.*

class TemperatsItem(private val content: TemperatsResponse) : Item() {

    override fun getLayout() = R.layout.item_temperature

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val cal = convecrStringToDate(content.time)
        val time = "${cal.get(Calendar.HOUR_OF_DAY)}: ${cal.get(Calendar.MINUTE)}"
        viewHolder.textTemperature.text = content.temperat.toString()
        viewHolder.textDate.text = time

    }
}


