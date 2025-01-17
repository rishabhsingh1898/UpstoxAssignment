package com.rishabhsingh.upstoxassignment.screens.stocks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rishabhsingh.upstoxassignment.R
import com.rishabhsingh.upstoxassignment.models.AggregateState
import com.rishabhsingh.upstoxassignment.ui.theme.LossRed
import com.rishabhsingh.upstoxassignment.ui.theme.ProfitGreen
import com.rishabhsingh.upstoxassignment.util.formatCurrency

@Composable
fun ExpandableSection(
    aggregateState: AggregateState
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .clickable { isExpanded = !isExpanded }
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .fillMaxWidth()
    ) {

        AnimatedVisibility(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .fillMaxWidth(),
            visible = isExpanded
        ) {
            ExpandedSection(aggregateState)
        }
        HorizontalLine()
        CollapsedSection(
            totalProfitAndLoss = aggregateState.totalInvestedValue-aggregateState.totalCurrentInvestedValue,
            totalProfitAndLossPercentage = aggregateState.profitAndLossPercentage,
            isExpanded = isExpanded
        )

    }
}

@Composable
fun ExpandedSection(
    aggregateState: AggregateState
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(R.string.current_value))
            Text(text = stringResource(R.string.stock_amount, aggregateState.totalCurrentInvestedValue))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(R.string.total_investment))
            Text(text = stringResource(R.string.stock_amount, aggregateState.totalInvestedValue))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(R.string.today_s_profit_loss))
            Text(
                text = aggregateState.sumOfTodayProfitAndLoss.formatCurrency(),
                color = if (aggregateState.sumOfTodayProfitAndLoss < 0) LossRed else ProfitGreen
            )
        }
    }
}

@Composable
fun CollapsedSection(
    totalProfitAndLoss: Float,
    totalProfitAndLossPercentage: String,
    modifier: Modifier = Modifier, isExpanded: Boolean
) {

    val icon = if (isExpanded) Icons.Rounded.KeyboardArrowDown else Icons.Rounded.KeyboardArrowUp

    Row(modifier = modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = stringResource(R.string.profit_and_loss),
            style = MaterialTheme.typography.bodyMedium
        )
        Image(
            modifier = Modifier.size(20.dp),
            imageVector = icon,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimaryContainer),
            contentDescription = stringResource(id = R.string.expand_or_collapse)
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Text(
                text = "${totalProfitAndLoss.formatCurrency()}($totalProfitAndLossPercentage%)",
                color = if (totalProfitAndLoss > 0) ProfitGreen else LossRed,
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }
}

@Composable
fun HorizontalLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray)
    )
}