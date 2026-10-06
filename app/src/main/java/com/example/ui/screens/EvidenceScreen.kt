package com.example.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.local.entity.EvidenceEntity
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.ArchiveViewModel

@Composable
fun EvidenceScreen(
    viewModel: ArchiveViewModel,
    modifier: Modifier = Modifier
) {
    val evidences by viewModel.evidences.collectAsState()
    val isAdmin by viewModel.isAdminAuthenticated.collectAsState()
    var selectedTypeFilter by remember { mutableStateOf("TODAS") }

    var showAddEvidenceDialog by remember { mutableStateOf(false) }

    // Dialog state for new evidence
    var newId by remember { mutableStateOf("VLC-E") }
    var newDossierCode by remember { mutableStateOf("VLC-014") }
    var newType by remember { mutableStateOf("FOTOGRAFÍA") }
    var newStatus by remember { mutableStateOf("VERIFICACIÓN PENDIENTE") }
    var newDescription by remember { mutableStateOf("") }
    var newLocation by remember { mutableStateOf("Comunitat Valenciana") }
    var newTechnicalNotes by remember { mutableStateOf("") }
    var newMediaUri by remember { mutableStateOf<String?>(null) }
    var newAudioDuration by remember { mutableStateOf("") }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            newMediaUri = uri.toString()
        }
    }

    val types = listOf("TODAS", "FOTOGRAFÍA", "VÍDEO", "AUDIO", "DOCUMENTO")

    val filtered = remember(evidences, selectedTypeFilter) {
        if (selectedTypeFilter == "TODAS") evidences
        else evidences.filter { it.type.contains(selectedTypeFilter, ignoreCase = true) }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SlateBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            TechnicalHeader(title = "FONDO PERICIAL DE EVIDENCIAS", code = "${filtered.size} REGISTROS")
            Text(
                text = "Materiales gráficos, registros acústicos, vídeos y actas documentales recuperadas durante las investigaciones periciales en la Comunitat Valenciana.",
                style = MaterialTheme.typography.bodySmall,
                color = ArchivalMuted
            )

            if (isAdmin) {
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        val rnd = (100..999).random()
                        newId = "VLC-E$rnd"
                        newMediaUri = null
                        showAddEvidenceDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("btn_admin_add_evidence"),
                    shape = RoundedCornerShape(2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RedactRedDark)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "+ AÑADIR NUEVA EVIDENCIA (FOTO / VÍDEO / AUDIO)",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = ArchivalPaper,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }

        // Type filter pills
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                types.forEach { t ->
                    val isSelected = selectedTypeFilter == t
                    Button(
                        onClick = { selectedTypeFilter = t },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) SlateSurface else SlateDark
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            0.8.dp,
                            if (isSelected) PericialCyan else SlateBorder
                        ),
                        shape = RoundedCornerShape(2.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("filter_evidence_$t")
                    ) {
                        Text(
                            text = t,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = if (isSelected) PericialCyan else ArchivalMuted,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        )
                    }
                }
            }
        }

        items(filtered) { evidence ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.6.dp, SlateBorder, RoundedCornerShape(2.dp)),
                colors = CardDefaults.cardColors(containerColor = SlateDark),
                shape = RoundedCornerShape(2.dp)
            ) {
                Column {
                    // Check if it's a built-in drawable resource or custom content URI / URL
                    val builtInRes = when (evidence.drawableResName) {
                        "evidence_nodule_eye_1791219501249" -> R.drawable.evidence_nodule_eye_1791219501249
                        "evidence_dashcam_anomaly_1791219514207" -> R.drawable.evidence_dashcam_anomaly_1791219514207
                        "hero_intake_forensic_1791219485484" -> R.drawable.hero_intake_forensic_1791219485484
                        else -> null
                    }

                    if (builtInRes != null) {
                        Image(
                            painter = painterResource(id = builtInRes),
                            contentDescription = evidence.description,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                            contentScale = ContentScale.Crop
                        )
                    } else if (evidence.drawableResName.isNotBlank()) {
                        AsyncImage(
                            model = evidence.drawableResName,
                            contentDescription = evidence.description,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                            contentScale = ContentScale.Crop
                        )
                    } else if (evidence.type.contains("AUDIO", ignoreCase = true)) {
                        // Simulated Audio Waveform Box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(90.dp)
                                .background(SlateSurface)
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Audiotrack,
                                    contentDescription = null,
                                    tint = PericialCyan,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "CANAL DE AUDIO // DURACIÓN: ${evidence.audioDuration.ifBlank { "00:01:45" }} // INFRASONIDO 17.4 HZ",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = PericialCyan,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    } else if (evidence.type.contains("VÍDEO", ignoreCase = true)) {
                        // Simulated Video Player Slate
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .background(SlateSurface)
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.Videocam,
                                    contentDescription = null,
                                    tint = AlertOrange,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "PISTA DE VÍDEO // PROTOCOLO CRUDA",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ArchivalPaper,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                if (evidence.audioDuration.isNotBlank()) {
                                    Text(
                                        text = "TIEMPO: ${evidence.audioDuration}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = ArchivalMuted,
                                            fontSize = 9.sp,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    )
                                }
                            }
                        }
                    }

                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = evidence.id,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = PericialCyan,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                StatusBadge(status = evidence.status)
                                if (isAdmin) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    IconButton(
                                        onClick = { viewModel.deleteEvidence(evidence) },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Eliminar",
                                            tint = RedactRed,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "ORIGEN: ${evidence.originDossierCode} // ${evidence.type}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ArchivalPaper,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = evidence.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = ArchivalPaper
                        )

                        if (evidence.technicalNotes.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(SlateSurface)
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = "NOTAS TÉCNICAS: ${evidence.technicalNotes}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ArchivalMuted,
                                        fontSize = 9.sp
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "UBICACIÓN: ${evidence.location}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 8.sp,
                                    color = ArchivalFaint
                                )
                            )
                            Text(
                                text = "FECHA: ${evidence.dateRecovery}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 8.sp,
                                    color = ArchivalFaint
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    // Add Evidence Dialog for Admin
    if (showAddEvidenceDialog) {
        AlertDialog(
            onDismissRequest = { showAddEvidenceDialog = false },
            containerColor = SlateDark,
            title = {
                Text(
                    text = "AÑADIR EVIDENCIA // MODO ROOT",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = ArchivalPaper,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = newId,
                        onValueChange = { newId = it },
                        label = { Text("CÓDIGO (Ej: VLC-E099)", style = MaterialTheme.typography.labelSmall) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                    )

                    OutlinedTextField(
                        value = newDossierCode,
                        onValueChange = { newDossierCode = it },
                        label = { Text("EXPEDIENTE ASOCIADO (Ej: VLC-014)", style = MaterialTheme.typography.labelSmall) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                    )

                    // Type selection
                    Text("TIPO DE EVIDENCIA:", style = MaterialTheme.typography.labelSmall.copy(color = ArchivalMuted))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        listOf("FOTOGRAFÍA", "VÍDEO", "AUDIO", "DOCUMENTO").forEach { t ->
                            val sel = newType == t
                            FilterChip(
                                selected = sel,
                                onClick = { newType = t },
                                label = { Text(t, fontSize = 9.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = PericialCyanMuted,
                                    selectedLabelColor = SlateBlack,
                                    containerColor = SlateSurface,
                                    labelColor = ArchivalMuted
                                )
                            )
                        }
                    }

                    // Photo Picker button
                    if (newType == "FOTOGRAFÍA" || newType == "DOCUMENTO") {
                        Button(
                            onClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(2.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SlateSurface)
                        ) {
                            Icon(imageVector = Icons.Default.Image, contentDescription = null, modifier = Modifier.size(16.dp), tint = PericialCyan)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (newMediaUri != null) "IMAGEN SELECCIONADA (CAMBIAR)" else "SELECCIONAR FOTO DE LA GALERÍA",
                                style = MaterialTheme.typography.labelSmall.copy(color = ArchivalPaper, fontWeight = FontWeight.Bold)
                            )
                        }
                        if (newMediaUri != null) {
                            Text(
                                text = "URI: $newMediaUri",
                                style = MaterialTheme.typography.labelSmall.copy(color = VerifiedGreen, fontSize = 8.sp),
                                maxLines = 1
                            )
                        }
                    } else if (newType == "AUDIO") {
                        OutlinedTextField(
                            value = newAudioDuration,
                            onValueChange = { newAudioDuration = it },
                            label = { Text("DURACIÓN DE AUDIO (Ej: 00:03:17)", style = MaterialTheme.typography.labelSmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                    } else if (newType == "VÍDEO") {
                        OutlinedTextField(
                            value = newAudioDuration,
                            onValueChange = { newAudioDuration = it },
                            label = { Text("DURACIÓN / METRAJE (Ej: 00:04:12)", style = MaterialTheme.typography.labelSmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                    }

                    OutlinedTextField(
                        value = newDescription,
                        onValueChange = { newDescription = it },
                        label = { Text("DESCRIPCIÓN DEL HALLAZGO", style = MaterialTheme.typography.labelSmall) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                    )

                    OutlinedTextField(
                        value = newLocation,
                        onValueChange = { newLocation = it },
                        label = { Text("UBICACIÓN / TÉRMINO", style = MaterialTheme.typography.labelSmall) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                    )

                    OutlinedTextField(
                        value = newTechnicalNotes,
                        onValueChange = { newTechnicalNotes = it },
                        label = { Text("NOTAS TÉCNICAS / PERICIALES", style = MaterialTheme.typography.labelSmall) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newId.isNotBlank() && newDescription.isNotBlank()) {
                            viewModel.saveEvidence(
                                EvidenceEntity(
                                    id = newId.trim(),
                                    originDossierCode = newDossierCode.trim(),
                                    type = newType,
                                    dateRecovery = "05/10/2026",
                                    status = "AUTENTICADO",
                                    description = newDescription.trim(),
                                    location = newLocation.trim(),
                                    technicalNotes = newTechnicalNotes.trim(),
                                    drawableResName = newMediaUri ?: "",
                                    audioDuration = newAudioDuration.trim()
                                )
                            )
                            showAddEvidenceDialog = false
                        }
                    },
                    shape = RoundedCornerShape(2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RedactRedDark)
                ) {
                    Text("GUARDAR EVIDENCIA", color = ArchivalPaper, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddEvidenceDialog = false }) {
                    Text("CANCELAR", color = ArchivalMuted)
                }
            }
        )
    }
}
