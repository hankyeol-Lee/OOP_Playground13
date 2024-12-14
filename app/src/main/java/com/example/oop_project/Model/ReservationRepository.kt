package com.example.oop_project.Model
import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

data class Reservation(
    val place: String = "",
    val date: String = "",
    val startTime: String = "",
    val endTime: String = ""
)

data class Place(
    val title: String,
    val address: String,
    val category: String,
    val mapx : Int,
    val mapy : Int
)
data class SearchViewItem(
    var placeTitle: String = "",
    var placeAddress: String = "",
    var placeCategory: String = "",
    var placeMapX: Int = 0,
    var placeMapY: Int = 0
)

data class SearchResponse(
    val items : List<Place>
)

class ReservationRepository {
    private val database = FirebaseDatabase.getInstance().getReference("reservations")

    fun saveReservation(reservation: Reservation, onComplete: (Boolean) -> Unit) {
        val reservationId = database.push().key
        reservationId?.let {
            database.child(it).setValue(reservation).addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
        } ?: onComplete(false)
    }

    fun checkReservationOverlap(
        newReservation: Reservation,
        onResult: (Boolean) -> Unit
    ) {
        database.get().addOnSuccessListener { snapshot ->
            val existingReservations = snapshot.children.mapNotNull { it.getValue(Reservation::class.java) }

            val isOverlapping = existingReservations.any { existing ->
                existing.place == newReservation.place &&
                        existing.date == newReservation.date &&
                        !(newReservation.endTime <= existing.startTime || newReservation.startTime >= existing.endTime)
            }

            onResult(isOverlapping)
        }.addOnFailureListener { exception ->
            Log.e("ReservationRepository", "Failed to check overlap: ${exception.message}")
            onResult(false)
        }
    }

    fun getReservationsForDate(place: String, date: String, onResult: (List<Reservation>) -> Unit) {
        database.orderByChild("place").equalTo(place).get().addOnSuccessListener { snapshot ->
            val reservations = snapshot.children.mapNotNull { it.getValue(Reservation::class.java) }
                .filter { it.date == date }
            onResult(reservations)
        }.addOnFailureListener { exception ->
            Log.e("ReservationRepository", "Failed to fetch reservations: ${exception.message}")
            onResult(emptyList())
        }
    }
}

class PlaceRepository {

    suspend fun searchPlaces(searchWord: String): List<Place> = withContext(Dispatchers.IO) {
        val clientId = "1HrT0QePI7N6dZbrwXUb"
        val clientSecretKey = "0ltKmtrzaf"
        val display: Int = 5

        try {
            // 1. 검색어 URL 인코딩
            val text = URLEncoder.encode(searchWord, "UTF-8")
            val apiURL = "https://openapi.naver.com/v1/search/local?query=$text&display=$display&"
            val url = URL(apiURL)
            val con = url.openConnection() as HttpURLConnection
            con.requestMethod = "GET"
            con.setRequestProperty("X-Naver-Client-Id", clientId)
            con.setRequestProperty("X-Naver-Client-Secret", clientSecretKey)

            // 2. 응답 읽기
            val responseCode = con.responseCode
            val response = if (responseCode == 200) {
                con.inputStream.bufferedReader().use { it.readText() }
            } else {
                con.errorStream.bufferedReader().use { it.readText() }
            }
            con.disconnect()

            val gson = Gson()
            val searchResponse = gson.fromJson(response, SearchResponse::class.java)
            Log.d("SearchResult", "$searchResponse")
            return@withContext searchResponse.items // items 리스트 반환
        } catch (e: JsonSyntaxException) {
            Log.d("PlaceRepository", "JSON 파싱 오류: $e")
            return@withContext emptyList()
        } catch (e: Exception) {
            Log.d("PlaceRepository", "네트워크 오류: $e")
            return@withContext emptyList()
        }
    }
}