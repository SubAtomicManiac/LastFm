package com.example.lastfm.presenter

import com.example.lastfm.domain.entities.track.*
import com.example.lastfm.domain.entities.tracks.Image
import com.example.lastfm.domain.entities.tracks.TrackSearchEntity
import com.example.lastfm.viewmodel.TrackDetailViewModel
import com.example.lastfm.domain.entities.tracks.Track as T
import com.example.lastfm.viewmodel.TrackViewModel
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LastFmPresenterTest {

    private lateinit var presenter: LastFmPresenter

    private val trackSearchEntity = mockk<TrackSearchEntity>()
    private val individualTrackEntityA = mockk<IndividualTrackEntity>()
    private val individualTrackEntityB = mockk<IndividualTrackEntity>()

    @Before
    fun setup(){
        presenter = LastFmPresenter()
    }

    @Test
    fun `map tracks to viewmodel correctly maps tracks to viewModels`() {
        every {trackSearchEntity.trackMatches.track} returns expectedTracks
        mockImages()
        assertEquals(expectedTrackViewModels, presenter.mapTracksToViewModel(trackSearchEntity))
    }

    @Test
    fun `map track to detailed viewmodel maps a tracks to the correct detailedViewModel`(){
        every { individualTrackEntityA.individualTrack} returns expectedIndividualTrackA
        every { individualTrackEntityB.individualTrack} returns expectedIndividualTrackB
        every { expectedIndividualTrackA.album?.image } returns IMAGE_A
        every { expectedIndividualTrackB.album?.image } returns IMAGE_B
        every { ARTIST_A.name } returns ARTIST_NAME_A
        every { ARTIST_B.name } returns ARTIST_NAME_B
        mockImages(3)
        assertEquals(expectedTrackDetailViewModelA, presenter.mapTrackDetailToViewModel(individualTrackEntityA))
        assertEquals(expectedTrackDetailViewModelB, presenter.mapTrackDetailToViewModel(individualTrackEntityB))
    }

    private fun mockImages(index: Int = 0){
        every { IMAGE_A[index] } returns imageMock
        every { IMAGE_A.size } returns 4
        every { imageMock.text } returns IMAGE_TEXT
        every { IMAGE_B[index] } returns nullImageMock
        every { IMAGE_B.size } returns 0
        every { nullImageMock.text } returns DEFAULT_IMG
    }

    companion object {
        private const val NAME_A = "b"
        private const val MBID_A = "mbid"
        private const val URL_A = "URL"
        private const val DURATION_A = 0
        private val STREAMABLE_A = mockk<Streamable>()
        private const val LISTENERS_A = 9999
        private const val PLAY_COUNT_A = 99887
        private val ARTIST_A = mockk<Artist>()
        private val ALBUM_A = mockk<Album>()
        private val TOP_TAGS_A = mockk<Toptags>()
        private val WIKI_A = mockk<Wiki>()

        private const val ARTIST_NAME_A = "c"
        private const val STREAMABLE_NAME_A = "streamble"
        private val IMAGE_A = mockk<List<Image>>()
        private const val IMAGE_TEXT = "imageText"

        private const val NAME_B = "bB"
        private const val MBID_B = "mbidB"
        private const val URL_B = "URLB"
        private const val DURATION_B = 10
        private val STREAMABLE_B = mockk<Streamable>()
        private const val LISTENERS_B = 99999
        private const val PLAY_COUNT_B = 998879
        private val ARTIST_B = mockk<Artist>()
        private val ALBUM_B = mockk<Album>()
        private val TOP_TAGS_B = mockk<Toptags>()
        private val WIKI_B = mockk<Wiki>()

        private const val ARTIST_NAME_B = "cB"
        private const val STREAMABLE_NAME_B = "streambleB"
        private val IMAGE_B = mockk<List<Image>>()
        private val imageMock = mockk<Image>()
        private val nullImageMock = mockk<Image>()

        private val expectedTrackA  = T(NAME_A, ARTIST_NAME_A, URL_A, STREAMABLE_NAME_A, LISTENERS_A, IMAGE_A, MBID_A)
        private val expectedTrackB  = T(NAME_B, ARTIST_NAME_B, URL_B, STREAMABLE_NAME_B, LISTENERS_B, IMAGE_B, MBID_B)

        private val expectedTracks = listOf(expectedTrackA, expectedTrackB)

        private val expectedTrackViewModelA = TrackViewModel(MBID_A, NAME_A, ARTIST_NAME_A, IMAGE_TEXT, LISTENERS + LISTENERS_A)
        private val expectedTrackViewModelB = TrackViewModel(MBID_B, NAME_B, ARTIST_NAME_B, DEFAULT_IMG, LISTENERS + LISTENERS_B)

        private val expectedTrackViewModels = listOf(expectedTrackViewModelA, expectedTrackViewModelB)

        private val expectedIndividualTrackA = Track(NAME_A, MBID_A, URL_A, DURATION_A, STREAMABLE_A, LISTENERS_A, PLAY_COUNT_A, ARTIST_A, ALBUM_A, TOP_TAGS_A, WIKI_A)
        private val expectedIndividualTrackB = Track(NAME_B, MBID_B, URL_B, DURATION_B, STREAMABLE_B, LISTENERS_B, PLAY_COUNT_B, ARTIST_B, ALBUM_B, TOP_TAGS_B, WIKI_B)

        private val expectedTrackDetailViewModelA = TrackDetailViewModel(NAME_A, IMAGE_TEXT, ARTIST + ARTIST_NAME_A, LISTENERS + LISTENERS_A, PLAY_COUNT + PLAY_COUNT_A, URL + URL_A)
        private val expectedTrackDetailViewModelB = TrackDetailViewModel(NAME_B, DEFAULT_IMG, ARTIST + ARTIST_NAME_B, LISTENERS + LISTENERS_B, PLAY_COUNT + PLAY_COUNT_B, URL + URL_B)
    }
}
