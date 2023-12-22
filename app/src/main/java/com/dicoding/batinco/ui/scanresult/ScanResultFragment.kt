package com.dicoding.batinco.ui.scanresult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import com.dicoding.batinco.R
import com.dicoding.batinco.databinding.FragmentScanBinding
import com.dicoding.batinco.databinding.FragmentScanResultBinding
import com.dicoding.batinco.ui.scan.UploadFragment

class ScanResultFragment : Fragment() {

    private var _binding: FragmentScanResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanResultBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnScanBack.setOnClickListener {
            view.findNavController().navigate(R.id.action_scanResultFragment_to_navigation_scan)
        }

        if (arguments != null) {
            val photo = arguments?.getString(EXTRA_PHOTO)!!.toUri()
            binding.ivScanPhoto.setImageURI(photo)
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
        var EXTRA_RESULT = "extra_result"
    }
}