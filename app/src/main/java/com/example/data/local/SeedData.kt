package com.example.data.local

import com.example.data.local.entity.*

object SeedData {
    val dossiers = listOf(
        DossierEntity(
            id = "VLC-000",
            codeNumber = 0,
            title = "TABLA GENERAL DE RECUPERACIÓN",
            classification = "DESCLASIFICADO TÉCNICO",
            accessLevel = "NIVEL I",
            status = "RECUPERADO",
            dateString = "05/10/2026",
            location = "SECTOR CENTRAL // REGISTRO GENERAL",
            referenceCode = "REF-SYS-000-INDEX",
            registeredElements = "INVENTARIO / REGISTRO MATRIZ / EXPEDIENTES VINCULADOS",
            summary = "Punto de entrada general. Existe documentación relacionada con diferentes anomalías e incidentes registrados en la Comunitat Valenciana durante los últimos 32 años. La autoría original de los expedientes permanece suprimida.",
            incidentReport = "Inventario general de expedientes recuperados tras la exposición del repositorio. Notas internas anexas:\n«La ausencia de un expediente no implica que el incidente no haya ocurrido.»\n«Algunos documentos solamente han podido reconstruirse mediante referencias contenidas en expedientes posteriores.»\n«Se autoriza la consulta pública de los expedientes con estado recuperado o parcial.»",
            recoveredMaterial = "16 índices de inventario, 4 tablas de referencias cruzadas, 1 registro de topología.",
            missingMaterial = "Códices de acceso 108, 134, 402 y 506 sin copia digital disponible.",
            crossReferences = "VLC-001,VLC-014,VLC-027,VLC-134,VLC-311,VLC-901",
            observations = "El índice se actualiza conforme avanza la desclasificación y el análisis pericial.",
            drawableResName = "hero_intake_forensic_1791219485484",
            pagesCount = "01/03"
        ),
        DossierEntity(
            id = "VLC-001",
            codeNumber = 1,
            title = "REGISTRO DIGITAL DE RECUPERACIÓN",
            classification = "DESCLASIFICADO PARCIAL",
            accessLevel = "NIVEL II",
            status = "RECUPERADO PARCIAL",
            dateString = "██/██/2026",
            location = "ESTRUCTURA RURAL ABANDONADA — COMUNITAT VALENCIANA",
            referenceCode = "REC-EXP-URBEX-01",
            registeredElements = "DOCUMENTACIÓN EN PAPEL / CARPETA FÍSICA / FOTOGRAFÍAS / NOTAS MANUSCRITAS",
            summary = "Explorador rural localiza documentación física dispersa en una construcción aislada. Pese al estado ruinoso de la estructura, una carpeta técnica clasificada se encontraba herméticamente conservada.",
            incidentReport = "Una persona que realizaba exploración urbana/rural encontró hojas sueltas y una carpeta técnica conservada en el suelo de una estructura aislada. La carpeta contenía expedientes clasificados con los códigos VLC-014, VLC-027, VLC-041, VLC-134 y VLC-402.\n\nEl individuo fotografió el material y contactó con los administradores para entregar copias digitales. Tras remitir la tercera remesa, cesó toda comunicación. Su última transmisión registrada contiene únicamente la frase:\n«Creo que esto no empieza aquí.»\n\nEstado del informante: PARADERO / CONTACTO NO LOCALIZADO.",
            recoveredMaterial = "1 carpeta pericial de cartón prensado, 14 negativos fotográficos, 3 fragmentos de actas de defunción y planos parcelarios.",
            missingMaterial = "Originales físicos retirados antes de la llegada de la comisión pericial.",
            crossReferences = "VLC-014,VLC-027,VLC-041,VLC-134,VLC-402",
            observations = "Se presume que la persona actuó de buena fe. No constan registros policiales abiertos bajo su filiación conocida.",
            pagesCount = "02/05"
        ),
        DossierEntity(
            id = "VLC-014",
            codeNumber = 14,
            title = "ANOMALÍAS EN ZONAS RURALES // NÓDULO 014",
            classification = "DESCLASIFICADO PARCIAL",
            accessLevel = "NIVEL III",
            status = "INVESTIGACIÓN INCONCLUSA",
            dateString = "14/09/2008 – ACTIVO",
            location = "COMUNITAT VALENCIANA // L'HORTA NORD – CAMP DE TÚRIA – LA SAFOR",
            referenceCode = "ANO-BIO-014-TREE",
            registeredElements = "VEGETACIÓN / ESTRUCTURAS OCULARES / CORTEZA / TEJIDO CELULAR VEGETAL",
            summary = "Aparición recurrente de nudos y fisuras en corteza de árboles viejos (algarrobos, olivos, naranjos) con morfología visualmente compatible con tejido ocular humano. El fenómeno no es aislado y se reproduce en municipios distantes.",
            incidentReport = "Durante diferentes inspecciones forestales y agrícolas en partidas rurales se documentaron formaciones anómalas en troncos y raíces. Los investigadores descartaron inicialmente alteraciones fúngicas o tallas antropogénicas.\n\nExtracto técnico de peritaje:\n«La morfología observada en los registros 014-A a 014-D presenta capas concéntricas, hendidura esclerótica y humedad superficial sin secreción de resina. La similitud observada no debería resultar estadísticamente relevante.»\n«Instrucción de servicio: Evitar terminología descriptiva no técnica en documentación pública. No utilizar el término 'ojo'.»",
            recoveredMaterial = "4 series fotográficas macro nocturnas (014-A, 014-B, 014-C, 014-D), 2 muestras botánicas con degradación acelerada.",
            missingMaterial = "Muestra de tejido 014-M2 extraviada en tránsito a laboratorio pericial de Valencia en 2012.",
            crossReferences = "VLC-027,VLC-041,VLC-073,VLC-209,VLC-311",
            observations = "Los agricultores colindantes afirmaron sentir vigilancia constante antes del secado imprevisto de los campos.",
            drawableResName = "evidence_nodule_eye_1791219501249",
            pagesCount = "04/08"
        ),
        DossierEntity(
            id = "VLC-027",
            codeNumber = 27,
            title = "INCIDENTE RURAL // COMBUSTIÓN Y SIMBOLOGÍA",
            classification = "DESCLASIFICADO PARCIAL",
            accessLevel = "NIVEL II",
            status = "RECUPERACIÓN PARCIAL",
            dateString = "22/11/2011",
            location = "VALENCIA SECTOR NORTE // EL PUIG DE SANTA MARIA",
            referenceCode = "INC-RUR-027-FIRE",
            registeredElements = "FUEGO / SIMBOLOGÍA PERIMETRAL / RESTOS ORGÁNICOS / DOCUMENTOS CALCINADOS",
            summary = "Combustión a baja temperatura localizada en perímetro agrícola. Marcas grabadas sobre postes de hormigón y restos de expedientes de 1998 semi-quemados en el epicentro.",
            incidentReport = "Acta de intervención por conato anómalo sin propagación térmica circundante. La vegetación a 10 cm del fuego presentaba escarcha.\n\nSe localizaron 9 páginas de un informe anterior.\n[AVISO DE INTEGRIDAD: PÁGINA 04/09 AUSENTE / RETIRADA DEL LEGAJO ANTES DEL ESCANEO]\n\nNota marginal a lápiz en página 03: «Consultar VLC-134. La firma del acta pericial fue suplantada.»",
            recoveredMaterial = "3 fotografías de contraste pericial, 8 páginas manuscritas con carbonización perimetral.",
            missingMaterial = "PÁGINA 04/09 — NO RECUPERADA. Contenía los nombres de los 2 técnicos inspectores.",
            crossReferences = "VLC-014,VLC-134,VLC-402,VLC-506",
            observations = "La zona fue cercada durante 72 horas por personal no identificado con uniformes sin distintivo.",
            pagesCount = "08/09"
        ),
        DossierEntity(
            id = "VLC-041",
            codeNumber = 41,
            title = "REGISTRO AUDIOVISUAL DE CAMPO",
            classification = "DESCLASIFICADO PARCIAL",
            accessLevel = "NIVEL II",
            status = "ARCHIVO CORRUPTO",
            dateString = "03/04/2015 – 03:14:22",
            location = "CAMINO RURAL // INTERSECCIÓN CV-315 – CV-306",
            referenceCode = "REC-AV-041-CORRUPT",
            registeredElements = "CÁMARA VEHICULAR / AUDIO INFRASÓNICO 17.4 HZ / INTERFERENCIA DE BANDA",
            summary = "Grabación continua de 00:03:17 procedente de una unidad vehicular. Alteraciones magnéticas progresivas y saltos de cuadro sin causa mecánica demostrada.",
            incidentReport = "Transcripción pericial con marcas de tiempo homologadas:\n00:00:00 — Desplazamiento regular a 42 km/h por asfalto secundario.\n00:00:41 — Ruido sordo no identificado; caída de revoluciones de audio.\n00:01:12 — Artefactos visuales de barrido vertical; pérdida del canal verde.\n00:02:06 — Anomalía en margen derecho; sujeto o elemento vertical fuera de plano focal.\n00:02:49 — Interrupción de telemetría GPS.\n00:03:17 — Corte abrupto de cuadro a negro. Salto de 14 minutos en reloj de procesador.",
            recoveredMaterial = "Archivo crudo .raw (74 MB) con sectores dañados.",
            missingMaterial = "Fotogramas 1204 al 1850 irrecuperables por sobreescritura cuántica.",
            crossReferences = "VLC-001,VLC-014,VLC-209,VLC-311",
            observations = "El vehículo fue hallado estacionado en punto muerto con las luces de emergencia apagadas.",
            drawableResName = "evidence_dashcam_anomaly_1791219514207",
            pagesCount = "01/02"
        ),
        DossierEntity(
            id = "VLC-073",
            codeNumber = 73,
            title = "INCONSISTENCIA TEMPORAL DE REGISTRO",
            classification = "DOCUMENTACIÓN INTERNA",
            accessLevel = "NIVEL III",
            status = "INCONSISTENTE / CONTRADICCIÓN",
            dateString = "18/06/2018",
            location = "VALENCIA ARCHIVO CENTRAL",
            referenceCode = "DOC-INT-073-CONFLICT",
            registeredElements = "ACTAS DUPLICADAS / FECHAS DISCORDANTES / CORRECCIÓN MANUAL",
            summary = "Expediente interno que contradice abiertamente la fecha oficial de descubrimiento del fenómeno VLC-014. Documentos de 1994 ya describían el mismo patrón biológico.",
            incidentReport = "VLC-014 sostiene que la primera catalogación oficial ocurrió en septiembre de 2008. Sin embargo, en VLC-073 se conserva un parte de peritaje rural fechado el 12 de noviembre de 1994 donde se cita literalmente la misma estructura ocular en un algarrobo de término de Sagunt.\n\nInterrogante pericial:\n¿Por qué el expediente matriz fue purgado y reiniciado con fecha de 2008?\n¿Quién ordenó la destrucción de las copias previas a la digitalización autonómica?",
            recoveredMaterial = "Copia heliográfica de informe municipal de 1994 con sello desvaído.",
            missingMaterial = "Legajo original de la Dirección General de Medio Natural de 1994.",
            crossReferences = "VLC-014,VLC-108,VLC-134",
            observations = "Existe anotación en tinta roja: «No contrastar con gabinete de prensa. Mantener versión 2008.»",
            pagesCount = "02/03"
        ),
        DossierEntity(
            id = "VLC-108",
            codeNumber = 108,
            title = "SOLICITUD DE TRANSFERENCIA // COPIA EXPURGADA",
            classification = "MÁXIMO SECRETO // EXPURGADO",
            accessLevel = "RESTRINGIDO",
            status = "NO LOCALIZADO",
            dateString = "09/02/2013",
            location = "ORIGEN Y DESTINO CLASIFICADOS",
            referenceCode = "REQ-TRF-108-VOID",
            registeredElements = "OFICIO ADMINISTRATIVO / SALIDA SIN RETORNO",
            summary = "Expediente solicitado formalmente en tres auditorías sucesivas sin respuesta documental. No existe soporte físico en dependencias autonómicas ni copia en base de datos.",
            incidentReport = "Transcripción del intercambio administrativo oficial:\n«Se solicita con carácter urgente copia del expediente VLC-108 para cotejo pericial.»\nRespuesta de custodio (14/02/2013):\n«No existe copia disponible en servidor central ni soporte magnético.»\nAclaración posterior:\n«No consta en acta registral que haya sido destruido legalmente.»",
            recoveredMaterial = "Número de registro de salida en libro de registro general.",
            missingMaterial = "Totalidad del contenido del informe VLC-108.",
            crossReferences = "VLC-073,VLC-134,VLC-402",
            observations = "Mencionado en las anotaciones del explorador del expediente VLC-001.",
            pagesCount = "00/01",
            isMissing = true,
            missingReason = "Expurgado del depósito principal sin acta de destrucción autorizada."
        ),
        DossierEntity(
            id = "VLC-134",
            codeNumber = 134,
            title = "DESAPARICIÓN DE FONDOS // CADENA DE REFERENCIAS",
            classification = "RESTRINGIDO // CLASIFICADO",
            accessLevel = "NIVEL MÁXIMO",
            status = "DESAPARECIDO",
            dateString = "██/██/2013",
            location = "MARJAL DE LA SAFOR // SECTOR RURAL SUR",
            referenceCode = "LST-134-MISSING",
            registeredElements = "EXPEDIENTE BISAGRA / CONEXIÓN 027-402-506 / EVIDENCIAS EXTRAVIADAS",
            summary = "El eslabón clave del archivo. Citado en VLC-027, VLC-402 y VLC-506. Su contenido explicaba el origen de los materiales hallados en las zonas rurales quemadas.",
            incidentReport = "Del expediente VLC-134 solo han podido recuperarse referencias cruzadas y dos líneas de encabezado desclasificadas por error:\n«Asunto: Identificación de emisores acústicos y perimetraje en suelo rústico protegido.»\n«[LÍNEA CENSURADA POR PROTOCOLO DE SEGURIDAD TERRITORIAL]»\n\nConexión pericial:\nVLC-027 → VLC-134 → VLC-402 → VLC-506.\nQuien controle el contenido de VLC-134 conoce el propósito de las coordenadas del expediente 311.",
            recoveredMaterial = "1 carátula de expediente con sello 'RESERVADO' y fecha parcial.",
            missingMaterial = "34 folios mecanografiados, cinta de audio en microcassette y 6 mapas parcelarios.",
            crossReferences = "VLC-027,VLC-402,VLC-506,VLC-311",
            observations = "Búsqueda activa prioritaria en repositorios privados y aportaciones ciudadanas.",
            pagesCount = "01/35",
            isMissing = true,
            missingReason = "Desaparición física simultánea en 3 depósitos tras el apagón de servidores de 2013."
        ),
        DossierEntity(
            id = "VLC-209",
            codeNumber = 209,
            title = "FONDO FOTOGRÁFICO PERICIAL // REVISIÓN FORENSE",
            classification = "DESCLASIFICADO TÉCNICO",
            accessLevel = "NIVEL II",
            status = "RECUPERADO",
            dateString = "11/10/2019",
            location = "CAMINOS RURALES // TÉRMINOS DE CHESTE Y GODELLETA",
            referenceCode = "PHO-FOR-209-SERIES",
            registeredElements = "FOTOGRAFÍAS ANALÓGICAS / ANOMALÍAS PERIFÉRICAS / ELEMENTOS NO IDENTIFICADOS",
            summary = "Colección de 12 placas fotográficas periciales. Inspecciones rutinarias donde tras un análisis minucioso con ampliación digital se aprecian sombras y deformaciones ajenas al entorno.",
            incidentReport = "Serie fotográfica pericial:\nEVIDENCIA-209-A: Camino de servicio rural en Godelleta. Enfoque al vallado perimetral.\nEVIDENCIA-209-B: Construcción de aperos abandonada. Detalle de vano de ventana.\nEVIDENCIA-209-C: Tronco de algarrobo centenario en linde de parcela.\n\nNota técnica del perito de laboratorio:\n«Elemento no identificado durante la revisión inicial de contacto en negativo 4. En la ampliación al 400% se observa una distorsión luminosa con silueta no concordante con la vegetación circundante.»",
            recoveredMaterial = "12 positivos en papel baritado 18x24 cm con anotaciones periciales al dorso.",
            missingMaterial = "Negativos originales en soporte acetato correspondientes a las tomas 7 y 8.",
            crossReferences = "VLC-014,VLC-041,VLC-311",
            observations = "Las fotografías no presentan indicios de manipulación por doble exposición química.",
            pagesCount = "03/06"
        ),
        DossierEntity(
            id = "VLC-311",
            codeNumber = 311,
            title = "REGISTRO DE COORDENADAS // BALIZAMIENTO Y EVENTOS",
            classification = "DESCLASIFICADO TÉCNICO",
            accessLevel = "NIVEL II",
            status = "RECUPERADO",
            dateString = "2008 – 2026",
            location = "RED GEODÉSICA TERRITORIAL COMUNITAT VALENCIANA",
            referenceCode = "GEO-RAD-311-MAP",
            registeredElements = "COORDENADAS ETRS89 / LAT-LONG / EVENTOS / HITOS Y CLAVOS PERIMETRALES",
            summary = "Compendio de coordenadas georreferenciadas. Advertencia capital de los investigadores: no todos los puntos corresponden a ubicaciones geográficas fijas; algunos registran eventos temporales.",
            incidentReport = "ADVERTENCIA DE SERVICIO FORENSE:\n«NO TODAS LAS COORDENADAS REPRESENTAN UBICACIONES FÍSICAS ESTABLES.»\n«ALGUNAS REPRESENTAN PUNTOS DE EVENTO, DESPLAZAMIENTO O FENÓMENOS DINÁMICOS.»\n\nPuntos registrados activos:\n• LOC-311-01: El Puig de Santa Maria (39.5886° N, -0.3032° W) — Referencia VLC-027.\n• LOC-311-02: Cheste Rural (39.4947° N, -0.6825° W) — Referencia VLC-667.\n• LOC-311-03: Marjal de la Safor (39.0112° N, -0.1843° W) — Referencia VLC-134.\n• LOC-311-04: Albufera / Silla (39.3456° N, -0.3842° W) — Referencia VLC-041.\n• LOC-311-05: L'Horta Nord (39.5421° N, -0.3912° W) — Nódulo Biológico VLC-014.\n• LOC-311-06: Costa de Sueca / Faro Cullera (39.1852° N, -0.2289° W) — Registro Luminoso Costero.",
            recoveredMaterial = "1 tabla geodésica digitalizada con 6 registros consolidados y 22 en cálculo.",
            missingMaterial = "Cuadrante de cálculo geodésico correspondiente a la Serranía y Rincón de Ademuz.",
            crossReferences = "VLC-014,VLC-027,VLC-134,VLC-402,VLC-667",
            observations = "El sistema permite al administrador incorporar nuevas coordenadas desde el panel pericial.",
            pagesCount = "02/04"
        ),
        DossierEntity(
            id = "VLC-402",
            codeNumber = 402,
            title = "LEGAJO ANALÓGICO // DESLOCALIZACIÓN",
            classification = "RESTRINGIDO",
            accessLevel = "NIVEL III",
            status = "NO LOCALIZADO",
            dateString = "██/██/2016",
            location = "DEPÓSITO DOCUMENTAL EXTERNO",
            referenceCode = "REC-402-ANALOG",
            registeredElements = "SOPORTE MAGNÉTICO / INFORMACIÓN NO DIGITALIZADA",
            summary = "Expediente citado de forma recurrente en VLC-134, VLC-311 y VLC-506. Las comisiones de investigación no han logrado dar con la copia física íntegra.",
            incidentReport = "Anotación en libro de registro:\n«La última copia conocida de VLC-402 no estaba almacenada en formato digital ni en los servidores de la Generalitat.»\n«Se encontraba en custodia privada en una caja de seguridad bancaria cuya titularidad quedó extinguida sin herederos acreditados.»",
            recoveredMaterial = "Comprobante de depósito bancario de 2004 con clave pericial.",
            missingMaterial = "Caja pericial y legajo completo de 120 páginas.",
            crossReferences = "VLC-134,VLC-311,VLC-506",
            observations = "Existe sospecha de que fragmentos del documento han sido filtrados a foros de exploración urbana.",
            pagesCount = "00/12",
            isMissing = true,
            missingReason = "Custodia bancaria vencida con destrucción o extracción por terceros no autorizados."
        ),
        DossierEntity(
            id = "VLC-506",
            codeNumber = 506,
            title = "PROTOCOLO DE CONTENCIÓN // ADVERTENCIA CRÍTICA",
            classification = "CLASIFICACIÓN MÁXIMA // RESTRINGIDO",
            accessLevel = "NIVEL MÁXIMO",
            status = "DESAPARECIDO",
            dateString = "██/██/2021",
            location = "UBICACIÓN CONFIDENCIAL",
            referenceCode = "WRN-506-RECURSIVE",
            registeredElements = "CIRCULAR INTERNA / ALERTA DE SUPLANTACIÓN DOCUMENTAL",
            summary = "Uno de los expedientes más herméticos de la red. Solo se conserva una directiva técnica de dos líneas.",
            incidentReport = "Directiva técnica recuperada de copia de seguridad residual:\n«DIRECTIVA DE SEGURIDAD 506-BIS:\nSi el expediente VLC-506 vuelve a aparecer o es remitido por fuentes no oficiales:\nNO UTILIZAR LA COPIA ANTERIOR NI COTEJAR CON VERSIONES PREVIAS A 2021.»\n\nNo se aportan motivos técnicos ni justificación en el registro.",
            recoveredMaterial = "1 directiva impresa en papel autocopiativo con firma no identificada.",
            missingMaterial = "Copia matriz y anexos explicativos de la directiva 506.",
            crossReferences = "VLC-027,VLC-134,VLC-402",
            observations = "No abrir bajo ningún concepto archivos .pdf o escaneos que afirmen contener la totalidad de VLC-506 sin verificación pericial criptográfica.",
            pagesCount = "01/01",
            isMissing = true,
            missingReason = "Retirado preventivamente por orden directa no numerada."
        ),
        DossierEntity(
            id = "VLC-667",
            codeNumber = 667,
            title = "L'HUMANITAT ANTIGUA DE CHESTE // INVESTIGACIÓN EN CURSO",
            classification = "PENDIENTE DE DESCLASIFICACIÓN",
            accessLevel = "NIVEL II",
            status = "EN INVESTIGACIÓN",
            dateString = "2024 – 2026",
            location = "CHESTE // PLA DE CHESTE – FOYA DE CHESTE",
            referenceCode = "INV-DOC-667-CHESTE",
            registeredElements = "ASENTAMIENTOS RURALES / ESTRUCTURAS ANÓNIMAS / RESTOS MATERIALES",
            summary = "Investigación pericial activa sobre hallazgos y estructuras anómalas en el término de Cheste. Modelo documental de nueva apertura.",
            incidentReport = "Análisis actualmente en curso por comisión técnica independiente.\n«La documentación disponible no permite establecer una conclusión definitiva.»\n«Se han identificado referencias cruzadas con documentación todavía no publicada en los fondos de Horta Sud y Ribera Alta.»\nProgreso del dictamen pericial: 42% consolidado. Estimación de desclasificación formal: 6 a 12 meses.",
            recoveredMaterial = "3 informes preliminares de arqueología preventiva, 5 muestras cerámicas no catalogadas.",
            missingMaterial = "Informe topográfico del sector norte de la Foia de Cheste.",
            crossReferences = "VLC-014,VLC-209,VLC-311",
            observations = "No se realizan afirmaciones concluyentes hasta finalizar la prueba de termoluminiscencia.",
            pagesCount = "02/07"
        ),
        DossierEntity(
            id = "VLC-901",
            codeNumber = 901,
            title = "AVISO DE EXPANSIÓN TERRITORIAL Y FONDOS",
            classification = "DESCLASIFICADO TÉCNICO",
            accessLevel = "NIVEL I",
            status = "RECUPERADO",
            dateString = "05/10/2026",
            location = "COMUNITAT VALENCIANA // ÁMBITO GLOBAL",
            referenceCode = "EXP-GLB-901-SCOPE",
            registeredElements = "MAPA DE COBERTURA / SECTOR NORTE / SECTOR SUR / COSTA / MARJALES",
            summary = "Declaración de alcance. ARCHIVO VLC representa únicamente el 8.4% de los fondos documentales interceptados. Existen expedientes correlativos en trámite de rescate.",
            incidentReport = "La investigación iniciada en el entorno rural de Valencia cuenta con ramificaciones geográficas plenamente documentadas en:\n• Valencia Norte: Sagunt, El Puig, Massamagrell, Rafelbunyol.\n• Valencia Sur y Marjales: Sueca, Cullera, Tavernes, Gandia.\n• Interior y Comarcas Centrales: Cheste, Godelleta, Chiva, Buñol.\n• Edificaciones aisladas en suelo rústico protegido.\n\nSe abre formalmente el PROTOCOLO DE RECEPCIÓN CIUDADANA para recabar registros no catalogados.",
            recoveredMaterial = "1 mapa de zonificación territorial con 14 cuadrículas periciales.",
            missingMaterial = "Expedientes de las comarcas del interior norte y sur en proceso de desencriptado.",
            crossReferences = "VLC-000,VLC-001,VLC-014,VLC-311",
            observations = "El repositorio se mantendrá en crecimiento continuo conforme se reciban y verifiquen nuevas aportaciones.",
            pagesCount = "01/02"
        )
    )

    val evidences = listOf(
        EvidenceEntity(
            id = "VLC-E014-B",
            originDossierCode = "VLC-014",
            type = "FOTOGRAFÍA",
            dateRecovery = "14/09/2008 – 23:42",
            status = "ANOMALÍA CONFIRMADA",
            description = "Fotografía pericial nocturna de alta resolución con linterna táctica. Hendidura en nudo de corteza de algarrobo con conformación ocular humana húmeda y escala métrica forense.",
            location = "Partida de Baix, Horta Nord (Valencia)",
            technicalNotes = "Cámara Nikon D80, f/5.6, ISO 400. Muestra sin incisión mecánica. Reacción fototrópica anómala documentada.",
            drawableResName = "evidence_nodule_eye_1791219501249"
        ),
        EvidenceEntity(
            id = "VLC-E041-01",
            originDossierCode = "VLC-041",
            type = "VÍDEO / FOTOGRAMA",
            dateRecovery = "03/04/2015 – 03:14",
            status = "CORRUPTO",
            description = "Fotograma extraído de cámara vehicular infrarroja en carretera comarcal solitaria rodeada de naranjos a las 03:14 AM. Distorsión perimétrica y artefacto vertical anómalo en arcén derecho.",
            location = "Intersección CV-315 // Camp de Túria",
            technicalNotes = "Codec H.264 comprimido. Pérdida súbita de paridad a 17.4 Hz en pista de audio analógica.",
            drawableResName = "evidence_dashcam_anomaly_1791219514207",
            audioDuration = "00:03:17"
        ),
        EvidenceEntity(
            id = "VLC-E001-A",
            originDossierCode = "VLC-001",
            type = "DOCUMENTO",
            dateRecovery = "02/10/2026",
            status = "AUTENTICADO",
            description = "Carpeta pericial física de cartón prensado con sello 'RECEPCIÓN DE EXPEDIENTES' hallada en la estructura abandonada por el explorador anónimo.",
            location = "Término rural Comunitat Valenciana",
            technicalNotes = "Documento de papel soporte 80g con grapado perimetral y sellos de cera desintegrados.",
            drawableResName = "hero_intake_forensic_1791219485484"
        ),
        EvidenceEntity(
            id = "VLC-E027-C",
            originDossierCode = "VLC-027",
            type = "MANUSCRITO / ACTA",
            dateRecovery = "23/11/2011",
            status = "VERIFICACIÓN PENDIENTE",
            description = "Página 03 mutilada del informe pericial de fuego en El Puig con la anotación manuscrita: 'Consultar VLC-134'.",
            location = "El Puig de Santa Maria (Valencia)",
            technicalNotes = "Tinta ferrogálica con degradación térmica moderada."
        ),
        EvidenceEntity(
            id = "VLC-E311-MAP",
            originDossierCode = "VLC-311",
            type = "REGISTRO TÉCNICO",
            dateRecovery = "15/01/2026",
            status = "AUTENTICADO",
            description = "Registro geodésico y balizamiento de coordenadas en ETRS89. Señala la diferencia entre puntos fijos y puntos de evento cinemático.",
            location = "Conselleria d'Infraestructures (Fondo expuesto)",
            technicalNotes = "Cálculo pericial de radio de convergencia de 4.2 km."
        )
    )

    val locations = listOf(
        LocationEntity(
            id = "LOC-01",
            code = "LOC-311-01",
            name = "El Puig de Santa Maria",
            latitude = 39.5886,
            longitude = -0.3032,
            area = "L'Horta Nord",
            relatedDossier = "VLC-027",
            description = "Ubicación del incidente de combustión a baja temperatura y hallazgo de documentos con la página 4 mutilada.",
            status = "ACTIVO",
            visibilityLevel = "PÚBLICA"
        ),
        LocationEntity(
            id = "LOC-02",
            code = "LOC-311-02",
            name = "Partidas Rurales de Cheste",
            latitude = 39.4947,
            longitude = -0.6825,
            area = "La Hoya de Buñol - Cheste",
            relatedDossier = "VLC-667",
            description = "Sector de estructuras anónimas y asentamientos rústicos bajo investigación pericial activa.",
            status = "EN COTEJO",
            visibilityLevel = "PÚBLICA"
        ),
        LocationEntity(
            id = "LOC-03",
            code = "LOC-311-03",
            name = "Marjal de la Safor",
            latitude = 39.0112,
            longitude = -0.1843,
            area = "La Safor - Sueca Sur",
            relatedDossier = "VLC-134",
            description = "Zona húmeda donde se registró la última emisión del expediente desaparecido VLC-134 antes de su expurgo.",
            status = "RESTRINGIDO",
            visibilityLevel = "PARCIAL"
        ),
        LocationEntity(
            id = "LOC-04",
            code = "LOC-311-04",
            name = "Acequia Mayor // Albufera",
            latitude = 39.3456,
            longitude = -0.3842,
            area = "Ribera Baixa - Albufera",
            relatedDossier = "VLC-041",
            description = "Punto cinemático donde la cámara del vehículo registró la interferencia y el salto temporal de 14 minutos.",
            status = "EVENTO CINEMÁTICO",
            visibilityLevel = "EVENTO_NO_LUGAR"
        ),
        LocationEntity(
            id = "LOC-05",
            code = "LOC-311-05",
            name = "Camino de Servicio Horta Nord",
            latitude = 39.5421,
            longitude = -0.3912,
            area = "L'Horta Nord",
            relatedDossier = "VLC-014",
            description = "Epicentro del nudo de corteza biológica 014-B. Muestras botánicas con alta reactividad lumínica nocturna.",
            status = "ACTIVO",
            visibilityLevel = "PÚBLICA"
        ),
        LocationEntity(
            id = "LOC-06",
            code = "LOC-311-06",
            name = "Litoral de Sueca / Faro de Cullera",
            latitude = 39.1852,
            longitude = -0.2289,
            area = "Litoral Ribera Baixa",
            relatedDossier = "VLC-901",
            description = "Observaciones marítimas de fuentes luminosas no balizadas sobre aguas territoriales a baja cota.",
            status = "NO CONCLUYENTE",
            visibilityLevel = "PARCIAL"
        )
    )

    val chronologies = listOf(
        ChronologyEntity(
            year = 1994,
            dateFormatted = "12/11/1994",
            title = "Primer reporte de anomalía ocular en Sagunt",
            relatedDossier = "VLC-073",
            description = "Acta municipal de peritaje rural archivada en soporte heliográfico. Describe por primera vez la morfología ocular en árboles."
        ),
        ChronologyEntity(
            year = 1998,
            dateFormatted = "04/05/1998",
            title = "Constitución del fondo confidencial territorial",
            relatedDossier = "VLC-000",
            description = "Creación de la codificación alfanumérica VLC para sucesos territoriales sin explicación convencional."
        ),
        ChronologyEntity(
            year = 2008,
            dateFormatted = "14/09/2008",
            title = "Apertura forzada de VLC-014 (Horta Nord)",
            relatedDossier = "VLC-014",
            description = "Se formaliza el expediente Nódulo 014 omitiendo deliberadamente los antecedentes de 1994."
        ),
        ChronologyEntity(
            year = 2011,
            dateFormatted = "22/11/2011",
            title = "Incidente de fuego frío en El Puig",
            relatedDossier = "VLC-027",
            description = "Peritaje de combustión sin daño térmico y sustracción de la página 4 con la identidad de los inspectores."
        ),
        ChronologyEntity(
            year = 2013,
            dateFormatted = "14/02/2013",
            title = "Apagón documental // Desaparición de VLC-108 y 134",
            relatedDossier = "VLC-134",
            description = "La auditoría interna concluye que no existen copias físicas de los expedientes bisagra."
        ),
        ChronologyEntity(
            year = 2015,
            dateFormatted = "03/04/2015",
            title = "Registro audiovisual dañado en la CV-315",
            relatedDossier = "VLC-041",
            description = "Recuperación de la tarjeta SD de la cámara de abordo con salto temporal inexplicable a las 03:14."
        ),
        ChronologyEntity(
            year = 2021,
            dateFormatted = "19/08/2021",
            title = "Emisión de la Directiva de Seguridad 506",
            relatedDossier = "VLC-506",
            description = "Prohibición categórica de cotejar copias no oficiales de VLC-506."
        ),
        ChronologyEntity(
            year = 2026,
            dateFormatted = "28/09/2026",
            title = "Contacto del explorador urbano y entrega física",
            relatedDossier = "VLC-001",
            description = "Localización de la carpeta en la construcción aislada. Pérdida de comunicación con el informante."
        ),
        ChronologyEntity(
            year = 2026,
            dateFormatted = "02/10/2026",
            title = "Exposición parcial del repositorio digital",
            relatedDossier = "VLC-000",
            description = "Brecha pericial no confirmada. El sistema queda accesible con una integridad global del 61.4%."
        ),
        ChronologyEntity(
            year = 2026,
            dateFormatted = "05/10/2026",
            title = "Apertura del Protocolo de Recepción Ciudadana",
            relatedDossier = "VLC-901",
            description = "Habilitación del canal seguro para recepción pericial de grabaciones y pruebas de observadores externos."
        )
    )

    val citizenReports = listOf(
        CitizenReportEntity(
            trackingCode = "REC-VLC-8842",
            timestamp = "05/10/2026 – 02:41",
            municipality = "Sueca (Partida de Marjal)",
            category = "CÁMARA SEGURIDAD / DASHCAM",
            description = "Cámara perimetral de nave agrícola registra a las 03:12 un destello azulado sobre la acequia y el apagado simultáneo de 4 farolas solares.",
            status = "EN CUARENTENA / COTEJO ACTIVO",
            matchedDossier = "VLC-041",
            isUrgent = true,
            verificationNotes = "Vídeo remitido por DM de TikTok. Pendiente de verificación de metadatos EXIF."
        ),
        CitizenReportEntity(
            trackingCode = "REC-VLC-8839",
            timestamp = "04/10/2026 – 21:18",
            municipality = "Paterna (Polígono / Zona Verde)",
            category = "MARCADO PERIMETRAL",
            description = "Localizado un clavo de nivelación topográfica con el grabado 'VLC-14' en la esquina de un transformador antiguo.",
            status = "ASOCIADO A EXPEDIENTE",
            matchedDossier = "VLC-311",
            isUrgent = false,
            verificationNotes = "Fotografía de alta nitidez. Coordenadas agregadas a la cuadrícula pericial."
        ),
        CitizenReportEntity(
            trackingCode = "REC-VLC-8831",
            timestamp = "03/10/2026 – 19:05",
            municipality = "Godelleta (Camino de tierra)",
            category = "ANOMALÍA VISUAL",
            description = "Tronco de algarrobo centenario con hendidura semejante a ojo con lágrima oscura. Los perros del remitente se niegan a pasar cerca.",
            status = "EN CUARENTENA / COTEJO ACTIVO",
            matchedDossier = "VLC-014",
            isUrgent = true,
            verificationNotes = "Remitido por observador privado. Cotejando con el sub-nodo 014-C."
        ),
        CitizenReportEntity(
            trackingCode = "REC-VLC-8815",
            timestamp = "02/10/2026 – 14:30",
            municipality = "El Puig de Santa Maria",
            category = "OBJETO FUERA DE LUGAR",
            description = "Hallada una libreta grapada con notas mecanografiadas dentro de una tubería de desagüe de regadío tradicional.",
            status = "ARCHIVADO PENDIENTE DE VERIFICACIÓN",
            matchedDossier = "VLC-027",
            isUrgent = false,
            verificationNotes = "Papel humedecido. Remisión de escaneos pendiente de entrega."
        )
    )

    val auditLogs = listOf(
        AuditLogEntity(
            timestamp = "05/10/2026 – 09:40",
            actionCode = "SEC-BREACH-09",
            description = "Acceso concurrente no autenticado desde red externa (tráfico detectado desde enlace TikTok).",
            level = "BREACH"
        ),
        AuditLogEntity(
            timestamp = "05/10/2026 – 08:15",
            actionCode = "SYS-SYNC-404",
            description = "Sincronización abortada con servidor nodriza de Conselleria. Integridad detenida en 61.4%.",
            level = "WARNING"
        ),
        AuditLogEntity(
            timestamp = "05/10/2026 – 03:14",
            actionCode = "CITIZEN-REC-NEW",
            description = "Nuevo informe pericial incorporado a buzón de cuarentena: REC-VLC-8842.",
            level = "INFO"
        ),
        AuditLogEntity(
            timestamp = "04/10/2026 – 22:50",
            actionCode = "DOC-CENSOR-AUTO",
            description = "Activado bloqueo de caracteres periciales en el expediente VLC-134 y VLC-506.",
            level = "ALERT"
        ),
        AuditLogEntity(
            timestamp = "04/10/2026 – 16:30",
            actionCode = "MAP-PIN-UPD",
            description = "Actualización de coordenadas geodésicas en sector Cheste (LOC-311-02).",
            level = "INFO"
        )
    )
}
