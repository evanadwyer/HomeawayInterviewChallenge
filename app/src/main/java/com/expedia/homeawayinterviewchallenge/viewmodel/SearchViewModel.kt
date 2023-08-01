package com.expedia.homeawayinterviewchallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expedia.homeawayinterviewchallenge.api.repository.foursquare.FoursquareRepository
import com.expedia.homeawayinterviewchallenge.data.getListings
import com.expedia.homeawayinterviewchallenge.data.getSuggestions
import com.expedia.homeawayinterviewchallenge.ui.state.AutocompleteState
import com.expedia.homeawayinterviewchallenge.ui.state.SearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

class SearchViewModel(
    private val foursquareRepository: FoursquareRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchState())
    val uiState: StateFlow<SearchState> = _uiState.asStateFlow()

    private val _autocompleteState = MutableStateFlow(AutocompleteState())
    val autocompleteState: StateFlow<AutocompleteState> = _autocompleteState.asStateFlow()

    // use SupervisorJob to cancel autocomplete jobs in case of empty query
    private var autocompleteJob = Dispatchers.IO + SupervisorJob()

    fun search() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            _uiState.value =
                _uiState.value.copy(
                    currentSearchListings = foursquareRepository.search(_uiState.value.currentQueryCategory)
                        .getListings()
                )
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    private fun autocomplete() {
        if (_uiState.value.currentQueryCategory.isEmpty()) {
            autocompleteJob.cancelChildren()
            _autocompleteState.value =
                _autocompleteState.value.copy(currentAutocompleteSuggestions = listOf())
        } else {
            viewModelScope.launch(autocompleteJob) {
                yield()
                _autocompleteState.value = _autocompleteState.value.copy(
                    currentAutocompleteSuggestions = foursquareRepository.autocomplete(_uiState.value.currentQueryCategory)
                        .getSuggestions()
                )
            }
        }
    }

    fun clearSearchResponse() {
        _uiState.value = _uiState.value.copy(
            currentSearchListings = listOf()
        )
    }

    fun updateQueryCategory(category: String) {
        _uiState.value = _uiState.value.copy(currentQueryCategory = category)
        autocomplete()
    }
}