package com.dicoding.batinco.ui.scanresult

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.findNavController
import com.dicoding.batinco.R
import com.dicoding.batinco.databinding.FragmentScanResultMotifBinding

class ScanResultMotifFragment : Fragment() {

    private var _binding: FragmentScanResultMotifBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanResultMotifBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnScanBack.setOnClickListener {
            view.findNavController().navigate(R.id.action_fragment_scan_result_motif_to_navigation_scan)
        }

        Toast.makeText(requireActivity(), arguments.toString(), Toast.LENGTH_SHORT).show()

        if (arguments != null) {
            val photo = arguments?.getString(EXTRA_PHOTO)!!.toUri()
            binding.ivScanPhoto.setImageURI(photo)
            Log.d("ScanResultFragment", photo.toString())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var EXTRA_PHOTO = "extra_photo"
        var EXTRA_LIST1 = "extra_list1"
        var EXTRA_LIST2 = "extra_list2"
    }
}