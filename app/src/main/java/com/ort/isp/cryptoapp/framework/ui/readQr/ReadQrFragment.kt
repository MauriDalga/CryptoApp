package com.ort.isp.cryptoapp.framework.ui.readQr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.databinding.FragmentReadQrBinding
import com.ort.isp.cryptoapp.framework.ui.shared.TitledNavActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadQrFragment : Fragment() {

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

        binding.btnConfirm.setOnClickListener {
            val action =
                ReadQrFragmentDirections.actionNavigationReadQrToTransactionFragment(
                    binding.publicKeyReadQr.text.toString()
                )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}