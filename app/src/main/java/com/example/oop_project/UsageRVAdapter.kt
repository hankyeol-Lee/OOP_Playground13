package com.example.oop_project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oop_project.databinding.RecentGameItemBinding
import com.example.oop_project.databinding.UsageItemBinding

class UsageRVAdapter(private val usageList: ArrayList<Usage>): RecyclerView.Adapter<UsageRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsageRVAdapter.ViewHolder {
        val binding: UsageItemBinding = UsageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsageRVAdapter.ViewHolder, position: Int) {
        holder.bind(usageList[position])
    }

    override fun getItemCount(): Int = usageList.size

    inner class ViewHolder(val binding: UsageItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(usage: Usage){
            binding.usageBackground.setImageResource(usage.backgroundImg!!)
            binding.usagePlaceImg.setImageResource(usage.placeImg!!)
            binding.usagePlaceName.text = usage.placeName
            binding.usagePlaceTime.text = usage.placeTime
            binding.usagePlaceCapacity.text = usage.placeCapacity
            binding.usageFaceImg.setImageResource(usage.faceImg!!)
        }
    }
}