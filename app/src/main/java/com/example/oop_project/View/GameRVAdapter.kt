package com.example.oop_project.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oop_project.Model.Game
import com.example.oop_project.databinding.RecentGameItemBinding

class GameRVAdapter(private var gameList: ArrayList<Game>): RecyclerView.Adapter<GameRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RecentGameItemBinding = RecentGameItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(gameList[position])
    }

    override fun getItemCount(): Int = gameList.size

    fun updateData(newData: List<Game>) {
        gameList.clear()
        gameList.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RecentGameItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(game: Game){

            binding.team1Name.text = game.team1
            binding.team2Name.text = game.team2
            binding.score.text = game.score
            Glide.with(binding.root.context).load(game.team1Img).override(150, 150).into(binding.team1Img)
            Glide.with(binding.root.context).load(game.team2Img).override(150, 150).into(binding.team2Img)
        }
    }
}