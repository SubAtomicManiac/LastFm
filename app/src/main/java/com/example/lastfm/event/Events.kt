package com.example.lastfm.event

import com.example.lastfm.domain.entities.track.IndividualSearch
import com.example.lastfm.domain.entities.track.IndividualTrackEntity
import com.example.lastfm.domain.entities.tracks.TrackSearchEntity
import com.example.lastfm.domain.entities.tracks.TrackSearchOrdered
import com.example.lastfm.viewmodel.TrackDetailViewModel
import com.example.lib.ControllerFullEvent
import com.example.lastfm.viewmodel.TrackViewModel
import com.example.lib.UIEvent

//Key part of the event driven architecture
//Summarizes the functionality of the application while remaining type safe
//Each event is created with the type variables:
//  -First for the type the controller publishes
//  -Secondly for the type the domain/model publishes
//  -Thirdly for the type the presenter publishes to the viewModel
//By default the controllers value is passed from the controller, to domain, to presenter to viewModel
//So it's not necessary to add a domain or presenter to pass a value straight to the viewModel

object TracksSearched{val event = ControllerFullEvent.create<TrackSearchOrdered, TrackSearchEntity, List<TrackViewModel>>()}
//UIEvents will bypass the domain and presenter and only publish to a view model
object TrackClicked{val event = ControllerFullEvent.create<IndividualSearch, IndividualTrackEntity, TrackDetailViewModel>()}
object TrackDetailsMinimized{val event = UIEvent.create<Any?>()}
object Loading{val event = UIEvent.create<Any?>()}

