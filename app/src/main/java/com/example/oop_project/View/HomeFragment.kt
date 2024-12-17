package com.example.oop_project.View

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop_project.R
import com.example.oop_project.ViewModel.GameViewModel
import com.example.oop_project.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
//    private var gameDatas = ArrayList<Game>()
//    private var postDatas = ArrayList<PopularPost>()
    private lateinit var viewModel: GameViewModel
    private lateinit var gameRVAdapter: GameRVAdapter

    private val handler = Handler(Looper.getMainLooper())
    private var currentPosition = 0

    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            if (gameRVAdapter.itemCount > 0) {
                currentPosition = (currentPosition + 1) % gameRVAdapter.itemCount
                binding.homeRecentGameRv.smoothScrollToPosition(currentPosition)
                handler.postDelayed(this, 3000) // 3초마다 스크롤
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        setupRecyclerView()
        observeViewModel()

        val dates = listOf("2024-10-28", "2024-10-26", "2024-10-19", "2024-10-17")
        viewModel.loadGames(dates)

//        postDatas.apply {
//            add(PopularPost("페이커 월즈 5연승 ㄷㄷ", "[10]"))
//            add(PopularPost("멘시티 또 토트넘한테 짐;;", "[35]"))
//            add(PopularPost("오타니는 왜 이렇게 완벽한가", "[2]"))
//            add(PopularPost("페이커 월즈 5연승 ㄷㄷ", "[10]"))
//            add(PopularPost("멘시티 또 토트넘한테 짐;;", "[35]"))
//            add(PopularPost("오타니는 왜 이렇게 완벽한가", "[2]"))
//            add(PopularPost("페이커 월즈 5연승 ㄷㄷ", "[10]"))
//            add(PopularPost("멘시티 또 토트넘한테 짐;;", "[35]"))
//            add(PopularPost("오타니는 왜 이렇게 완벽한가", "[2]"))
//            add(PopularPost("페이커 월즈 5연승 ㄷㄷ", "[10]"))
//            add(PopularPost("멘시티 또 토트넘한테 짐;;", "[35]"))
//            add(PopularPost("오타니는 왜 이렇게 완벽한가", "[2]"))
//        }
//
//        val popularPostRVAdapter = PopularPostRVAdapter(postDatas)
//        binding.homePopularPostRv.adapter = popularPostRVAdapter
//        binding.homePopularPostRv.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)

        binding.homeReservation.setOnClickListener{
            val intent = Intent(requireContext(),ReserveMainActivity::class.java)
            startActivity(intent)
        }

        binding.homeUseage.setOnClickListener {
            val targetFragment = UsageFragment() // 전환할 Fragment
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frame, targetFragment)
            transaction.commit()
        }
        return binding.root

    }

    private fun setupRecyclerView() {
        gameRVAdapter = GameRVAdapter(ArrayList())
        binding.homeRecentGameRv.adapter = gameRVAdapter
        binding.homeRecentGameRv.layoutManager = LinearLayoutManager(
            binding.root.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun observeViewModel() {
        viewModel.games.observe(viewLifecycleOwner) { games ->
            if (games.isNullOrEmpty()) {
                Log.d("HomeFragment", "No games found")
            } else {
                Log.d("HomeFragment", "Games loaded: ${games.size}")
                gameRVAdapter.updateData(games)
                startAutoScroll()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Log.e("HomeFragment", "Error loading games: $error")
        }
    }

    private fun startAutoScroll() {
        handler.removeCallbacks(autoScrollRunnable) // 기존 콜백 제거
        handler.postDelayed(autoScrollRunnable, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(autoScrollRunnable) // 메모리 누수 방지
    }
}