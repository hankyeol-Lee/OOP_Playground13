package com.example.oop_project

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.oop_project.databinding.ActivityReserveMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;

class reserveMain : AppCompatActivity() {
    private val information = arrayListOf("전체보기", "풋살장", "탁구장", "볼링장")
    private lateinit var binding: ActivityReserveMainBinding

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

        initViewPager()
    }

    private fun initViewPager() {
        val reserveAdapter = reserveVPAdapter(this)
        binding.reserveVP.adapter = reserveAdapter

        // TabLayoutMediator로 탭 설정 (TabItem을 사용하지 않고 직접 텍스트를 설정)
        TabLayoutMediator(binding.reserveTL, binding.reserveVP) { tab, position ->
            tab.text = information[position]
        }.attach()
    }
}
