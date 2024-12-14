package com.example.oop_project.ViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.oop_project.Model.ReservationRepository

class ReserveViewModelFactory(private val repository: ReservationRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReserveViewModel::class.java)) {
            return ReserveViewModel(repository) as T
        }
        throw IllegalArgumentException()
    }
}