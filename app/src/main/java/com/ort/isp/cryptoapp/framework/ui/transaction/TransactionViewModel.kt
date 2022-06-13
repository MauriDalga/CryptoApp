package com.ort.isp.cryptoapp.framework.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) :
    ViewModel() {

    private val _transactionForm = MutableLiveData<TransactionFormState>()
    val transactionForm: LiveData<TransactionFormState> = _transactionForm
    private val _transaction = MutableLiveData<Resource<Nothing>>()
    val transaction: LiveData<Resource<Nothing>> = _transaction

    fun createTransaction(
        amount: Double,
        coinId: Int,
        receiverWalletAddress: String
    ) {
        viewModelScope.launch {
            _transaction.value = Resource.Loading()
            _transaction.value =
                transactionRepository.createTransaction(amount, coinId, receiverWalletAddress)
        }
    }

    fun transactionAmountChanged(amount: String) {
        if (!isAmountValid(amount)) {
            _transactionForm.value = TransactionFormState(amountError = R.string.invalid_amount)
        } else {
            _transactionForm.value = TransactionFormState(isAmountValid = true)
        }
    }

    private fun isAmountValid(amount: String): Boolean {
        return TRANSACTION_MIN_AMOUNT < amount.toDouble()
    }
}

private const val TRANSACTION_MIN_AMOUNT = 0.0