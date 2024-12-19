package com.example.oop_project.Model

import com.google.firebase.database.FirebaseDatabase

// 데이터 모델 클래스: 게임 정보 저장
data class Game(
    var team1: String? = "",      // 팀 1의 이름
    var team2: String? = "",      // 팀 2의 이름
    var team1Img: String? = "",   // 팀 1의 이미지 URL
    var team2Img: String? = "",   // 팀 2의 이미지 URL
    var score: String? = ""       // 경기 점수
)

// 게임 데이터를 가져오는 리포지토리 클래스
class GameRepository {
    // Firebase 실시간 데이터베이스 인스턴스 생성
    private val db = FirebaseDatabase.getInstance()

    //경기 데이터를 Firebase에서 가져오는 함수
    fun fetchGames(dates: List<String>, onSuccess: (List<Game>) -> Unit, onFailure: (Exception) -> Unit) {
        val games = mutableListOf<Game>() // 가져온 게임 데이터를 저장할 리스트
        var counter = 0 // 비동기 작업 완료를 체크하기 위한 카운터

        // 날짜 리스트를 순회하면서 Firebase에서 각 날짜에 해당하는 게임 데이터를 가져옴
        for (date in dates) {
            val gameReference = db.getReference("RecentGame/$date") // 게임 데이터 참조 경로 설정
            gameReference.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) { // 데이터가 존재하는지 확인
                    val game = snapshot.getValue(Game::class.java) // 데이터를 Game 객체로 변환

                    if (game != null)
                        games.add(game) // 가져온 데이터를 리스트에 추가
                }

                counter++ // 작업 완료 시 카운터 증가

                // 모든 날짜의 데이터를 가져오면 성공 콜백 호출
                if (counter == dates.size) {
                    onSuccess(games)
                }
            }.addOnFailureListener { exception ->
                counter++ // 실패 시에도 카운터 증가

                // 모든 작업이 완료되면 실패 콜백 호출
                if (counter == dates.size) {
                    onFailure(exception)
                }
            }
        }
    }
}
