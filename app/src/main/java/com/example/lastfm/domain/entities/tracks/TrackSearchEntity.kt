package com.example.lastfm.domain.entities.tracks

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackSearchEntity (
    @PrimaryKey
    val trackSearch_id : String,
    val trackMatches : TrackMatches
)
