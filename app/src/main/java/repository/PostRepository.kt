package repository

import android.renderscript.Sampler.Value
import com.google.firebase.database.FirebaseDatabase
import com.example.oop_project.Post // 이거 MVVM 폴더로 구분할때 변경해주어야 함. Post가 있는쪽으로!
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class PostRepository {
    private val database = FirebaseDatabase.getInstance()
    private val postRef = database.getReference("posts") // posts라는 노드를 참조.

    fun addPost(post:Post) {
        val key = postRef.push().key // postRef에 있는 key를 자동으로 생성.
        if (key == null) { //early return
            return
        }
        postRef.child(key).setValue(post) // posts 노드 아래에 고유 키를 지정 -> 이제 하위 데이터 저장 가능
    }

    //firebase에서 Post 데이터를 가져와야함. < 어떻게?
    fun getPost(callback: (List<Post>) -> Unit) {
        postRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //데이터를 업데이트 해줘야 함. 어떤 데이터를 update?
                val postList = mutableListOf<Post>() // Post가 들어가지는 리스트 정의
                for (postvalue in snapshot.children) {
                    val post = postvalue.getValue(Post::class.java) //
                    if (post != null) postList.add(post)
                }
                callback(postList) // 함수 호출 후 이 postList를 반환.여기에 글 목록이 들어있음.
            }
            override fun onCancelled(error: DatabaseError) {
                //아직안됨.
            }
        })
    }
}