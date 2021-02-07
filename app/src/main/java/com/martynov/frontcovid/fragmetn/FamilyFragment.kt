package com.martynov.frontcovid.fragmetn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.martynov.frontcovid.R
import kotlinx.android.synthetic.main.fragment_family.view.*

class FamilyFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewFamaly = inflater.inflate(R.layout.fragment_family, null)
        viewFamaly.textView2.text = "Семья"
        return viewFamaly
    }
}