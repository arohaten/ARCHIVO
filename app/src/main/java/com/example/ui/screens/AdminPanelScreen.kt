package com.example.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.DossierEntity
import com.example.data.local.entity.LocationEntity
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.ArchiveViewModel

@Composable
fun AdminPanelScreen(
    viewModel: ArchiveViewModel,
    modifier: Modifier = Modifier
) {
    val isAuthenticated by viewModel.isAdminAuthenticated.collectAsState()
    val citizenReports by viewModel.citizenReports.collectAsState()

    var passwordInput by remember { mutableStateOf("") }
    var authError by remember { mutableStateOf(false) }

    // New Dossier Form state
    var newId by remember { mutableStateOf("VLC-") }
    var newTitle by remember { mutableStateOf("") }
    var newStatus by remember { mutableStateOf("RECUPERADO") }
    var newClassification by remember { mutableStateOf("DESCLASIFICADO PARCIAL") }
    var newLocation by remember { mutableStateOf("") }
    var newSummary by remember { mutableStateOf("") }
    var newReport by remember { mutableStateOf("") }
    var newRefs by remember { mutableStateOf("VLC-014") }
    var newMediaUri by remember { mutableStateOf<String?>(null) }
    var dossierAddedSuccess by remember { mutableStateOf(false) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            newMediaUri = uri.toString()
        }
    }

    // New Location Form state
    var newLocCode by remember { mutableStateOf("LOC-311-") }
    var newLocName by remember { mutableStateOf("") }
    var newLat by remember { mutableStateOf("39.") }
    var newLong by remember { mutableStateOf("-0.") }
    var newArea by remember { mutableStateOf("Valencia") }
    var newRelDossier by remember { mutableStateOf("VLC-014") }
    var locationAddedSuccess by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SlateBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        if (!isAuthenticated) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, RedactRed, RoundedCornerShape(2.dp)),
                    colors = CardDefaults.cardColors(containerColor = SlateDark),
                    shape = RoundedCornerShape(2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = RedactRed,
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            text = "CONSOLA DE ADMINISTRACIÓN // ROOT ACCESS",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = ArchivalPaper,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                        Text(
                            text = "Introduzca la clave de autorización (clave raíz: Archivo27798).",
                            style = MaterialTheme.typography.bodySmall,
                            color = ArchivalMuted
                        )

                        OutlinedTextField(
                            value = passwordInput,
                            onValueChange = { passwordInput = it; authError = false },
                            label = { Text("CLAVE PERICIAL", style = MaterialTheme.typography.labelSmall) },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("admin_pass_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AlertOrange,
                                unfocusedBorderColor = SlateBorder,
                                focusedTextColor = ArchivalPaper,
                                unfocusedTextColor = ArchivalPaper
                            )
                        )

                        if (authError) {
                            Text(
                                text = "ACCESO DENEGADO: Clave incorrecta.",
                                style = MaterialTheme.typography.labelSmall.copy(color = RedactRed, fontWeight = FontWeight.Bold)
                            )
                        }

                        Button(
                            onClick = {
                                val success = viewModel.authenticateAdmin(passwordInput)
                                if (!success) authError = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                                .testTag("btn_admin_login"),
                            shape = RoundedCornerShape(2.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AlertOrange)
                        ) {
                            Text(
                                text = "AUTENTICAR EN SISTEMA",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SlateBlack
                                )
                            )
                        }
                    }
                }
            }
        } else {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(0.8.dp, VerifiedGreen, RoundedCornerShape(2.dp)),
                    colors = CardDefaults.cardColors(containerColor = VerifiedGreenDark.copy(alpha = 0.2f)),
                    shape = RoundedCornerShape(2.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "MODO DE ADMINISTRACIÓN ACTIVO",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = VerifiedGreen,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            TextButton(onClick = { viewModel.exitAdminMode() }) {
                                Text("CERRAR SESIÓN", style = MaterialTheme.typography.labelSmall.copy(color = ArchivalMuted))
                            }
                        }
                        Text(
                            text = "Puedes navegar por las pestañas de la app (Expedientes, Evidencias, Radar) y verás botones para editar y agregar contenido directamente.",
                            style = MaterialTheme.typography.bodySmall,
                            color = ArchivalPaper
                        )
                    }
                }
            }

            // Create New Dossier
            item {
                TechnicalHeader(title = "CREAR NUEVO EXPEDIENTE", code = "INSERT")
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(0.6.dp, SlateBorder, RoundedCornerShape(2.dp)),
                    colors = CardDefaults.cardColors(containerColor = SlateDark),
                    shape = RoundedCornerShape(2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = newId,
                            onValueChange = { newId = it },
                            label = { Text("CÓDIGO (Ej: VLC-1204)", style = MaterialTheme.typography.labelSmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                        OutlinedTextField(
                            value = newTitle,
                            onValueChange = { newTitle = it },
                            label = { Text("TÍTULO DEL EXPEDIENTE", style = MaterialTheme.typography.labelSmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                        OutlinedTextField(
                            value = newLocation,
                            onValueChange = { newLocation = it },
                            label = { Text("LOCALIZACIÓN / COMUNITAT VALENCIANA", style = MaterialTheme.typography.labelSmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                        OutlinedTextField(
                            value = newSummary,
                            onValueChange = { newSummary = it },
                            label = { Text("RESUMEN PERICIAL", style = MaterialTheme.typography.labelSmall) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                        OutlinedTextField(
                            value = newReport,
                            onValueChange = { newReport = it },
                            label = { Text("INFORME TÉCNICO DETALLADO", style = MaterialTheme.typography.labelSmall) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                        OutlinedTextField(
                            value = newRefs,
                            onValueChange = { newRefs = it },
                            label = { Text("REFERENCIAS CRUZADAS (Separadas por comas)", style = MaterialTheme.typography.labelSmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )

                        // Attach photo from gallery
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
                                text = if (newMediaUri != null) "IMAGEN ADJUNTA (CAMBIAR)" else "ADJUNTAR IMAGEN DE GALERÍA",
                                style = MaterialTheme.typography.labelSmall.copy(color = ArchivalPaper, fontWeight = FontWeight.Bold)
                            )
                        }

                        Button(
                            onClick = {
                                if (newId.isNotBlank() && newTitle.isNotBlank()) {
                                    val codeNum = newId.filter { it.isDigit() }.toIntOrNull() ?: 999
                                    viewModel.saveDossier(
                                        DossierEntity(
                                            id = newId.trim(),
                                            codeNumber = codeNum,
                                            title = newTitle.trim(),
                                            classification = newClassification,
                                            accessLevel = "NIVEL II",
                                            status = newStatus,
                                            dateString = "05/10/2026",
                                            location = newLocation.ifBlank { "Comunitat Valenciana" },
                                            referenceCode = "REF-${newId.trim()}",
                                            registeredElements = "REGISTRO DE CAMPO // EXPEDIENTE INCORPORADO",
                                            summary = newSummary,
                                            incidentReport = newReport,
                                            recoveredMaterial = "Documentación aportada desde consola pericial.",
                                            missingMaterial = "Anexos en curso de digitalización.",
                                            crossReferences = newRefs,
                                            observations = "Expediente formalizado por administración técnica.",
                                            drawableResName = newMediaUri ?: "",
                                            pagesCount = "01/01"
                                        )
                                    )
                                    dossierAddedSuccess = true
                                    newTitle = ""
                                    newSummary = ""
                                    newReport = ""
                                    newMediaUri = null
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(2.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AlertOrange)
                        ) {
                            Text("GUARDAR Y PUBLICAR EXPEDIENTE", color = SlateBlack, fontWeight = FontWeight.Bold)
                        }

                        if (dossierAddedSuccess) {
                            Text("Expediente incorporado y publicado con éxito.", color = VerifiedGreen, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }

            // Create New Coordinate Location
            item {
                TechnicalHeader(title = "INCORPORAR COORDENADA AL RADAR", code = "LOC-MAP")
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(0.6.dp, SlateBorder, RoundedCornerShape(2.dp)),
                    colors = CardDefaults.cardColors(containerColor = SlateDark),
                    shape = RoundedCornerShape(2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = newLocCode,
                            onValueChange = { newLocCode = it },
                            label = { Text("CÓDIGO (Ej: LOC-311-07)", style = MaterialTheme.typography.labelSmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                        OutlinedTextField(
                            value = newLocName,
                            onValueChange = { newLocName = it },
                            label = { Text("NOMBRE / MUNICIPIO", style = MaterialTheme.typography.labelSmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                        )
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = newLat,
                                onValueChange = { newLat = it },
                                label = { Text("LATITUD", style = MaterialTheme.typography.labelSmall) },
                                singleLine = true,
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                            )
                            OutlinedTextField(
                                value = newLong,
                                onValueChange = { newLong = it },
                                label = { Text("LONGITUD", style = MaterialTheme.typography.labelSmall) },
                                singleLine = true,
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = ArchivalPaper, unfocusedTextColor = ArchivalPaper)
                            )
                        }

                        Button(
                            onClick = {
                                val lat = newLat.toDoubleOrNull() ?: 39.4699
                                val lon = newLong.toDoubleOrNull() ?: -0.3763
                                if (newLocCode.isNotBlank() && newLocName.isNotBlank()) {
                                    viewModel.addLocation(
                                        LocationEntity(
                                            id = newLocCode.trim(),
                                            code = newLocCode.trim(),
                                            name = newLocName.trim(),
                                            latitude = lat,
                                            longitude = lon,
                                            area = newArea,
                                            relatedDossier = newRelDossier,
                                            description = "Coordenada registrada mediante consola pericial de administración.",
                                            status = "ACTIVO",
                                            visibilityLevel = "PÚBLICA"
                                        )
                                    )
                                    locationAddedSuccess = true
                                    newLocName = ""
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(2.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AlertOrange)
                        ) {
                            Text("AÑADIR A LA RED GEODÉSICA", color = SlateBlack, fontWeight = FontWeight.Bold)
                        }

                        if (locationAddedSuccess) {
                            Text("Coordenada georreferenciada guardada en el radar.", color = VerifiedGreen, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }

            // Review citizen reports
            item {
                TechnicalHeader(title = "INFORMES CIUDADANOS // GESTIÓN DE ESTADO", code = "${citizenReports.size} REPORTES")
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
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = report.trackingCode, color = PericialCyan, fontWeight = FontWeight.Bold)
                            StatusBadge(status = report.status)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "${report.municipality} - ${report.description}", color = ArchivalPaper, style = MaterialTheme.typography.bodySmall)

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            OutlinedButton(
                                onClick = { viewModel.updateReportStatus(report, "ASOCIADO A EXPEDIENTE") },
                                shape = RoundedCornerShape(2.dp),
                                modifier = Modifier.height(28.dp),
                                contentPadding = PaddingValues(horizontal = 6.dp)
                            ) {
                                Text("VINCULAR A EXPEDIENTE", fontSize = 8.sp, color = VerifiedGreen)
                            }
                            OutlinedButton(
                                onClick = { viewModel.updateReportStatus(report, "ARCHIVADO VERIFICADO") },
                                shape = RoundedCornerShape(2.dp),
                                modifier = Modifier.height(28.dp),
                                contentPadding = PaddingValues(horizontal = 6.dp)
                            ) {
                                Text("VERIFICAR", fontSize = 8.sp, color = PericialCyan)
                            }
                        }
                    }
                }
            }
        }
    }
}
