package com.maguro.terebi.ui.screens.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.Channel
import com.maguro.terebi.data.model.ScheduleItem
import com.maguro.terebi.domain.GetGroupedScheduleUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Locale

class ScheduleViewModel(
    private val getGroupedScheduleUseCase: GetGroupedScheduleUseCase
) : ViewModel() {

    private val _schedule = MutableStateFlow<State>(State.Idle)
    private var loadingJob: Job? = null
    var date: LocalDate = LocalDate.now()
        private set

    val schedule: StateFlow<State>
        get() = _schedule

    init {
        reloadSchedule()
    }

    fun setDate(date: LocalDate) {
        if (loadingJob?.isActive == true) {
            loadingJob?.cancel()
        }
        this.date = date
        reloadSchedule()
    }

    fun reloadSchedule() {

        if (_schedule.value == State.Loading)
            return

        loadingJob = viewModelScope.launch {
            _schedule.value = State.Loading

            val result = getGroupedScheduleUseCase(
                date = date,
                countryCode = Locale.US.country
            )

            _schedule.value = when (result) {
                is RequestResponse.Success -> {
                    State.Success(result.data)
                }
                is RequestResponse.Error -> {
                    State.Error(result)
                }
            }
        }
    }

    sealed interface State {
        object Idle : State
        object Loading : State
        data class Success(val data: Map<Channel, List<ScheduleItem>>): State
        data class Error(val error: RequestResponse.Error): State
    }

}