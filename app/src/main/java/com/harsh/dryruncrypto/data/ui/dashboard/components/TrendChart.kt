package com.harsh.dryruncrypto.ui.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun TrendChart(
    sparklinePrices: List<Double>,
    priceChange24h: Double,
    modifier: Modifier = Modifier
) {
    if (sparklinePrices.isEmpty()) return

    val lineColor = if (priceChange24h >= 0) Color(0xFF4CAF50) else Color.Red

    Canvas(
        modifier = modifier
            .fillMaxWidth() // Expands across the total layout space dynamically
    ) {
        val minPrice = sparklinePrices.minOrNull() ?: 0.0
        val maxPrice = sparklinePrices.maxOrNull() ?: 0.0
        val priceRange = maxPrice - minPrice

        val distance = size.width / (sparklinePrices.size - 1)
        val path = Path()

        sparklinePrices.forEachIndexed { index, price ->
            val x = index * distance
            val y = if (priceRange != 0.0) {
                size.height - ((price - minPrice) / priceRange * size.height).toFloat()
            } else {
                size.height / 2f
            }

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 2.5.dp.toPx()) // Slightly thicker lines for visibility
        )
    }
}