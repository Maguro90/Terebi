package com.maguro.terebi.ui.screens.show_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maguro.terebi.data.IdDeserializer
import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.domain.GetShowWithEpisodeUseCase
import com.maguro.terebi.domain.ShowWithEpisode
import com.maguro.terebi.ui.navigation.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    stateHandle: SavedStateHandle,
    idDeserializer: IdDeserializer,
    private val getShowWithEpisodeUseCase: GetShowWithEpisodeUseCase
) : ViewModel() {

    private val showId =
        stateHandle
            .get<String>(Route.ShowDetails.Args.ShowId.pathName)
            ?.let { idDeserializer(it) }
            ?: throw IllegalArgumentException("showId argument is expected")

    private val episodeId =
        stateHandle
            .get<String>(Route.ShowDetails.Args.EpisodeId.pathName)
            ?.let { idDeserializer(it) }
            ?: throw IllegalArgumentException("episodeId argument is expected")

    private val _details = MutableStateFlow<State>(State.Idle)

    val details: StateFlow<State> = _details

    init {
        reloadDetails()
    }

    fun reloadDetails() {

        viewModelScope.launch {

            _details.value = State.Loading

            val response = getShowWithEpisodeUseCase(
                showId = showId,
                episodeId = episodeId
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
        data class Success(val data: ShowWithEpisode): State
        data class Error(val error: RequestResponse.Error): State
    }

}