package com.expedia.homeawayinterviewchallenge.di

import com.expedia.homeawayinterviewchallenge.api.repository.foursquare.FoursquareClient
import com.expedia.homeawayinterviewchallenge.api.repository.foursquare.FoursquareRepository
import com.expedia.homeawayinterviewchallenge.api.repository.foursquare.FoursquareRepositoryImpl
import com.expedia.homeawayinterviewchallenge.viewmodel.DetailsViewModel
import com.expedia.homeawayinterviewchallenge.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::SearchViewModel) // NOTE: issue with koin and interface on viewmodels
    viewModelOf(::DetailsViewModel)
    singleOf(::FoursquareRepositoryImpl) { bind<FoursquareRepository>() }
    singleOf(::FoursquareClient)
}