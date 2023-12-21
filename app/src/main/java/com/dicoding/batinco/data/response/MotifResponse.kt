package com.dicoding.batinco.data.response

import com.google.gson.annotations.SerializedName

data class MotifResponse(

	@field:SerializedName("error")
	val error: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
)
