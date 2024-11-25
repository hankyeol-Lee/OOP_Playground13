package com.example.oop_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oop_project.databinding.ListPostsBinding



class Community_PostAdapter(
    val Postdata : Array<Community_Post>,
    private val listener: OnPostClickListener // 인터페이스를 생성자로 받아와서 안에 있는 메소드 사용가능
) : RecyclerView.Adapter<Community_PostAdapter.Holder>() { // 어떤 data를 바탕으로 만들 것인가를 알아야 함. 그래서 data 모델을 넘겨줘야 함

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder { // holder는 한 '칸'.
        val binding = ListPostsBinding.inflate(LayoutInflater.from(parent.context)) // holder를 갖고있는 viewgroup이 parent. 즉 CommunityFragment로부터 LayoutInflater를 받아 inflate함.
        return Holder(binding, listener)
    }

    override fun getItemCount() = Postdata.size

    override fun onBindViewHolder(holder: Holder, position: Int)
    { // 렌더링 함수
        holder.bind(Postdata[position]) // 배열 보여주기.
    }

    class Holder(
        private val binding : ListPostsBinding,
        private val listener: OnPostClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(communityPost: Community_Post) {
            binding.txtPost.text = communityPost.PostTitle
            binding.PostType.text = communityPost.PostType
            binding.root.setOnClickListener {
                listener.onPostClick(communityPost)

            }
        }
    }
}
