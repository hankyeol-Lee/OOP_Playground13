package com.example.oop_project.ViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oop_project.Model.Place
import com.example.oop_project.Model.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PlaceViewModel(private val repository: PlaceRepository) : ViewModel() {

    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: StateFlow<List<Place>> get() = _places

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun searchPlaces(query: String) {
        viewModelScope.launch {
            try {
                val result = repository.searchPlaces(query)
                _places.value = result
            } catch (e: Exception) {
                _error.value = "Failed to load data"
            }
        }
    }
}