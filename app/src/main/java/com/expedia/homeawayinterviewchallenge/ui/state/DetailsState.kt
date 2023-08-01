package com.expedia.homeawayinterviewchallenge.ui.state

import com.expedia.homeawayinterviewchallenge.data.DetailsResponse
import com.expedia.homeawayinterviewchallenge.data.EmptyDetailsResponse

data class DetailsState(
    val currentDetailsResponse: DetailsResponse = EmptyDetailsResponse.detailsResponse,
    val isLoading: Boolean = false
)