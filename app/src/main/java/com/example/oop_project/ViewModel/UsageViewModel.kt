package com.example.oop_project.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oop_project.Model.Usage
import com.example.oop_project.Model.UsageRepository

class UsageViewModel: ViewModel() {
    private val repository = UsageRepository()

    private val _usages = MutableLiveData<List<Usage>>()
    val usages: LiveData<List<Usage>> get() = _usages

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun loadUsages(places: List<String>) {
        repository.fetchUsages(places,
            onSuccess = { usages -> _usages.value = usages },
            onFailure = { exception -> _error.value = exception.message }
        )
    }
}