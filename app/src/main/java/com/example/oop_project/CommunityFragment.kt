package com.example.oop_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop_project.databinding.FragmentCommunityBinding

import android.util.Log // 디버깅용 log 라이브러리 임시 추가
import androidx.lifecycle.ViewModelProvider

interface OnPostClickListener {
    fun onPostClick(post:Community_Post) // 클릭 이벤트를 분리하기 위한 인터페이스 지정.
}

class CommunityFragment : Fragment(), OnPostClickListener {

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
        )
    val PostEPL = arrayOf(
        Community_Post("DK","감코진 방출..디플러스 기아의 미래는?"),
        Community_Post("T1","페이커는 진짜 괴물이다"),
        Community_Post("DK","[오피셜] 디플러스 기아, 서포터' 모함' 정재훈과 계약 종료"),
        Community_Post("GEN","미리 보는 젠지 로스터.jpg"),
        Community_Post("KT","그래서 룰러 킅 옴? 진짜모름"),
        )





    override fun onPostClick(post: Community_Post) {
        // 인터페이스의 추상 메소드 구체화.
        val postFragment = PostFragment()

        val bundle = Bundle().apply {
            putString("postTitle", post.PostTitle) // 제목 전달. id값을 string으로 찾나보네?

            // 내용 전달 코드
        }
        postFragment.arguments = bundle // fragment에 데이터 넘겨줌.
        //Log.d("arguments","${bundle}")
        parentFragmentManager.beginTransaction() // fragment 전환
            .replace(R.id.fragment_postfragment, postFragment)
            .addToBackStack(null)
            .commit()
    }


    lateinit var viewModel: CommunityPostViewModel // ViewModel을 구현하기 위해 설정
    lateinit var binding: FragmentCommunityBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CommunityPostViewModel::class.java)

        //예시 데이터 하나.글 하나 씀.
        val newPost = Post(
            category = "KBO", // 카테고리
            title = "삼성은 어떻게 강팀이 되었는가",
            author = "야빠어디가",
            content = "ㅈㄱㄴ",
            image = "https://www.instagram.com/samsunglions_baseballclub/p/DBTFl4MvBlf/",
            likes = 0,
            dislikes = 0,
            comment = emptyMap()
        )

        viewModel.addPost(newPost) // viewModel에게 data 저장하라고 함


        //ItemDecoration을 이용한 item 별 밑줄
        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.recForumKBO.addItemDecoration(itemDecoration)
        binding.recForumLCK.addItemDecoration(itemDecoration)
        binding.recForumEPL.addItemDecoration(itemDecoration)

        // reForumKBO recycler에 adapter 연결
        binding.recForumKBO.layoutManager = LinearLayoutManager(requireContext())
        binding.recForumKBO.adapter = Community_PostAdapter(PostKBO,this)

        // reForumLCK recycler에 adapter 연결.
        binding.recForumLCK.layoutManager = LinearLayoutManager(requireContext())
        binding.recForumLCK.adapter = Community_PostAdapter(PostLCK,this)

        //reForumEPL recycler에 adapter 연결.
        binding.recForumEPL.layoutManager = LinearLayoutManager(requireContext())
        binding.recForumEPL.adapter = Community_PostAdapter(PostEPL,this)

        return binding.root
    }
}