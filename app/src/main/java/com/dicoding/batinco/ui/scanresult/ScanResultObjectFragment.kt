package com.dicoding.batinco.ui.scanresult

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import com.dicoding.batinco.R
import com.dicoding.batinco.databinding.FragmentScanResultObjectBinding

class ScanResultObjectFragment : Fragment() {

    private var _binding: FragmentScanResultObjectBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanResultObjectBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnScanBackObj.setOnClickListener {
            view.findNavController().navigate(R.id.action_fragment_scan_result_object_to_navigation_scan)
        }

        if (arguments != null) {
            val photo = arguments?.getString(EXTRA_PHOTO)!!.toUri()
            binding.ivScanPhotoObj.setImageURI(photo)
            Log.d("ScanResultFragment", photo.toString())

            val result = arguments?.getString(EXTRA_RESULT)
            binding.tvScanResultObj.text = result
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var EXTRA_PHOTO = "extra_photo"
        var EXTRA_RESULT = "extra_result"
    }
}