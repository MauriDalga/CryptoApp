package com.ort.isp.cryptoapp.framework.ui.transaction

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.databinding.FragmentTransactionConfirmBinding
import com.ort.isp.cryptoapp.framework.data.local.CoinsCache.getCoinIdByCoinName
import com.ort.isp.cryptoapp.framework.ui.shared.TitledNavActivity
import com.ort.isp.cryptoapp.framework.ui.shared.logout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class TransactionConfirmFragment : Fragment() {

    private lateinit var receiverPublicKey: String
    private lateinit var transactionViewModel: TransactionViewModel
    private var _binding: FragmentTransactionConfirmBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        transactionViewModel =
            ViewModelProvider(this)[TransactionViewModel::class.java]
        _binding = FragmentTransactionConfirmBinding.inflate(inflater, container, false)
        (activity as TitledNavActivity).setNavTitle(getString(R.string.confirm_transaction_title))
        receiverPublicKey = arguments!!.getString("publicKey")!!
        binding.publicKey.text = receiverPublicKey
        binding.quantityEntry.text = arguments!!.getString("amount")
        binding.cryptoEntry.text = arguments!!.getString("coinName")
        addListenerOnButton()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transactionViewModel.transaction.observe(viewLifecycleOwner, Observer { transaction ->
            transaction ?: return@Observer
            updateUIWithTxResult(transaction)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addListenerOnButton() {
        binding.btnConfirm.setOnClickListener {
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
                TransactionConfirmFragmentDirections.actionTransactionConfirmFragmentToLoggedUserNavigation()
            findNavController().navigate(action)
        }
    }

    private fun showMessageAndNavigateTo(navDirections: NavDirections) {
        binding.transactionResult.visibility = View.VISIBLE
        lifecycleScope.launch {
            delay(RESULT_MESSAGE_DELAY)
            withContext(Dispatchers.Main) { findNavController().navigate(navDirections) }
        }
    }

    private fun updateUIWithTxResult(txResult: Resource<Unit>) {
        val loadingProgressBar = binding.loading
        when (txResult) {
            is Resource.Loading -> loadingProgressBar.visibility = View.VISIBLE
            is Resource.Success -> {
                loadingProgressBar.visibility = View.GONE
                binding.transactionResult.text = SUCCESSFUL_TRANSACTION
                binding.transactionResult.setBackgroundColor(
                    Color.parseColor(COLOR_GREEN)
                )
                showMessageAndNavigateTo(TransactionConfirmFragmentDirections.actionTransactionConfirmFragmentToLoggedUserNavigation())
            }
            is Resource.Unauthorized -> {
                logout()
            }
            else -> {
                loadingProgressBar.visibility = View.GONE
                binding.transactionResult.setBackgroundColor(
                    Color.parseColor(COLOR_RED)
                )
                if (txResult.message.equals(INSUFFICIENT_BALANCE_ERROR)) {
                    binding.transactionResult.text = INSUFFICIENT_BALANCE
                    showMessageAndNavigateTo(
                        TransactionConfirmFragmentDirections.actionTransactionConfirmFragmentToTransactionFragment(
                            receiverPublicKey
                        )
                    )
                } else {
                    binding.transactionResult.text = txResult.message!!
                    showMessageAndNavigateTo(TransactionConfirmFragmentDirections.actionTransactionConfirmFragmentToLoggedUserNavigation())
                }
            }
        }
    }
}

private const val SUCCESSFUL_TRANSACTION = "Transacción exitosa"
private const val INSUFFICIENT_BALANCE = "Saldo insuficiente"
private const val COLOR_GREEN = "#47C244"
private const val COLOR_RED = "#DA3636"
private const val INSUFFICIENT_BALANCE_ERROR = "Insufficient funds to complete this transaction."
private const val RESULT_MESSAGE_DELAY = 2000L