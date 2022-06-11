package com.ort.isp.cryptoapp.framework.ui.shareQr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.databinding.FragmentShareQrBinding
import com.ort.isp.cryptoapp.framework.ui.shared.TitledNavActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareQrFragment : Fragment() {

    private var _binding: FragmentShareQrBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShareQrBinding.inflate(inflater, container, false)
        (activity as TitledNavActivity).setNavTitle(getString(R.string.share_QR_title))
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}