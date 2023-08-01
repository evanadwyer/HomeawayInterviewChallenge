package com.expedia.homeawayinterviewchallenge.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.expedia.homeawayinterviewchallenge.R
import com.expedia.homeawayinterviewchallenge.api.repository.googlemaps.getStaticMapUrl
import com.expedia.homeawayinterviewchallenge.data.LatLong
import com.expedia.homeawayinterviewchallenge.viewmodel.DetailsViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun Details(
    id: String,
    viewModel: DetailsViewModel = koinViewModel(parameters = { parametersOf(id) })
) {
    val currentState by viewModel.uiState.collectAsState()
    val isLoading = currentState.isLoading
    val name = currentState.currentDetailsResponse.name
    when {
        isLoading -> CircularProgressIndicator()
        name.isNotBlank() -> DetailsWithMap(
            name = name,
            latLong = currentState.currentDetailsResponse.geocodes.main
        )
    }
}

@Composable
fun DetailsWithMap(
    name: String,
    latLong: LatLong
) {
    // TODO: Display map as part of Collapsible TopAppBar
    //  Material3 does not easily allow for image in background
    Column {
        AsyncImage(
            model = getStaticMapUrl(latLong = latLong),
            placeholder = painterResource(id = R.drawable.center_of_seattle),
            contentDescription = "Map of Seattle",
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = name)
    }
}