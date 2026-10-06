package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.ArchiveViewModel

@Composable
fun ChronologyScreen(
    viewModel: ArchiveViewModel,
    onNavigateToDossierId: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val chronologies by viewModel.chronologies.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SlateBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            TechnicalHeader(title = "LÍNEA TEMPORAL DE ACONTECIMIENTOS", code = "1994 – 2026")
            Text(
                text = "Cronograma documental de actuaciones, hallazgos de campo y expurgos oficiales. Algunos hitos reflejan contradicciones premeditadas entre informes de distintas décadas.",
                style = MaterialTheme.typography.bodySmall,
                color = ArchivalMuted
            )
        }

        items(chronologies) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.6.dp, SlateBorder, RoundedCornerShape(2.dp))
                    .testTag("chrono_item_${item.id}"),
                colors = CardDefaults.cardColors(containerColor = SlateDark),
                shape = RoundedCornerShape(2.dp)
            ) {
                Row(modifier = Modifier.padding(12.dp)) {
                    // Year badge
                    Column(
                        modifier = Modifier
                            .width(60.dp)
                            .background(SlateSurface)
                            .padding(vertical = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = item.year.toString(),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = PericialCyan,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = item.dateFormatted,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = ArchivalMuted,
                                    fontSize = 9.sp
                                )
                            )
                            if (item.relatedDossier.isNotBlank()) {
                                Text(
                                    text = item.relatedDossier,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = AlertOrange,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ArchivalPaper
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = item.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = ArchivalMuted
                        )
                    }
                }
            }
        }
    }
}
