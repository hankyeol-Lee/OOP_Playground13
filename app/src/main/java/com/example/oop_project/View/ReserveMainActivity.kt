package com.example.oop_project.View

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oop_project.R
import com.example.oop_project.ViewModel.PlaceViewModel
import com.example.oop_project.databinding.ActivityReserveMainBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource

class ReserveMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReserveMainBinding
    private lateinit var mRecyclerView: RecyclerView
    private val viewModel: PlaceViewModel by viewModels()
    private lateinit var recyclerAdapter: SearchViewAdaptor
    private lateinit var naverMap: NaverMap
    var camX = 0.0
    var camY = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReserveMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RecyclerView 초기화
        mRecyclerView = findViewById(R.id.search_recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = SearchViewAdaptor { place ->
            // 아이템 클릭 이벤트 처리
            val intent = Intent(this, ReserveDetailActivity::class.java).apply {
                putExtra("title", place.placeTitle)
                putExtra("address", place.placeAddress)
                putExtra("category", place.placeCategory)
            }
            startActivity(intent)
        }
        mRecyclerView.adapter = recyclerAdapter

        setupObservers()
        setupListeners()
        initNaverMap()
        // 초기 검색
        viewModel.searchPlaces("구미 볼링장")
    }

    private fun setupObservers() {
        // 장소 리스트 옵저버
        viewModel.places.observe(this) { places ->

            recyclerAdapter.clear()
            for (place in places) {
                recyclerAdapter.addItem(place.title, place.address, place.category)
                displayMarker(viewModel.convertCoordinates(place.mapx,place.mapy))
                camX += place.mapx
                camY += place.mapy
            }
        }

    }

    private fun setupListeners() {
        // 검색창 동작 설정
        binding.adresssearch.isSubmitButtonEnabled = true
        binding.adresssearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchPlaces(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun initNaverMap() {
        binding.navermap.getMapAsync { map ->
            naverMap = map
            naverMap.cameraPosition = CameraPosition(LatLng(camX/5,camY/5),10.0)
            naverMap.uiSettings.isZoomControlEnabled = true
            naverMap.uiSettings.isLocationButtonEnabled = true
            naverMap.locationTrackingMode = LocationTrackingMode.Follow

        }
    }

    private fun displayMarker(latLng : LatLng) {
        val cameraupdate = CameraUpdate.scrollTo(latLng)
        naverMap.moveCamera(cameraupdate)
        val marker = Marker()
        marker.position = latLng
        marker.map = naverMap
    }




}