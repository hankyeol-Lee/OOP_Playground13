package com.example.oop_project.Model

data class Post(
    val category : String = "",
    val title :String = "",
    val author : String = "",
    val content : String = "",
    val image: String? = null,
    val authorImage : String = "",
    val comment: MutableMap<String, postComment> = mutableMapOf() // K, V가 있는 map으로 댓글 정의, 변경 가능하도록. mutablemap 으로 변경.
)

data class postComment( // 확장 가능하도록 댓글 기능 추가
    val author :String = "",
    val authorImage:String = "",
    val content:String = "",
)