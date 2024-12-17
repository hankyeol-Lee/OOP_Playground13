package com.example.oop_project.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oop_project.Model.Game
import com.example.oop_project.Model.GameRepository

// 게임 데이터를 관리하는 ViewModel 클래스
class GameViewModel: ViewModel() {
    private val repository = GameRepository() // 게임 데이터를 가져오는 레포지토리 객체

    // 게임 리스트를 저장하고 관찰할 수 있는 LiveData 객체
    private val _games = MutableLiveData<List<Game>>()
    val games: LiveData<List<Game>> get() = _games // 외부에서 읽기만 가능하게 제공

    // 에러 메시지를 저장하고 관찰할 수 있는 LiveData 객체
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error // 외부에서 읽기만 가능하게 제공

    // 게임 데이터를 로드하는 함수
    fun loadGames(dates: List<String>) {
        // 레포지토리에서 게임 데이터를 가져오고 성공/실패 콜백 처리
        repository.fetchGames(dates,
            onSuccess = { games ->
                _games.value = games // 성공 시 게임 리스트를 LiveData에 설정
            },
            onFailure = { exception ->
                _error.value = exception.message // 실패 시 에러 메시지를 LiveData에 설정
            }
        )
    }
}
