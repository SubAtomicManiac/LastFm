package com.example.lastfm.controller

import com.example.lastfm.unittests.EventUnitTest
import com.example.lastfm.domain.entities.track.IndividualSearch
import com.example.lastfm.domain.entities.tracks.TrackOrder.DEFAULT
import com.example.lastfm.domain.entities.tracks.TrackSearchOrdered
import com.example.lastfm.event.Loading
import com.example.lastfm.event.TrackClicked
import com.example.lastfm.event.TrackDetailsMinimized
import com.example.lastfm.event.TracksSearched
import io.mockk.*
import org.junit.Test

import org.junit.Before

class LastFmControllerTest : EventUnitTest(){

    private lateinit var controller: LastFmController

    @Before
    fun setup(){
        controller = LastFmController()
    }

    @Test
    fun `searchTracks publishes loading and track request events meeting search criteria`() {
        mockPublishEvent(Loading.event)
        mockPublishEvent(TracksSearched.event)
        controller.searchTracks(EXPECTED_TRACK,EXPECTED_ORDER.name)
        verify{Loading.event.publishEvent()}
        verify{TracksSearched.event.publishEvent(expectedTrackSearch)}
    }

    @Test
    fun `searchTracks does not publish loading and track request events if track is null`() {
        mockPublishEvent(Loading.event)
        mockPublishEvent(TracksSearched.event)
        controller.searchTracks(EXPECTED_NULL_TRACK,EXPECTED_ORDER.name)
        verify(exactly = 0){Loading.event.publishEvent()}
        verify(exactly = 0){TracksSearched.event.publishEvent(any())}
    }

    @Test
    fun `searchTracks does not publish loading and track request events if track is empty string`() {
        mockPublishEvent(Loading.event)
        mockPublishEvent(TracksSearched.event)
        controller.searchTracks(EXPECTED_EMPTY_TRACK,EXPECTED_ORDER.name)
        verify(exactly = 0){Loading.event.publishEvent()}
        verify(exactly = 0){TracksSearched.event.publishEvent(expectedEmptyTrackSearch)}
    }

    @Test
    fun `fetchTrack publishes loading and detail track request events`() {
        mockPublishEvent(Loading.event)
        mockPublishEvent(TrackClicked.event, expectedIndividualSearch)
        controller.fetchTrack(EXPECTED_TRACK, EXPECTED_ARTIST)
        verify{Loading.event.publishEvent()}
        verify{TrackClicked.event.publishEvent(expectedIndividualSearch)}
    }

    @Test
    fun `trackMinimised publishes a minimize event`() {
        mockPublishEvent(TrackDetailsMinimized.event)
        controller.trackMinimised()
        verify{TrackDetailsMinimized.event.publishEvent()}
    }

    companion object {
        private const val EXPECTED_TRACK = "b"
        private val EXPECTED_NULL_TRACK = null
        private const val EXPECTED_EMPTY_TRACK = ""
        private val EXPECTED_ORDER = DEFAULT
        private val expectedTrackSearch = TrackSearchOrdered(EXPECTED_TRACK, EXPECTED_ORDER)
        private val expectedEmptyTrackSearch = TrackSearchOrdered(EXPECTED_EMPTY_TRACK, EXPECTED_ORDER)

        private const val EXPECTED_ARTIST = "c"
        private val expectedIndividualSearch = IndividualSearch(EXPECTED_TRACK, EXPECTED_ARTIST)
    }

}
