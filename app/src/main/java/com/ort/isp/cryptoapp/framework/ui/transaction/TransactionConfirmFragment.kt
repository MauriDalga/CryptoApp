package com.ort.isp.cryptoapp.framework.ui.transaction

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.databinding.FragmentTransactionConfirmBinding
import com.ort.isp.cryptoapp.framework.data.local.CoinsCache.getCoinIdByCoinName
import com.ort.isp.cryptoapp.framework.ui.shared.TitledNavActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class TransactionConfirmFragment : Fragment() {

    private lateinit var transactionViewModel: TransactionViewModel
    private var _binding: FragmentTransactionConfirmBinding? = null
    private val binding get() = _binding!!
    private var btnConfirm: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        transactionViewModel =
            ViewModelProvider(this)[TransactionViewModel::class.java]
        _binding = FragmentTransactionConfirmBinding.inflate(inflater, container, false)
        (activity as TitledNavActivity).setNavTitle(getString(R.string.confirm_transaction_title))
        binding.publicKey.text = arguments!!.getString("publicKey")
        binding.quantityEntry.text = arguments!!.getString("amount")
        binding.cryptoEntry.text = arguments!!.getString("coinName")
        addListenerOnButton()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loadingProgressBar = binding.loading

        transactionViewModel.transaction.observe(viewLifecycleOwner, Observer { transaction ->
            transaction ?: return@Observer
            when (transaction) {
                is Resource.Loading -> loadingProgressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    loadingProgressBar.visibility = View.GONE
                    binding.transactionResult.text = SUCCESSFUL_TRANSACTION
                    binding.transactionResult.setBackgroundColor(
                        Color.parseColor(COLOR_GREEN)
                    )
                    showMessageAndNavigateToReadQrTransaction()
                }
                else -> {
                    loadingProgressBar.visibility = View.GONE
                    binding.transactionResult.setBackgroundColor(
                        Color.parseColor(COLOR_RED)
                    )
                    if (transaction.message.equals(INSUFFICIENT_BALANCE_ERROR)) {
                        binding.transactionResult.text = INSUFFICIENT_BALANCE
                    } else {
                        binding.transactionResult.text = transaction.message!!
                    }
                    showMessageAndNavigateToReadQrTransaction()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addListenerOnButton() {
        binding.btnConfirm!!.setOnClickListener {
            transactionViewModel.createTransaction(
                binding.quantityEntry.text.toString().toDouble(),
                getCoinIdByCoinName(binding.cryptoEntry.text.toString()),
                binding.publicKey.text.toString()
            )
        }
        binding.backButton.setOnClickListener {
            val action =
                TransactionConfirmFragmentDirections.actionTransactionConfirmFragmentToTransactionFragment(
                    binding.publicKey.text.toString()
                )
            findNavController().navigate(action)
        }
        binding.cancelTransaction.setOnClickListener {
            val action =
                TransactionConfirmFragmentDirections.actionTransactionConfirmFragmentToNavigationReadQr()
            findNavController().navigate(action)
        }
    }

    private fun showMessageAndNavigateToReadQrTransaction() {
        binding.transactionResult.visibility = View.VISIBLE
        Executors.newSingleThreadScheduledExecutor().schedule({
            val action =
                TransactionConfirmFragmentDirections.actionTransactionConfirmFragmentToNavigationReadQr()
            findNavController().navigate(action)
        }, 2, TimeUnit.SECONDS)
    }
}

private const val SUCCESSFUL_TRANSACTION = "Transacción exitosa"
private const val INSUFFICIENT_BALANCE = "Saldo insuficiente"
private const val COLOR_GREEN = "#47C244"
private const val COLOR_RED = "#DA3636"
private const val INSUFFICIENT_BALANCE_ERROR = "Insufficient funds to complete this transaction."