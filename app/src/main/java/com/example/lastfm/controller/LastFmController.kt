package com.example.lastfm.controller

import com.example.lastfm.domain.entities.track.IndividualSearch
import com.example.lastfm.domain.entities.tracks.TrackOrder.*
import com.example.lastfm.domain.entities.tracks.TrackSearchOrdered
import com.example.lastfm.event.Loading
import com.example.lastfm.event.TrackClicked
import com.example.lastfm.event.TrackDetailsMinimized
import com.example.lastfm.event.TracksSearched
import com.example.lib.EventController

//Controller initiates every event in the application

class LastFmController : EventController(){

    fun trackMinimised(){
        TrackDetailsMinimized.event.publishEvent()
    }

    fun searchTracks(track: String?, orderString: String){
        if (track != "") track?.also{
            Loading.event.publishEvent()
            val order = when (orderString){
                TRACK.name -> TRACK
                ARTIST.name -> ARTIST
                LISTENERS.name -> LISTENERS
                else -> DEFAULT
            }
            TracksSearched.event.publishEvent(TrackSearchOrdered(it, order))
        }
    }

    fun fetchTrack(track: String, artist: String){
        Loading.event.publishEvent()
        TrackClicked.event.publishEvent(IndividualSearch(track,artist))
    }
}
