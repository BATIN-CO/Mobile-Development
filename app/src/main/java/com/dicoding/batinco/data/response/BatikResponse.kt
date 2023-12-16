package com.dicoding.batinco.data.response

import com.google.gson.annotations.SerializedName

data class BatikResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class RowsItem(

	@field:SerializedName("asal")
	val asal: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("gambar")
	val gambar: String,

	@field:SerializedName("batikId")
	val batikId: Int
)

data class Data(

	@field:SerializedName("rows")
	val rows: List<RowsItem>
)
