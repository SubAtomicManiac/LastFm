package com.example.lastfm.viewmodel

import com.example.lastfm.unittests.EventUnitTest
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TrackListViewModelTest : EventUnitTest(){

    private lateinit var viewModel: TrackListViewModel

    private val tracks = mockk<List<TrackViewModel>>()
    private val track = mockk<TrackDetailViewModel>()

    @Before
    fun setup(){
        viewModel = TrackListViewModel()
    }

    @Test
    fun `update tracks Should post new tracks to liveData and end loading`(){
        viewModel.beginLoading()
        assertEquals(EXPECTED_VIEW_VISIBLE, viewModel.loadingVisibility)
        viewModel.updateTracks(tracks)
        assertEquals(EXPECTED_VIEW_GONE, viewModel.loadingVisibility)
        assertEquals(tracks, viewModel.tracksLiveData.value)
    }

    @Test
    fun `update track details should update details view, make it visible and end loading`(){
        viewModel.beginLoading()
        assertEquals(EXPECTED_VIEW_VISIBLE, viewModel.loadingVisibility)
        assertEquals(EXPECTED_VIEW_GONE, viewModel.detailsVisibility)
        viewModel.updateTrackDetails(track)
        assertEquals(EXPECTED_VIEW_GONE, viewModel.loadingVisibility)
        assertEquals(EXPECTED_VIEW_VISIBLE, viewModel.detailsVisibility)
        assertEquals(track, viewModel.trackDetailViewModel)
    }

    @Test
    fun `hide track details Minimized should hide details view`(){
        assertEquals(EXPECTED_VIEW_GONE, viewModel.detailsVisibility)
        viewModel.updateTrackDetails(track)
        assertEquals(EXPECTED_VIEW_VISIBLE, viewModel.detailsVisibility)
        viewModel.hideTrackDetails()
        assertEquals(EXPECTED_VIEW_GONE, viewModel.detailsVisibility)
    }

    @Test
    fun `begin loading should make loading visible`(){
        assertEquals(EXPECTED_VIEW_GONE, viewModel.loadingVisibility)
        viewModel.beginLoading()
        assertEquals(EXPECTED_VIEW_VISIBLE, viewModel.loadingVisibility)
    }

    companion object {
        private const val EXPECTED_VIEW_GONE = 8
        private const val EXPECTED_VIEW_VISIBLE = 0
    }

}
