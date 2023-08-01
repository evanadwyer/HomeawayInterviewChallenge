package com.expedia.homeawayinterviewchallenge.ui.state

import com.expedia.homeawayinterviewchallenge.data.Place

data class SearchState(
    val currentQueryCategory: String = "",
    val currentSearchListings: List<Place> = listOf(),
    val isLoading: Boolean = false
)