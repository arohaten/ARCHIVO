package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun StatusBadge(
    status: String,
    modifier: Modifier = Modifier
) {
    val (bgColor, borderColor, textColor) = when {
        status.contains("RECUPERADO") && !status.contains("PARCIAL") ->
            Triple(VerifiedGreenDark.copy(alpha = 0.3f), VerifiedGreen, VerifiedGreen)
        status.contains("PARCIAL") || status.contains("INVESTIGACIÓN") ->
            Triple(AlertOrange.copy(alpha = 0.15f), AlertOrange, AlertOrange)
        status.contains("CORRUPTO") ->
            Triple(PericialCyanMuted.copy(alpha = 0.15f), PericialCyan, PericialCyan)
        status.contains("DESAPARECIDO") || status.contains("NO LOCALIZADO") || status.contains("EXPURGADO") ->
            Triple(RedactRedDark.copy(alpha = 0.35f), RedactRed, RedactRed)
        else ->
            Triple(SlateSurface, ArchivalMuted, ArchivalPaper)
    }

    Box(
        modifier = modifier
            .border(width = 0.8.dp, color = borderColor, shape = RoundedCornerShape(2.dp))
            .background(bgColor, shape = RoundedCornerShape(2.dp))
            .padding(horizontal = 6.dp, vertical = 3.dp)
    ) {
        Text(
            text = status.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.6.sp
            ),
            color = textColor
        )
    }
}

@Composable
fun ClassificationStamp(
    classification: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(width = 1.dp, color = RedactRed.copy(alpha = 0.8f), shape = RoundedCornerShape(2.dp))
            .background(RedactRedDark.copy(alpha = 0.2f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = "« $classification »",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp
            ),
            color = RedactRed
        )
    }
}

@Composable
fun RedactedText(
    text: String,
    isCensored: Boolean,
    modifier: Modifier = Modifier,
    onToggle: () -> Unit = {}
) {
    if (isCensored) {
        Box(
            modifier = modifier
                .background(Color.Black)
                .border(0.6.dp, RedactRed.copy(alpha = 0.6f))
                .clickable { onToggle() }
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text(
                text = "████████████ [REDACTADO // TOCAR PARA REVELAR]",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily.Monospace,
                    color = RedactRed.copy(alpha = 0.9f),
                    fontWeight = FontWeight.Bold
                )
            )
        }
    } else {
        Box(
            modifier = modifier
                .background(SlateSurface)
                .border(0.6.dp, ArchivalFaint)
                .clickable { onToggle() }
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = ArchivalPaper
                )
            )
        }
    }
}

@Composable
fun MetricBox(
    label: String,
    value: String,
    sublabel: String = "",
    valueColor: Color = PericialCyan,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(SlateDark, RoundedCornerShape(2.dp))
            .border(0.8.dp, SlateBorder, RoundedCornerShape(2.dp))
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Column {
            Text(
                text = label.uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(
                    color = ArchivalMuted,
                    fontSize = 9.sp,
                    letterSpacing = 0.6.sp
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = valueColor,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            )
            if (sublabel.isNotBlank()) {
                Text(
                    text = sublabel,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = ArchivalFaint,
                        fontSize = 8.sp
                    )
                )
            }
        }
    }
}

@Composable
fun TechnicalHeader(
    title: String,
    code: String = "",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(14.dp)
                .background(PericialCyan)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.labelLarge.copy(
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Bold
            ),
            color = ArchivalPaper
        )
        if (code.isNotBlank()) {
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "//$code",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = FontFamily.Monospace
                ),
                color = ArchivalMuted
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .weight(1f)
                .height(0.8.dp)
                .background(SlateBorder)
        )
    }
}

@Composable
fun HairlineDivider(
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        modifier = modifier,
        thickness = 0.8.dp,
        color = SlateBorder
    )
}
