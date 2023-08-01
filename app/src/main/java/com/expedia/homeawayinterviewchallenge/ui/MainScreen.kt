package com.expedia.homeawayinterviewchallenge.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.PopupProperties
import coil.compose.AsyncImage
import com.expedia.homeawayinterviewchallenge.R
import com.expedia.homeawayinterviewchallenge.data.Category
import com.expedia.homeawayinterviewchallenge.data.Place
import com.expedia.homeawayinterviewchallenge.data.toMiles
import com.expedia.homeawayinterviewchallenge.viewmodel.SearchViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Main(
    onPlaceClicked: (String) -> Unit,
    viewModel: SearchViewModel = koinViewModel()
) {
    val currentSearchState by viewModel.uiState.collectAsState()
    val currentAutocompleteState by viewModel.autocompleteState.collectAsState()

    val listing = currentSearchState.currentSearchListings

    BackHandler(enabled = listing.isNotEmpty()) {
        viewModel.clearSearchResponse()
    }

    when {
        currentSearchState.isLoading -> CircularProgressIndicator()
        listing.isNotEmpty() -> SearchListings(
            listing,
            onPlaceClicked
        )

        else -> SearchField(
            suggestions = currentAutocompleteState.currentAutocompleteSuggestions,
            queryCategory = currentSearchState.currentQueryCategory,
            onQueryChange = viewModel::updateQueryCategory,
            onSubmitQuery = viewModel::search
        )
    }
}

@Composable
fun SearchListings(
    places: List<Place>,
    onPlaceClicked: (String) -> Unit
) {
    LazyColumn {
        itemsIndexed(places) { index, place ->
            SearchPlaceCard(
                index = index + 1,
                place = place,
                onPlaceClicked = onPlaceClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchPlaceCard(
    index: Int,
    place: Place,
    onPlaceClicked: (String) -> Unit
) {
    val foursquareIconSize = "bg_88"
    Card(
        onClick = { onPlaceClicked.invoke(place.id) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.search_listing_padding))
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.search_listing_padding))
        ) {
            Text(text = "$index.)")
            AsyncImage(
                model = "${place.categories[0].icon.prefix}$foursquareIconSize${place.categories[0].icon.suffix}",
                placeholder = painterResource(id = R.drawable.baseline_change_circle_64),
                contentDescription = "place icon"
            )
            Column {
                Text(
                    text = place.name,
                    style = MaterialTheme.typography.h6
                )
                Text(text = place.categories.first().name)
                Text(text = "%.1f mi".format(place.distance.toMiles()))
            }
        }
    }
}

@Composable
fun SearchField(
    suggestions: List<String>,
    queryCategory: String,
    onQueryChange: (String) -> Unit,
    onSubmitQuery: () -> Unit
) {
    Column {
        OutlinedTextField(
            value = queryCategory,
            onValueChange = {
                onQueryChange(it)
            },
            label = { Text(text = stringResource(R.string.query_prompt)) },
            trailingIcon = {
                if (queryCategory.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear, contentDescription = stringResource(
                                R.string.clear_icon
                            )
                        )
                    }
                }
            },
            isError = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onSubmitQuery()
                }
            ),
            modifier = Modifier
        )
        DropdownMenu(
            expanded = suggestions.isNotEmpty(),
            onDismissRequest = { },
            properties = PopupProperties(focusable = false)
        ) {
            suggestions.forEach { suggestion ->
                DropdownMenuItem(onClick = {
                    onQueryChange(suggestion)
                    onSubmitQuery()
                }) {
                    Text(text = suggestion)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSearchPlaceCard() {
    SearchPlaceCard(
        index = 1,
        place = Place(
            id = ",",
            categories = listOf(
                Category(
                    name = "Category",
                    com.expedia.homeawayinterviewchallenge.data.Icon(
                        prefix = "",
                        suffix = ""
                    )
                )
            ),
            distance = 0,
            name = "Place"
        ),
        onPlaceClicked = { })
}