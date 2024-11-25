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

        val title = arguments?.getString("postTitle")?: "기본 제목" // nullcheck
        binding.postTitle.text = title // postTitle id textView에 읽어온 값 지정
        
        return binding.root
    }
}