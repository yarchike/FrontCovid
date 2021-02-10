package ru.androidschool.groupiesample.items

import android.util.Log
import com.martynov.frontcovid.R
import com.martynov.frontcovid.convecrStringToDate
import com.martynov.frontcovid.convertMount
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_measurements.*
import java.util.*

class MainCardContainer(
        private val date: String = "",
        private val contactCount: String? = "",
        private val onClick: (position: Int) -> Unit,
        private val items: List<Item>
) : Item() {

    override fun getLayout() = R.layout.item_measurements

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val cal = convecrStringToDate(date)
        val dateComplite = "${cal.get(Calendar.DAY_OF_MONTH)} ${convertMount(cal.get(Calendar.MONTH))}"
        viewHolder.textDateView.text = dateComplite
        viewHolder.textContaktCount.text = contactCount
        viewHolder.items_container.adapter = GroupAdapter<GroupieViewHolder>().apply { addAll(items) }
        viewHolder.itemView.setOnClickListener {
            Log.d("My", "iteam $position")
            onClick(position)
        }
    }
}
