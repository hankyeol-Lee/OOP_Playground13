package com.example.oop_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop_project.databinding.FragmentCommunityBinding

class CommunityFragment : Fragment() {

    val PostKBO = arrayOf(
        Community_Post("KBO","KIA 우승 기여한 불펜 투수 장현식… 52억원에 LG로"),
        Community_Post("KBO","돈보다 거인 자부심”…김원중은 낭만을 택했다"),
        Community_Post("KBO","2024년 한짤로 요약.PNG"),
        Community_Post("KBO","2024 WBSC 프리미어 12 (11/10 ~ 11/24)"),
        Community_Post("KBO","개인적으로 써보는 2024 기아 타이거즈 정규시즌 마무리 후기"),
    )
    val PostLCK = arrayOf(
        Community_Post("DK","감코진 방출..디플러스 기아의 미래는?"),
        Community_Post("T1","페이커는 진짜 괴물이다"),
        Community_Post("DK","[오피셜] 디플러스 기아, 서포터' 모함' 정재훈과 계약 종료"),
        Community_Post("GEN","미리 보는 젠지 로스터.jpg"),
        Community_Post("KT","그래서 룰러 킅 옴? 진짜모름"),
        Community_Post("DK","감코진 방출..디플러스 기아의 미래는?"),
        Community_Post("T1","페이커는 진짜 괴물이다"),
        Community_Post("DK","[오피셜] 디플러스 기아, 서포터' 모함' 정재훈과 계약 종료"),
        Community_Post("GEN","미리 보는 젠지 로스터.jpg"),
        Community_Post("KT","그래서 룰러 킅 옴? 진짜모름"),
        Community_Post("DK","감코진 방출..디플러스 기아의 미래는?"),
        Community_Post("T1","페이커는 진짜 괴물이다"),
        Community_Post("DK","[오피셜] 디플러스 기아, 서포터' 모함' 정재훈과 계약 종료"),
        Community_Post("GEN","미리 보는 젠지 로스터.jpg"),
        Community_Post("KT","그래서 룰러 킅 옴? 진짜모름"),
        Community_Post("DK","감코진 방출..디플러스 기아의 미래는?"),
        Community_Post("T1","페이커는 진짜 괴물이다"),
        Community_Post("DK","[오피셜] 디플러스 기아, 서포터' 모함' 정재훈과 계약 종료"),
        Community_Post("GEN","미리 보는 젠지 로스터.jpg"),
        Community_Post("KT","그래서 룰러 킅 옴? 진짜모름"),
        Community_Post("DK","감코진 방출..디플러스 기아의 미래는?"),
        Community_Post("T1","페이커는 진짜 괴물이다"),
        Community_Post("DK","[오피셜] 디플러스 기아, 서포터' 모함' 정재훈과 계약 종료"),
        Community_Post("GEN","미리 보는 젠지 로스터.jpg"),
        Community_Post("KT","그래서 룰러 킅 옴? 진짜모름"),
        )
    val PostEPL = arrayOf(
        Community_Post("DK","감코진 방출..디플러스 기아의 미래는?"),
        Community_Post("T1","페이커는 진짜 괴물이다"),
        Community_Post("DK","[오피셜] 디플러스 기아, 서포터' 모함' 정재훈과 계약 종료"),
        Community_Post("GEN","미리 보는 젠지 로스터.jpg"),
        Community_Post("KT","그래서 룰러 킅 옴? 진짜모름"),
        )
    lateinit var binding: FragmentCommunityBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityBinding.inflate(inflater, container, false)

        
        //ItemDecoration을 이용한 item 별 밑줄
        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.recForumKBO.addItemDecoration(itemDecoration)
        binding.recForumLCK.addItemDecoration(itemDecoration)
        binding.recForumEPL.addItemDecoration(itemDecoration)


        // reForumKBO recycler에 adapter 연결
        binding.recForumKBO.layoutManager = LinearLayoutManager(requireContext())
        binding.recForumKBO.adapter = Community_PostAdapter(PostKBO)

        // reForumLCK recycler에 adapter 연결.
        binding.recForumLCK.layoutManager = LinearLayoutManager(requireContext())
        binding.recForumLCK.adapter = Community_PostAdapter(PostLCK)

        //reForumEPL recycler에 adapter 연결.
        binding.recForumEPL.layoutManager = LinearLayoutManager(requireContext())
        binding.recForumEPL.adapter = Community_PostAdapter(PostEPL)


        return binding.root
    }
}