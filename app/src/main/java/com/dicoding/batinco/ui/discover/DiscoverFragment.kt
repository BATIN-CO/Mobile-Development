package com.dicoding.batinco.ui.discover

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.batinco.data.response.RowsItem
import com.dicoding.batinco.databinding.FragmentDiscoverBinding
import com.dicoding.batinco.databinding.FragmentHomeBinding
import com.dicoding.batinco.ui.detail.DetailActivity

class DiscoverFragment : Fragment() {
    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private val discoverViewModel by viewModels<DiscoverViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding.rvBatikDiscover.layoutManager = layoutManager

        discoverViewModel.getAllBatik()

        discoverViewModel.batik.observe(viewLifecycleOwner) {
            setBatikData(it)
        }

        discoverViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setBatikData(batikList: List<RowsItem>) {
        val adapter = DiscoverAdapter()
        adapter.submitList(batikList)
        binding.rvBatikDiscover.adapter = adapter

        adapter.setOnItemClickCallback(object : DiscoverAdapter.OnItemClickCallback{
            override fun onItemClicked(data: RowsItem) {
//                val bundle = Bundle()
//                bundle.putInt(EXTRA_ID, data.batikId)
//                NavHostFragment
//                    .findNavController(this@DiscoverFragment)
//                    .navigate(R.id.action_navigation_discover_to_navigation_detail, bundle)

                val intentToDetail = Intent(requireActivity(), DetailActivity::class.java)
                intentToDetail.putExtra("EXTRA_ID", data.batikId)
                startActivity(intentToDetail)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    companion object {
//        const val EXTRA_ID = "extra_id"
//    }

}