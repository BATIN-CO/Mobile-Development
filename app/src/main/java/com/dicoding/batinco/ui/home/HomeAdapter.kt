package com.dicoding.batinco.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.batinco.data.response.SearchItem
import com.dicoding.batinco.databinding.BatikItemBinding
import com.dicoding.batinco.databinding.BatikItemHomeBinding

class HomeAdapter : ListAdapter<SearchItem, HomeAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = BatikItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val batik = getItem(position)
        holder.bind(batik)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(batik)
        }
    }

    class MyViewHolder(private val binding: BatikItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(batik: SearchItem) {
            Glide.with(binding.root.context)
                .load(batik.gambar)
                .into(binding.ivBatikPhoto)
            binding.tvBatikName.text = batik.nama
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchItem>() {
            override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: SearchItem)
    }
}