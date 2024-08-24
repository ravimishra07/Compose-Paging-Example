package com.ravi.pagingtest

import androidx.paging.Pager
import androidx.paging.PagingConfig

class TransactionRepository {
    fun getTransactionsPager(): Pager<Int, Transaction> {
        return Pager(PagingConfig(pageSize = 20)) {
            TransactionPagingSource()
        }
    }
}