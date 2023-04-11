package com.example.mynounou.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CalendarViewModel @Inject internal constructor(
) : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    private var _selectionFlow  = MutableStateFlow<List<PlannedDay>>(emptyList())

    private val _isLoading = MutableStateFlow(true)

    val selectionFlow = _selectionFlow.asStateFlow()
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()



    @RequiresApi(Build.VERSION_CODES.O)
    fun getDay(day : LocalDate) : PlannedDay {
        // try to search day if exist or create
        var plannedDay = PlannedDay(day, PlannedHours("", ""))
        return _selectionFlow.value.filter { it.date == day }.firstOrNull()?: plannedDay
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateDay (plannedDay :PlannedDay){
        _selectionFlow.value.find { it.date == plannedDay.date }?.plannedPeriod?.startHour = plannedDay.plannedPeriod.startHour
        _selectionFlow.value.find { it.date == plannedDay.date }?.plannedPeriod?.endHour = plannedDay.plannedPeriod.endHour
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun removeEventDay(plannedDay :PlannedDay){
        _selectionFlow.value = _selectionFlow.value.toMutableList().also { list ->
            if (list.contains(plannedDay)) {
                list.remove(plannedDay)
            }
        }
        Log.d("", "remove")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addEventDay(plannedDay :PlannedDay){
        _selectionFlow.value = _selectionFlow.value.toMutableList().also { list ->
            if (list.contains(plannedDay)) {
                list.remove(plannedDay)
            }
            list.add(plannedDay)
        }

        Log.d("", "add")
    }

    fun initialize() {
        viewModelScope.launch() {
            _isLoading.emit(true)
            _selectionFlow.value = listbegin.value
            _isLoading.emit(false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val listbegin = MutableStateFlow(
        listOf(
            PlannedDay(LocalDate.now().plusDays(1), PlannedHours("20.0", "10.0")),
            PlannedDay(LocalDate.now().plusDays(3), PlannedHours("20.0", "10.0")),
            PlannedDay(LocalDate.now().plusDays(5), PlannedHours("20.0", "10.0")),
            PlannedDay(LocalDate.now().plusDays(-2), PlannedHours("20.0", "10.0")),
        )
    )
}

data class PlannedDay(
    var date: LocalDate,
    var plannedPeriod: PlannedHours
)

data class PlannedHours(
    var startHour: String,
    var endHour: String,
)
