package com.example.oop_project

import repository.PostRepository
import androidx.lifecycle.ViewModel
//ViewModel을 상속받는 클래스 CommunityPostViewModel
class CommunityPostViewModel :ViewModel() {
    private val repository = PostRepository()

    fun addPost(post:Post) {
        repository.addPost(post)
    }

}