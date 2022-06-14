package com.ort.isp.cryptoapp.framework.ui.transaction

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.databinding.FragmentTransactionBinding
import com.ort.isp.cryptoapp.framework.data.local.CoinsCache.getCoins
import com.ort.isp.cryptoapp.framework.ui.shared.TitledNavActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionFragment : Fragment() {

    private lateinit var transactionViewModel: TransactionViewModel
    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!
    private var cryptoSpinner: Spinner? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        transactionViewModel =
            ViewModelProvider(this)[TransactionViewModel::class.java]
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        (activity as TitledNavActivity).setNavTitle(getString(R.string.new_transaction_title))
        binding.publicKey.text = arguments!!.getString("publicKey")
        addListenerOnButton()
        updateUIWithUserCrypto()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transactionViewModel.transactionForm.observe(viewLifecycleOwner,
            Observer { transactionFormState ->
                transactionFormState ?: return@Observer

                binding.btnSubmit.isEnabled = transactionFormState.isAmountValid
                transactionFormState.amountError?.let {
                    binding.quantityInput.error = getString(it)
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                transactionViewModel.transactionAmountChanged(binding.quantityInput.text.toString())
            }
        }
        binding.quantityInput.addTextChangedListener(afterTextChangedListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addListenerOnButton() {
        cryptoSpinner = binding.cryptoSpinner
        binding.btnSubmit.setOnClickListener {
            val action =
                TransactionFragmentDirections.actionTransactionFragmentToTransactionConfirmFragment(
                    binding.publicKey.text.toString(),
                    cryptoSpinner!!.selectedItem.toString(),
                    binding.quantityInput.text.toString()
                )
            findNavController().navigate(action)
        }

        binding.backButton.setOnClickListener {
            val action =
                TransactionFragmentDirections.actionTransactionFragmentToNavigationReadQr()
            findNavController().navigate(action)
        }
    }

    private fun updateUIWithUserCrypto() {
        cryptoSpinner = binding.cryptoSpinner
        val list: MutableList<String> = ArrayList()
        for (coin in getCoins()) list.add(coin.longName)
        val dataAdapter = ArrayAdapter(
            activity!!.applicationContext,
            R.layout.my_spinner, list
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cryptoSpinner!!.adapter = dataAdapter
    }
}