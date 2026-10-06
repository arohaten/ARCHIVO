package com.example.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Search
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DossierListScreen(
    viewModel: ArchiveViewModel,
    onSelectDossier: (DossierEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val dossiers by viewModel.filteredDossiers.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filterStatus by viewModel.filterStatus.collectAsState()
    val isAdmin by viewModel.isAdminAuthenticated.collectAsState()

    var showCreateDossierDialog by remember { mutableStateOf(false) }

    // Create state
    var newId by remember { mutableStateOf("VLC-") }
    var newTitle by remember { mutableStateOf("") }
    var newLocation by remember { mutableStateOf("Comunitat Valenciana") }
    var newStatus by remember { mutableStateOf("RECUPERADO") }
    var newClassification by remember { mutableStateOf("DESCLASIFICADO PARCIAL") }
    var newSummary by remember { mutableStateOf("") }
    var newReport by remember { mutableStateOf("") }
    var newRefs by remember { mutableStateOf("VLC-014") }
    var newImageUri by remember { mutableStateOf<String?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            newImageUri = uri.toString()
        }
    }

    val filters = listOf("TODOS", "RECUPERADO", "PARCIAL", "CORRUPTO", "NO LOCALIZADO", "DESAPARECIDO", "EN INVESTIGACIÓN")

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SlateBlack)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Search Field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.searchQuery.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_search_dossiers"),
                placeholder = {
                    Text(
                        text = "Buscar por código (VLC-014), término, elementos...",
                        style = MaterialTheme.typography.bodySmall.copy(color = ArchivalMuted)
                    )
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = ArchivalMuted, modifier = Modifier.size(18.dp))
                },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = { viewModel.searchQuery.value = "" }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Limpiar", tint = ArchivalMuted, modifier = Modifier.size(16.dp))
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(2.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PericialCyan,
                    unfocusedBorderColor = SlateBorder,
                    focusedTextColor = ArchivalPaper,
                    unfocusedTextColor = ArchivalPaper,
                    focusedContainerColor = SlateDark,
                    unfocusedContainerColor = SlateDark
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Filter chips horizontal scroll
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                filters.forEach { filter ->
                    val isSelected = filterStatus == filter
                    Box(
                        modifier = Modifier
                            .border(
                                width = 0.8.dp,
                                color = if (isSelected) PericialCyan else SlateBorder,
                                shape = RoundedCornerShape(2.dp)
                            )
                            .background(
                                color = if (isSelected) SlateSurface else SlateDark,
                                shape = RoundedCornerShape(2.dp)
                            )
                            .clickable { viewModel.filterStatus.value = filter }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = filter,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = if (isSelected) PericialCyan else ArchivalMuted,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            TechnicalHeader(
                title = "LEGAJOS TERRITORIALES",
                code = "${dossiers.size} REGISTROS"
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(dossiers) { dossier ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(0.6.dp, if (dossier.isMissing) RedactRed.copy(alpha = 0.6f) else SlateBorder, RoundedCornerShape(2.dp))
                            .clickable { onSelectDossier(dossier) }
                            .testTag("dossier_card_${dossier.id}"),
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
                                    text = dossier.id,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = if (dossier.isMissing) RedactRed else PericialCyan,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace,
                                        letterSpacing = 1.sp
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

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "UBICACIÓN: ${dossier.location}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = ArchivalMuted,
                                    fontSize = 10.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = dossier.summary,
                                style = MaterialTheme.typography.bodySmall,
                                color = ArchivalMuted,
                                maxLines = 2
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            HairlineDivider()

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "NIVEL: ${dossier.accessLevel}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 8.sp,
                                        color = ArchivalFaint
                                    )
                                )
                                Text(
                                    text = "FOLIOS: ${dossier.pagesCount}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 8.sp,
                                        color = ArchivalFaint
                                    )
                                )
                                Text(
                                    text = "REF: ${dossier.referenceCode}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 8.sp,
                                        color = PericialCyan
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // Admin Floating Button to create new dossier
        if (isAdmin) {
            FloatingActionButton(
                onClick = {
                    val codeNum = (200..999).random()
                    newId = "VLC-$codeNum"
                    newImageUri = null
                    showCreateDossierDialog = true
                },
                containerColor = AlertOrange,
                contentColor = SlateBlack,
                shape = RoundedCornerShape(2.dp),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp)
                    .testTag("fab_create_dossier")
            ) {
                Row(modifier = Modifier.padding(horizontal = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "NUEVO EXPEDIENTE", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }
            }
        }
    }

    // New Dossier Dialog
    if (showCreateDossierDialog) {
        AlertDialog(
            onDismissRequest = { showCreateDossierDialog = false },
            containerColor = SlateDark,
            title = {
                Text(
                    text = "CREAR EXPEDIENTE // MODO ROOT",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = ArchivalPaper,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                )
            },
            text = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 440.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        OutlinedTextField(
                            value = newId,
                            onValueChange = { newId = it },
                            label = { Text("CÓDIGO (Ej: VLC-667)", style = MaterialTheme.typography.labelSmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = newTitle,
                            onValueChange = { newTitle = it },
                            label = { Text("TÍTULO", style = MaterialTheme.typography.labelSmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = newLocation,
                            onValueChange = { newLocation = it },
                            label = { Text("LOCALIZACIÓN", style = MaterialTheme.typography.labelSmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = newStatus,
                            onValueChange = { newStatus = it },
                            label = { Text("ESTADO (RECUPERADO, PARCIAL, DESAPARECIDO...)", style = MaterialTheme.typography.labelSmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = newSummary,
                            onValueChange = { newSummary = it },
                            label = { Text("RESUMEN GENERAL", style = MaterialTheme.typography.labelSmall) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = newReport,
                            onValueChange = { newReport = it },
                            label = { Text("INFORME PERICIAL DETALLADO", style = MaterialTheme.typography.labelSmall) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = newRefs,
                            onValueChange = { newRefs = it },
                            label = { Text("REFERENCIAS CRUZADAS", style = MaterialTheme.typography.labelSmall) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                    }

                    item {
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
                                text = if (newImageUri != null) "IMAGEN ADJUNTADA" else "ADJUNTAR FOTO DE GALERÍA",
                                style = MaterialTheme.typography.labelSmall.copy(color = ArchivalPaper, fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newId.isNotBlank() && newTitle.isNotBlank()) {
                            val codeNum = newId.filter { it.isDigit() }.toIntOrNull() ?: 999
                            val isMissingDossier = newStatus.contains("DESAPARECIDO") || newStatus.contains("NO LOCALIZADO")
                            viewModel.saveDossier(
                                DossierEntity(
                                    id = newId.trim(),
                                    codeNumber = codeNum,
                                    title = newTitle.trim(),
                                    classification = newClassification,
                                    accessLevel = "NIVEL II",
                                    status = newStatus.trim(),
                                    dateString = "05/10/2026",
                                    location = newLocation.trim(),
                                    referenceCode = "REF-${newId.trim()}",
                                    registeredElements = "REGISTRO INCORPORADO POR ADMINISTRADOR",
                                    summary = newSummary.trim(),
                                    incidentReport = newReport.trim(),
                                    recoveredMaterial = "Documentación registrada desde consola.",
                                    missingMaterial = if (isMissingDossier) "Fondos no localizados." else "Ninguno.",
                                    crossReferences = newRefs.trim(),
                                    observations = "Incorporado en sesión autenticada con clave Archivo27798.",
                                    drawableResName = newImageUri ?: "",
                                    pagesCount = "01/01",
                                    isMissing = isMissingDossier
                                )
                            )
                            showCreateDossierDialog = false
                        }
                    },
                    shape = RoundedCornerShape(2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AlertOrange)
                ) {
                    Text("PUBLICAR EXPEDIENTE", color = SlateBlack, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateDossierDialog = false }) {
                    Text("CANCELAR", color = ArchivalMuted)
                }
            }
        )
    }
}
