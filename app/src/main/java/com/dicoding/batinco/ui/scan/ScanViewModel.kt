package com.dicoding.batinco.ui.scan

import androidx.lifecycle.ViewModel
import com.dicoding.batinco.data.BatikRepository
import java.io.File

class ScanViewModel(private val repository: BatikRepository) : ViewModel() {
    fun uploadMotif(picture: File) = repository.uploadMotifImage(picture)

    fun uploadObjectDetect(picture: File) = repository.uploadObjectImage(picture)

}