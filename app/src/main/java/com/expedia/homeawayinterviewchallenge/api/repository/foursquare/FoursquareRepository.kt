package com.expedia.homeawayinterviewchallenge.api.repository.foursquare

import com.expedia.homeawayinterviewchallenge.data.AutocompleteResponse
import com.expedia.homeawayinterviewchallenge.data.DetailsResponse
import com.expedia.homeawayinterviewchallenge.data.SearchResponse

interface FoursquareRepository {
    suspend fun search(category: String): SearchResponse
    suspend fun autocomplete(category: String): AutocompleteResponse
    suspend fun details(id: String): DetailsResponse
}