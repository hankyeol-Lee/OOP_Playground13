package repository

import com.google.firebase.database.FirebaseDatabase
import com.example.oop_project.Post // 이거 MVVM 폴더로 구분할때 변경해주어야 함. Post가 있는쪽으로!

class PostRepository {
    private val database = FirebaseDatabase.getInstance()
    private val postRef = database.getReference("posts") // post라는 노드를 참조.

    fun addPost(post:Post) {
        val key = postRef.push().key // postRef에 있는 key를 자동으로 생성.
        if (key == null) {
            return
        }
        postRef.child(key).setValue(post) // posts 노드 아래에 고유 키를 지정 -> 이제 하위 데이터 저장 가능
    }
}