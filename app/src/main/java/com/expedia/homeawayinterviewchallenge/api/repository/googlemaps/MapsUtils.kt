package com.expedia.homeawayinterviewchallenge.api.repository.googlemaps

import com.expedia.homeawayinterviewchallenge.data.LatLong
import com.expedia.homeawayinterviewchallenge.data.parameterize
import io.ktor.http.Parameters
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol

private const val HOST = "maps.googleapis.com"
private const val SIZE_TAG = "size"
private const val SIZE_VALUE = "400x400"
private const val SCALE_TAG = "scale"
private const val SCALE_VALUE = "2"
private const val MARKERS_TAG = "markers"
private const val MARKERS_VALUE_SEATTLE = "color:green|label:C|47.6062,-122.3321"
private const val MARKERS_VALUE_DESTINATION = "color:red|label:D|%s"
private const val KEY_TAG = "key"
// TODO: Use Play Integration API for App Attestation
//  key is Android Key (auto created by Google Service) from HomeAway Traveler App
//  it is unrestricted
//  https://console.cloud.google.com/apis/credentials?project=homeaway.com:api-project-42464624060&supportedpurview=project
private const val KEY_VALUE = ""

private val urlProtocol = URLProtocol.HTTPS
private val PATH = listOf("maps", "api", "staticmap")

fun getStaticMapUrl(latLong: LatLong): String {
    return URLBuilder(
        protocol = urlProtocol,
        host = HOST,
        pathSegments = PATH,
        parameters = Parameters.build {
            append(SIZE_TAG, SIZE_VALUE)
            append(SCALE_TAG, SCALE_VALUE)
            append(MARKERS_TAG, MARKERS_VALUE_SEATTLE)
            append(MARKERS_TAG, String.format(MARKERS_VALUE_DESTINATION, latLong.parameterize()))
            append(KEY_TAG, KEY_VALUE)
        }
    ).buildString()
}