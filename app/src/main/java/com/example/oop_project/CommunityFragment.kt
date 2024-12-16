package com.example.oop_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oop_project.databinding.FragmentCommunityBinding

interface OnPostClickListener {
    fun onPostClick(post: Community_Post) // 클릭 이벤트를 분리하기 위한 인터페이스 지정.
}

class CommunityFragment : Fragment(), OnPostClickListener {

    private lateinit var viewModel: CommunityPostViewModel // ViewModel을 구현하기 위해 설정
    private lateinit var binding: FragmentCommunityBinding

    // 인터페이스의 추상 메소드 구체화.
    override fun onPostClick(post: Community_Post) {
        val selectedPost = viewModel.posts.value?.find { it.title == post.PostTitle } ?: return // nullcheck

        val postFragment = PostFragment()
        val bundle = Bundle().apply {
            // template : putString("id값", selectedPost.id에해당하는속성)
            putString("postTitle", selectedPost.title) // 제목 전달. id값을 string으로 찾나보네?
            putString("postData", selectedPost.content) // 내용 전달
            putString("postAuthor",selectedPost.author) // 글쓴이 전달
        }
        postFragment.arguments = bundle // fragment에 데이터 넘겨줌.
        parentFragmentManager.beginTransaction() // fragment 전환
            .replace(R.id.fragment_postfragment, postFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CommunityPostViewModel::class.java) // viewModel을 찾고, 없으면 생성

        // 각각의 recyclerview와 카테고리를 연결
        setupRecyclerView(binding.recForumKBO, "KBO")
        setupRecyclerView(binding.recForumLCK, "LCK")
        setupRecyclerView(binding.recForumEPL, "EPL")
        setupRecyclerView(binding.recForumF1, "F1")
        setupRecyclerView(binding.recForumNBA, "NBA")

        viewModel.loadPosts()
        return binding.root
    }
    //recyclerview 한꺼번에 관리하는 함수 만듦.
    private fun setupRecyclerView(recyclerView: RecyclerView, category: String) {
        val adapter = Community_PostAdapter(emptyArray(), this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // DividerItemdecoration - 그 줄 아래에 선 나오게 하는 코드
        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)

        //
        viewModel.posts.observe(viewLifecycleOwner) { posts ->
            val filteredPosts = posts.filter { it.category == category }
            adapter.updateData(filteredPosts.map { post ->
                Community_Post(post.category, post.title)
            })
        }
    }
}
