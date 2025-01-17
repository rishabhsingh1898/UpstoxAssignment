package com.rishabhsingh.upstoxassignment.repository.stocks


import com.rishabhsingh.upstoxassignment.api.StockApi
import com.rishabhsingh.upstoxassignment.models.Stocks
import javax.inject.Inject

class StocksRepositoryImpl @Inject constructor(private val api: StockApi) : StocksRepository {

    override suspend fun getStocks(): List<Stocks> {
        return try {
            api.getStocks().stocksData.stocksList
        } catch (e: Exception) {
            throw Exception("Failed to fetch stocks: ${e.message}")
        }
    }
}
