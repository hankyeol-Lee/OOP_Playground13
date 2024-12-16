package com.example.oop_project.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oop_project.Model.Place
import com.example.oop_project.Model.PlaceRepository
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.launch

class PlaceViewModel(private val repository: PlaceRepository) : ViewModel() {

    private val _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>> get() = _places

    private val _coordinates = MutableLiveData<LatLng?>()
    val coordinates: LiveData<LatLng?> get() = _coordinates

    fun searchPlaces(searchWord: String) {
        viewModelScope.launch {
            val results = repository.searchPlaces(searchWord)
            _places.value = results
        }
    }

    fun fetchCoordinatesByAddress(roadAddress: String) {
        viewModelScope.launch {
            val result = repository.getCoordinatesByAddress(roadAddress)
            _coordinates.value = result
        }
    }
}