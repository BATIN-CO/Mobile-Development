package com.dicoding.batinco.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.batinco.data.response.BatikResponse
import com.dicoding.batinco.data.response.RowsItem
import com.dicoding.batinco.data.response.SearchItem
import com.dicoding.batinco.data.response.SearchResponse
import com.dicoding.batinco.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _batik = MutableLiveData<List<SearchItem>>()
    val batik: LiveData<List<SearchItem>> = _batik

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        searchBatik(QUERY)
    }

    private fun findBatik(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchBatik(query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _batik.value = responseBody.data.rows
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun searchBatik(q: String) {
        findBatik(q)
    }

    companion object {
        private const val TAG = "HomeFragment"
        private const val QUERY = "a"
    }

}