package com.example.oop_project

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop_project.View.ReserveMainActivity
import com.example.oop_project.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var gameDatas = ArrayList<Game>()
    private var postDatas = ArrayList<PopularPost>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        gameDatas.apply {
            add(Game("KIA 타이거즈", "삼성 라이온즈", R.drawable.recent_game_kia, R.drawable.recent_game_samsung, "0 : 1"))
            add(Game("KIA 타이거즈", "KIA 타이거즈", R.drawable.recent_game_kia, R.drawable.recent_game_kia, "10 : 7"))
            add(Game("삼성 라이온즈", "삼성 라이온즈", R.drawable.recent_game_samsung, R.drawable.recent_game_samsung, "0 : 4"))
        }

        val gameRVAdapter = GameRVAdapter(gameDatas)
        binding.homeRecentGameRv.adapter = gameRVAdapter
        binding.homeRecentGameRv.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)

        postDatas.apply {
            add(PopularPost("페이커 월즈 5연승 ㄷㄷ", "[10]"))
            add(PopularPost("멘시티 또 토트넘한테 짐;;", "[35]"))
            add(PopularPost("오타니는 왜 이렇게 완벽한가", "[2]"))
            add(PopularPost("페이커 월즈 5연승 ㄷㄷ", "[10]"))
            add(PopularPost("멘시티 또 토트넘한테 짐;;", "[35]"))
            add(PopularPost("오타니는 왜 이렇게 완벽한가", "[2]"))
            add(PopularPost("페이커 월즈 5연승 ㄷㄷ", "[10]"))
            add(PopularPost("멘시티 또 토트넘한테 짐;;", "[35]"))
            add(PopularPost("오타니는 왜 이렇게 완벽한가", "[2]"))
            add(PopularPost("페이커 월즈 5연승 ㄷㄷ", "[10]"))
            add(PopularPost("멘시티 또 토트넘한테 짐;;", "[35]"))
            add(PopularPost("오타니는 왜 이렇게 완벽한가", "[2]"))
        }

        val popularPostRVAdapter = PopularPostRVAdapter(postDatas)
        binding.homePopularPostRv.adapter = popularPostRVAdapter
        binding.homePopularPostRv.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)

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
}