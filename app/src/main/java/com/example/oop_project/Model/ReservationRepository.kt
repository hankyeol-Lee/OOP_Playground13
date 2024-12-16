package com.example.oop_project.Model
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
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
    val mapx: Int,
    val mapy: Int
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
    // Firebase Database Reference
    val database = Firebase.database.reference

    // 예약 저장 함수
    fun saveReservation(context: Context, reservation: Reservation) {
        val facilityName = reservation.place
        val reservationDate = reservation.date

        // Firebase 데이터 구조 경로
        val reservationRef = database.child("Reservation")
            .child(facilityName)
            .child(reservationDate)

        // 데이터 맵으로 저장
        val reservationData = mapOf(
            "startTime" to reservation.startTime,
            "endTime" to reservation.endTime
        )

        // 고유한 키 생성 후 데이터 저장
        val newReservationRef = reservationRef.push() // Firebase에서 고유 키 생성
        newReservationRef.setValue(reservationData)
            .addOnSuccessListener {
                Toast.makeText(context,"예약이 완료되었습니다!",Toast.LENGTH_LONG ).show()
            }
            .addOnFailureListener { exception ->
                Log.e("saveReservation", "Failed to save reservation", exception)
            }
    }

    fun fetchReservedTimeSlots(
        place: String,
        date: String,
        onSuccess: (List<String>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val ref = Firebase.database.reference.child("Reservation").child(place).child(date)

        ref.get().addOnSuccessListener { snapshot ->
            val reservedTimes = mutableListOf<String>()

            if (snapshot.exists()) {
                for (reservation in snapshot.children) {
                    val startTime = reservation.child("startTime").getValue(String::class.java)
                    val endTime = reservation.child("endTime").getValue(String::class.java)

                    if (startTime != null && endTime != null) {
                        reservedTimes.addAll(getTimeRange(startTime, endTime))
                    }
                }
            }

            Log.d("reservedTime", "Fetched times: $reservedTimes")
            onSuccess(reservedTimes)
        }.addOnFailureListener { exception ->
            Log.e("reservedTime", "Failed to fetch times", exception)
            onFailure(exception)
        }
    }

    // Helper 함수: 시간 범위 계산
    private fun getTimeRange(startTime: String, endTime: String): List<String> {
        val reservedTimes = mutableListOf<String>()
        val startHour = startTime.split(":")[0].toInt()
        val endHour = endTime.split(":")[0].toInt()

        for (hour in startHour..endHour) {
            reservedTimes.add(String.format("%02d:00", hour))
        }

        return reservedTimes
    }
}

class PlaceRepository {
    val clientId = "1HrT0QePI7N6dZbrwXUb"
    val clientSecretKey = "0ltKmtrzaf"

    suspend fun searchPlaces(searchWord: String): List<Place> = withContext(Dispatchers.IO) {
        try {
            val encodedQuery = URLEncoder.encode(searchWord, "UTF-8")
            val apiUrl =
                "https://openapi.naver.com/v1/search/local.json?query=$encodedQuery&display=5"
            val url = URL(apiUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("X-Naver-Client-Id", clientId)
            connection.setRequestProperty("X-Naver-Client-Secret", clientSecretKey)

            val response = connection.inputStream.bufferedReader().readText()
            connection.disconnect()

            val json = JSONObject(response)
            val items = json.getJSONArray("items")
            val results = mutableListOf<Place>()

            for (i in 0 until items.length()) {
                val item = items.getJSONObject(i)
                results.add(
                    Place(
                        title = item.getString("title"),
                        address = item.getString("roadAddress"),
                        category = item.getString("category"),
                        mapx = item.getInt("mapx"),
                        mapy = item.getInt("mapy")
                    )
                )
                Log.d("item",item.toString())
            }

            return@withContext results
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext emptyList()
        }
    }

    // Geocoding API 호출
    suspend fun getCoordinatesByAddress(roadAddress: String): LatLng? = withContext(Dispatchers.IO) {
        try {
            val encodedAddress = URLEncoder.encode(roadAddress, "UTF-8")
            val apiUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=$encodedAddress"
            val url = URL(apiUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId)
            connection.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecretKey)

            val response = connection.inputStream.bufferedReader().readText()
            connection.disconnect()

            val json = JSONObject(response)
            val addresses = json.getJSONArray("addresses")
            if (addresses.length() > 0) {
                val addressObject = addresses.getJSONObject(0)
                return@withContext LatLng(
                    addressObject.getString("y").toDouble(),
                    addressObject.getString("x").toDouble()

                )

            }
            return@withContext null
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }
}
