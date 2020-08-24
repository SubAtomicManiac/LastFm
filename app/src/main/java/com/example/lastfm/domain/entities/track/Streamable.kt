package com.example.lastfm.domain.entities.track

import com.google.gson.annotations.SerializedName

data class Streamable (
	@SerializedName("#text")
	val text : Int,
	val fulltrack : Int
)
