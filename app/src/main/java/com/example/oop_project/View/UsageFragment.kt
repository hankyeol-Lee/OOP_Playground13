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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsageBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(UsageViewModel::class.java)

        setupRecyclerView()
        observeViewModel()

        val places = listOf("place1", "place2", "place3", "place4", "place5")
        viewModel.loadUsages(places)

        return binding.root
    }

    private fun setupRecyclerView() {
        usageRVAdapter = UsageRVAdapter(ArrayList())
        binding.usagePlaceRv.adapter = usageRVAdapter
        binding.usagePlaceRv.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
    }

    private fun observeViewModel() {
        viewModel.usages.observe(viewLifecycleOwner) { usages ->
            if (usages.isNullOrEmpty()) {
                Log.d("UsageFragment", "No usage places found")
            } else {
                Log.d("UsageFragment", "Usages loaded: ${usages.size}")
                usageRVAdapter.updateData(usages)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Log.e("UsageFragment", "Error loading usages: $error")
        }
    }
}