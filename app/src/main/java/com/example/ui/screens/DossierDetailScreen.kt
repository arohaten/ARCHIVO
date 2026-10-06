package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.local.entity.DossierEntity
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.ArchiveViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DossierDetailScreen(
    dossier: DossierEntity,
    viewModel: ArchiveViewModel,
    onBack: () -> Unit,
    onNavigateToDossierId: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val context = LocalContext.current
    val isAdmin by viewModel.isAdminAuthenticated.collectAsState()
    var isCensoredState by remember(dossier.id) { mutableStateOf(dossier.isCensored) }

    var showEditDialog by remember { mutableStateOf(false) }

    // Edit form states
    var editTitle by remember(dossier.id) { mutableStateOf(dossier.title) }
    var editLocation by remember(dossier.id) { mutableStateOf(dossier.location) }
    var editStatus by remember(dossier.id) { mutableStateOf(dossier.status) }
    var editClassification by remember(dossier.id) { mutableStateOf(dossier.classification) }
    var editSummary by remember(dossier.id) { mutableStateOf(dossier.summary) }
    var editIncidentReport by remember(dossier.id) { mutableStateOf(dossier.incidentReport) }
    var editRecoveredMaterial by remember(dossier.id) { mutableStateOf(dossier.recoveredMaterial) }
    var editMissingMaterial by remember(dossier.id) { mutableStateOf(dossier.missingMaterial) }
    var editCrossReferences by remember(dossier.id) { mutableStateOf(dossier.crossReferences) }
    var editObservations by remember(dossier.id) { mutableStateOf(dossier.observations) }
    var editDrawableResName by remember(dossier.id) { mutableStateOf(dossier.drawableResName) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            editDrawableResName = uri.toString()
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SlateBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // Back and Share header + Admin Edit actions
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("btn_back_to_list")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = ArchivalPaper
                    )
                }

                Text(
                    text = "EXPEDIENTE // ${dossier.id}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = PericialCyan,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                )

                Row {
                    if (isAdmin) {
                        IconButton(
                            onClick = { showEditDialog = true },
                            modifier = Modifier.testTag("btn_edit_current_dossier")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Editar",
                                tint = AlertOrange
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            val shareLink = "https://archivo-vlc.net/archivo/${dossier.id.substringAfter("VLC-")}"
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Archivo VLC Link", shareLink)
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "Enlace copiado para compartir: $shareLink", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.testTag("btn_share_dossier")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Compartir",
                            tint = ArchivalPaper
                        )
                    }
                }
            }
        }

        // Admin banner when editing this file
        if (isAdmin) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AlertOrange.copy(alpha = 0.15f))
                        .border(0.6.dp, AlertOrange, RoundedCornerShape(2.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "MODO EDICIÓN ACTIVO: Toca 'Editar' para cambiar cualquier dato.",
                        style = MaterialTheme.typography.labelSmall.copy(color = AlertOrange, fontWeight = FontWeight.Bold)
                    )
                    IconButton(
                        onClick = {
                            viewModel.deleteDossier(dossier)
                            onBack()
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar expediente", tint = RedactRed, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        // Folio Header Box
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.8.dp, if (dossier.isMissing) RedactRed else SlateBorder, RoundedCornerShape(2.dp)),
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
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = if (dossier.isMissing) RedactRed else PericialCyan,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                        ClassificationStamp(classification = dossier.classification)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = dossier.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = ArchivalPaper,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    HairlineDivider()

                    Spacer(modifier = Modifier.height(10.dp))

                    // Metadata 2-column table
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "NIVEL DE ACCESO:",
                                style = MaterialTheme.typography.labelSmall.copy(color = ArchivalMuted, fontSize = 9.sp),
                                modifier = Modifier.width(130.dp)
                            )
                            Text(
                                text = dossier.accessLevel,
                                style = MaterialTheme.typography.labelSmall.copy(color = ArchivalPaper, fontWeight = FontWeight.Bold, fontSize = 9.sp)
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "ESTADO OFICIAL:",
                                style = MaterialTheme.typography.labelSmall.copy(color = ArchivalMuted, fontSize = 9.sp),
                                modifier = Modifier.width(130.dp)
                            )
                            StatusBadge(status = dossier.status)
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "FECHA REGISTRO:",
                                style = MaterialTheme.typography.labelSmall.copy(color = ArchivalMuted, fontSize = 9.sp),
                                modifier = Modifier.width(130.dp)
                            )
                            Text(
                                text = dossier.dateString,
                                style = MaterialTheme.typography.labelSmall.copy(color = ArchivalPaper, fontSize = 9.sp)
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "LOCALIZACIÓN:",
                                style = MaterialTheme.typography.labelSmall.copy(color = ArchivalMuted, fontSize = 9.sp),
                                modifier = Modifier.width(130.dp)
                            )
                            Text(
                                text = dossier.location,
                                style = MaterialTheme.typography.labelSmall.copy(color = PericialCyan, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "CÓDIGO DE REFERENCIA:",
                                style = MaterialTheme.typography.labelSmall.copy(color = ArchivalMuted, fontSize = 9.sp),
                                modifier = Modifier.width(130.dp)
                            )
                            Text(
                                text = dossier.referenceCode,
                                style = MaterialTheme.typography.labelSmall.copy(color = ArchivalFaint, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "ELEMENTOS REGISTRADOS:",
                                style = MaterialTheme.typography.labelSmall.copy(color = ArchivalMuted, fontSize = 9.sp),
                                modifier = Modifier.width(130.dp)
                            )
                            Text(
                                text = dossier.registeredElements,
                                style = MaterialTheme.typography.labelSmall.copy(color = ArchivalPaper, fontSize = 9.sp)
                            )
                        }
                    }
                }
            }
        }

        // Image attachment if available (built-in or custom URI via Coil)
        if (dossier.drawableResName.isNotBlank()) {
            val builtInRes = when (dossier.drawableResName) {
                "evidence_nodule_eye_1791219501249" -> R.drawable.evidence_nodule_eye_1791219501249
                "evidence_dashcam_anomaly_1791219514207" -> R.drawable.evidence_dashcam_anomaly_1791219514207
                "hero_intake_forensic_1791219485484" -> R.drawable.hero_intake_forensic_1791219485484
                else -> null
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(0.8.dp, SlateBorder, RoundedCornerShape(2.dp)),
                    shape = RoundedCornerShape(2.dp),
                    colors = CardDefaults.cardColors(containerColor = SlateSurface)
                ) {
                    Column {
                        if (builtInRes != null) {
                            Image(
                                painter = painterResource(id = builtInRes),
                                contentDescription = "Evidencia asociada a ${dossier.id}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            AsyncImage(
                                model = dossier.drawableResName,
                                contentDescription = "Evidencia asociada a ${dossier.id}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SlateDark)
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "ANEXO GRÁFICO // PLACA PERICIAL",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = PericialCyan,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = "REGISTRADO",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ArchivalMuted,
                                        fontSize = 9.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // Technical Incident Report
        item {
            TechnicalHeader(title = "INFORME TÉCNICO Y PERITAJE", code = dossier.pagesCount)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.6.dp, SlateBorder, RoundedCornerShape(2.dp)),
                colors = CardDefaults.cardColors(containerColor = SlateDark),
                shape = RoundedCornerShape(2.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = dossier.incidentReport,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 22.sp,
                            color = ArchivalPaper
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    RedactedText(
                        text = "OBSERVACIONES CLASIFICADAS: Los análisis microscópicos de savia revelaron cadenas proteicas no descritas en el reino vegetal. Se ordenó la custodia de los 3 kilómetros perimetrales.",
                        isCensored = isCensoredState,
                        onToggle = { isCensoredState = !isCensoredState }
                    )
                }
            }
        }

        // Material Recovered vs Material Missing
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .border(0.6.dp, VerifiedGreen.copy(alpha = 0.5f), RoundedCornerShape(2.dp)),
                    colors = CardDefaults.cardColors(containerColor = SlateDark),
                    shape = RoundedCornerShape(2.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "MATERIAL RECUPERADO",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = VerifiedGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = dossier.recoveredMaterial,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = ArchivalPaper
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .border(0.6.dp, RedactRed.copy(alpha = 0.5f), RoundedCornerShape(2.dp)),
                    colors = CardDefaults.cardColors(containerColor = SlateDark),
                    shape = RoundedCornerShape(2.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "MATERIAL FALTANTE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = RedactRed,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = dossier.missingMaterial,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = ArchivalPaper
                        )
                    }
                }
            }
        }

        // Cross References
        item {
            TechnicalHeader(title = "REFERENCIAS CRUZADAS", code = "CONEXIONES DE LEGAJO")
            val refs = dossier.crossReferences.split(",").map { it.trim() }.filter { it.isNotBlank() }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                refs.forEach { refCode ->
                    OutlinedButton(
                        onClick = { onNavigateToDossierId(refCode) },
                        modifier = Modifier.testTag("ref_button_$refCode"),
                        shape = RoundedCornerShape(2.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = PericialCyan
                        ),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = androidx.compose.ui.graphics.SolidColor(SlateBorder)
                        )
                    ) {
                        Text(
                            text = "→ $refCode",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                    }
                }
            }
        }

        // Notes and Chain of Custody
        item {
            TechnicalHeader(title = "OBSERVACIONES Y CADENA DE CUSTODIA")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.6.dp, SlateBorder, RoundedCornerShape(2.dp)),
                colors = CardDefaults.cardColors(containerColor = SlateSurface),
                shape = RoundedCornerShape(2.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = dossier.observations,
                        style = MaterialTheme.typography.bodySmall,
                        color = ArchivalMuted
                    )
                }
            }
        }
    }

    // Full In-Place Editor Dialog for Admin Mode
    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            containerColor = SlateDark,
            title = {
                Text(
                    text = "EDITAR EXPEDIENTE // ${dossier.id}",
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
                            value = editTitle,
                            onValueChange = { editTitle = it },
                            label = { Text("TÍTULO", style = MaterialTheme.typography.labelSmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = editStatus,
                            onValueChange = { editStatus = it },
                            label = { Text("ESTADO (RECUPERADO, PARCIAL, DESAPARECIDO...)", style = MaterialTheme.typography.labelSmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = editClassification,
                            onValueChange = { editClassification = it },
                            label = { Text("CLASIFICACIÓN", style = MaterialTheme.typography.labelSmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = editLocation,
                            onValueChange = { editLocation = it },
                            label = { Text("LOCALIZACIÓN", style = MaterialTheme.typography.labelSmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = editSummary,
                            onValueChange = { editSummary = it },
                            label = { Text("RESUMEN GENERAL", style = MaterialTheme.typography.labelSmall) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = editIncidentReport,
                            onValueChange = { editIncidentReport = it },
                            label = { Text("INFORME PERICIAL DETALLADO", style = MaterialTheme.typography.labelSmall) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = editRecoveredMaterial,
                            onValueChange = { editRecoveredMaterial = it },
                            label = { Text("MATERIAL RECUPERADO", style = MaterialTheme.typography.labelSmall) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = editMissingMaterial,
                            onValueChange = { editMissingMaterial = it },
                            label = { Text("MATERIAL FALTANTE", style = MaterialTheme.typography.labelSmall) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = editCrossReferences,
                            onValueChange = { editCrossReferences = it },
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
                                text = if (editDrawableResName.isNotBlank()) "CAMBIAR IMAGEN ADJUNTA" else "ADJUNTAR IMAGEN DE GALERÍA",
                                style = MaterialTheme.typography.labelSmall.copy(color = ArchivalPaper, fontWeight = FontWeight.Bold)
                            )
                        }
                        if (editDrawableResName.isNotBlank()) {
                            Text(
                                text = "ADJUNTO: $editDrawableResName",
                                style = MaterialTheme.typography.labelSmall.copy(color = VerifiedGreen, fontSize = 8.sp),
                                maxLines = 1
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val updated = dossier.copy(
                            title = editTitle.trim(),
                            location = editLocation.trim(),
                            status = editStatus.trim(),
                            classification = editClassification.trim(),
                            summary = editSummary.trim(),
                            incidentReport = editIncidentReport.trim(),
                            recoveredMaterial = editRecoveredMaterial.trim(),
                            missingMaterial = editMissingMaterial.trim(),
                            crossReferences = editCrossReferences.trim(),
                            observations = editObservations.trim(),
                            drawableResName = editDrawableResName.trim(),
                            isMissing = editStatus.contains("DESAPARECIDO") || editStatus.contains("NO LOCALIZADO")
                        )
                        viewModel.saveDossier(updated)
                        viewModel.selectDossier(updated)
                        showEditDialog = false
                        Toast.makeText(context, "Expediente ${dossier.id} actualizado con éxito", Toast.LENGTH_SHORT).show()
                    },
                    shape = RoundedCornerShape(2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AlertOrange)
                ) {
                    Text("GUARDAR CAMBIOS", color = SlateBlack, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("CANCELAR", color = ArchivalMuted)
                }
            }
        )
    }
}
