package com.martynov.frontcovid.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.martynov.frontcovid.R
import com.martynov.frontcovid.convecrStringToDate
import com.martynov.frontcovid.convertMount
import com.martynov.frontcovid.dto.MeasurementsResponse
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class MeasurementsAdapter(val list: ArrayList<MeasurementsResponse>, val maneger: LinearLayoutManager):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       val measurementsView =  LayoutInflater.from(parent.context).inflate(R.layout.iteam_measurements, parent, false)
        return MeasurementsViewHolder(this, measurementsView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MeasurementsViewHolder -> holder.bind(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    inner class MeasurementsViewHolder(val adapter: MeasurementsAdapter, view: View) :RecyclerView.ViewHolder(view){
        @SuppressLint("CutPasteId")
        fun bind(iteam: MeasurementsResponse){
            with(itemView){
                findViewById<TextView>(R.id.textContaktCount).text = iteam.contacts.size.toString()
                Log.d("My",iteam.contacts.size.toString())
                val cal = convecrStringToDate(iteam.date)
                val date = "${cal.get(Calendar.DAY_OF_MONTH)} ${convertMount(cal.get(Calendar.MONTH))}"
                findViewById<TextView>(R.id.textDateView).text = date
                findViewById<RecyclerView>(R.id.recycleMyTemperature).layoutManager = LayoutManager(this@MeasurementsViewHolder)
                findViewById<RecyclerView>(R.id.recycleMyTemperature).adapter = TemperatureAdapter(iteam.temperats)
            }

        }
    }
}