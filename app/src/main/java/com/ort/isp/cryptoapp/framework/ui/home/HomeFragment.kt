package com.ort.isp.cryptoapp.framework.ui.home

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.model.`in`.UserFullData
import com.ort.isp.cryptoapp.databinding.FragmentHomeBinding
import com.ort.isp.cryptoapp.framework.data.notification.TransactionNotificationService
import com.ort.isp.cryptoapp.framework.ui.shared.TitledNavActivity
import com.ort.isp.cryptoapp.framework.ui.shared.logout
import com.ort.isp.cryptoapp.framework.ui.shared.showMessage
import com.ort.isp.cryptoapp.framework.ui.shared.toBase64Bitmap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: CoinAccountAdapter
    private val requestPhotoToCameraApp =
        registerForActivityResult(
            ActivityResultContracts.TakePicturePreview(),
            this::handleUserPhotoTaken
        )
    private val requestCamera =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                dispatchTakePictureIntent()
            } else {
                showMessage(getString(R.string.camera_access_denied_feedback_profile))
            }
        }
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        adapter = CoinAccountAdapter()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        (activity as TitledNavActivity).setNavTitle(getString(R.string.home_title))
        TransactionNotificationService.notificationAck()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loadingProgressBar = binding.loading
        binding.coinAccountList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@HomeFragment.adapter
        }

        homeViewModel.userFullData.observe(viewLifecycleOwner, Observer { userFullData ->
            userFullData ?: return@Observer

            when (userFullData) {
                is Resource.Loading -> loadingProgressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    loadingProgressBar.visibility = View.GONE
                    updateUIWithUserData(userFullData.data!!)
                }
                is Resource.Unauthorized -> {
                    loadingProgressBar.visibility = View.GONE
                    logout()
                }
                else -> {
                    loadingProgressBar.visibility = View.GONE
                    showMessage(userFullData.message!!)
                }
            }
        })

        homeViewModel.userPhotoUploadStatus.observe(viewLifecycleOwner, Observer { userPhoto ->
            userPhoto ?: return@Observer

            when (userPhoto) {
                is Resource.Loading -> {
                    // Do nothing
                }
                is Resource.Success -> {
                    binding.userPhoto.setImageBitmap(userPhoto.data!!)
                }
                is Resource.Unauthorized -> {
                    logout()
                }
                else -> {
                    showMessage(userPhoto.message!!)
                }
            }
        })

        binding.editUserPhoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                dispatchTakePictureIntent()
            } else {
                requestCamera.launch(Manifest.permission.CAMERA)
            }
        }

        homeViewModel.fetchUserData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUIWithUserData(userFullData: UserFullData) {
        adapter.setCoinAccount(userFullData.coinAccounts)
        userFullData.image?.takeIf { it.isNotBlank() }
            ?.let { binding.userPhoto.setImageBitmap(it.toBase64Bitmap()) }

        binding.userCompleteName.text = "${userFullData.name}\n${userFullData.lastname}"
        binding.userEmail.text = userFullData.email
    }

    private fun dispatchTakePictureIntent() {
        try {
            requestPhotoToCameraApp.launch(null)
        } catch (e: ActivityNotFoundException) {
            showMessage(getString(R.string.unexpected_upload_photo_error))
        }
    }

    private fun handleUserPhotoTaken(photoBitmap: Bitmap?) {
        photoBitmap?.let { homeViewModel.uploadUserPhoto(it) }
    }
}