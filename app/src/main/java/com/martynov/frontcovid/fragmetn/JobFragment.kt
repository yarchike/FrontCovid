package com.martynov.frontcovid.fragmetn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.martynov.frontcovid.R
import kotlinx.android.synthetic.main.fragment_job.view.*

class JobFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewJob = inflater.inflate(R.layout.fragment_job, null)
        viewJob.textView.text = "Привет"
        return viewJob
    }


}