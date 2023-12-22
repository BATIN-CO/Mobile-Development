package com.dicoding.batinco.ui.scanresult

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.batinco.data.model.PredictionModel
import com.dicoding.batinco.databinding.ScanItemBinding

class ScanResultAdapter(private val dataList: List<PredictionModel>) : RecyclerView.Adapter<ScanResultAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ScanItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val batik = dataList[position]

        holder.tvName.text = batik.stringList.toString()
        val percentage = batik.doubleArray * 100
        holder.tvPercent.text = "${String.format("%.2f", percentage)}%"
    }

    class MyViewHolder(binding: ScanItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvPercent : TextView = binding.tvScanPercent
        val tvName : TextView = binding.tvScanBatik
    }
}