package com.example.oop_project.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oop_project.Model.Game
import com.example.oop_project.Model.GameRepository

class GameViewModel: ViewModel() {
    private val repository = GameRepository()

    private val _games = MutableLiveData<List<Game>>()
    val games: LiveData<List<Game>> get() = _games

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun loadGames() {
        repository.fetchGames(
            onSuccess = { games -> _games.value = games },
            onFailure = { exception -> _error.value = exception.message }
        )
    }
}