package com.rishabhsingh.upstoxassignment.models

data class Stocks(
    val symbol: String,
    val quantity: Int,
    val ltp: Float,
    val avgPrice: Float,
    val close: Float,
)
