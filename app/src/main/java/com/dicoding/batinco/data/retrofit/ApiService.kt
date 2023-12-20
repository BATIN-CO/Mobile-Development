package com.dicoding.batinco.data.retrofit

import com.dicoding.batinco.data.response.BatikResponse
import com.dicoding.batinco.data.response.DetailBatikResponse
import com.dicoding.batinco.data.response.DetailItem
import com.dicoding.batinco.data.response.DetailUserResponse
import com.dicoding.batinco.data.response.GithubResponse
import com.dicoding.batinco.data.response.ItemsItem
import com.dicoding.batinco.data.response.RowsItem
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
}
