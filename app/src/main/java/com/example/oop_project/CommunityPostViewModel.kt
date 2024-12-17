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
            image = "https://firebasestorage.googleapis.com/v0/b/oop-playground-605da.firebasestorage.app/o/kboimage.png?alt=media&token=93fd7956-e8ff-4720-981c-495664d3beac",
            likes = 0,
            dislikes = 0,
            comment = mutableMapOf(
                "comment_1" to postComment(author = "익명1", content = "당신 LG 팬이지"),
                "comment_2" to postComment(author = "익명2", content = "부두술 걸지 마세요")
            )
        ),
        Post(
            category = "LCK",
            title = "T1, 페이커의 놀라운 경기력",
            author = "eSportsFan",
            content = "T1의 전략과 페이커의 역할",
            image = "https://firebasestorage.googleapis.com/v0/b/oop-playground-605da.firebasestorage.app/o/t1image.webp?alt=media&token=fc63a65e-f7e4-4a47-b1c2-5f655c366092",
            likes = 10,
            dislikes = 1,
            comment = mutableMapOf(
                "comment_1" to postComment(author = "익명1", content = "페이커 대단해요."),
                "comment_2" to postComment(author = "T1 Fan", content = "T1 우승 가즈아!")
            )
        ),
        Post(
            category = "EPL",
            title = "리버풀의 새로운 시즌 준비",
            author = "FootballLover",
            content = "리버풀의 새로운 시즌을 위한 전술",
            image = "https://firebasestorage.googleapis.com/v0/b/oop-playground-605da.firebasestorage.app/o/t1image.webp?alt=media&token=fc63a65e-f7e4-4a47-b1c2-5f655c366092",
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