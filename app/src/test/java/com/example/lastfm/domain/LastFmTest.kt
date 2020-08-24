package com.example.lastfm.domain

import com.example.lastfm.domain.entities.track.IndividualSearch
import com.example.lastfm.domain.entities.track.IndividualTrackEntity
import com.example.lastfm.domain.entities.tracks.TrackOrder.DEFAULT
import com.example.lastfm.domain.entities.tracks.TrackSearchEntity
import com.example.lastfm.domain.entities.tracks.TrackSearchOrdered
import com.example.lastfm.repositry.TrackRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

class LastFmTest {

    private val trackRepository : TrackRepository = mockk()

    private val lastFm = LastFm(trackRepository)

    private val trackSearchEntityMock : TrackSearchEntity = mockk()
    private val individualTrackEntityMock : IndividualTrackEntity = mockk()

    @Test
    fun `fetch tracks fetches all searched tracks`() {
        coEvery { trackRepository.fetchTracksAsync(EXPECTED_TRACK, EXPECTED_ORDER) } returns trackSearchEntityMock
        val trackSearchEntity = runBlocking { lastFm.fetchTracks(expectedTrackSearch) }
        assertEquals(trackSearchEntityMock, trackSearchEntity)
        coVerify { trackRepository.fetchTracksAsync(EXPECTED_TRACK, EXPECTED_ORDER) }
    }

    @Test
    fun `fetch track fetches individual track`(){
        coEvery { trackRepository.fetchIndividualTrackAsync(EXPECTED_TRACK, EXPECTED_ARTIST) } returns individualTrackEntityMock
        val individualTrackEntity = runBlocking { lastFm.fetchTrack(expectedIndividualSearch) }
        assertEquals(individualTrackEntityMock, individualTrackEntity)
        coVerify { trackRepository.fetchIndividualTrackAsync(EXPECTED_TRACK, EXPECTED_ARTIST) }
    }

    companion object {
        private const val EXPECTED_TRACK = "b"
        private val EXPECTED_ORDER = DEFAULT
        private const val EXPECTED_ARTIST = "c"
        private val expectedTrackSearch = TrackSearchOrdered(EXPECTED_TRACK, EXPECTED_ORDER)
        private val expectedIndividualSearch = IndividualSearch(EXPECTED_TRACK,EXPECTED_ARTIST)
    }
}
