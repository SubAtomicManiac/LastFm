package com.example.lastfm.domain

import com.example.lastfm.domain.entities.track.IndividualSearch
import com.example.lastfm.domain.entities.track.IndividualTrackEntity
import com.example.lastfm.domain.entities.tracks.TrackOrder.DEFAULT
import com.example.lastfm.domain.entities.tracks.TrackSearchEntity
import com.example.lastfm.domain.entities.tracks.TrackSearchOrdered
import com.example.lastfm.repositry.TrackRepository

class LastFm(private val trackRepository: TrackRepository) {

    suspend fun fetchTracks(search : TrackSearchOrdered?) : TrackSearchEntity? {
        return trackRepository.fetchTracksAsync(search?.track ?: "", search?.order ?: DEFAULT)
    }

    suspend fun fetchTrack(search: IndividualSearch?) : IndividualTrackEntity {
        return trackRepository.fetchIndividualTrackAsync(search?.track ?: "",search?.artist ?: "")
    }

}
