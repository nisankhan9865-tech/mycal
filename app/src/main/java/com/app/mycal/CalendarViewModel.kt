package com.app.mycal

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

class CalendarViewModel : ViewModel() {

    private val _currentMonth = MutableStateFlow(LocalDate.now())
    val currentMonth: StateFlow<LocalDate> = _currentMonth

    private val _selectedDate = MutableStateFlow<LocalDate?>(null)
    val selectedDate: StateFlow<LocalDate?> = _selectedDate

    fun setCurrentMonth(month: LocalDate) {
        _currentMonth.value = month
    }

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }
}