package com.expedia.homeawayinterviewchallenge.api.repository.foursquare

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val AUTHORIZATION_HEADER = "Authorization"
// TODO: Use Play Integration API for App Attestation
//  key is personal one generated for Foursquare Places API
private const val API_KEY = ""
private const val HOST = "api.foursquare.com"
private const val PATH = "v3/"
private val urlProtocol = URLProtocol.HTTPS

class FoursquareClient {
    val client = HttpClient(Android) {
        install(DefaultRequest) {
            header(AUTHORIZATION_HEADER, API_KEY)
            url {
                protocol = urlProtocol
                host = HOST
                path(PATH)
            }
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
}