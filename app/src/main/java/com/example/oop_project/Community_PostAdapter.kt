package com.example.oop_project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.oop_project.databinding.ListPostsBinding

class Community_PostAdapter(
    private var Postdata: Array<Community_Post>,
    private val listener: OnPostClickListener
) : RecyclerView.Adapter<Community_PostAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListPostsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, listener)
    }

    override fun getItemCount(): Int = Postdata.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(Postdata[position])
    }

    // 효율적인 데이터 업데이트를 위한 메서드
    fun updateData(newData:List<Community_Post>) {
        Postdata = newData.toTypedArray()
        notifyDataSetChanged() // 리스트의
    }

    class Holder(
        private val binding: ListPostsBinding,
        private val listener: OnPostClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(communityPost: Community_Post) {
            binding.txtPost.text = communityPost.PostTitle
            binding.txtAuthor.text = communityPost.PostAuthor
            //binding.PostType.text = communityPost.PostType
            binding.root.setOnClickListener {
                listener.onPostClick(communityPost)
            }
        }
    }
}
