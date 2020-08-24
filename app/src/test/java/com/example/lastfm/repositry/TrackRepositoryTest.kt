package com.example.lastfm.repositry

import com.example.lastfm.unittests.EventUnitTest
import com.example.lastfm.domain.entities.tracks.*
import com.example.lastfm.domain.entities.tracks.TrackOrder.DEFAULT
import com.example.lastfm.network.JSON
import com.example.lastfm.network.KEY
import com.example.lastfm.network.LastFmNetworkService
import com.example.lastfm.network.TRACK_SEARCH_METHOD
import com.example.lastfm.persistence.*
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class TrackRepositoryTest : EventUnitTest() {

    private val networkService: LastFmNetworkService = mockk()

    private val defaultOrderDatabase: TrackSearchDefaultOrderDatabase = mockk()
    private val trackOrderDatabase: TrackSearchTrackOrderDatabase = mockk()
    private val artistOrderDatabase: TrackSearchArtistOrderDatabase = mockk()
    private val listenerOrderDatabase: TrackSearchListenersOrderDatabase = mockk()
    private val individualTrackDatabase: IndividualTrackDatabase = mockk()

    private val trackRepositry = TrackRepository(
        networkService,
        defaultOrderDatabase,
        trackOrderDatabase,
        artistOrderDatabase,
        listenerOrderDatabase,
        individualTrackDatabase
    )

    private val networkTracksMock = mockk<TrackSearch>()
    private val localTracksMockEntity = mockk<TrackSearchEntity>()
    private val trackMatchesMock = mockk<TrackMatches>()
    private val trackMock = mockk<List<Track>>()

    @Test
    fun `fetch tracks should make a fetch to the correct local database and return the tracks when found`() {
        coEvery { defaultOrderDatabase.trackSearchDao.getLocalTracksByNameAsync(EXPECTED_TRACK) } returns localTracksMockEntity
        val localTracks = runBlocking { trackRepositry.fetchTracksAsync(EXPECTED_TRACK, EXPECTED_ORDER) }
        assertEquals(localTracksMockEntity, localTracks)
        coVerify { defaultOrderDatabase.trackSearchDao.getLocalTracksByNameAsync(EXPECTED_TRACK) }
    }

    @Test
    fun `fetch tracks should make a fetch to the correct local database and return the tracks when not found`() {
        every { networkTracksMock.results.trackMatches } returns trackMatchesMock
        every { trackMatchesMock.track } returns trackMock
        every { trackMock.isEmpty() } returns false
        coEvery { networkService.fetchTracksAsync(EXPECTED_TRACK, TRACK_SEARCH_METHOD, KEY, JSON) } returns networkTracksMock
        coEvery { defaultOrderDatabase.trackSearchDao.getLocalTracksByNameAsync(EXPECTED_TRACK) } returns null
        val constructedTrackSearchEntity = TrackSearchEntity(EXPECTED_TRACK,trackMatchesMock)
        coEvery { defaultOrderDatabase.trackSearchDao.insertAll(constructedTrackSearchEntity) } just runs
        val networkTracks = runBlocking { trackRepositry.fetchTracksAsync(EXPECTED_TRACK, EXPECTED_ORDER) }
        assertEquals(constructedTrackSearchEntity, networkTracks)
        coVerify { defaultOrderDatabase.trackSearchDao.getLocalTracksByNameAsync(EXPECTED_TRACK) }
        coVerify { networkService.fetchTracksAsync(EXPECTED_TRACK, TRACK_SEARCH_METHOD, KEY, JSON) }
        coVerify { defaultOrderDatabase.trackSearchDao.insertAll(constructedTrackSearchEntity) }
    }

    companion object {
        private const val EXPECTED_TRACK = "b"
        private val EXPECTED_ORDER = DEFAULT
    }
}
