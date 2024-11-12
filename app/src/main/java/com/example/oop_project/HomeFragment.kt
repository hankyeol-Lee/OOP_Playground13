package com.example.oop_project

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.oop_project.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.homeReservation.setOnClickListener{
            val intent = Intent(requireContext(),reserveMain::class.java)
            startActivity(intent)
        }

        binding.homeUseage.setOnClickListener {
            val targetFragment = UsageFragment() // 전환할 Fragment
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frame, targetFragment)
            transaction.commit()
        }
        return binding.root

    }
}