package com.maguro.terebi.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.Show
import com.maguro.terebi.domain.SearchShowsUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchShowsUseCase: SearchShowsUseCase
) : ViewModel() {

    private var searchJob: Job? = null

    private val _searchHistory = ArrayDeque<String>()
    val searchHistory: List<String> = _searchHistory

    private val _state = MutableStateFlow<State>(State.Idle)

    val state: StateFlow<State>
        get() = _state

    private val _query = MutableStateFlow("")

    val query: StateFlow<String>
        get() = _query

    fun updateQuery(query: String) {
        _query.value = query
    }

    fun search(historyEntryIndex: Int? = null) {

        val query = if (historyEntryIndex != null) {
            _searchHistory.moveToFirst(historyEntryIndex)
                .also { updateQuery(it) }
        } else {
            _searchHistory.addOrMoveToFirst(_query.value)
        }

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            _state.value = State.Loading

            val response = searchShowsUseCase(query)

            _state.value = when (response) {
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
        data class Success(val data: List<Show>): State
        data class Error(val cause: RequestResponse.Error): State
    }

    private fun ArrayDeque<String>.moveToFirst(index: Int): String {
        return removeAt(index).also { entry -> addFirst(entry) }
    }

    private fun ArrayDeque<String>.addOrMoveToFirst(query: String): String {

        if (query.isBlank())
            return query

        indexOf(query).takeIf { it >= 0 }
            ?.let {
                moveToFirst(it)
            }
            ?: let {
                addFirst(query)
            }

        return query
    }
}