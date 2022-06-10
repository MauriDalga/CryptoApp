package com.ort.isp.cryptoapp.framework.ui.transactionHistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.databinding.FragmentTransactionHistoryBinding
import com.ort.isp.cryptoapp.framework.ui.TitledNavActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryFragment : Fragment() {

    private var _binding: FragmentTransactionHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionHistoryBinding.inflate(inflater, container, false)
        (activity as TitledNavActivity).setNavTitle(getString(R.string.transaction_history_title))
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}