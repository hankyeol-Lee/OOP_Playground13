package com.example.oop_project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oop_project.databinding.PopularPostItemBinding
import com.example.oop_project.databinding.RecentGameItemBinding

class PopularPostRVAdapter(private val postList: ArrayList<PopularPost>): RecyclerView.Adapter<PopularPostRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularPostRVAdapter.ViewHolder {
        val binding: PopularPostItemBinding = PopularPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularPostRVAdapter.ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int = postList.size

    inner class ViewHolder(val binding: PopularPostItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PopularPost){
            binding.postName.text = post.postName
            binding.postComment.text = post.postComment
        }
    }
}