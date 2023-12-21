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
//    @GET("search/users")
//    fun searchUsers(
//        @Query("q") query: String
//    ): Call<GithubResponse>
//
//    @GET("users/{username}")
//    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>
//
//    @GET("users/{username}/followers")
//    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>
//
//    @GET("users/{username}/following")
//    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>

    @Multipart
    @POST("stories/guest")
    suspend fun uploadImageObject(
        @Part file: MultipartBody.Part,
    ): ObjectResponse

    @Multipart
    @POST("stories/guest")
    suspend fun uploadImageMotif(
        @Part file: MultipartBody.Part,
    ): MotifResponse

    @GET("tampil/0")
    fun getAllBatik(): Call<BatikResponse>

    //if using paging
    @GET("tampil/0")
    suspend fun getAllBatik(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 5
    ): BatikResponse

    @GET("tampil/{id}")
    fun getBatik(@Path("id") id: Int): Call<DetailBatikResponse>

    @GET("cari/{search}")
    fun searchBatik(@Path("search") search: String): Call<SearchResponse>
}
