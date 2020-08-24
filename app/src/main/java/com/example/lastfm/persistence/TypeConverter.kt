package com.example.lastfm.persistence

import androidx.room.TypeConverter
import com.example.lastfm.domain.entities.track.IndividualTrack
import com.example.lastfm.domain.entities.track.Track
import com.example.lastfm.domain.entities.tracks.TrackMatches
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class TrackConverter {
    companion object {
        private var gson = Gson()
        private val trackMatchType: Type = object : TypeToken<TrackMatches?>() {}.type
        private val individualTrackType: Type = object : TypeToken<Track?>() {}.type

        @TypeConverter
        @JvmStatic
        fun fromTrackMatches(trackMatches: TrackMatches?) = gson.toJson(trackMatches, trackMatchType)

        @TypeConverter
        @JvmStatic
        fun toTrackMatches(trackMatchesString: String?) = gson.fromJson<TrackMatches?>(trackMatchesString, trackMatchType)

        @TypeConverter
        @JvmStatic
        fun fromIndividualTrack(individualTrack: Track?) = gson.toJson(individualTrack, individualTrackType)

        @TypeConverter
        @JvmStatic
        fun toIndividualTrack(individualTrackString: String?) = gson.fromJson<Track?>(individualTrackString, individualTrackType)
    }
}
