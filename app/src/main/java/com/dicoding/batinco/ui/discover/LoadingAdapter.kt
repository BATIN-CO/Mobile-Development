package com.dicoding.batinco.ui.discover

//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.core.view.isVisible
//import androidx.paging.LoadState
//import androidx.paging.LoadStateAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.dicoding.batinco.databinding.LoadItemBinding
//
//class LoadingAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadingAdapter.LoadingStateViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadingStateViewHolder {
//        val binding = LoadItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return LoadingStateViewHolder(binding, retry)
//    }
//
//    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
//        holder.bind(loadState)
//    }
//
//    class LoadingStateViewHolder(private val binding: LoadItemBinding, retry: () -> Unit) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        init {
//            binding.reloadButton.setOnClickListener { retry.invoke() }
//        }
//
//        fun bind(loadState: LoadState) {
//            binding.apply {
//                if (loadState is LoadState.Error) {
//                    error.text = loadState.error.localizedMessage
//                }
//                progressBar.isVisible = loadState is LoadState.Loading
//                reloadButton.isVisible = loadState is LoadState.Error
//                error.isVisible = loadState is LoadState.Error
//            }
//        }
//    }
//}