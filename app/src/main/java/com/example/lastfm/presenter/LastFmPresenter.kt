package com.example.lastfm.presenter

import com.example.lastfm.domain.entities.track.IndividualTrackEntity
import com.example.lastfm.domain.entities.tracks.TrackSearchEntity
import com.example.lastfm.viewmodel.TrackDetailViewModel
import com.example.lastfm.viewmodel.TrackViewModel

const val ARTIST = "Artist: "
const val LISTENERS = "Listeners: "
const val PLAY_COUNT = "Play Count: "
const val URL = "URL: "
const val DEFAULT_IMG = "https://www.elegantthemes.com/blog/wp-content/uploads/2020/02/000-404.png"

//Responsible for all the mapping in the application
class LastFmPresenter {

    fun mapTracksToViewModel(tracks : TrackSearchEntity?) : List<TrackViewModel>?{
        return tracks?.trackMatches?.track?.map {
            TrackViewModel(
                it.mbid,
                it.track,
                it.artist,
                it.image.getOrNull(0)?.text ?: DEFAULT_IMG,
                LISTENERS + it.listeners.toString()
            )
        }
    }

    fun mapTrackDetailToViewModel(track : IndividualTrackEntity?) : TrackDetailViewModel?{
        return track?.run{
            val trk = this.individualTrack
            TrackDetailViewModel(
                trk.name,
                trk.album?.image?.getOrNull(3)?.text ?: DEFAULT_IMG,
                ARTIST + trk.artist.name,
                LISTENERS + trk.listeners.toString(),
                PLAY_COUNT + trk.playcount.toString(),
                URL + trk.url
            )
        }
    }
}
