package com.expedia.homeawayinterviewchallenge.data

import kotlinx.serialization.Serializable

@Serializable
data class DetailsResponse(
    val name: String,
    val geocodes: GeoCodes
)

@Serializable
data class GeoCodes(
    val main: LatLong
)

@Serializable
data class LatLong(
    val latitude: Double,
    val longitude: Double
)

fun LatLong.parameterize() = "$latitude,$longitude"

object EmptyDetailsResponse {
    val detailsResponse = DetailsResponse(
        name = "",
        geocodes =
        GeoCodes(
            LatLong(
                47.6062,
                -122.3321
            )
        )
    )
}