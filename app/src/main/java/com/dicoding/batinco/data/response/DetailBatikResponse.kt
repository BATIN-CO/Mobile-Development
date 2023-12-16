package com.dicoding.batinco.data.response

import com.google.gson.annotations.SerializedName

data class DetailBatikResponse(

	@field:SerializedName("data")
	val data: DetailData,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DetailItem(

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

data class DetailData(

	@field:SerializedName("rows")
	val rows: List<DetailItem>
)
