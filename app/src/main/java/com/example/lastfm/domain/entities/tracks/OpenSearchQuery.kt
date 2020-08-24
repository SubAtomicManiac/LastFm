package com.example.lastfm.domain.entities.tracks

import com.google.gson.annotations.SerializedName

data class OpenSearchQuery (
	@SerializedName("#text")
	val text : String,
	val role : String,
	val startPage : Int
)
