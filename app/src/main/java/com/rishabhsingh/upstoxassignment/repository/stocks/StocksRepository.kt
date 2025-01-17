package com.rishabhsingh.upstoxassignment.repository.stocks

import com.rishabhsingh.upstoxassignment.models.Stocks

interface StocksRepository {
    suspend fun getStocks(): List<Stocks>
}