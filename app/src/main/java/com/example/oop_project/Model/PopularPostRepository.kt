package com.example.oop_project.Model

import com.google.firebase.database.FirebaseDatabase

// 데이터 모델 클래스: 인기 게시글의 제목과 카테고리를 저장
data class PopularPost(
    val title: String? = "",   // 게시글 제목
    val category: String? = "" // 게시글 카테고리
)

// 인기 게시글 데이터를 가져오는 리포지토리 클래스
class PopularPostRepository {
    // Firebase 실시간 데이터베이스 인스턴스 생성
    private val db = FirebaseDatabase.getInstance()

    //인기 게시글 데이터를 Firebase에서 가져오는 함수
    fun fetchPopularPosts(posts: List<String>, onSuccess: (List<PopularPost>) -> Unit, onFailure: (Exception) -> Unit) {
        val popularPosts = mutableListOf<PopularPost>() // 가져온 게시글 데이터를 저장할 리스트
        var counter = 0 // 비동기 작업 완료를 체크하기 위한 카운터

        // 게시글 ID 리스트를 순회하면서 Firebase에서 각 게시글 데이터를 가져옴
        for (post in posts) {
            val postReference = db.getReference("posts/$post") // 게시글 데이터 참조 경로 설정
            postReference.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) { // 데이터가 존재하는지 확인
                    val popularPost = snapshot.getValue(PopularPost::class.java) // 데이터를 PopularPost 객체로 변환

                    if (popularPost != null)
                        popularPosts.add(popularPost) // 가져온 데이터를 리스트에 추가
                }

                counter++ // 작업 완료 시 카운터 증가

                // 모든 게시글 데이터 가져오기가 완료되면 성공 콜백 호출
                if (counter == posts.size) {
                    onSuccess(popularPosts)
                }
            }.addOnFailureListener { exception ->
                counter++ // 실패 시에도 카운터 증가

                // 모든 작업이 완료되면 실패 콜백 호출
                if (counter == posts.size) {
                    onFailure(exception)
                }
            }
        }
    }
}
