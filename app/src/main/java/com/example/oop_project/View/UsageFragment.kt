package com.example.oop_project.View

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop_project.ViewModel.UsageViewModel
import com.example.oop_project.databinding.FragmentUsageBinding

class UsageFragment : Fragment() {

    lateinit var binding: FragmentUsageBinding
    private lateinit var viewModel: UsageViewModel
    private lateinit var usageRVAdapter: UsageRVAdapter

    // Fragment의 View가 생성될 때 호출되는 함수
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsageBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(UsageViewModel::class.java)

        setupRecyclerView() // RecyclerView 설정
        observeViewModel() // ViewModel 관찰

        // 장소 ID 리스트로 데이터 로드
        val places = listOf("place1", "place2", "place3", "place4", "place5")
        viewModel.loadUsages(places)

        return binding.root
    }

    // RecyclerView를 설정하는 함수
    private fun setupRecyclerView() {
        usageRVAdapter = UsageRVAdapter(ArrayList()) // 어댑터 초기화
        binding.usagePlaceRv.adapter = usageRVAdapter
        binding.usagePlaceRv.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false) // 세로 방향으로 스크롤
    }

    // ViewModel을 관찰하여 데이터가 변경되면 처리하는 함수
    private fun observeViewModel() {
        viewModel.usages.observe(viewLifecycleOwner) { usages -> // 장소 데이터가 변경되면 호출
            if (usages.isNullOrEmpty()) {
                Log.d("UsageFragment", "No usage places found") // 데이터가 없을 경우 로그 출력
            } else {
                Log.d("UsageFragment", "Usages loaded: ${usages.size}") // 데이터 로드 완료 시 로그 출력
                usageRVAdapter.updateData(usages) // RecyclerView 데이터 업데이트
            }
        }

        // 에러가 발생하면 로그에 출력
        viewModel.error.observe(viewLifecycleOwner) { error ->
            Log.e("UsageFragment", "Error loading usages: $error")
        }
    }
}
