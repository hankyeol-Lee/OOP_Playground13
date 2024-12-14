package com.example.oop_project.ViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.oop_project.Model.PlaceRepository

class PlaceViewModelFactory(private val repository: PlaceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaceViewModel::class.java)) {
            return PlaceViewModel(repository) as T
        }
        throw IllegalArgumentException()
    }
}