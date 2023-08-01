package com.expedia.homeawayinterviewchallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expedia.homeawayinterviewchallenge.api.repository.foursquare.FoursquareRepository
import com.expedia.homeawayinterviewchallenge.ui.state.DetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val foursquareRepository: FoursquareRepository,
    id: String
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailsState())
    val uiState: StateFlow<DetailsState> = _uiState.asStateFlow()

    private fun details(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            _uiState.value =
                _uiState.value.copy(currentDetailsResponse = foursquareRepository.details(id))
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    init {
        details(id)
    }
}