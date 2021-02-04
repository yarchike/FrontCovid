package com.martynov.frontcovid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.martynov.frontcovid.R
import com.martynov.frontcovid.dto.ContactsResponse
import com.martynov.frontcovid.dto.TemperatsResponse
import org.w3c.dom.Text

class TemperatureAdapter(val list: ArrayList<TemperatsResponse>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val temperatureView = LayoutInflater.from(parent.context).inflate(R.layout.iteam_temperature, parent, true)
        return TemperatureViewHolder(this, temperatureView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TemperatureViewHolder -> holder.bind(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class TemperatureViewHolder(val adapter: TemperatureAdapter, view: View) : RecyclerView.ViewHolder(view) {
        fun bind(iteam: TemperatsResponse) {
            with(itemView) {
             findViewById<TextView>(R.id.textTemperature).text = iteam.temperat.toString()
                findViewById<TextView>(R.id.textDate).text = iteam.time
            }
        }
    }
}