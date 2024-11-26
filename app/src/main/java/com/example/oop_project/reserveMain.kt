package com.example.oop_project

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.oop_project.databinding.ActivityReserveMainBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class reserveMain : AppCompatActivity() {
    private lateinit var binding: ActivityReserveMainBinding

    private var placetitle: Array<String> = Array(5) { "" }
    private var placeaddress: Array<String> = Array(5) { "" }
    private var placecategory: Array<String> = Array(5) { "" }

    private lateinit var mListView: ListView

    private var itemCount: Int = 0
    private val SearchViewAdaptor = SearchViewAdaptor()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ViewBinding 초기화
        binding = ActivityReserveMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // WindowInsets 설정
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        searchNaver("행신 골프장")
        mListView = findViewById(R.id.SearchListView)
        mListView.setOnItemClickListener { _, _, position, _ ->
            val title = SearchViewAdaptor.getItem(position).placeTitle
            val address = SearchViewAdaptor.getItem(position).placeAddress
            val intent = Intent(applicationContext, reserveDetail::class.java).apply {
                putExtra("title", title)
                putExtra("address", address)
            }
            startActivity(intent)
        }
    }
    private fun searchNaver(searchWord : String)
    {
        val clientId = "1HrT0QePI7N6dZbrwXUb"
        val clientSecretKey = "0ltKmtrzaf"
        val display : Int = 5
        Thread {
            try {
                val text = URLEncoder.encode(searchWord, "UTF-8")
                val apiURL = "https://openapi.naver.com/v1/search/local?query=$text&display=$display&" // json 결과
                val url = URL(apiURL)
                val con = url.openConnection() as HttpURLConnection
                con.requestMethod = "GET"
                con.setRequestProperty("X-Naver-Client-Id", clientId)
                con.setRequestProperty("X-Naver-Client-Secret", clientSecretKey)

                val responseCode = con.responseCode
                val br = if (responseCode == 200) { // 정상 호출
                    BufferedReader(InputStreamReader(con.inputStream))
                } else { // 에러 발생
                    BufferedReader(InputStreamReader(con.errorStream))
                }

                val searchResult = StringBuilder()
                br.use { reader ->
                    reader.forEachLine {
                        searchResult.append(it).append("\n")
                    }
                }
                con.disconnect()

                val data = searchResult.toString()
                val array = data.split("\"")
                itemCount = 0
                for (i in array.indices) {
                    if (itemCount >= placetitle.size) break
                    when (array[i]) {
                        "title" -> placetitle[itemCount] = array[i + 2]
                        "roadAddress" -> placeaddress[itemCount] = array[i + 2]
                        "category" -> {
                            placecategory[itemCount] = array[i + 2]
                            itemCount++
                        }
                    }
                }


                // 결과를 UI Thread에서 listView에 데이터 추가
                runOnUiThread {
                    listViewDataAdd()
                }
            } catch (e: Exception) {
                Log.d("TAG", "error: $e")
            }
        }.start()
    }
    private fun listViewDataAdd() {
        for (i in 0 until itemCount) {
            SearchViewAdaptor.addItem(
                Html.fromHtml(placetitle[i], Html.FROM_HTML_MODE_LEGACY).toString(),
                Html.fromHtml(placeaddress[i], Html.FROM_HTML_MODE_LEGACY).toString(),
                Html.fromHtml(placecategory[i], Html.FROM_HTML_MODE_LEGACY).toString(),
            )
        }
        // Adapter 설정
        mListView.adapter = SearchViewAdaptor
    }

}