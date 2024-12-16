package com.example.oop_project.View

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.oop_project.MainActivity
import com.example.oop_project.R
import com.example.oop_project.databinding.ActivityReserveCompleteBinding

class ReserveCompleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReserveCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReserveCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Intent로 전달된 데이터 가져오기
        val facilityName = intent.getStringExtra("facilityName") ?: "알 수 없음"
        val date = intent.getStringExtra("date") ?: "알 수 없음"
        val timeRange = intent.getStringExtra("timeRange") ?: "알 수 없음"

        // UI 업데이트
        binding.textFacilityName.text = "예약시설: $facilityName"
        binding.textReservationDate.text = "예약날짜: $date"
        binding.textReservationTime.text = "예약시간: $timeRange"

        // 닫기 버튼 클릭 이벤트
        binding.closeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java) // 현재 화면 종료
            startActivity(intent)
        }
    }
}