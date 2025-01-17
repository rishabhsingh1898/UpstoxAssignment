package com.rishabhsingh.upstoxassignment.viewmodel.stock


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rishabhsingh.upstoxassignment.api.NetworkState
import com.rishabhsingh.upstoxassignment.models.AggregateState
import com.rishabhsingh.upstoxassignment.models.Stocks
import com.rishabhsingh.upstoxassignment.repository.stocks.StocksRepository
import com.rishabhsingh.upstoxassignment.util.calculateProfitPercentage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StocksHoldingViewModel @Inject constructor(private val stocksRepositoryImpl: StocksRepository) :
    ViewModel() {

    private val _stocksState = MutableStateFlow<NetworkState<List<Stocks>>>(NetworkState.Loading)
    val stocksState: StateFlow<NetworkState<List<Stocks>>> = _stocksState

    private val _aggregateState = MutableStateFlow(AggregateState())
    val aggregateState: StateFlow<AggregateState> = _aggregateState


    init {
        getStocks()
    }

    fun getStocks() {
        viewModelScope.launch {
            _stocksState.emit(NetworkState.Loading)
            try {
                val stocks = stocksRepositoryImpl.getStocks()
                if (stocks.isEmpty()) {
                    _stocksState.emit(NetworkState.Empty)
                } else {
                    calculateAggregateValues(stocks)
                    _stocksState.emit(NetworkState.Success(stocks))
                }
            } catch (e: Exception) {
                _stocksState.emit(NetworkState.Failure(e.message ?: "Unknown error occurred"))
            }
        }
    }

    fun calculateAggregateValues(stocks: List<Stocks>) {
        var currentValue = 0.0f
        var totalInvestment = 0.0f
        var todayProfitAndLoss = 0.0f

        stocks.forEach { stock ->
            currentValue += stock.ltp * stock.quantity
            totalInvestment += stock.avgPrice * stock.quantity
            todayProfitAndLoss += (stock.close - stock.ltp) * stock.quantity
        }

        _aggregateState.value = AggregateState(
            totalInvestedValue = currentValue,
            totalCurrentInvestedValue = totalInvestment,
            sumOfTodayProfitAndLoss = todayProfitAndLoss,
            profitAndLossPercentage = calculateProfitPercentage(currentValue, totalInvestment)
        )
    }
}