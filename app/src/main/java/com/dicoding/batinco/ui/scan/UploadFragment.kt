package com.dicoding.batinco.ui.scan

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import com.dicoding.batinco.R
import com.dicoding.batinco.databinding.FragmentUploadBinding

class UploadFragment : DialogFragment() {

    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!

    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            currentImageUri = arguments?.getString(EXTRA_FILE)!!.toUri()
            binding.ivDialogPreview.setImageURI(currentImageUri)
        }

        binding.btnDialogRetry.setOnClickListener {
//            it.findNavController().navigate(R.id.action_uploadFragment_to_navigation_scan)
            dialog?.dismiss()
        }

        binding.btnDialogObj.setOnClickListener {

        }

        binding.btnDialogMotif.setOnClickListener {

        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragment = parentFragment

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var EXTRA_FILE = "extra_file"
    }

}