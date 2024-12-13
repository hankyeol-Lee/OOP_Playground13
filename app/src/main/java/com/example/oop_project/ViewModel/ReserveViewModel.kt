package com.example.oop_project.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oop_project.Model.Reservation
import com.example.oop_project.Model.ReservationRepository

class ReserveViewModel(private val repository: ReservationRepository) : ViewModel() {

    private val _reservationStatus = MutableLiveData<Boolean>()
    val reservationStatus: LiveData<Boolean> get() = _reservationStatus

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> get() = _selectedDate

    fun setSelectedDate(date: String) {
        _selectedDate.value = date
    }

    fun reservePlace(place: String, date: String, startTime: String, endTime: String) {
        if (date.isNotEmpty() && startTime.isNotEmpty() && endTime.isNotEmpty()) {
            val reservation = Reservation(place, date, startTime, endTime)
            repository.saveReservation(reservation) { isSuccess ->
                _reservationStatus.value = isSuccess
            }
        } else {
            _reservationStatus.value = false
        }
    }
}