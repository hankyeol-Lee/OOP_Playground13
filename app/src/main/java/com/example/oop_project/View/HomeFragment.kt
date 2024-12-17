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
import com.example.oop_project.ViewModel.PopularPostViewModel
import com.example.oop_project.R
import com.example.oop_project.ViewModel.GameViewModel
import com.example.oop_project.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: GameViewModel
    private lateinit var gameRVAdapter: GameRVAdapter

    private lateinit var viewModel2: PopularPostViewModel
    private lateinit var popularPostRVAdapter: PopularPostRVAdapter

    private val handler = Handler(Looper.getMainLooper()) // Handler 생성: UI 스레드에서 작업 수행
    private var currentPosition = 0 // 현재 위치 변수

    // 자동 스크롤을 위한 Runnable 객체
    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            if (gameRVAdapter.itemCount > 0) {
                currentPosition = (currentPosition + 1) % gameRVAdapter.itemCount // 다음 아이템으로 이동
                binding.homeRecentGameRv.smoothScrollToPosition(currentPosition) // 스크롤 이동
                handler.postDelayed(this, 3000) // 3초 후에 다시 실행
            }
        }
    }

    // Fragment의 View가 생성될 때 호출되는 함수
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        setupRecyclerView() // 게임 RecyclerView 설정
        observeViewModel() // 게임 ViewModel 관찰

        // 날짜 리스트로 게임 데이터 로드
        val dates = listOf("2024-10-28", "2024-10-26", "2024-10-19", "2024-10-17")
        viewModel.loadGames(dates)

        viewModel2 = ViewModelProvider(this).get(PopularPostViewModel::class.java)

        setupRecyclerView2() // 인기 게시글 RecyclerView 설정
        observeViewModel2() // 인기 게시글 ViewModel 관찰

        // 게시글 ID 리스트로 인기 게시글 데이터 로드
        val posts = listOf(
            "-OELrNhO6_Cv1sNHUypQ",
            "-OELrNhWNFlpeiVrSiON",
            "-OELrNhXujpJ1Vap60hV",
            "-OELrNhZeN8A8SNu-ooC",
            "-OELrNhe3aEAztSz9Dfd",
            "-OELrYBJHLfWXxWXMrQZ",
            "-OELrYBOUMuyZ4ORhnwG",
            "-OELrYBP3Dy2xE7vQFhC",
            "-OELrYBQ0YeYihhgpZgC",
            "-OELrYBRFeBC8q8pnK_7"
        )
        viewModel2.loadPopularPosts(posts)

        // 예약 버튼 클릭 시 ReserveMainActivity로 이동
        binding.homeReservation.setOnClickListener{
            val intent = Intent(requireContext(), ReserveMainActivity::class.java)
            startActivity(intent)
        }

        // 사용 버튼 클릭 시 UsageFragment로 전환
        binding.homeUseage.setOnClickListener {
            val targetFragment = UsageFragment() // 전환할 Fragment
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frame, targetFragment) // Fragment 교체
            transaction.commit()
        }
        return binding.root
    }

    // 게임 RecyclerView 설정 함수
    private fun setupRecyclerView() {
        gameRVAdapter = GameRVAdapter(ArrayList()) // 어댑터 초기화
        binding.homeRecentGameRv.adapter = gameRVAdapter
        binding.homeRecentGameRv.layoutManager = LinearLayoutManager(
            binding.root.context,
            LinearLayoutManager.HORIZONTAL, // 가로 방향 스크롤
            false
        )
    }

    // 게임 ViewModel을 관찰하여 데이터 업데이트 시 처리하는 함수
    private fun observeViewModel() {
        viewModel.games.observe(viewLifecycleOwner) { games -> // 게임 데이터가 변경되면 호출
            if (games.isNullOrEmpty()) {
                Log.d("HomeFragment", "No games found")
            } else {
                Log.d("HomeFragment", "Games loaded: ${games.size}")
                gameRVAdapter.updateData(games) // 데이터 업데이트
                startAutoScroll() // 자동 스크롤 시작
            }
        }

        // 에러가 발생하면 로그 출력
        viewModel.error.observe(viewLifecycleOwner) { error ->
            Log.e("HomeFragment", "Error loading games: $error")
        }
    }

    // 인기 게시글 RecyclerView 설정 함수
    private fun setupRecyclerView2() {
        popularPostRVAdapter = PopularPostRVAdapter(ArrayList()) // 어댑터 초기화
        binding.homePopularPostRv.adapter = popularPostRVAdapter
        binding.homePopularPostRv.layoutManager = LinearLayoutManager(
            binding.root.context,
            LinearLayoutManager.VERTICAL, // 세로 방향 스크롤
            false
        )
    }

    // 인기 게시글 ViewModel을 관찰하여 데이터 업데이트 시 처리하는 함수
    private fun observeViewModel2() {
        viewModel2.popularPosts.observe(viewLifecycleOwner) { popularPosts -> // 인기 게시글 데이터가 변경되면 호출
            if (popularPosts.isNullOrEmpty()) {
                Log.d("HomeFragment", "No posts found")
            } else {
                Log.d("HomeFragment", "Posts loaded: ${popularPosts.size}")
                popularPostRVAdapter.updateData(popularPosts) // 데이터 업데이트
                startAutoScroll() // 자동 스크롤 시작
            }
        }

        // 에러가 발생하면 로그 출력
        viewModel2.error.observe(viewLifecycleOwner) { error ->
            Log.e("HomeFragment", "Error loading posts: $error")
        }
    }

    // 자동 스크롤을 시작하는 함수
    private fun startAutoScroll() {
        handler.removeCallbacks(autoScrollRunnable) // 기존 콜백 제거
        handler.postDelayed(autoScrollRunnable, 3000) // 3초 후에 자동 스크롤 시작
    }

    // View가 파괴될 때 호출되어 메모리 누수를 방지하는 함수
    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(autoScrollRunnable) // 메모리 누수 방지
    }
}
