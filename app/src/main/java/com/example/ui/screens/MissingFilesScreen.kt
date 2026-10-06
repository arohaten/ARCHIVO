package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.DossierEntity
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.ArchiveViewModel

@Composable
fun MissingFilesScreen(
    viewModel: ArchiveViewModel,
    onSelectDossier: (DossierEntity) -> Unit,
    onNavigateToCitizenDrop: () -> Unit,
    modifier: Modifier = Modifier
) {
    val missingDossiers by viewModel.missingDossiers.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SlateBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, RedactRed, RoundedCornerShape(2.dp)),
                colors = CardDefaults.cardColors(containerColor = RedactRedDark.copy(alpha = 0.25f)),
                shape = RoundedCornerShape(2.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "EXPEDIENTES EXPURGADOS O DESAPARECIDOS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = RedactRed,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = RedactRed,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "FONDOS SIN COPIA DIGITAL CERTIFICADA",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = ArchivalPaper,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Estos documentos constan en los libros de registro oficiales porque aparecen citados en actas posteriores, pero sus contenidos físicos fueron retirados, quemados o deslocalizados antes de la digitalización.",
                        style = MaterialTheme.typography.bodySmall,
                        color = ArchivalPaper
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = onNavigateToCitizenDrop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .testTag("btn_report_missing_clue"),
                        shape = RoundedCornerShape(2.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RedactRed)
                    ) {
                        Text(
                            text = "APORTAR PISTA O REGISTRO SOBRE ESTOS ARCHIVOS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ArchivalPaper,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }

        item {
            TechnicalHeader(
                title = "LEGAJOS EN BÚSQUEDA ACTIVA",
                code = "${missingDossiers.size} CLASIFICADOS"
            )
        }

        items(missingDossiers) { dossier ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.8.dp, RedactRed.copy(alpha = 0.6f), RoundedCornerShape(2.dp))
                    .clickable { onSelectDossier(dossier) }
                    .testTag("missing_dossier_${dossier.id}"),
                colors = CardDefaults.cardColors(containerColor = SlateDark),
                shape = RoundedCornerShape(2.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = dossier.id,
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = RedactRed,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                        StatusBadge(status = dossier.status)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = dossier.title,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = ArchivalPaper
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(RedactRedDark.copy(alpha = 0.2f))
                            .border(0.6.dp, RedactRed.copy(alpha = 0.4f))
                            .padding(8.dp)
                    ) {
                        Column {
                            Text(
                                text = "MOTIVO DE NO LOCALIZACIÓN:",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = RedactRed,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = dossier.missingReason.ifBlank { "Retirado preventivamente de los fondos generales." },
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                color = ArchivalPaper
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "REFERENCIADO EN: ${dossier.crossReferences}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = PericialCyan,
                            fontFamily = FontFamily.Monospace
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = dossier.summary,
                        style = MaterialTheme.typography.bodySmall,
                        color = ArchivalMuted
                    )
                }
            }
        }
    }
}
