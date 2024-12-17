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

    fun fetchGames(dates: List<String>, onSuccess: (List<Game>) -> Unit, onFailure: (Exception) -> Unit) {
        val games = mutableListOf<Game>()
        var counter = 0

        for (date in dates) {
            val gameReference = db.getReference("RecentGame/$date")
            gameReference.get().addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        val game = snapshot.getValue(Game::class.java)

                        if (game != null)
                            games.add(game)
                    }

                    counter++

                    if (counter == dates.size) {
                        onSuccess(games)
                    }
                }.addOnFailureListener { exception ->
                    counter++
                    if (counter == dates.size) {
                        onFailure(exception)
                    }
                }
        }
    }

}