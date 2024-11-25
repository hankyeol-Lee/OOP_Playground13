package com.example.oop_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.oop_project.databinding.FragmentPostfragmentBinding


class PostFragment : Fragment() {

    lateinit var binding:FragmentPostfragmentBinding //binding 선언
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPostfragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


}