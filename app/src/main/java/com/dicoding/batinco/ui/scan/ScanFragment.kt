package com.dicoding.batinco.ui.scan

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.dicoding.batinco.R
import com.dicoding.batinco.databinding.FragmentScanBinding
import com.dicoding.batinco.ui.ViewModelFactory
import com.dicoding.batinco.ui.scanresult.ScanResultMotifFragment
import com.dicoding.batinco.ui.scanresult.ScanResultObjectFragment
import com.dicoding.batinco.utils.ResultState
import com.dicoding.batinco.utils.createCustomTempFile
import com.dicoding.batinco.utils.reduceFileImage
import com.dicoding.batinco.utils.uriToFile
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch

class ScanFragment : Fragment() {
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ScanViewModel> {
        ViewModelFactory.getInstance()
    }

    private val cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null

    private var currentImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireActivity(), "Permission request granted", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(requireActivity(), "Permission request denied", Toast.LENGTH_LONG)
                    .show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireActivity(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.ivCamCapture.setOnClickListener { takePhoto() }

        binding.ivCamClose.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_scan_to_navigation_home)
        }

        binding.ivCamGallery.setOnClickListener { startGallery() }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI(requireActivity())
        startCamera()
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            val imageUri = currentImageUri.toString()
            val bundle = Bundle().apply {
                putString(UploadFragment.EXTRA_FILE, imageUri)
            }
            showDialog(bundle)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.pvCamViewfinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (exc: Exception) {
                Toast.makeText(
                    requireActivity(),
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "startCamera: ${exc.message}")
            }
        }, ContextCompat.getMainExecutor(requireActivity()))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = createCustomTempFile(requireActivity())
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireActivity()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//                    val intent = Intent()
//                    intent.putExtra(EXTRA_CAMERAX_IMAGE, output.savedUri.toString())
//                    activity!!.setResult(CAMERAX_RESULT, intent)
//                    activity!!.finish()

                    val imageUri = output.savedUri.toString()
                    val bundle = Bundle().apply {
                        putString(UploadFragment.EXTRA_FILE, imageUri)
                    }
                    showDialog(bundle)
                }

                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        requireActivity(),
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(TAG, "onError: ${exc.message}")
                }
            }
        )
    }

    fun showDialog(bundle: Bundle) {
        val uploadDialogFragment = UploadFragment()
        uploadDialogFragment.arguments = bundle

        val fragmentManager = childFragmentManager
        uploadDialogFragment.show(fragmentManager, UploadFragment::class.java.simpleName)
    }

    private fun hideSystemUI(activity: Activity) {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private val orientationEventListener by lazy {
        object : OrientationEventListener(requireActivity()) {
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN) {
                    return
                }

                val rotation = when (orientation) {
                    in 45 until 135 -> Surface.ROTATION_270
                    in 135 until 225 -> Surface.ROTATION_180
                    in 225 until 315 -> Surface.ROTATION_90
                    else -> Surface.ROTATION_0
                }

                imageCapture?.targetRotation = rotation
            }
        }
    }

    override fun onStart() {
        super.onStart()
        orientationEventListener.enable()
    }

    override fun onStop() {
        super.onStop()
        orientationEventListener.disable()
    }

    internal var optionDialogListener: UploadFragment.OnOptionDialogListener =
        object : UploadFragment.OnOptionDialogListener {
            override fun onOptionChosen(text: String?, image: String?) {
//            Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()

                currentImageUri = image!!.toUri()
                currentImageUri?.let { uri ->
                    val imageFile = uriToFile(uri, requireActivity()).reduceFileImage()

                    lifecycleScope.launch {
                        val compressedImage = Compressor.compress(requireContext(), imageFile)
                        Log.d("Image File", "showImage: ${imageFile.path}")
                        showToast(currentImageUri.toString())
                        showLoading(true)
//                        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
//                        val multipartBody = MultipartBody.Part.createFormData(
//                            "picture", imageFile.name, requestImageFile
//                        )

                        when (text) {
                            "Object Detection" -> {
                                showToast(text)
                                viewModel.uploadObjectDetect(compressedImage)
                                    .observe(this@ScanFragment) { result ->
                                        if (result != null) {
                                            when (result) {
                                                is ResultState.Success -> {
                                                    showLoading(false)
//                                                val data = result.data
//                                                //navigate to scan result
//
//                                                val bundle = bundleOf(
//                                                    "photo" to image,
//                                                    "dataObj" to data
//                                                )

                                                    val responseBundle = Bundle().apply {
                                                        // Menambahkan dua list ke dalam Bundle
                                                        putString(ScanResultObjectFragment.EXTRA_PHOTO, image)
                                                        putString(ScanResultObjectFragment.EXTRA_RESULT, result.data.prediction)
                                                    }

                                                    val receiverFragment = ScanResultObjectFragment()
                                                    receiverFragment.arguments = responseBundle

                                                    view!!.findNavController().navigate(R.id.action_navigation_scan_to_scanResultObjectFragment, responseBundle)
                                                }

                                                is ResultState.Loading -> {
                                                    showLoading(true)
                                                }

                                                is ResultState.Error -> {
                                                    showLoading(false)
                                                    showToast("Server Error")
                                                }
                                            }
                                        }
                                    }
                            }

                            "Motif" -> {
                                showToast(text)
                                viewModel.uploadMotif(compressedImage)
                                    .observe(this@ScanFragment) { result ->
                                        if (result != null) {
                                            when (result) {
                                                is ResultState.Success -> {
                                                    showLoading(false)

                                                    val probabilityArray = result.data.prediction.predictedProbabilities.toDoubleArray()
                                                    val responseBundle = Bundle().apply {
                                                        putString(ScanResultMotifFragment.EXTRA_PHOTO, image)
                                                        putStringArrayList(ScanResultMotifFragment.EXTRA_LIST1, ArrayList(result.data.prediction.predictedClassNames))
                                                        putDoubleArray(ScanResultMotifFragment.EXTRA_LIST2, probabilityArray)
                                                    }

                                                    val receiverFragment = ScanResultMotifFragment()
                                                    receiverFragment.arguments = responseBundle

                                                    view!!.findNavController().navigate(R.id.action_navigation_scan_to_scanResultMotifFragment, responseBundle)

                                                }

                                                is ResultState.Loading -> {
                                                    showLoading(true)
                                                }

                                                is ResultState.Error -> {
                                                    showLoading(false)
                                                    showToast("Server Error")
                                                }
                                            }
                                        }
                                    }
                            }

                            else -> {
                                showToast("Unknown")
                            }
                        }
                    }


                }
            }
        }


    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "ScanFragment"
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}