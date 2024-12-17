package com.example.oop_project.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oop_project.Model.PopularPost
import com.example.oop_project.databinding.PopularPostItemBinding

// RecyclerView 어댑터 클래스: 인기 게시글 데이터를 화면에 표시
class PopularPostRVAdapter(private val postList: ArrayList<PopularPost>): RecyclerView.Adapter<PopularPostRVAdapter.ViewHolder>() {

    // ViewHolder를 생성하는 함수: 레이아웃을 바인딩하고 ViewHolder 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: PopularPostItemBinding = PopularPostItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    // ViewHolder에 데이터를 바인딩하는 함수: 게시글 데이터를 화면에 표시
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    // 아이템 개수를 반환하는 함수
    override fun getItemCount(): Int = postList.size

    // 데이터 업데이트 함수
    fun updateData(newData: List<PopularPost>) {
        postList.clear() // 기존 데이터 삭제
        postList.addAll(newData) // 새로운 데이터 추가
        notifyDataSetChanged() // RecyclerView에 데이터 변경 알림
    }

    // ViewHolder 클래스: 각 아이템 뷰를 관리
    inner class ViewHolder(val binding: PopularPostItemBinding): RecyclerView.ViewHolder(binding.root) {
        // 게시글 데이터를 화면에 바인딩하는 함수
        fun bind(post: PopularPost) {
            binding.title.text = post.title // 게시글 제목 설정
            binding.category.text = "[" + post.category + "]" // 게시글 카테고리 설정
        }
    }
}
