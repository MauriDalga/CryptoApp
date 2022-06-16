package com.ort.isp.cryptoapp.framework.ui.readQr

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.databinding.FragmentReadQrBinding
import com.ort.isp.cryptoapp.framework.ui.shared.TitledNavActivity
import com.ort.isp.cryptoapp.framework.ui.shared.showMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadQrFragment : Fragment() {

    private val requestCamera =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                isCammeraPermissionAccepted = true
            } else {
                showMessage(getString(R.string.camera_access_denied_feedback_profile))
            }
        }
    private var isCammeraPermissionAccepted: Boolean = false
    private lateinit var codeScanner: CodeScanner
    private var _binding: FragmentReadQrBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReadQrBinding.inflate(inflater, container, false)
        (activity as TitledNavActivity).setNavTitle(getString(R.string.read_QR_title))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            isCammeraPermissionAccepted = true
        } else {
            requestCamera.launch(Manifest.permission.CAMERA)
        }

        val scannerView = binding.scannerView
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scannerView)
        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {
                val action =
                    ReadQrFragmentDirections.actionNavigationReadQrToTransactionFragment(it.text)
                findNavController().navigate(action)
            }
        }

        codeScanner.errorCallback = ErrorCallback {
            activity.runOnUiThread {
                it.message?.let { it1 -> showMessage(it1) }
            }
        }
        if (isCammeraPermissionAccepted) scannerView.setOnClickListener { codeScanner.startPreview() }
    }

    override fun onResume() {
        super.onResume()
        if (isCammeraPermissionAccepted) codeScanner.startPreview()
    }

    override fun onPause() {
        if (isCammeraPermissionAccepted) codeScanner.releaseResources()
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}