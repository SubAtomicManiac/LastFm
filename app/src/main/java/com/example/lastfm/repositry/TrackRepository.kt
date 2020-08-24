package com.example.lastfm.repositry

import android.util.Log
import com.example.lastfm.domain.entities.track.IndividualTrackEntity
import com.example.lastfm.domain.entities.tracks.TrackMatches
import com.example.lastfm.domain.entities.tracks.TrackOrder
import com.example.lastfm.domain.entities.tracks.TrackOrder.*
import com.example.lastfm.domain.entities.tracks.TrackSearchEntity
import com.example.lastfm.network.LastFmNetworkService
import com.example.lastfm.persistence.*

class TrackRepository(
    private val networkService: LastFmNetworkService,
    private val defaultOrderDatabase: TrackSearchDefaultOrderDatabase,
    private val trackOrderDatabase: TrackSearchTrackOrderDatabase,
    private val artistOrderDatabase: TrackSearchArtistOrderDatabase,
    private val listenersOrderDatabase: TrackSearchListenersOrderDatabase,
    private val individualTrackDatabase: IndividualTrackDatabase
) {
    suspend fun fetchIndividualTrackAsync(track: String, artist: String) : IndividualTrackEntity{
        val localIndividualTrack = individualTrackDatabase.individualTrackDao.getLocalTrackByIDAsync(track+artist)
        return localIndividualTrack ?: getNetworkIndividualTrackAsync(track,artist)
    }

    private suspend fun getNetworkIndividualTrackAsync(track: String, artist: String) : IndividualTrackEntity{
        val individualNetworkTrack = networkService.fetchTrackAsync(track,artist)
        val individualTrackEntity = IndividualTrackEntity(track+artist, individualNetworkTrack.track)
        individualTrackDatabase.individualTrackDao.insertAll(individualTrackEntity)
        return individualTrackEntity
    }

    suspend fun fetchTracksAsync(track: String, order: TrackOrder) : TrackSearchEntity? {
        val localTracks = getLocalTracksAsync(track,order)
        return localTracks ?: getNetworkTracksAsync(track, order)
    }

    private suspend fun getLocalTracksAsync(track: String, order: TrackOrder) = when (order) {
        DEFAULT -> defaultOrderDatabase.trackSearchDao.getLocalTracksByNameAsync(track)
        TRACK -> trackOrderDatabase.trackSearchDao.getLocalTracksByNameAsync(track)
        ARTIST -> artistOrderDatabase.trackSearchDao.getLocalTracksByNameAsync(track)
        LISTENERS -> listenersOrderDatabase.trackSearchDao.getLocalTracksByNameAsync(track)
    }

    private fun storeLocalTracksAsync(track: TrackSearchEntity, order: TrackOrder) = when (order) {
        DEFAULT -> defaultOrderDatabase.trackSearchDao.insertAll(track)
        TRACK -> trackOrderDatabase.trackSearchDao.insertAll(sortByTrack(track))
        ARTIST -> artistOrderDatabase.trackSearchDao.insertAll(sortByArtist(track))
        LISTENERS -> listenersOrderDatabase.trackSearchDao.insertAll(sortByListeners(track))
    }

    private fun sortByTrack(track: TrackSearchEntity): TrackSearchEntity{
        val sortedTracks = track.trackMatches.track.sortedBy { trackToSort -> trackToSort.track }
        return TrackSearchEntity(track.trackSearch_id, TrackMatches(sortedTracks))
    }

    private fun sortByArtist(track: TrackSearchEntity): TrackSearchEntity {
        val sortedTracks = track.trackMatches.track.sortedBy { trackToSort -> trackToSort.artist }
        return TrackSearchEntity(track.trackSearch_id, TrackMatches(sortedTracks))
    }

    private fun sortByListeners(track: TrackSearchEntity): TrackSearchEntity{
        val sortedTracks = track.trackMatches.track.sortedBy { trackToSort -> trackToSort.listeners }
        return TrackSearchEntity(track.trackSearch_id, TrackMatches(sortedTracks))
    }

    private suspend fun getNetworkTracksAsync(track: String, order: TrackOrder) : TrackSearchEntity? {
        val networkTracks = networkService.fetchTracksAsync(track)
        val trackMatches = networkTracks.results.trackMatches
        val trackSearchEntity = if (trackMatches.track.isNotEmpty()){
            TrackSearchEntity(track, trackMatches)
        } else {
            null
        }
        trackSearchEntity?.also { storeLocalTracksAsync(it, order) }
        return trackSearchEntity
    }

}
