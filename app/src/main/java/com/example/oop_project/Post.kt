package com.example.oop_project

import org.w3c.dom.Comment

data class Post(
    val category : String = "",
    val title :String = "",
    val author : String = "",
    val content : String = "",
    val image: String? = null,
    val likes: Int = 0,
    val dislikes: Int = 0,
    val comment: Map<String,Comment> = emptyMap() // K, V가 있는 map으로 댓글 정의
)

data class Comment( // 확장 가능하도록 댓글 기능 추가..?
    val author :String = "",
    val content:String = "",
    val timePassed : Long = 0L
)