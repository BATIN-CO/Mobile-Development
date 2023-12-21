package com.dicoding.batinco.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.batinco.data.response.MotifResponse
import com.dicoding.batinco.data.response.ObjectResponse
import com.dicoding.batinco.data.response.RowsItem
import com.dicoding.batinco.data.retrofit.ApiService
import com.dicoding.batinco.utils.ResultState
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class BatikRepository(
    private val apiService: ApiService
) {

    fun uploadMotifImage(picture: MultipartBody.Part) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.uploadImageMotif(picture)
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