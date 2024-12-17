package com.example.oop_project.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oop_project.Model.CommunityPost
import com.example.oop_project.databinding.ListPostsBinding

class CommunityPostAdapter(
    private var Postdata: Array<CommunityPost>,
    private val listener: OnPostClickListener
) : RecyclerView.Adapter<CommunityPostAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListPostsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, listener)
    }

    override fun getItemCount(): Int = Postdata.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(Postdata[position])
    }

    // 데이터 변경을 위한 메소드
    fun updateData(newData:List<CommunityPost>) {
        Postdata = newData.toTypedArray()
        notifyDataSetChanged() // 리스트의 모든 item을 다시 그리고 재배치.
    }

    class Holder(
        private val binding: ListPostsBinding,
        private val listener: OnPostClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(communityPost: CommunityPost) {
            binding.txtPost.text = communityPost.PostTitle
            binding.txtAuthor.text = communityPost.PostAuthor
            //binding.PostType.text = communityPost.PostType
            binding.root.setOnClickListener {
                listener.onPostClick(communityPost)
            }
        }
    }
}
