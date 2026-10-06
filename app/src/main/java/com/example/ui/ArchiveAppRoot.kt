package com.example.ui

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.entity.DossierEntity
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.viewmodel.ArchiveViewModel

enum class ArchiveTab(val label: String, val code: String) {
    HOME("BRECHA", "SYS"),
    DOSSIERS("ARCHIVO", "01"),
    EVIDENCES("EVIDENCIAS", "02"),
    CITIZEN_DROP("BUZÓN", "03"),
    MAP("MAPA", "04"),
    MISSING("FALTANTES", "05"),
    ADMIN("ADMIN", "ROOT")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveAppRoot(
    viewModel: ArchiveViewModel = viewModel()
) {
    val context = LocalContext.current
    var currentTab by remember { mutableStateOf(ArchiveTab.HOME) }
    val selectedDossier by viewModel.selectedDossier.collectAsState()
    val isAdmin by viewModel.isAdminAuthenticated.collectAsState()

    var showKeyAuthDialog by remember { mutableStateOf(false) }
    var passwordInput by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var authError by remember { mutableStateOf(false) }

    var showAdminMenuDialog by remember { mutableStateOf(false) }

    BackHandler(enabled = selectedDossier != null || currentTab != ArchiveTab.HOME) {
        if (selectedDossier != null) {
            viewModel.selectDossier(null)
        } else {
            currentTab = ArchiveTab.HOME
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(if (isAdmin) AlertOrange else PericialCyan, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "ARCHIVO VLC",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.2.sp,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = ArchivalPaper
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isAdmin) "// ROOT ACTIVO" else "// 61.4%",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (isAdmin) AlertOrange else PericialCyan,
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                if (isAdmin) {
                                    showAdminMenuDialog = true
                                } else {
                                    passwordInput = ""
                                    authError = false
                                    showKeyAuthDialog = true
                                }
                            },
                            modifier = Modifier.testTag("btn_top_admin")
                        ) {
                            Icon(
                                imageVector = if (isAdmin) Icons.Default.LockOpen else Icons.Default.Key,
                                contentDescription = "Acceso pericial",
                                tint = if (isAdmin) AlertOrange else ArchivalMuted,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = SlateDark
                    )
                )

                // Admin notification bar when editing mode is enabled
                if (isAdmin) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(AlertOrange.copy(alpha = 0.2f))
                            .border(0.6.dp, AlertOrange)
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = AlertOrange,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "MODO EDICIÓN HABILITADO // PUEDES AÑADIR O MODIFICAR CONTENIDO",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = AlertOrange,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Text(
                            text = "SALIR",
                            modifier = Modifier
                                .clickable {
                                    viewModel.exitAdminMode()
                                    Toast.makeText(context, "Modo consulta restaurado", Toast.LENGTH_SHORT).show()
                                }
                                .padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = RedactRed,
                                fontWeight = FontWeight.Black,
                                fontSize = 9.sp
                            )
                        )
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = SlateDark,
                tonalElevation = 0.dp,
                modifier = Modifier.border(width = 0.6.dp, color = SlateBorder)
            ) {
                listOf(
                    ArchiveTab.HOME to Icons.Default.Home,
                    ArchiveTab.DOSSIERS to Icons.Default.Folder,
                    ArchiveTab.EVIDENCES to Icons.Default.Visibility,
                    ArchiveTab.CITIZEN_DROP to Icons.Default.UploadFile,
                    ArchiveTab.MAP to Icons.Default.Place,
                    ArchiveTab.MISSING to Icons.Default.Search
                ).forEach { (tab, icon) ->
                    val isSelected = currentTab == tab && selectedDossier == null
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            viewModel.selectDossier(null)
                            currentTab = tab
                        },
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = tab.label,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        label = {
                            Text(
                                text = tab.label,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 8.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = if (isAdmin) AlertOrange else PericialCyan,
                            selectedTextColor = if (isAdmin) AlertOrange else PericialCyan,
                            unselectedIconColor = ArchivalMuted,
                            unselectedTextColor = ArchivalMuted,
                            indicatorColor = SlateSurface
                        )
                    )
                }
            }
        },
        containerColor = SlateBlack
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (selectedDossier != null) {
                DossierDetailScreen(
                    dossier = selectedDossier!!,
                    viewModel = viewModel,
                    onBack = { viewModel.selectDossier(null) },
                    onNavigateToDossierId = { id -> viewModel.selectDossierById(id) }
                )
            } else {
                when (currentTab) {
                    ArchiveTab.HOME -> HomeScreen(
                        viewModel = viewModel,
                        onNavigateToDossiers = { currentTab = ArchiveTab.DOSSIERS },
                        onNavigateToCitizenDrop = { currentTab = ArchiveTab.CITIZEN_DROP },
                        onNavigateToMap = { currentTab = ArchiveTab.MAP },
                        onNavigateToMissing = { currentTab = ArchiveTab.MISSING },
                        onSelectDossier = { d -> viewModel.selectDossier(d) }
                    )
                    ArchiveTab.DOSSIERS -> DossierListScreen(
                        viewModel = viewModel,
                        onSelectDossier = { d -> viewModel.selectDossier(d) }
                    )
                    ArchiveTab.EVIDENCES -> EvidenceScreen(
                        viewModel = viewModel
                    )
                    ArchiveTab.CITIZEN_DROP -> CitizenDropScreen(
                        viewModel = viewModel
                    )
                    ArchiveTab.MAP -> MapRadarScreen(
                        viewModel = viewModel,
                        onNavigateToDossierId = { id -> viewModel.selectDossierById(id) }
                    )
                    ArchiveTab.MISSING -> MissingFilesScreen(
                        viewModel = viewModel,
                        onSelectDossier = { d -> viewModel.selectDossier(d) },
                        onNavigateToCitizenDrop = { currentTab = ArchiveTab.CITIZEN_DROP }
                    )
                    ArchiveTab.ADMIN -> AdminPanelScreen(
                        viewModel = viewModel
                    )
                }
            }
        }
    }

    // Key Password Dialog
    if (showKeyAuthDialog) {
        AlertDialog(
            onDismissRequest = { showKeyAuthDialog = false },
            containerColor = SlateDark,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Key, contentDescription = null, tint = AlertOrange, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ACCESO PERICIAL // CLAVE ROOT",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = ArchivalPaper,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Introduzca la clave de autorización para habilitar el modo de edición y gestión de archivos.",
                        style = MaterialTheme.typography.bodySmall,
                        color = ArchivalMuted
                    )

                    OutlinedTextField(
                        value = passwordInput,
                        onValueChange = {
                            passwordInput = it
                            authError = false
                        },
                        label = { Text("CLAVE PERICIAL", style = MaterialTheme.typography.labelSmall) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("dialog_admin_pass_input"),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Ver contraseña",
                                    tint = ArchivalMuted
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AlertOrange,
                            unfocusedBorderColor = SlateBorder,
                            focusedTextColor = ArchivalPaper,
                            unfocusedTextColor = ArchivalPaper
                        )
                    )

                    if (authError) {
                        Text(
                            text = "CLAVE INCORRECTA // ACCESO DENEGADO",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = RedactRed,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val ok = viewModel.authenticateAdmin(passwordInput)
                        if (ok) {
                            showKeyAuthDialog = false
                            Toast.makeText(context, "Modo edición activado: Clave autorizada", Toast.LENGTH_SHORT).show()
                        } else {
                            authError = true
                        }
                    },
                    shape = RoundedCornerShape(2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AlertOrange)
                ) {
                    Text("DESBLOQUEAR EDICIÓN", color = SlateBlack, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showKeyAuthDialog = false }) {
                    Text("CANCELAR", color = ArchivalMuted)
                }
            }
        )
    }

    // Admin Active Menu Dialog
    if (showAdminMenuDialog) {
        AlertDialog(
            onDismissRequest = { showAdminMenuDialog = false },
            containerColor = SlateDark,
            title = {
                Text(
                    text = "GESTIÓN DE MODO EDICIÓN",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = AlertOrange,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "El modo de edición está actualmente ACTIVO. Puedes editar directamente cualquier expediente en su ficha o añadir nuevas evidencias y coordenadas.",
                        style = MaterialTheme.typography.bodySmall,
                        color = ArchivalPaper
                    )

                    Button(
                        onClick = {
                            showAdminMenuDialog = false
                            currentTab = ArchiveTab.ADMIN
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(2.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SlateSurface)
                    ) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(16.dp), tint = PericialCyan)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("IR AL PANEL DE ADMINISTRACIÓN GLOBAL", color = ArchivalPaper, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            viewModel.exitAdminMode()
                            showAdminMenuDialog = false
                            Toast.makeText(context, "Modo edición desactivado", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(2.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RedactRedDark)
                    ) {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(16.dp), tint = ArchivalPaper)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("CERRAR MODO EDICIÓN", color = ArchivalPaper, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showAdminMenuDialog = false }) {
                    Text("VOLVER A LA APP", color = ArchivalMuted)
                }
            }
        )
    }
}
