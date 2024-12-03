package com.example.oop_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop_project.databinding.FragmentProfileBinding
import com.example.oop_project.databinding.FragmentUsageBinding

class UsageFragment : Fragment() {

    lateinit var binding: FragmentUsageBinding
    private var usageDatas = ArrayList<Usage>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsageBinding.inflate(inflater, container, false)

        usageDatas.apply {
            add(Usage(R.drawable.usage_background_green, R.drawable.usage_place_img_1, "낙동강체육공원축구장", "09:00 ~ 22:00", "최대 30명", R.drawable.usage_smile))
            add(Usage(R.drawable.usage_background_red, R.drawable.usage_place_img_2, "낙동강체육공원 제1농구장", "09:00 ~ 22:00", "최대 20명", R.drawable.usage_angry))
            add(Usage(R.drawable.usage_background_green, R.drawable.usage_place_img_1, "낙동강축구장", "09:00 ~ 22:00", "최대 30명", R.drawable.usage_smile))
            add(Usage(R.drawable.usage_background_green, R.drawable.usage_place_img_1, "낙동강국립풋살장", "09:00 ~ 22:00", "최대 30명", R.drawable.usage_smile))
            add(Usage(R.drawable.usage_background_red, R.drawable.usage_place_img_2, "낙동강체육공원 제1농구장", "09:00 ~ 22:00", "최대 20명", R.drawable.usage_angry))
            add(Usage(R.drawable.usage_background_green, R.drawable.usage_place_img_1, "낙동강체육공원축구장", "09:00 ~ 22:00", "최대 30명", R.drawable.usage_smile))
        }

        val usageRVAdapter = UsageRVAdapter(usageDatas)
        binding.usagePlaceRv.adapter = usageRVAdapter
        binding.usagePlaceRv.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }
}