package com.example.oop_project.ViewModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oop_project.Model.Place
import com.example.oop_project.Model.PlaceRepository
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.launch
import kotlin.math.log

class PlaceViewModel() : ViewModel() {

    private val repository: PlaceRepository = PlaceRepository()
    private val _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>> get() = _places

    fun searchPlaces(searchWord: String) {
        viewModelScope.launch {
            val results = repository.searchPlaces(searchWord)
            _places.value = results
        }
    }

    fun convertCoordinates(mapx: Int, mapy: Int): LatLng {
        val mapxConverted = mapx / 1e7
        val mapyConverted = mapy / 1e7
        Log.d("coord",mapxConverted.toString())
        Log.d("coord",mapyConverted.toString())
        return LatLng(mapyConverted, mapxConverted)
    }

}