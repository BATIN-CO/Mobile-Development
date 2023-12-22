package com.dicoding.batinco.data

import androidx.lifecycle.liveData
import com.dicoding.batinco.data.response.MotifResponse
import com.dicoding.batinco.data.response.ObjectResponse
import com.dicoding.batinco.data.retrofit.ApiService
import com.dicoding.batinco.utils.ResultState
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class BatikRepository(
    private val apiService: ApiService
) {
    fun uploadMotifImage(picture: File) = liveData {
        emit(ResultState.Loading)
        val requestImageFile = picture.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "picture", picture.name, requestImageFile
        )
        try {
            val successResponse = apiService.uploadImageMotif(multipartBody)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, MotifResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
    }

    fun uploadObjectImage(picture: File) = liveData {
        emit(ResultState.Loading)
        val requestImageFile = picture.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "picture", picture.name, requestImageFile
        )
        try {
            val successResponse = apiService.uploadImageObject(multipartBody)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ObjectResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
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