package com.dicoding.batinco.ui.scan

import androidx.lifecycle.ViewModel
import com.dicoding.batinco.data.BatikRepository
import okhttp3.MultipartBody

class ScanViewModel(private val repository: BatikRepository) : ViewModel() {
    fun uploadMotif(picture: MultipartBody.Part) = repository.uploadMotifImage(picture)

    fun uploadObjectDetect(picture: MultipartBody.Part) = repository.uploadObjectImage(picture)

}