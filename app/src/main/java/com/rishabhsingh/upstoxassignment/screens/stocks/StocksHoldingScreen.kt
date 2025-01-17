package com.rishabhsingh.upstoxassignment.screens.stocks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rishabhsingh.upstoxassignment.R
import com.rishabhsingh.upstoxassignment.api.NetworkState
import com.rishabhsingh.upstoxassignment.viewmodel.stock.StocksHoldingViewModel

@Composable
fun StocksHoldingScreen(modifier: Modifier) {
    val viewModel: StocksHoldingViewModel = viewModel()
    val stocksUiState = viewModel.stocksState.collectAsState().value
    val aggregateState = viewModel.aggregateState.collectAsState().value

    when (stocksUiState) {
        is NetworkState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is NetworkState.Success -> {
            val stocks = stocksUiState.data
            Column(modifier = modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(stocks, key = { it.symbol }) { stock ->
                        StockItems(stock)
                    }
                }
                ExpandableSection(aggregateState)
            }
        }
        is NetworkState.Failure -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.error, stocksUiState.errorMessage),
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        is NetworkState.Empty -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.no_stocks_available), modifier = Modifier.padding(16.dp))
            }
        }
    }
}