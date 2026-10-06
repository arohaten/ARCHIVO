package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.LocationEntity
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.ArchiveViewModel
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun MapRadarScreen(
    viewModel: ArchiveViewModel,
    onNavigateToDossierId: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val locations by viewModel.locations.collectAsState()
    var selectedLocation by remember { mutableStateOf<LocationEntity?>(null) }

    // Radar sweep animation
    val infiniteTransition = rememberInfiniteTransition(label = "radarSweep")
    val sweepAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sweepAngle"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SlateBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            TechnicalHeader(title = "RED GEODÉSICA DE BALIZAMIENTO", code = "ETRS89-VLC")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.8.dp, AlertOrange.copy(alpha = 0.6f), RoundedCornerShape(2.dp)),
                colors = CardDefaults.cardColors(containerColor = SlateDark),
                shape = RoundedCornerShape(2.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "ADVERTENCIA TÉCNICA CAP. 311:",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = AlertOrange,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "«NO TODAS LAS COORDENADAS REPRESENTAN UBICACIONES FÍSICAS ESTABLES. ALGUNAS IDENTIFICAN PUNTOS DE EVENTO, DESPLAZAMIENTO O FENÓMENOS DINÁMICOS.»",
                        style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                        color = ArchivalPaper
                    )
                }
            }
        }

        // Radar Canvas Display
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .border(0.8.dp, SlateBorder, RoundedCornerShape(2.dp)),
                colors = CardDefaults.cardColors(containerColor = SlateDark),
                shape = RoundedCornerShape(2.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val center = Offset(size.width / 2f, size.height / 2f)
                        val maxRadius = minOf(size.width, size.height) * 0.44f

                        // Grid lines
                        drawLine(
                            color = SlateBorder,
                            start = Offset(0f, center.y),
                            end = Offset(size.width, center.y),
                            strokeWidth = 1f
                        )
                        drawLine(
                            color = SlateBorder,
                            start = Offset(center.x, 0f),
                            end = Offset(center.x, size.height),
                            strokeWidth = 1f
                        )

                        // Concentric rings
                        drawCircle(color = SlateBorder, radius = maxRadius * 0.33f, center = center, style = Stroke(1f))
                        drawCircle(color = SlateBorder, radius = maxRadius * 0.66f, center = center, style = Stroke(1f))
                        drawCircle(color = SlateBorder, radius = maxRadius, center = center, style = Stroke(1f))

                        // Radar sweep line
                        val rad = Math.toRadians(sweepAngle.toDouble())
                        val sweepEnd = Offset(
                            x = center.x + (maxRadius * cos(rad)).toFloat(),
                            y = center.y + (maxRadius * sin(rad)).toFloat()
                        )
                        drawLine(
                            color = PericialCyan.copy(alpha = 0.6f),
                            start = center,
                            end = sweepEnd,
                            strokeWidth = 2f
                        )

                        // Draw location blips
                        locations.forEachIndexed { index, loc ->
                            // Map coordinates to radar relative space
                            val angleOffset = (index * (360.0 / (locations.size.coerceAtLeast(1))))
                            val locRad = Math.toRadians(angleOffset)
                            val dist = maxRadius * (0.35f + (index % 3) * 0.25f)
                            val blipOffset = Offset(
                                x = center.x + (dist * cos(locRad)).toFloat(),
                                y = center.y + (dist * sin(locRad)).toFloat()
                            )

                            val blipColor = when (loc.visibilityLevel) {
                                "PÚBLICA" -> VerifiedGreen
                                "PARCIAL" -> AlertOrange
                                else -> RedactRed
                            }

                            drawCircle(color = blipColor, radius = 5f, center = blipOffset)
                            drawCircle(color = blipColor.copy(alpha = 0.3f), radius = 10f, center = blipOffset)
                        }
                    }

                    // Overlay HUD text
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "SECTOR 39°N // 0°W",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = PericialCyan,
                                fontSize = 8.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                        Text(
                            text = "ESTADO: ESCANEO ACTIVO",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ArchivalMuted,
                                fontSize = 8.sp
                            )
                        )
                    }

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "${locations.size} NODOS BALIZADOS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ArchivalPaper,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }

        // List of Coordinate Nodes
        item {
            TechnicalHeader(title = "PUNTOS BALIZADOS", code = "COTEJO PERICIAL")
        }

        items(locations) { loc ->
            val isSelected = selectedLocation?.id == loc.id
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 0.8.dp,
                        color = if (isSelected) PericialCyan else SlateBorder,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .clickable { selectedLocation = loc }
                    .testTag("location_item_${loc.code}"),
                colors = CardDefaults.cardColors(containerColor = SlateDark),
                shape = RoundedCornerShape(2.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = loc.code,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = PericialCyan,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                        StatusBadge(status = loc.visibilityLevel)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${loc.name.uppercase()} (${loc.area})",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = ArchivalPaper
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "LAT: ${String.format("%.4f", loc.latitude)}° N  //  LONG: ${String.format("%.4f", loc.longitude)}° W",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = AlertOrange,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = loc.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = ArchivalMuted
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ESTADO: ${loc.status}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 8.sp,
                                color = ArchivalFaint
                            )
                        )
                        OutlinedButton(
                            onClick = { onNavigateToDossierId(loc.relatedDossier) },
                            shape = RoundedCornerShape(2.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                            modifier = Modifier.height(28.dp)
                        ) {
                            Text(
                                text = "VER ${loc.relatedDossier}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = PericialCyan,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
