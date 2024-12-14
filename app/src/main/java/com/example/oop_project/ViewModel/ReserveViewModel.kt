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

    private val _isOverlapping = MutableLiveData<Boolean>()
    val isOverlapping: LiveData<Boolean> get() = _isOverlapping

    fun setSelectedDate(date: String) {
        _selectedDate.value = date
    }

    fun reservePlace(place: String, date: String, startTime: String, endTime: String) {
        if (date.isNotEmpty() && startTime.isNotEmpty() && endTime.isNotEmpty()) {
            val reservation = Reservation(place, date, startTime, endTime)
            repository.checkReservationOverlap(reservation) { overlap ->
                if (!overlap) {
                    // 중복되지 않으면 예약 진행
                    repository.saveReservation(reservation) { isSuccess ->
                        _reservationStatus.value = isSuccess
                    }
                } else {
                    // 중복인 경우 예약 실패 상태 업데이트
                    _reservationStatus.value = false
                }
            }
        } else {
            _reservationStatus.value = false
        }
    }

    fun checkDuplication(place: String, date: String, startTime: String, endTime: String) {
        val reservation = Reservation(place, date, startTime, endTime)
        repository.checkReservationOverlap(reservation) { overlap ->
            _isOverlapping.value = overlap
        }
    }
}