package com.example.oop_project.ViewModel
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oop_project.Model.Reservation
import com.example.oop_project.Model.ReservationRepository


class ReserveViewModel() : ViewModel() {
    private val repository: ReservationRepository = ReservationRepository()
    private val _reservedTimes = MutableLiveData<List<String>>()
    val reservedTimes: LiveData<List<String>> get() = _reservedTimes

    fun saveReserve(context: Context, reservation: Reservation){
        repository.saveReservation(context, reservation)
    }

    fun fetchReservedTimeSlots(place: String, date: String) {
        repository.fetchReservedTimeSlots(
            place,
            date,
            onSuccess = { reservedTimes ->
                _reservedTimes.value = reservedTimes
            },
            onFailure = { exception ->
                _reservedTimes.value = emptyList()
            }
        )
    }



}