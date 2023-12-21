package com.dicoding.batinco.data.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("data")
	val data: SearchData,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class SearchItem(

	@field:SerializedName("asal")
	val asal: String,

	@field:SerializedName("tentang")
	val tentang: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("gambar")
	val gambar: String,

	@field:SerializedName("batikId")
	val batikId: Int
)

data class SearchData(

	@field:SerializedName("rows")
	val rows: List<SearchItem>
)
