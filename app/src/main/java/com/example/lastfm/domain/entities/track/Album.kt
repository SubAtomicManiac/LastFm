package com.example.lastfm.domain.entities.track

import com.example.lastfm.domain.entities.tracks.Image
import com.google.gson.annotations.SerializedName

data class Album (
	val artist : String,
	val title : String,
	val mbid : String,
	val url : String,
	val image : List<Image>,
	@SerializedName("@attr")
	val attr : Attr
)
