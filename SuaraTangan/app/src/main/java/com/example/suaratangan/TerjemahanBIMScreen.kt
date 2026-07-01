package com.example.suaratangan.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.suaratangan.data.translateSentence
import com.example.suaratangan.model.TranslationItem
import kotlinx.coroutines.launch
import com.example.suaratangan.ui.theme.GlosariBgColor
import com.example.suaratangan.ui.theme.PinkBorder
import com.example.suaratangan.ui.theme.PinkUtama
import com.example.suaratangan.ui.theme.TeksGelap
import com.example.suaratangan.ui.theme.TeksKelabu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerjemahanBIMScreen(
    onNavigateToUtama: () -> Unit = {},
    onNavigateToGlosari: () -> Unit = {},
    onNavigateToKuiz: () -> Unit = {},
    onNavigateToTerjemah: () -> Unit = {},
    onNavigateToPenyemak: () -> Unit = {},
    onBack: () -> Unit
) {
    var currentTab by remember { mutableStateOf("Terjemah") }
    var inputText by remember { mutableStateOf("") }
    var translationResult by remember { mutableStateOf<List<TranslationItem>>(emptyList()) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = "Terjemahan BIM",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = TeksGelap,
                                fontSize = 20.sp
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Kembali",
                                tint = TeksGelap
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.4f), thickness = 1.dp)
            }
        },
        bottomBar = {
            CustomBottomBar(
                selectedTab = currentTab,
                onTabSelected = { tab ->
                    currentTab = tab
                    when (tab) {
                        "Utama" -> onNavigateToUtama()
                        "Glosari" -> onNavigateToGlosari()
                        "Kuiz" -> onNavigateToKuiz()
                        "Terjemah" -> onNavigateToTerjemah()
                        "Ejaan Jari" -> onNavigateToPenyemak()
                    }
                }
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(GlosariBgColor)
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // ================= BAHAGIAN INPUT AYAT =================
            item {
                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.3f))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Teks Terjemahan",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TeksGelap,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        OutlinedTextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp),
                            placeholder = {
                                Text(
                                    text = "Sila taip ayat atau perkataan di sini...",
                                    color = TeksKelabu,
                                    fontSize = 14.sp
                                )
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PinkUtama,
                                unfocusedBorderColor = Color.LightGray.copy(alpha = 0.7f),
                                focusedContainerColor = GlosariBgColor.copy(alpha = 0.3f),
                                unfocusedContainerColor = GlosariBgColor.copy(alpha = 0.3f)
                            ),
                            maxLines = 3
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (inputText.isNotBlank()) {
                                    scope.launch {
                                        // 💡 CARA SELESAIKAN MASALAH 2: Kosongkan hasil lama terlebih dahulu
                                        translationResult = emptyList()

                                        // Ambil hasil terjemahan yang baharu
                                        translationResult = translateSentence(inputText)
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PinkUtama,
                                disabledContainerColor = Color.LightGray
                            ),
                            shape = RoundedCornerShape(14.dp),
                            enabled = inputText.isNotBlank()
                        ) {
                            Text(
                                text = "Terjemah Sekarang",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            // ================= TAJUK HASIL TERJEMAHAN =================
            if (translationResult.isNotEmpty()) {
                item {
                    Column {
                        Text(
                            text = "Hasil Terjemahan Isyarat",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = TeksGelap,
                                fontSize = 16.sp
                            ),
                            modifier = Modifier.padding(start = 4.dp)
                        )
                        Text(
                            text = "Berikut adalah pecahan kod isyarat mengikut perkataan",
                            fontSize = 12.sp,
                            color = TeksKelabu,
                            modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                        )
                    }
                }
            }

            // ================= SENARAI HASIL TERJEMAHAN =================
            // 💡 CARA SELESAIKAN MASALAH 1 & 2: Tambah `key` berasaskan kombinasi teks dan url supaya Compose tahu item berubah
            items(
                items = translationResult,
                key = { item -> "${item.text}_${item.videoUrl ?: item.imageUrl ?: ""}" }
            ) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    border = BorderStroke(1.5.dp, PinkBorder.copy(alpha = 0.6f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        if (item.imageUrl != null || item.videoUrl != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(GlosariBgColor.copy(alpha = 0.4f)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (item.imageUrl != null) {
                                    AsyncImage(
                                        model = item.imageUrl,
                                        contentDescription = item.text,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                else if (item.videoUrl != null) {
                                    // 💡 Guna key() di sini supaya Player Video dipaksa reset sepenuhnya apabila url bertukar
                                    key(item.videoUrl) {
                                        VideoPlayer(
                                            videoUrl = item.videoUrl,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                        }

                        HorizontalDivider(
                            color = GlosariBgColor,
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = item.text.uppercase(),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = PinkUtama,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}