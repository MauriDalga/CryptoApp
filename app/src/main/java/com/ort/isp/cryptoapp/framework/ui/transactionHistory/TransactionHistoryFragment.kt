package com.ort.isp.cryptoapp.framework.ui.transactionHistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.databinding.FragmentTransactionHistoryBinding
import com.ort.isp.cryptoapp.framework.ui.shared.TitledNavActivity
import com.ort.isp.cryptoapp.framework.ui.shared.logout
import com.ort.isp.cryptoapp.framework.ui.shared.showMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryFragment : Fragment() {

    private lateinit var transactionHistoryViewModel: TransactionHistoryViewModel
    lateinit var adapter: TransactionHistoryAdapter
    private var _binding: FragmentTransactionHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        transactionHistoryViewModel =
            ViewModelProvider(this)[TransactionHistoryViewModel::class.java]
        adapter = TransactionHistoryAdapter()
        _binding = FragmentTransactionHistoryBinding.inflate(inflater, container, false)
        (activity as TitledNavActivity).setNavTitle(getString(R.string.transaction_history_title))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loadingProgressBar = binding.loading
        binding.transactionHistoryList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@TransactionHistoryFragment.adapter
        }

        transactionHistoryViewModel.transactionsHistory.observe(
            viewLifecycleOwner,
            Observer { transactionsHistory ->
                transactionsHistory ?: return@Observer

                when (transactionsHistory) {
                    is Resource.Loading -> loadingProgressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        loadingProgressBar.visibility = View.GONE
                        val transactionsList = transactionsHistory.data!!
                        if (transactionsList.isEmpty()) {
                            binding.transactionHistoryList.visibility = View.GONE
                            binding.emptyTransactionList.visibility = View.VISIBLE
                        } else {
                            binding.emptyTransactionList.visibility = View.GONE
                            adapter.setTransactionsDetail(transactionsList)
                            binding.transactionHistoryList.visibility = View.VISIBLE
                        }
                    }
                    is Resource.Unauthorized -> {
                        logout()
                    }
                    else -> {
                        loadingProgressBar.visibility = View.GONE
                        showMessage(transactionsHistory.message!!)
                    }
                }
            })
        transactionHistoryViewModel.getTransactionsHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}