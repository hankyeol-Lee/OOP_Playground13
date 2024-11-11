package com.example.oop_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oop_project.databinding.ListPostsBinding

class Community_PostAdapter(
    val Postdata : Array<Community_Post>,
    //val onItemClick : (Community_Post) -> Unit // 클릭 할 때 실행하는 callback?? 선언. ( 잘 모르겠음)
) : RecyclerView.Adapter<Community_PostAdapter.Holder>() { // 어떤 data를 바탕으로 만들 것인가를 알아야 함. 그래서 data 모델을 넘겨줘야 함

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder { // holder는 한 '칸'.
        val binding = ListPostsBinding.inflate(LayoutInflater.from(parent.context)) // holder를 갖고있는 viewgroup이 parent. 즉 CommunityFragment로부터 LayoutInflater를 받아 inflate함.
        return Holder(binding)
    }

    override fun getItemCount() = Postdata.size

    override fun onBindViewHolder(holder: Holder, position: Int)
    { // 렌더링 함수
        holder.bind(Postdata[position])
    }

    class Holder(private val binding : ListPostsBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(communityPost: Community_Post)
        {
            binding.txtPost.text = communityPost.PostTitle
            binding.PostType.text = communityPost.PostType

            """.root.setOnClickListener {
                onItemClick(communityPost) // 클릭 시 콜백 호출
            }
            """
        }
    }
}