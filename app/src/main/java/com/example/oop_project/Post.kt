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
    val comment: Map<String,Comment> = emptyMap()
)

data class Comment(
    val author :String = "",
    val content:String = "",
    val timePassed : Long = 0L
)