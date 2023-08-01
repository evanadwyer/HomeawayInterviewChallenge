package com.expedia.homeawayinterviewchallenge.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val metersToMiles = 6.213712e-4

@Serializable
data class SearchResponse(
    val results: List<Place>
)

@Serializable
data class Place(
    @SerialName("fsq_id") val id: String,
    val categories: List<Category>,
    val distance: Int,
    val name: String,
)

@Serializable
data class Category(
    val name: String,
    val icon: Icon
)

@Serializable
data class Icon(
    val prefix: String,
    val suffix: String
)

suspend fun SearchResponse.getListings() = withContext(Dispatchers.Default) {
    yield()
    results.filter { it.categories.isNotEmpty() }
}

fun Int.toMiles() = this * metersToMiles