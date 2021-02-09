package com.martynov.frontcovid.fragmetn

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.martynov.frontcovid.R
import com.martynov.frontcovid.repository.OnData
import kotlinx.android.synthetic.main.fragment_contackt.view.*

class ContacktFragment : Fragment() {
    var dataPasser: OnData? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        dataPasser = activity as OnData?
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = this.arguments
        val fio = bundle?.getString("fio")
        val status = bundle?.getString("status")
        val contid = bundle?.getString("id")
        Log.d("My", contid.toString())
        val viewContackt = inflater.inflate(R.layout.fragment_contackt, null)
        viewContackt.editTextFIOContackt.setText(fio)
        viewContackt.checkBoxStateContackt.isChecked = status.equals("Здоров")
        viewContackt.btnAddContactContackt.setOnClickListener{
            val fioEnd = viewContackt.editTextFIOContackt.text.toString()
            val statusEnd =
                if(viewContackt.checkBoxStateContackt.isChecked){
                    "Здоров"
                }else{
                    "Болен"
                }
            if (contid != null) {
                dataPasser?.onDataPass(contid, fioEnd, statusEnd)
                activity?.onBackPressed();
            }
        }



        return viewContackt
    }
}