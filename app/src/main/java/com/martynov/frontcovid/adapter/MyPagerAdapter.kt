package com.martynov.frontcovid.adapter


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.martynov.frontcovid.fragmetn.FamilyFragment
import com.martynov.frontcovid.fragmetn.FriendsFragment
import com.martynov.frontcovid.fragmetn.JobFragment
import com.martynov.frontcovid.fragmetn.OtherFragment

class MyPagerAdapter(fm: FragmentManager, val bundle: Bundle) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val jobfragment = JobFragment()
        val familyFragment = FamilyFragment()
        val friendsFragment = FriendsFragment()
        val otherFragment = OtherFragment()
        when (position) {
            0 -> jobfragment.arguments = bundle
            1 -> familyFragment.arguments = bundle
            2 -> friendsFragment.arguments = bundle
            else -> otherFragment.arguments = bundle
        }
        return when (position) {
            0 -> jobfragment
            1 -> familyFragment
            2 -> friendsFragment
            else -> otherFragment


        }

    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Работа"
            1 -> "Семья"
            2 -> "Друзья"
            else -> "Другое"
        }
    }


}