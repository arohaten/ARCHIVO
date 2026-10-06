package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.local.entity.DossierEntity
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.ArchiveViewModel

@Composable
fun HomeScreen(
    viewModel: ArchiveViewModel,
    onNavigateToDossiers: () -> Unit,
    onNavigateToCitizenDrop: () -> Unit,
    onNavigateToMap: () -> Unit,
    onNavigateToMissing: () -> Unit,
    onSelectDossier: (DossierEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val dossiers by viewModel.dossiers.collectAsState()
    val citizenReports by viewModel.citizenReports.collectAsState()
    val auditLogs by viewModel.auditLogs.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SlateBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // System Header / Breach Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.8.dp, RedactRed.copy(alpha = 0.5f), RoundedCornerShape(2.dp)),
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
                            text = "SYS // BRECHA DOCUMENTAL",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = RedactRed,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            )
                        )
                        Box(
                            modifier = Modifier
                                .border(0.6.dp, RedactRed, RoundedCornerShape(2.dp))
                                .background(RedactRedDark.copy(alpha = 0.4f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "ACCESO NO AUTENTICADO",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 8.sp,
                                    color = ArchivalPaper
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "ARCHIVO VLC // REPOSITORIO TERRITORIAL",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = ArchivalPaper,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Fondo documental expuesto tras pérdida de custodia en depósitos periféricos. Expedientes técnicos, registros audiovisuales e investigaciones de anomalías en la Comunitat Valenciana.",
                        style = MaterialTheme.typography.bodySmall,
                        color = ArchivalMuted
                    )
                }
            }
        }

        // Metrics Grid
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetricBox(
                    label = "INTEGRIDAD",
                    value = "61.4%",
                    sublabel = "SINCRONIZACIÓN CORRUPTA",
                    valueColor = AlertOrange,
                    modifier = Modifier.weight(1f)
                )
                MetricBox(
                    label = "EXPEDIENTES",
                    value = "${dossiers.size}",
                    sublabel = "14 ACCESIBLES",
                    valueColor = PericialCyan,
                    modifier = Modifier.weight(1f)
                )
                MetricBox(
                    label = "FALTANTES",
                    value = "19",
                    sublabel = "PURGADOS // EN BÚSQUEDA",
                    valueColor = RedactRed,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Hero Forensic Dossier Image
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.8.dp, SlateBorder, RoundedCornerShape(2.dp)),
                shape = RoundedCornerShape(2.dp),
                colors = CardDefaults.cardColors(containerColor = SlateSurface)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.hero_intake_forensic_1791219485484),
                            contentDescription = "Legajo pericial oficial ARCHIVO VLC",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .fillMaxWidth()
                                .background(Color.Black.copy(alpha = 0.75f))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "PROTOCOLO RECEPTOR // ARCHIVOS PERDIDOS ACTIVO",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = PericialCyan,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }

        // Primary Action CTAs
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = onNavigateToCitizenDrop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_citizen_intake"),
                    shape = RoundedCornerShape(2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RedactRedDark)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = ArchivalPaper,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "BUZÓN DE RECEPCIÓN // REMITIR PRUEBAS",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = ArchivalPaper,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onNavigateToDossiers,
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("btn_explore_archive"),
                        shape = RoundedCornerShape(2.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ArchivalPaper)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Folder,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = PericialCyan
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "EXPEDIENTES",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    OutlinedButton(
                        onClick = onNavigateToMissing,
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("btn_missing_archive"),
                        shape = RoundedCornerShape(2.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ArchivalPaper)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = RedactRed
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "FALTANTES (19)",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }

        // Notice of Collaborative Field Investigation
        item {
            TechnicalHeader(title = "CIRCULAR OPERATIVA", code = "PROTO-REC-2026")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SlateSurface, RoundedCornerShape(2.dp))
                    .border(0.8.dp, SlateBorder, RoundedCornerShape(2.dp))
                    .padding(12.dp)
            ) {
                Column {
                    Text(
                        text = "«El 64% de la documentación técnica fue expurgada o destruida entre 2013 y 2021. La reconstrucción de expedientes críticos (VLC-108, VLC-134, VLC-402, VLC-506) requiere cotejar metadatos de grabaciones no catalogadas, cámaras de seguridad, grabadores de a bordo y registros visuales obtenidos por observadores externos.»",
                        style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp),
                        color = ArchivalPaper
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Los envíos recibidos por canal privado se ingresan en cuarentena pericial bajo clave de seguimiento anonimizada.",
                        style = MaterialTheme.typography.labelSmall.copy(color = ArchivalMuted)
                    )
                }
            }
        }

        // Live Submissions in Quarantine
        item {
            TechnicalHeader(title = "APORTACIONES EN CUARENTENA", code = "COTEJO ACTIVO")
        }

        items(citizenReports.take(3)) { report ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.6.dp, SlateBorder, RoundedCornerShape(2.dp)),
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
                            text = report.trackingCode,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = PericialCyan,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        StatusBadge(status = report.status)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${report.municipality.uppercase()} // ${report.category}",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = ArchivalPaper,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = report.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = ArchivalMuted,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "REMITIDO: ${report.timestamp}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 8.sp,
                                color = ArchivalFaint
                            )
                        )
                        Text(
                            text = "ASOCIADO: ${report.matchedDossier}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 8.sp,
                                color = PericialCyan
                            )
                        )
                    }
                }
            }
        }

        // Live Audit Feed snippet
        item {
            TechnicalHeader(title = "REGISTRO DE INCIDENCIAS EN VIVO", code = "SEC-LOG")
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                auditLogs.take(3).forEach { log ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SlateSurface.copy(alpha = 0.5f))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = log.timestamp.substringAfter("– ").trim(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ArchivalMuted,
                                fontSize = 9.sp
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "[${log.actionCode}]",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = if (log.level == "BREACH" || log.level == "ALERT") RedactRed else PericialCyan,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp
                            )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = log.description,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                            color = ArchivalPaper,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}
