package com.example.lastfm.viewmodel

import com.example.lastfm.extensions.ListItemViewModel

data class TrackViewModel (
    val trackId : String,
    val track : String,
    val artist : String,
    val img : String,
    val listeners : String
) : ListItemViewModel()
