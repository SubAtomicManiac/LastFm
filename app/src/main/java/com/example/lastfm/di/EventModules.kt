package com.example.lastfm.di

import com.example.lastfm.controller.LastFmController
import com.example.lastfm.domain.LastFm
import com.example.lastfm.event.*
import com.example.lastfm.presenter.LastFmPresenter
import com.example.lastfm.viewmodel.TrackListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

//This file gives a brief overview of the entire applications operations split into Controller, Domain, Presenter and ViewModel

val controllerModule = module {
    single(createdAtStart=true) {
        LastFmController()
    }
}

val domainModule = module {
    single(createdAtStart=true){
        LastFm(get()).apply {
            TrackClicked.event.registerDomain(::fetchTrack)
            TracksSearched.event.registerDomain(::fetchTracks)
        }
    }
}
val presenterModule = module {
    single(createdAtStart=true) {
        LastFmPresenter().apply {
            TracksSearched.event.registerPresenter(::mapTracksToViewModel)
            TrackClicked.event.registerPresenter(::mapTrackDetailToViewModel)
        }
    }
}

val viewModelModule = module {
    viewModel {
        TrackListViewModel().apply {
            subscribe(TrackClicked.event, ::updateTrackDetails)
            subscribe(TrackDetailsMinimized.event, ::hideTrackDetails)
            subscribe(TracksSearched.event, ::updateTracks)
            subscribe(Loading.event, ::beginLoading)
        }
    }
}
