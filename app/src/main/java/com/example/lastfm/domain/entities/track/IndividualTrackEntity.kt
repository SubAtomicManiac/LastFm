package com.example.lastfm.domain.entities.track

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IndividualTrackEntity (
    @PrimaryKey
    val individualTrack_id: String,
    val individualTrack: Track
)
