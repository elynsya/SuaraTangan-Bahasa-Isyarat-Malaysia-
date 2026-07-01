package com.example.suaratangan.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.suaratangan.R
import com.example.suaratangan.data.getGlosari
import com.example.suaratangan.model.Glosari
import com.example.suaratangan.ui.theme.GlosariBgColor
import com.example.suaratangan.ui.theme.PinkBorder
import com.example.suaratangan.ui.theme.PinkUtama
import com.example.suaratangan.ui.theme.TeksGelap
import com.example.suaratangan.ui.theme.TeksKelabu

@Composable
fun AbjadScreen(
    wordToShow: String = "",
    onBackToHome: () -> Unit
) {
    var data by remember { mutableStateOf(listOf<Glosari>()) }
    var error by remember { mutableStateOf<String?>(null) }
    var selectedItem by remember { mutableStateOf<Glosari?>(null) }

    LaunchedEffect(Unit) {
        try {
            val list = getGlosari()
                .filter { it.category == "Abjad dan Nombor" }
                .sortedBy { it.word }
            data = list

            // 💡 Logik Auto-Buka: Jika skrin dibuka membawa data perkataan hasil carian (Cth: "A")
            if (wordToShow.isNotBlank()) {
                val found = list.find { it.word.equals(wordToShow, ignoreCase = true) }
                if (found != null) {
                    selectedItem = found
                }
            }
        } catch (e: Exception) {
            error = e.message
        }
    }

    // =======================
    // ERROR STATE
    // =======================
    if (error != null) {
        Box(
            modifier = Modifier.fillMaxSize().background(GlosariBgColor),
            contentAlignment = Alignment.Center
        ) {
            Text("ERROR: $error", color = Color.Red, fontWeight = FontWeight.Bold)
        }
        return
    }

    // =======================
    // LOADING STATE
    // =======================
    if (data.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize().background(GlosariBgColor),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = PinkUtama)
        }
        return
    }

    // =======================
    // DETAIL PAGE STATE
    // =======================
    if (selectedItem != null) {
        BackHandler {
            selectedItem = null
        }

        AbjadDetailScreen(
            item = selectedItem!!,
            onBack = {
                selectedItem = null
            }
        )
        return
    }

    // =======================
    // LIST PAGE (Grid Paparan Utama)
    // =======================
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GlosariBgColor) // Menggunakan kelabu lembut/putih tema
    ) {
        // TOP HEADER BAR (Putih Bersih)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackToHome) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = TeksGelap
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Abjad Bahasa Isyarat",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TeksGelap
            )
        }

        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)

        Spacer(modifier = Modifier.height(16.dp))

        // GRID TEMA PINK PUTIH COMEL
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 30.dp)
        ) {
            items(data) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedItem = item },
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.5.dp, PinkBorder.copy(alpha = 0.7f)) // Garisan luar lembut pink
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = item.image_url,
                            contentDescription = item.word,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(20.dp)),
                            contentScale = ContentScale.Crop,
                            // 💡 Tambah fallback, placeholder dan error handling
                            placeholder = painterResource(id = R.drawable.ic_abjad), // Tukar kepada icon sedia ada anda
                            error = painterResource(id = android.R.drawable.stat_notify_error), // Papar ikon error jika gagal
                            fallback = painterResource(id = android.R.drawable.stat_notify_error)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = item.word,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = TeksGelap
                        )
                    }
                }
            }
        }
    }
}

// ===================================
// PAPARAN DETAIL (AbjadDetailScreen)
// ===================================
@Composable
fun AbjadDetailScreen(
    item: Glosari,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GlosariBgColor)
    ) {
        // TOP HEADER BAR DETAIL
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = TeksGelap
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Isyarat Huruf",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TeksGelap
            )
        }

        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)

        Spacer(modifier = Modifier.height(24.dp))

        // HURUF BESAR INDIKATOR (Menggunakan PinkUtama)
        Text(
            text = item.word,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 80.sp,
            fontWeight = FontWeight.ExtraBold,
            color = PinkUtama
        )

        Spacer(modifier = Modifier.height(16.dp))

        // BINGKAI GRAFIK GAMBAR BESAR (Putih + Sempadan Pink)
        Card(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            border = BorderStroke(1.5.dp, PinkBorder),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = item.image_url,
                    contentDescription = item.word,
                    modifier = Modifier.size(280.dp),
                    contentScale = ContentScale.Fit,
                    // 💡 Tambah pengendalian ralat di sini juga
                    placeholder = painterResource(id = R.drawable.ic_abjad),
                    error = painterResource(id = android.R.drawable.stat_notify_error),
                    fallback = painterResource(id = android.R.drawable.stat_notify_error)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Penerangan tambahan bawah gambar
        Text(
            text = "Ejaan jari bagi huruf di atas",
            fontSize = 14.sp,
            color = TeksKelabu,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

// ===================================
// PRATONTON (Preview) TEMA BARU
// ===================================
@Preview(showBackground = true)
@Composable
fun PreviewAbjadScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GlosariBgColor)
            .padding(20.dp)
    ) {
        Text(
            text = "A-Z Preview Tema",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TeksGelap
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listOf("A", "B", "C", "D")) { letter ->
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.5.dp, PinkBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.LightGray.copy(alpha = 0.3f))
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = letter,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = TeksGelap
                        )
                    }
                }
            }
        }
    }
}