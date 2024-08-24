package com.ravi.pagingtest

// Simulate loading data
private fun loadTransactionsFromDataSource(page: Int, size: Int): List<Transaction> {
    return List(size) { index ->
        Transaction(id = "id_${page * size + index}", name = "Transaction ${page * size + index}")
    }
}