package com.example.oop_project.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oop_project.Model.Game
import com.example.oop_project.databinding.RecentGameItemBinding

// RecyclerView 어댑터 클래스: 게임 데이터를 화면에 표시
class GameRVAdapter(private var gameList: ArrayList<Game>): RecyclerView.Adapter<GameRVAdapter.ViewHolder>() {

    // ViewHolder를 생성하는 함수: 레이아웃을 바인딩하고 ViewHolder 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RecentGameItemBinding = RecentGameItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    // ViewHolder에 데이터를 바인딩하는 함수: 게임 데이터를 화면에 표시
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(gameList[position])
    }

    // 아이템 개수를 반환하는 함수
    override fun getItemCount(): Int = gameList.size

    // 데이터 업데이트 함수
    fun updateData(newData: List<Game>) {
        gameList.clear() // 기존 데이터 삭제
        gameList.addAll(newData) // 새로운 데이터 추가
        notifyDataSetChanged() // RecyclerView에 데이터 변경 알림
    }

    // ViewHolder 클래스: 각 아이템 뷰를 관리
    inner class ViewHolder(val binding: RecentGameItemBinding): RecyclerView.ViewHolder(binding.root) {
        // 게임 데이터를 화면에 바인딩하는 함수
        fun bind(game: Game) {
            // 팀 이름과 점수 설정
            binding.team1Name.text = game.team1
            binding.team2Name.text = game.team2
            binding.score.text = game.score

            // Glide를 사용해 팀 이미지 로드 및 사이즈 조정
            Glide.with(binding.root.context).load(game.team1Img)
                .override(150, 150).into(binding.team1Img)

            Glide.with(binding.root.context).load(game.team2Img)
                .override(150, 150).into(binding.team2Img)
        }
    }
}
