package com.dicoding.batinco.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dicoding.batinco.data.response.DetailItem
import com.dicoding.batinco.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val batikId = intent.getIntExtra(extraData, 0)

        batikId.let { detailViewModel.searchBatikDetail(it) }

        detailViewModel.detailBatik.observe(this) {
            setData(it)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setData(data: DetailItem) {
        binding.apply {
            Glide.with(root.context)
                .load(data.gambar)
                .into(ivDetailPhoto)
            tvDetailBatik.text = data.nama
            tvDetailOrigin.text = data.asal
            tvDetailMeaning.text = data.tentang
        }
    }

    private fun showLoading(isLoading: Boolean) {

    }

    companion object {
        const val extraData = "EXTRA_ID"
    }
}