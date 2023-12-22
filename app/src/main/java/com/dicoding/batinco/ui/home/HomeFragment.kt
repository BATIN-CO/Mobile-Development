package com.dicoding.batinco.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.batinco.R
import com.dicoding.batinco.data.response.RowsItem
import com.dicoding.batinco.data.response.SearchItem
import com.dicoding.batinco.databinding.FragmentHomeBinding
import com.dicoding.batinco.ui.detail.DetailActivity
import com.dicoding.batinco.ui.discover.DiscoverAdapter
import com.google.android.material.search.SearchBar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(context, 2)
        binding.rvHome.layoutManager = layoutManager

        homeViewModel.batik.observe(viewLifecycleOwner) {
            setBatikData(it)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    val searchText = searchView.text.toString()
                    searchView.hide()
                    homeViewModel.searchBatik(searchText)
                    false
                }
        }

        binding.cvScan.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_home_to_navigation_scan)
        }
    }

    private fun setBatikData(batikList: List<SearchItem>) {
        val adapter = HomeAdapter()
        adapter.submitList(batikList)
        binding.rvHome.adapter = adapter

        adapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback{
            override fun onItemClicked(data: SearchItem) {
                val intentToDetail = Intent(requireActivity(), DetailActivity::class.java)
                intentToDetail.putExtra("EXTRA_ID", data.batikId)
                startActivity(intentToDetail)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}