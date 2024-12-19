package com.example.oop_project.View

import android.content.Intent
import android.os.Bundle
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: GameViewModel
    private lateinit var gameRVAdapter: GameRVAdapter

    private lateinit var viewModel2: PopularPostViewModel
    private lateinit var popularPostRVAdapter: PopularPostRVAdapter

    // CoroutineScope 정의
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

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

        // 자동 스크롤 설정 (코루틴 사용)
        startAutoScroll()

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

    // 코루틴을 사용한 자동 스크롤 함수
    private fun startAutoScroll() {
        // 주기적으로 스크롤하는 작업을 시작
        coroutineScope.launch {
            while (isActive) {
                delay(4000) // 일정 시간마다 자동 스크롤
                val currentPosition = (binding.homeRecentGameRv.layoutManager as LinearLayoutManager)
                    .findFirstVisibleItemPosition()
                val nextPosition = currentPosition + 1
                if (nextPosition < gameRVAdapter.itemCount) {
                    binding.homeRecentGameRv.smoothScrollToPosition(nextPosition)
                } else {
                    binding.homeRecentGameRv.smoothScrollToPosition(0) // 맨 처음으로 돌아가면 다시 시작
                }
            }
        }
    }

    // Fragment가 파괴되기 전에 코루틴을 취소하는 코드 추가 (메모리 누수 방지)
    override fun onDestroyView() {
        super.onDestroyView()
        coroutineScope.cancel() // 코루틴 취소
    }
}
