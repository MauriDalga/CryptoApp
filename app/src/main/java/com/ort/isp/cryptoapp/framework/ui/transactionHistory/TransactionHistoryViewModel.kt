package com.ort.isp.cryptoapp.framework.ui.transactionHistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.model.`in`.TransactionDetail
import com.ort.isp.cryptoapp.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionHistoryViewModel @Inject constructor(private val transactionRepository: TransactionRepository) :
    ViewModel() {

    private val _transactionsHistory = MutableLiveData<Resource<List<TransactionDetail>>>()
    val transactionsHistory: LiveData<Resource<List<TransactionDetail>>> = _transactionsHistory

    fun getTransactionsHistory() {
        viewModelScope.launch {
            _transactionsHistory.value = Resource.Loading()
            _transactionsHistory.value = transactionRepository.getTransactionHistoryByUser()
        }
    }
}