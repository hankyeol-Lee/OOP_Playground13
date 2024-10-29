package com.example.oop_project

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class reserveVPAdapter(fragment: FragmentActivity) :FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> reserveEntireFragment()
            1 -> reserveSoccerFragment()
            2 -> reservePingPongFragment()
            else -> reserveBowlingFragment()
        }
    }
}