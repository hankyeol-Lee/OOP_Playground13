package com.example.oop_project.Model

import com.google.firebase.database.FirebaseDatabase

// 데이터 모델 클래스: 장소 사용 정보를 저장
data class Usage(
    var backgroundImg: String? = "",   // 배경 이미지 URL
    var placeImg: String? = "",        // 장소 이미지 URL
    var placeName: String? = "",       // 장소 이름
    var placeTime: String? = "",       // 장소 사용 시간
    var placeCapacity: String? = "",   // 장소 수용 인원
    var faceImg: String? = ""          // 얼굴 이미지 URL
)

// 장소 사용 정보를 가져오는 리포지토리 클래스
class UsageRepository {
    // Firebase 실시간 데이터베이스 인스턴스 생성
    private val db = FirebaseDatabase.getInstance()

    //장소 사용 데이터를 Firebase에서 가져오는 함수
    fun fetchUsages(places: List<String>, onSuccess: (List<Usage>) -> Unit, onFailure: (Exception) -> Unit) {
        val usages = mutableListOf<Usage>() // 가져온 장소 사용 데이터를 저장할 리스트
        var counter = 0 // 비동기 작업 완료를 체크하기 위한 카운터

        // 장소 리스트를 순회하면서 Firebase에서 각 장소의 데이터를 가져옴
        for (place in places) {
            val usageReference = db.getReference("Usage/$place") // 장소 사용 데이터 참조 경로 설정
            usageReference.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) { // 데이터가 존재하는지 확인
                    val usage = snapshot.getValue(Usage::class.java) // 데이터를 Usage 객체로 변환

                    if (usage != null)
                        usages.add(usage) // 가져온 데이터를 리스트에 추가
                }

                counter++ // 작업 완료 시 카운터 증가

                // 모든 장소의 데이터를 가져오면 성공 콜백 호출
                if (counter == places.size) {
                    onSuccess(usages)
                }
            }.addOnFailureListener { exception ->
                counter++ // 실패 시에도 카운터 증가

                // 모든 작업이 완료되면 실패 콜백 호출
                if (counter == places.size) {
                    onFailure(exception)
                }
            }
        }
    }
}
