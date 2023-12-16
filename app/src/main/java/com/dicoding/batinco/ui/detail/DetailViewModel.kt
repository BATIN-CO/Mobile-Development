package com.dicoding.batinco.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.batinco.data.response.DetailBatikResponse
import com.dicoding.batinco.data.response.DetailItem
import com.dicoding.batinco.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailBatik = MutableLiveData<DetailItem>()
    val detailBatik: LiveData<DetailItem> = _detailBatik

    private fun getBatikDetail(id: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getBatik(id)
        client.enqueue(object : Callback<DetailBatikResponse> {
            override fun onResponse(
                call: Call<DetailBatikResponse>,
                response: Response<DetailBatikResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailBatik.value = responseBody.data.rows[0]
                        Log.d("Berhasil", response.body().toString())
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailBatikResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun searchBatikDetail(id: Int) {
        getBatikDetail(id)
    }

    companion object {
        private const val TAG = "DetailFragment"
    }

}