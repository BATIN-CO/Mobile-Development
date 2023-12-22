package com.dicoding.batinco.ui.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.batinco.data.response.RowsItem
import com.dicoding.batinco.databinding.BatikItemBinding

class DiscoverAdapter : ListAdapter<RowsItem, DiscoverAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = BatikItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val batik = getItem(position)
        holder.bind(batik)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(batik)
        }
    }

    class MyViewHolder(private val binding: BatikItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(batik: RowsItem) {
            Glide.with(binding.root.context)
                .load(batik.gambar)
                .into(binding.ivBatikPhoto)
            binding.tvBatikName.text = batik.nama
            binding.tvBatikOrigin.text = batik.asal
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RowsItem>() {
            override fun areItemsTheSame(oldItem: RowsItem, newItem: RowsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RowsItem, newItem: RowsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: RowsItem)
    }
}