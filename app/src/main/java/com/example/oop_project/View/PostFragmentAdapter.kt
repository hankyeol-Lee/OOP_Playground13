package com.example.oop_project.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oop_project.Model.postComment
import com.example.oop_project.databinding.CommunitypostCommentBinding
// 댓글 recyclerview를 관리하는 adapter
class PostFragmentAdapter(
    private var comments: List<postComment> // 댓글 데이터를 리스트로 관리
) : RecyclerView.Adapter<PostFragmentAdapter.CommentViewHolder>() {

    // ViewHolder 클래스: 각 아이템의 View를 관리
    class CommentViewHolder(private val binding: CommunitypostCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: postComment) {
            binding.commentAuthor.text = comment.author
            binding.commentContent.text = comment.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = CommunitypostCommentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.bind(comment)
    }

    override fun getItemCount(): Int = comments.size

    fun updateComments(newComments: List<postComment>) {
        comments = newComments
        notifyDataSetChanged() // RecyclerView 갱신
    }
}
