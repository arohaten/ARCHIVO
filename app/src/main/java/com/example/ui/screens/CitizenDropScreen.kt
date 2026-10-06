package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.CitizenReportEntity
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.ArchiveViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitizenDropScreen(
    viewModel: ArchiveViewModel,
    modifier: Modifier = Modifier
) {
    val citizenReports by viewModel.citizenReports.collectAsState()

    var municipality by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("ANOMALÍA VISUAL") }
    var description by remember { mutableStateOf("") }
    var isUrgent by remember { mutableStateOf(false) }

    var submissionSuccessCode by remember { mutableStateOf<String?>(null) }
    var showForm by remember { mutableStateOf(false) }

    val categories = listOf(
        "ANOMALÍA VISUAL",
        "CÁMARA SEGURIDAD / DASHCAM",
        "VIBRACIÓN / AUDIO EN ACEQUIA",
        "MARCADO PERIMETRAL / CLAVO",
        "OBJETO FUERA DE LUGAR",
        "OTRO REGISTRO"
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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.8.dp, RedactRed, RoundedCornerShape(2.dp)),
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
                            text = "PROTOCOLO DE RECEPCIÓN CIUDADANA",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = RedactRed,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                        Box(
                            modifier = Modifier
                                .border(0.6.dp, VerifiedGreen, RoundedCornerShape(2.dp))
                                .background(VerifiedGreenDark.copy(alpha = 0.3f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "CANAL ABIERTO",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = VerifiedGreen,
                                    fontSize = 8.sp
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "APORTACIÓN DE PRUEBAS Y REGISTROS DE CAMPO",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = ArchivalPaper,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Si dispone de grabaciones de móvil, timbres inteligentes, cámaras de salpicadero (dashcam) o fotografías tomadas en zonas rurales o urbanas de la Comunitat Valenciana que presenten anomalías no catalogadas, formalice el informe pericial. Su envío será ingresado en cuarentena con asignación de código de custodia.",
                        style = MaterialTheme.typography.bodySmall,
                        color = ArchivalMuted
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { showForm = !showForm },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("btn_toggle_citizen_form"),
                        shape = RoundedCornerShape(2.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (showForm) SlateSurface else PericialCyanMuted
                        )
                    ) {
                        Icon(
                            imageVector = if (showForm) Icons.Default.Close else Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = if (showForm) ArchivalPaper else SlateBlack
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (showForm) "CERRAR TERMINAL DE ENTRADA" else "FORMALIZAR REGISTRO DE EVIDENCIA",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (showForm) ArchivalPaper else SlateBlack
                            )
                        )
                    }
                }
            }
        }

        // Intake Form
        if (showForm) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(0.8.dp, PericialCyan.copy(alpha = 0.6f), RoundedCornerShape(2.dp)),
                    colors = CardDefaults.cardColors(containerColor = SlateSurface),
                    shape = RoundedCornerShape(2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "ACTA PERICIAL DE REMISIÓN // CAMPO EXTERNO",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = PericialCyan,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        // Municipality field
                        OutlinedTextField(
                            value = municipality,
                            onValueChange = { municipality = it },
                            label = { Text("MUNICIPIO / ZONA / PARTIDA RURAL", style = MaterialTheme.typography.labelSmall) },
                            placeholder = { Text("Ej: Sueca, Cheste, El Puig, Paterna, etc.", style = MaterialTheme.typography.bodySmall) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_municipality"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PericialCyan,
                                unfocusedBorderColor = SlateBorder,
                                focusedTextColor = ArchivalPaper,
                                unfocusedTextColor = ArchivalPaper
                            )
                        )

                        // Category Dropdown / Selector
                        Text(
                            text = "TIPOLOGÍA DE REGISTRO:",
                            style = MaterialTheme.typography.labelSmall.copy(color = ArchivalMuted)
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            categories.forEach { cat ->
                                val isSelected = selectedCategory == cat
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(if (isSelected) SlateCard else SlateDark)
                                        .border(0.6.dp, if (isSelected) PericialCyan else SlateBorder, RoundedCornerShape(2.dp))
                                        .padding(horizontal = 10.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { selectedCategory = cat },
                                        colors = RadioButtonDefaults.colors(selectedColor = PericialCyan)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = cat,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = if (isSelected) ArchivalPaper else ArchivalMuted,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    )
                                }
                            }
                        }

                        // Description field
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("DESCRIPCIÓN PERICIAL DEL SUCESO / DETALLE", style = MaterialTheme.typography.labelSmall) },
                            placeholder = { Text("Detalle hora, comportamiento animal, reacción de dispositivos, distorsiones...", style = MaterialTheme.typography.bodySmall) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .testTag("input_description"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PericialCyan,
                                unfocusedBorderColor = SlateBorder,
                                focusedTextColor = ArchivalPaper,
                                unfocusedTextColor = ArchivalPaper
                            )
                        )

                        // Urgent toggle
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = isUrgent,
                                onCheckedChange = { isUrgent = it },
                                colors = CheckboxDefaults.colors(checkedColor = RedactRed)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "ANOMALÍA RECIENTE (< 48 HORAS) // PRIORIDAD DE COTEJO",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (isUrgent) RedactRed else ArchivalMuted,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Button(
                            onClick = {
                                if (description.isNotBlank()) {
                                    viewModel.submitCitizenReport(
                                        municipality = municipality,
                                        category = selectedCategory,
                                        description = description,
                                        isUrgent = isUrgent,
                                        onSuccess = { code ->
                                            submissionSuccessCode = code
                                            municipality = ""
                                            description = ""
                                            showForm = false
                                        }
                                    )
                                }
                            },
                            enabled = description.isNotBlank(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("btn_submit_citizen_report"),
                            shape = RoundedCornerShape(2.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = RedactRedDark)
                        ) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "REGISTRAR EN CUARENTENA PERICIAL",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = ArchivalPaper,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.8.sp
                                )
                            )
                        }
                    }
                }
            }
        }

        // Submission success alert
        submissionSuccessCode?.let { code ->
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, VerifiedGreen, RoundedCornerShape(2.dp)),
                    colors = CardDefaults.cardColors(containerColor = VerifiedGreenDark.copy(alpha = 0.25f)),
                    shape = RoundedCornerShape(2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "EVIDENCIA REGISTRADA CON ÉXITO",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = VerifiedGreen,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            IconButton(onClick = { submissionSuccessCode = null }, modifier = Modifier.size(24.dp)) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar", tint = ArchivalMuted)
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "CÓDIGO DE SEGUIMIENTO: $code",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = ArchivalPaper,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "El registro ha sido alojado en la partición de cuarentena del repositorio. Si dispone de los archivos de vídeo o fotografía bruta, remítalos por mensaje privado de TikTok indicando este código de seguimiento.",
                            style = MaterialTheme.typography.bodySmall,
                            color = ArchivalPaper
                        )
                    }
                }
            }
        }

        // Direct TikTok DM Advisory
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.6.dp, SlateBorder, RoundedCornerShape(2.dp)),
                colors = CardDefaults.cardColors(containerColor = SlateDark),
                shape = RoundedCornerShape(2.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "VÍA DIRECTA DE REMISIÓN AUDIOVISUAL (TIKTOK / PRIVADO)",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = PericialCyan,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Para transferir archivos de vídeo brutos (.mp4, .mov), pistas de audio o fotografías sin compresión, utilice la vía de mensajería privada de la cuenta @archivo.filtrado. Indique siempre el término municipal y fecha aproximada.",
                        style = MaterialTheme.typography.bodySmall,
                        color = ArchivalMuted
                    )
                }
            }
        }

        // List of all citizen reports
        item {
            TechnicalHeader(
                title = "REGISTROS CIUDADANOS EN PROCESO DE COTEJO",
                code = "${citizenReports.size} INFORMES"
            )
        }

        items(citizenReports) { report ->
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
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = PericialCyan,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                        StatusBadge(status = report.status)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "${report.municipality.uppercase()} // ${report.category}",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = ArchivalPaper,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = report.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = ArchivalPaper
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SlateSurface)
                            .padding(6.dp)
                    ) {
                        Text(
                            text = "NOTAS DE CUSTODIA: ${report.verificationNotes}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ArchivalMuted,
                                fontSize = 9.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "FECHA REGISTRO: ${report.timestamp}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 8.sp,
                                color = ArchivalFaint
                            )
                        )
                        Text(
                            text = "COINCIDENCIA: ${report.matchedDossier}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 8.sp,
                                color = AlertOrange,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}
