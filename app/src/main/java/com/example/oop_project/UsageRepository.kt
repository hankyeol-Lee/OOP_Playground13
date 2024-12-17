package com.example.oop_project

import com.example.oop_project.Model.Game
import com.google.firebase.database.FirebaseDatabase

data class Usage(
    var backgroundImg: String? = "",
    var placeImg: String? = "",
    var placeName: String? = "",
    var placeTime: String? = "",
    var placeCapacity: String? = "",
    var faceImg: String? = ""
)

class UsageRepository {
    private val db = FirebaseDatabase.getInstance()

    fun fetchUsages(places: List<String>, onSuccess: (List<Usage>) -> Unit, onFailure: (Exception) -> Unit) {
        val usages = mutableListOf<Usage>()
        var counter = 0

        for (place in places) {
            val usageReference = db.getReference("Usage/$place")
            usageReference.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val usage = snapshot.getValue(Usage::class.java)

                    if (usage != null)
                        usages.add(usage)
                }

                counter ++

                if (counter == places.size) {
                    onSuccess(usages)
                }
            }.addOnFailureListener { exception ->
                counter ++
                if (counter == places.size){
                    onFailure(exception)
                }
            }
        }
    }
}