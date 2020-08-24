package com.example.lastfm.viewmodel

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.MutableLiveData
import com.example.lib.EventViewModel

class TrackListViewModel : EventViewModel() {

    val tracksLiveData = MutableLiveData<List<TrackViewModel>>()
    var trackDetailViewModel = defaultTrackDetailViewModel
    var detailsVisibility = GONE
    var loadingVisibility = GONE

    fun updateTracks(tracks: List<TrackViewModel>?){
        endLoading()
        tracks?.also{tracksLiveData.postValue(it)}
    }

    fun updateTrackDetails(detailViewModel: TrackDetailViewModel?){
        endLoading()
        trackDetailViewModel = detailViewModel.also { detailsVisibility = VISIBLE } ?: defaultTrackDetailViewModel.also { detailsVisibility = GONE }
    }

    fun hideTrackDetails(cOut: Any? = null){
        detailsVisibility = GONE
    }

    fun beginLoading(cOut: Any? = null){ loadingVisibility = VISIBLE }
    private fun endLoading(){ loadingVisibility = GONE }

    companion object {
        private val defaultTrackDetailViewModel = TrackDetailViewModel("","","","","", "")
    }
}
