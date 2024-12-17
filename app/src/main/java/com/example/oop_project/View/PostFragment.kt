package com.example.oop_project.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop_project.databinding.FragmentPostfragmentBinding
import com.bumptech.glide.Glide
import com.example.oop_project.Model.postComment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PostFragment : Fragment() {

    lateinit var binding:FragmentPostfragmentBinding //binding 선언
    private lateinit var adapter: PostFragmentAdapter

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
        val commentsJson:String = arguments?.getString("postComments")?: "" //
        //val likes
        // val dislikes : 추가해야하는것들
        val comments: MutableMap<String, postComment> =
             Gson().fromJson<MutableMap<String, postComment>>(
                commentsJson,
                object : TypeToken<MutableMap<String, postComment>>() {}.type
            )

        Log.v("이미지에 들어간값","${image}")
        binding.postTitle.text = title // postTitle id textView에 읽어온 값 지정
        binding.postData.text = content
        binding.postAuthor.text = author
        // 이미지 추가
        Glide.with(this)
            .load(image)
            .into(binding.PostImage)

        setupRecyclerView()


        updateComments(comments.values.toList()) // 하나씩
        return binding.root
    }
    private fun setupRecyclerView() {
        adapter = PostFragmentAdapter(emptyList()) // 초기 데이터는 빈 리스트
        binding.recpostcomment.layoutManager = LinearLayoutManager(requireContext())
        binding.recpostcomment.adapter = adapter
    }
    private fun updateComments(newComments: List<postComment>) {
        adapter.updateComments(newComments)
    }

}