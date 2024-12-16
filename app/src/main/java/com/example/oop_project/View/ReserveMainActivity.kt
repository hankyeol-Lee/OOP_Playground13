package com.example.oop_project.View
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.oop_project.Model.Place
import com.example.oop_project.R
import com.example.oop_project.Model.PlaceRepository
import com.example.oop_project.ViewModel.PlaceViewModel
import com.example.oop_project.ViewModel.PlaceViewModelFactory
import com.example.oop_project.databinding.ActivityReserveMainBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker

class ReserveMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReserveMainBinding
    private lateinit var mListView: ListView
    private val viewModel: PlaceViewModel by viewModels { PlaceViewModelFactory(PlaceRepository()) }
    private lateinit var searchViewAdaptor: SearchViewAdaptor
    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReserveMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mListView = findViewById(R.id.SearchListView)
        searchViewAdaptor = SearchViewAdaptor()
        mListView.adapter = searchViewAdaptor

        setupObservers()
        setupListeners()
        initNaverMap()
        viewModel.searchPlaces("구미 볼링장")
    }

    private fun setupObservers() {
        viewModel.places.observe(this) { places ->
            // Adapter 데이터 초기화
            searchViewAdaptor.clear()

            // 새로운 데이터를 Adapter에 추가
            for (place in places) {
                searchViewAdaptor.addItem(place.title, place.address, place.category)
            }

            // Adapter에 데이터 변경 알림
            searchViewAdaptor.notifyDataSetChanged()
        }
        viewModel.places.observe(this) { places ->
            if (places.isEmpty()) {
                Toast.makeText(this, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                val roadAddress = places.first().address // 첫 번째 검색 결과 사용
                viewModel.fetchCoordinatesByAddress(roadAddress)
            }
        }

        viewModel.coordinates.observe(this) { coordinates ->
            if (coordinates != null) {
                displayMarker(coordinates)
            } else {
                Toast.makeText(this, "좌표를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupListeners() {
        mListView.setOnItemClickListener { _, _, position, _ ->
            val place = searchViewAdaptor.getItem(position)
            val intent = Intent(this, ReserveDetailActivity::class.java).apply {
                putExtra("title", place.placeTitle)
                putExtra("address", place.placeAddress)
                putExtra("category", place.placeCategory)
            }
            startActivity(intent)
        }
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

    private fun initNaverMap()
    {
        val map = binding.navermap.getMapAsync {map ->
            naverMap = map
            naverMap.uiSettings.isZoomControlEnabled = true
        }
    }

    private fun displayMarker(latLng: LatLng) {
        val cameraUpdate = CameraUpdate.scrollTo(latLng)
        naverMap.moveCamera(cameraUpdate)

        val marker = Marker()
        marker.position = latLng
        marker.map = naverMap
    }
}