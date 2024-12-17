package com.example.oop_project

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.oop_project.databinding.FragmentPostfragmentBinding
import com.bumptech.glide.Glide

class PostFragment : Fragment() {

    lateinit var binding:FragmentPostfragmentBinding //binding 선언
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPostfragmentBinding.inflate(inflater, container, false)

        val title = arguments?.getString("postTitle")?: "기본 제목" // nullcheck
        val content = arguments?.getString("postData")?: "기본 내용" // nullcheck
        val author = arguments?.getString("postAuthor")?: "ㅇㅇ" // nullcheck
        val image = arguments?.getString("PostImage")?:
        "https://firebasestorage.googleapis.com/v0/b/oop-playground-605da.firebasestorage.app/o/t1image.webp?alt=media&token=fc63a65e-f7e4-4a47-b1c2-5f655c366092";
        //val likes
        // val dislikes : 추가해야하는것들
        Log.v("이미지에 들어간값","${image}")
        binding.postTitle.text = title // postTitle id textView에 읽어온 값 지정
        binding.postData.text = content
        binding.postAuthor.text = author
        //binding.PostImage.
        // 이미지 추가
        Glide.with(this)
            .load(image)
            .into(binding.PostImage)


        return binding.root
    }
}