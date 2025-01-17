package com.rishabhsingh.upstoxassignment.screens.stocks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rishabhsingh.upstoxassignment.R
import com.rishabhsingh.upstoxassignment.models.Stocks
import com.rishabhsingh.upstoxassignment.ui.theme.LossRed
import com.rishabhsingh.upstoxassignment.ui.theme.ProfitGreen
import com.rishabhsingh.upstoxassignment.util.calculateTotalProfitAndLoss
import com.rishabhsingh.upstoxassignment.util.formatCurrency

@Composable
fun StockItems(stock: Stocks) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape
    ) {
        HorizontalLine()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stock.symbol,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.ltp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                    Text(text = stock.ltp.formatCurrency())
                }
            }
            val profitAndLoss = calculateTotalProfitAndLoss(stock)

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.net_qty),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                    Text(text = "${stock.quantity}")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.p_l),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                    Text(
                        text = profitAndLoss.formatCurrency(),
                        color = if (profitAndLoss > 0) ProfitGreen else LossRed
                    )
                }
            }
        }
    }

}

val stocks = Stocks("HDFC", 990, 38.05f, 40.0f, 40.0f)

@Preview
@Composable
fun StockItemsPreview() {
    StockItems(stock = stocks)
}
