package com.expedia.homeawayinterviewchallenge.api.repository.foursquare

import com.expedia.homeawayinterviewchallenge.data.AutocompleteResponse
import com.expedia.homeawayinterviewchallenge.data.DetailsResponse
import com.expedia.homeawayinterviewchallenge.data.SearchResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val LAT_LONG_TAG = "ll"
private const val LAT_LONG_VALUE = "47.6062,-122.3321"
private const val FIELDS_TAG = "fields"
private const val FIELDS_VALUE_SEARCH = "fsq_id,categories,distance,name"
private const val FIELDS_VALUE_DETAILS = "name,geocodes"
private const val LIMIT_TAG = "limit"
private const val LIMIT_VALUE_SEARCH = "50"
private const val LIMIT_VALUE_AUTOCOMPLETE = "5"
private const val QUERY_TAG = "query"

class FoursquareRepositoryImpl(
    foursquareClient: FoursquareClient
) : FoursquareRepository {
    private val httpClient = foursquareClient.client
    override suspend fun search(category: String): SearchResponse = withContext(Dispatchers.IO) {
        httpClient.get("places/search") {
            parameter(LAT_LONG_TAG, LAT_LONG_VALUE)
            parameter(FIELDS_TAG, FIELDS_VALUE_SEARCH)
            parameter(LIMIT_TAG, LIMIT_VALUE_SEARCH)
            parameter(QUERY_TAG, category)
        }.body()
    }

    override suspend fun autocomplete(category: String): AutocompleteResponse = withContext(Dispatchers.IO) {
        httpClient.get("autocomplete") {
            parameter(LAT_LONG_TAG, LAT_LONG_VALUE)
            parameter(LIMIT_TAG, LIMIT_VALUE_AUTOCOMPLETE)
            parameter(QUERY_TAG, category)
        }.body()
    }

    override suspend fun details(id: String): DetailsResponse = withContext(Dispatchers.IO) {
        httpClient.get("places/$id") {
            parameter(FIELDS_TAG, FIELDS_VALUE_DETAILS)
        }.body()
    }
}