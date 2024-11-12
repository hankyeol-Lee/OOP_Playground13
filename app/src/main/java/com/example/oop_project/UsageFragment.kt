package com.example.oop_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.oop_project.databinding.FragmentProfileBinding
import com.example.oop_project.databinding.FragmentUsageBinding

class UsageFragment : Fragment() {

    lateinit var binding: FragmentUsageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsageBinding.inflate(inflater, container, false)

        return binding.root
    }
}