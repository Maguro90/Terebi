package com.maguro.terebi.ui.screens.show_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maguro.terebi.data.IdDeserializer
import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.ScheduleItem
import com.maguro.terebi.domain.GetScheduleItemDetailsUseCase
import com.maguro.terebi.ui.navigation.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    stateHandle: SavedStateHandle,
    idDeserializer: IdDeserializer,
    private val getScheduleItemDetailsUseCase: GetScheduleItemDetailsUseCase
) : ViewModel() {

    private val scheduleItemId =
        stateHandle
            .get<String>(Route.ShowDetails.Args.ScheduleItemId.pathName)
            ?.let { idDeserializer(it) }
            ?: throw IllegalArgumentException("scheduleItemId argument is expected")

    private val _details = MutableStateFlow<State>(State.Idle)

    val details: StateFlow<State>
        get() = _details

    init {
        reloadDetails()
    }

    fun reloadDetails() {

        viewModelScope.launch {

            _details.value = State.Loading

            val response = getScheduleItemDetailsUseCase(
                scheduleItemId = scheduleItemId
            )

            _details.value = when (response) {
                is RequestResponse.Success -> {
                    State.Success(response.data)
                }
                is RequestResponse.Error -> {
                    State.Error(response)
                }
            }
        }

    }

    sealed interface State {
        object Idle: State
        object Loading: State
        data class Success(val data: ScheduleItem?): State
        data class Error(val error: RequestResponse.Error): State
    }

}