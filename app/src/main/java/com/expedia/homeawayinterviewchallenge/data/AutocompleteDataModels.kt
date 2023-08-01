package com.expedia.homeawayinterviewchallenge.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import kotlinx.serialization.Serializable

@Serializable
data class AutocompleteResponse(
    val results: List<Suggestion>
)

@Serializable
data class Suggestion(
    val text: Name
)

@Serializable
data class Name(
    val primary: String
)

suspend fun AutocompleteResponse.getSuggestions() = withContext(Dispatchers.Default) {
    yield()
    results.map { it.text.primary }.distinct()
}