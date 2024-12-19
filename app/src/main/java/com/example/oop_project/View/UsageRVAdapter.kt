package com.example.oop_project.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oop_project.Model.Usage
import com.example.oop_project.databinding.UsageItemBinding

// RecyclerView 어댑터 클래스: 장소 사용 정보를 화면에 표시
class UsageRVAdapter(private val usageList: ArrayList<Usage>): RecyclerView.Adapter<UsageRVAdapter.ViewHolder>() {

    // ViewHolder를 생성하는 함수: 레이아웃을 바인딩하고 ViewHolder 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: UsageItemBinding = UsageItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    // ViewHolder에 데이터를 바인딩하는 함수: 사용 정보를 화면에 표시
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(usageList[position])
    }

    // 아이템 개수를 반환하는 함수
    override fun getItemCount(): Int = usageList.size

    // 데이터 업데이트 함수
    fun updateData(newData: List<Usage>) {
        usageList.clear() // 기존 데이터 삭제
        usageList.addAll(newData) // 새로운 데이터 추가
        notifyDataSetChanged() // RecyclerView에 데이터 변경 알림
    }

    // ViewHolder 클래스: 각 아이템 뷰를 관리
    inner class ViewHolder(val binding: UsageItemBinding): RecyclerView.ViewHolder(binding.root) {
        // 사용 정보를 화면에 바인딩하는 함수
        fun bind(usage: Usage) {
            // 배경 이미지 설정
            Glide.with(binding.root.context).load(usage.backgroundImg)
                .override(1000, 500).into(binding.usageBackground)

            // 장소 이미지 설정 (원형 크롭)
            Glide.with(binding.root.context).load(usage.placeImg)
                .circleCrop().override(130, 130).into(binding.usagePlaceImg)

            // 장소 이름, 시간, 수용 인원 설정
            binding.usagePlaceName.text = usage.placeName
            binding.usagePlaceTime.text = usage.placeTime
            binding.usagePlaceCapacity.text = usage.placeCapacity

            // 얼굴 이미지 설정
            Glide.with(binding.root.context).load(usage.faceImg)
                .override(130, 130).into(binding.usageFaceImg)
        }
    }
}
