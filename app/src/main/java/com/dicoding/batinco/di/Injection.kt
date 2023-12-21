package com.dicoding.batinco.di

import com.dicoding.batinco.data.BatikRepository
import com.dicoding.batinco.data.retrofit.ApiConfig

object Injection {

    fun provideBatikRepository(): BatikRepository {
        val apiService = ApiConfig.getApiService()
        return BatikRepository.getInstance(apiService)
    }
}