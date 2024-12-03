package com.example.oop_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oop_project.databinding.FragmentUsageBinding
import com.example.oop_project.databinding.RecentGameItemBinding

class GameRVAdapter(private var gameList: ArrayList<Game>): RecyclerView.Adapter<GameRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameRVAdapter.ViewHolder {
        val binding: RecentGameItemBinding = RecentGameItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameRVAdapter.ViewHolder, position: Int) {
        holder.bind(gameList[position])
    }

    override fun getItemCount(): Int = gameList.size

    inner class ViewHolder(val binding: RecentGameItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(game: Game){
            binding.team1Img.setImageResource(game.team1Img!!)
            binding.team2Img.setImageResource(game.team2Img!!)
            binding.team1Name.text = game.team1
            binding.team2Name.text = game.team2
            binding.score.text = game.score
        }
    }
}