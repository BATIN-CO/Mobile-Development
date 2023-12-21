package com.dicoding.batinco.data.retrofit

import com.dicoding.batinco.data.response.BatikResponse
import com.dicoding.batinco.data.response.DetailBatikResponse
import com.dicoding.batinco.data.response.DetailItem
import com.dicoding.batinco.data.response.DetailUserResponse
import com.dicoding.batinco.data.response.GithubResponse
import com.dicoding.batinco.data.response.ItemsItem
import com.dicoding.batinco.data.response.MotifResponse
import com.dicoding.batinco.data.response.ObjectResponse
import com.dicoding.batinco.data.response.RowsItem
import com.dicoding.batinco.data.response.SearchResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("stories/guest")
    suspend fun uploadImageObject(
        @Part picture: MultipartBody.Part,
    ): ObjectResponse

    @Multipart
    @POST("stories/guest")
    suspend fun uploadImageMotif(
        @Part picture: MultipartBody.Part,
    ): MotifResponse

    @GET("tampil/0")
    fun getAllBatik(): Call<BatikResponse>

    @GET("tampil/{id}")
    fun getBatik(@Path("id") id: Int): Call<DetailBatikResponse>

    @GET("cari/{search}")
    fun searchBatik(@Path("search") search: String): Call<SearchResponse>
}
