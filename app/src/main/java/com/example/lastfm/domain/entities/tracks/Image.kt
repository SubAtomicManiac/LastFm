package com.example.lastfm.domain.entities.tracks

import com.google.gson.annotations.SerializedName

data class Image (
	@SerializedName("#text")
	val text : String,
	val size : String
)
