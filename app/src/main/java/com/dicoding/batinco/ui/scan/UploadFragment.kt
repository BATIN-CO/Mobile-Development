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
    private var optionDialogListener: OnOptionDialogListener? = null

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
            dialog?.dismiss()
        }

        binding.btnDialogObj.setOnClickListener {
            val option = "Object Detection"
            optionDialogListener?.onOptionChosen(option, currentImageUri.toString())
            dialog?.dismiss()
        }

        binding.btnDialogMotif.setOnClickListener {
            val option = "Motif"
            optionDialogListener?.onOptionChosen(option, currentImageUri.toString())
            dialog?.dismiss()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragment = parentFragment

        if (fragment is ScanFragment) {
            this.optionDialogListener = fragment.optionDialogListener
        }

    }

    override fun onDetach() {
        super.onDetach()
        this.optionDialogListener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnOptionDialogListener {
        fun onOptionChosen(text: String?, image: String?)
    }

    companion object {
        var EXTRA_FILE = "extra_file"
    }

}