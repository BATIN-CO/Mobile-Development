package com.dicoding.batinco.ui.discover

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.batinco.data.response.BatikResponse
import com.dicoding.batinco.data.response.RowsItem
import com.dicoding.batinco.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiscoverViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _batik = MutableLiveData<List<RowsItem>>()
    val batik: LiveData<List<RowsItem>> = _batik

    fun getAllBatik() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllBatik()
        client.enqueue(object : Callback<BatikResponse> {
            override fun onResponse(
                call: Call<BatikResponse>,
                response: Response<BatikResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _batik.value = responseBody.data.rows
                        Log.d("Berhasil", response.body().toString())
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BatikResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "DiscoverFragment"
    }

}