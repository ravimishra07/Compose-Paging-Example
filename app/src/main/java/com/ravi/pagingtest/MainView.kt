package com.ravi.pagingtest

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.items
import kotlinx.coroutines.delay

data class Transaction(val id: String, val name: String)

@Composable
fun TransactionList() {

    val viewModel: TransactionViewModel = viewModel()
    val lazyPagingItems = viewModel.transactions.collectAsLazyPagingItems()

    LazyColumn {
        items(lazyPagingItems) { transaction ->
            transaction?.let {
                Text(modifier = Modifier.padding(30.dp), text = "ID: ${it.id}, Name: ${it.name}")
            }
        }
    }
}


class TransactionPagingSource : PagingSource<Int, Transaction>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {
        delay(1000)

        val page = params.key ?: 0
        return try {
            val transactions = loadTransactionsFromDataSource(page, params.loadSize)
            LoadResult.Page(
                data = transactions,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (transactions.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Transaction>): Int? {
        return state.anchorPosition?.let { position ->
            val closestPage = state.closestPageToPosition(position)
            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
        }
    }

    private fun loadTransactionsFromDataSource(page: Int, size: Int): List<Transaction> {
        return List(size) { index ->
            Transaction(id = "id_${page * size + index}", name = "Transaction ${page * size + index}")
        }
    }
}