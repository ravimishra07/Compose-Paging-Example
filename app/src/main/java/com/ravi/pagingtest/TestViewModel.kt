package com.ravi.pagingtest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow



class TransactionViewModel : ViewModel() {
    private val repository = TransactionRepository() // Create repository instance

    // Expose the paging data
    val transactions: Flow<PagingData<Transaction>> = repository.getTransactionsPager().flow.cachedIn(viewModelScope)
}