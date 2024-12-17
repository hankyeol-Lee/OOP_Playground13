package com.example.oop_project.Model

import com.google.firebase.database.FirebaseDatabase

data class Game(
    var team1: String? = "",
    var team2: String? = "",
    var team1Img: String? = "",
    var team2Img: String? = "",
    var score: String? = ""
)

class GameRepository {
    private val db = FirebaseDatabase.getInstance()
    private val gameReference = db.getReference("RecentGame/2024-10-28")

    fun fetchGames(onSuccess: (List<Game>) -> Unit, onFailure: (Exception) -> Unit) {
        gameReference.get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val game = snapshot.getValue(Game::class.java)
                    if (game != null) {
                        onSuccess(listOf(game))
                    } else {
                        onFailure(Exception("No games found"))
                    }
                } else {
                    onFailure(Exception("No games found"))
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}