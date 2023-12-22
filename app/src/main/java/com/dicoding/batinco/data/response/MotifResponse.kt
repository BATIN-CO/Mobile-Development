package com.dicoding.batinco.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MotifResponse(

	@field:SerializedName("prediction")
	val prediction: Prediction,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Prediction(

	@field:SerializedName("predicted_class_names")
	val predictedClassNames: List<String>,

	@field:SerializedName("predicted_probabilities")
	val predictedProbabilities: List<Double>
)
