package com.rishabhsingh.upstoxassignment.models

data class AggregateState(
    val totalInvestedValue: Float = 0f,
    val totalCurrentInvestedValue: Float = 0f,
    val sumOfTodayProfitAndLoss: Float = 0f,
    val profitAndLossPercentage: String = "0.00"
)
