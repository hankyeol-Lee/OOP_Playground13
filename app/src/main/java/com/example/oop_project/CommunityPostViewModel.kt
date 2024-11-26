package com.example.oop_project


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import repository.PostRepository
import androidx.lifecycle.ViewModel

//ViewModel을 상속받는 클래스 CommunityPostViewModel
class CommunityPostViewModel :ViewModel() {
    private val repository = PostRepository()

    private val _posts = MutableLiveData<List<Post>>() //내부 livedata
    val posts : LiveData<List<Post>> get() = _posts


    val newPost = listOf(
        Post(
            category = "KBO", // 카테고리
            title = "삼성은 어떻게 강팀이 되었는가",
            author = "야빠어디가",
            content = "ㅈㄱㄴ",
            image = "https://www.instagram.com/samsunglions_baseballclub/p/DBTFl4MvBlf/",
            likes = 0,
            dislikes = 0,
            comment = emptyMap()
        ),
        Post(
            category = "LCK",
            title = "T1, 페이커의 놀라운 경기력",
            author = "eSportsFan",
            content = "T1의 전략과 페이커의 역할",
            image = "https://www.example.com/image2.jpg",
            likes = 10,
            dislikes = 1
        ),
        Post(
            category = "EPL",
            title = "리버풀의 새로운 시즌 준비",
            author = "FootballLover",
            content = "리버풀의 새로운 시즌을 위한 전술",
            image = "https://www.example.com/image3.jpg",
            likes = 15,
            dislikes = 2
        )
        // 이런 형식의 Post를 realtime DB에 추가하고, 거기 자체에서 수정해서 글을 만들었음.
    )
    //글쓰기 기능 확장을 위한, 그리고 json 형태 추가를 위한 addPost 정의.
    fun addPost() {
        newPost.forEach { post->
            repository.addPost(post)
            //Log.e("post추가","${post}")
        }
    }

    fun loadPosts() {
        repository.getPost(
            callback = {posts ->
                _posts.value = posts
            }
        )

    }





}