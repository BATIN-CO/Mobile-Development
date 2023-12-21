package com.dicoding.batinco.data

import androidx.lifecycle.liveData
import com.dicoding.batinco.data.response.MotifResponse
import com.dicoding.batinco.data.response.ObjectResponse
import com.dicoding.batinco.data.response.Prediction
import com.dicoding.batinco.data.retrofit.ApiService
import com.dicoding.batinco.utils.ResultState
import com.google.gson.Gson
import okhttp3.MultipartBody
import retrofit2.HttpException

class BatikRepository(
    private val apiService: ApiService
) {
   var motif : Prediction? = null
   var obj : ObjectResponse? = null

    fun uploadMotifImage(picture: MultipartBody.Part) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.uploadImageMotif(picture)
            fetchDataMotif(successResponse)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, MotifResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
    }

    fun uploadObjectImage(picture: MultipartBody.Part) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.uploadImageObject(picture)
            fetchDataObject(successResponse)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ObjectResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
    }

    private fun fetchDataMotif(response: MotifResponse) {
        motif = response.prediction
    }

    private fun fetchDataObject(response: ObjectResponse) {
        obj = response
    }

    companion object {
        @Volatile
        private var instance: BatikRepository? = null
        fun getInstance(
            apiService: ApiService
        ): BatikRepository = instance ?: synchronized(this) {
            instance ?: BatikRepository(apiService)
        }.also { instance = it }
    }

}