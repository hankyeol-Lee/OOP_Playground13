package com.example.oop_project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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

    fun updateData(newData: List<Usage>) {
        usageList.clear()
        usageList.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: UsageItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(usage: Usage){
            Glide.with(binding.root.context).load(usage.backgroundImg).override(1000, 500).into(binding.usageBackground)
            Glide.with(binding.root.context).load(usage.placeImg).circleCrop().override(130, 130).into(binding.usagePlaceImg)
            binding.usagePlaceName.text = usage.placeName
            binding.usagePlaceTime.text = usage.placeTime
            binding.usagePlaceCapacity.text = usage.placeCapacity
            Glide.with(binding.root.context).load(usage.faceImg).override(130, 130).into(binding.usageFaceImg)
        }
    }
}