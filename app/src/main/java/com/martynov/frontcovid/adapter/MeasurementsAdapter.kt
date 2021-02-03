package com.martynov.frontcovid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.martynov.frontcovid.R
import com.martynov.frontcovid.dto.MeasurementsResponse

class MeasurementsAdapter(val list: ArrayList<MeasurementsResponse>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       val measurementsView =  LayoutInflater.from(parent.context).inflate(R.layout.iteam_measurements, parent, false)
        return MeasurementsViewHolder(this, measurementsView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return list.size
    }
    class MeasurementsViewHolder(val adapter: MeasurementsAdapter, view: View) :RecyclerView.ViewHolder(view){

    }
}