package com.rishabhsingh.upstoxassignment.util

import com.rishabhsingh.upstoxassignment.models.Stocks
import kotlin.math.abs

fun Float.formatCurrency(): String {
    return if (this < 0) {
        "-₹${"%.2f".format(-this)}"
    } else {
        "₹ ${"%.2f".format(this)}"
    }
}

fun calculateTotalProfitAndLoss(stock: Stocks): Float {
    val stockCurrentInvestmentValue = stock.ltp * stock.quantity
    val stockInvestedValue = stock.avgPrice * stock.quantity
    val profitAndLoss = stockCurrentInvestmentValue - stockInvestedValue
    return profitAndLoss
}

fun calculateProfitPercentage(currentValue: Float, totalInvestment: Float): String {
    if (currentValue == 0f) return "0.00"
    val percentage = (abs(totalInvestment - currentValue) / totalInvestment) * 100
    return String.format("%.2f", percentage)
}