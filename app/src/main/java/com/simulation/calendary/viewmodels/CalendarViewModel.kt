package com.simulation.calendary.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simulation.calendary.apis.RetrofitHelper
import com.simulation.calendary.models.RequestBody
import com.simulation.calendary.models.TaskDetail
import com.simulation.calendary.models.TaskResponse
import com.simulation.calendary.repositories.CalendarRepository
import com.simulation.calendary.utils.Constants
import kotlinx.coroutines.launch
import retrofit2.Response
import java.time.LocalDate

class CalendarViewModel() : ViewModel() {

    private val _selectedDate = MutableLiveData<LocalDate>()
    val selectedDate: LiveData<LocalDate>
        get() = _selectedDate

    init {
        _selectedDate.value = LocalDate.now()
    }

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun storeCalendarTask(task: TaskDetail){
        val repository = CalendarRepository()
        viewModelScope.launch {
            repository.storeTask(task)
        }
    }
}
