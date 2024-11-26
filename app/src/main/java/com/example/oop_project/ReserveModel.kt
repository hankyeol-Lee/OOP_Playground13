package com.example.oop_project

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

// Reservation 데이터 클래스
data class Reservation(
    val place: String = "",
    val date: String = "",
    val startTime: String = "",
    val endTime: String = ""
)

// Repository 클래스: API 호출과 데이터 처리
class NaverSearchRepository {
    private val clientId = "1HrT0QePI7N6dZbrwXUb"
    private val clientSecretKey = "0ltKmtrzaf"

    fun searchNaver(searchWord: String, display: Int = 5, callback: (List<NaverPlace>) -> Unit) {
        Thread {
            try {
                val text = URLEncoder.encode(searchWord, "UTF-8")
                val apiURL =
                    "https://openapi.naver.com/v1/search/local?query=$text&display=$display&"
                val url = URL(apiURL)
                val con = url.openConnection() as HttpURLConnection
                con.requestMethod = "GET"
                con.setRequestProperty("X-Naver-Client-Id", clientId)
                con.setRequestProperty("X-Naver-Client-Secret", clientSecretKey)

                val responseCode = con.responseCode
                val br = if (responseCode == 200) {
                    BufferedReader(InputStreamReader(con.inputStream))
                } else {
                    BufferedReader(InputStreamReader(con.errorStream))
                }

                val searchResult = StringBuilder()
                br.use { reader ->
                    reader.forEachLine {
                        searchResult.append(it).append("\n")
                    }
                }
                con.disconnect()

                // 파싱 로직
                val places = parseNaverResult(searchResult.toString())
                callback(places)
            } catch (e: Exception) {
                Log.e("NaverSearchRepository", "Error: $e")
            }
        }.start()
    }

    private fun parseNaverResult(data: String): List<NaverPlace> {
        val array = data.split("\"")
        val places = mutableListOf<NaverPlace>()
        var title = ""
        var address = ""
        var category = ""

        for (i in array.indices) {
            when (array[i]) {
                "title" -> title = array[i + 2]
                "roadAddress" -> address = array[i + 2]
                "category" -> {
                    category = array[i + 2]
                    places.add(NaverPlace(title, address, category))
                }
            }
        }
        return places
    }
}

// Naver 검색 결과를 나타내는 데이터 클래스
data class NaverPlace(val title: String, val address: String, val category: String)
