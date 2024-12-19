package com.example.oop_project.ViewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import repository.PostRepository
import androidx.lifecycle.ViewModel
import com.example.oop_project.Model.Post
import com.example.oop_project.Model.postComment

//ViewModel을 상속받는 클래스 CommunityPostViewModel
class CommunityPostViewModel :ViewModel() {
    private val repository = PostRepository()

    private val _posts = MutableLiveData<List<Post>>() //내부 livedata
    val posts : LiveData<List<Post>> get() = _posts


    val newPost = listOf(
        Post(
            category = "KBO", // 카테고리
            title = "삼성은 어떻게 강팀이 되었는가",
            author = "최강LG",
            authorImage = "https://firebasestorage.googleapis.com/v0/b/oop-playground-605da.firebasestorage.app/o/lg_logo.png?alt=media&token=0408007e-4acf-48c3-a70c-6b74434ed992",
            content = "삼.어.강. 내년에 삼성의 건투를 빕니다",
            image = "https://firebasestorage.googleapis.com/v0/b/oop-playground-605da.firebasestorage.app/o/kboimage.png?alt=media&token=93fd7956-e8ff-4720-981c-495664d3beac",
            comment = mutableMapOf(
                "comment_1" to postComment(author = "익명", content = "당신 LG 팬이지", authorImage =
                "https://firebasestorage.googleapis.com/v0/b/oop-playground-605da.firebasestorage.app/o/samsung_logo.png?alt=media&token=c159816e-88cd-4493-88b3-4d80b6eed3aa"
                ),
                "comment_2" to postComment(author = "익명", content = "부두술 걸지 마세요",
                    authorImage =
                    "https://firebasestorage.googleapis.com/v0/b/oop-playground-605da.firebasestorage.app/o/samsung_logo.png?alt=media&token=c159816e-88cd-4493-88b3-4d80b6eed3aa"
                )
            )
        ),
        Post(
            category = "KBO", // 카테고리
            title = "의외로 계엄보다 우승 주기가 긴 팀..real",
            author = "야구안봅니다",
            authorImage = "https://firebasestorage.googleapis.com/v0/b/oop-playground-605da.firebasestorage.app/o/lottelogo.png?alt=media&token=30b86e69-1359-44bc-b9ac-7c9021441b94",
            content = "롯 데 자 이 언 츠 ",
            image =
            "https://firebasestorage.googleapis.com/v0/b/oop-playground-605da.firebasestorage.app/o/lottelogo.png?alt=media&token=30b86e69-1359-44bc-b9ac-7c9021441b94",
            comment = mutableMapOf(
                "comment_1" to postComment(author = "익명", content = "야이매국노야"),
                "comment_2" to postComment(author = "익명", content = "롯데 이번엔 다릅니다 한번만 기대해보세요")
            )
        ),
        Post(
            category = "KBO", // 카테고리
            title = "나..20대..청년인데..",
            author = "나는행복합니다",
            authorImage = "https://firebasestorage.googleapis.com/v0/b/oop-playground-605da.firebasestorage.app/o/logo_chick.webp?alt=media&token=9a182fe2-e1d7-4492-a193-15c56454e7ac",
            content = "동년배들..다들 한화 응원한다..",
            image = "https://firebasestorage.googleapis.com/v0/b/oop-playground-605da.firebasestorage.app/o/kboimage.png?alt=media&token=93fd7956-e8ff-4720-981c-495664d3beac",
            comment = mutableMapOf(
                "comment_1" to postComment(author = "익명", content = "이대남은 그런 말투 안써요"),
                "comment_2" to postComment(author = "익명", content = "역시 최강한화")
            )
        ),
        Post(
            category = "KBO",
            title = "내년 KBO 우승은",
            author = "eSportsFan",
            content = "진지하게 어디일까",
            image = "https://firebasestorage.googleapis.com/v0/b/oop-playground-605da.firebasestorage.app/o/t1image.webp?alt=media&token=fc63a65e-f7e4-4a47-b1c2-5f655c366092",
            comment = mutableMapOf(
                "comment_1" to postComment(author = "익명", content = "LG 가자"),
                "comment_2" to postComment(author = "익명", content = "롯데는 언제하냐")
            )
        ),
        Post(
            category = "EPL",
            title = "리버풀의 새로운 시즌 준비",
            author = "FootballLover",
            content = "리버풀의 새로운 시즌을 위한 전술",
            image = "https://firebasestorage.googleapis.com/v0/b/oop-playground-605da.firebasestorage.app/o/t1image.webp?alt=media&token=fc63a65e-f7e4-4a47-b1c2-5f655c366092",

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