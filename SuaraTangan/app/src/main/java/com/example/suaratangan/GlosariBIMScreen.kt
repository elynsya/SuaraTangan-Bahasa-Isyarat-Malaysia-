package com.example.suaratangan.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.suaratangan.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.example.suaratangan.ui.theme.GlosariBgColor
import com.example.suaratangan.ui.theme.PinkBorder
import com.example.suaratangan.ui.theme.PinkUtama
import com.example.suaratangan.ui.theme.TeksGelap
import com.example.suaratangan.ui.theme.TeksKelabu

data class WordItem(
    val word: String,
    val description: String = "Pelajari isyarat ini"
)

data class CategoryItem(
    val title: String,
    val iconRes: Int,
    val wordsInside: List<WordItem>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlosariBIMScreen(
    onNavigateToUtama: () -> Unit = {},
    onNavigateToGlosari: () -> Unit = {},
    onNavigateToKuiz: () -> Unit = {},
    onNavigateToTerjemah: () -> Unit = {},
    onNavigateToPenyemak: () -> Unit = {},
    onBack: () -> Unit = {},
    onAbjadClick: () -> Unit = {},
    onTempatArahClick: () -> Unit = {},
    onOrangClick: () -> Unit = {},

    onNavigateToIsyaratDetail: (String) -> Unit = {}
) {
    var currentTab by remember { mutableStateOf("Glosari") }
    var searchText by remember { mutableStateOf("") }

    val categoryList = remember {
        listOf(
            CategoryItem("Abjad dan Nombor", R.drawable.ic_abjad, listOf(WordItem("A"), WordItem("B"), WordItem("C"), WordItem("1"), WordItem("2"))),
            CategoryItem("Tempat dan Arah", R.drawable.ic_tempat, listOf(WordItem("Rumah"), WordItem("Sekolah"), WordItem("Utara"), WordItem("Selatan"))),
            CategoryItem("Orang", R.drawable.ic_keluarga, listOf(WordItem("Ayah"), WordItem("Ibu"), WordItem("Abang"), WordItem("Kakak"))),
            CategoryItem("Aktiviti Asas", R.drawable.ic_aktiviti, listOf(WordItem("Makan"), WordItem("Minum"), WordItem("Tidur"), WordItem("Berjalan"))),
            CategoryItem("Kata Ganti Diri", R.drawable.ic_kataganti, listOf(WordItem("Saya"), WordItem("Awak"), WordItem("Mereka"), WordItem("Dia"))),
            CategoryItem("Frasa Asas", R.drawable.ic_frasa, listOf(WordItem("Selamat Pagi"), WordItem("Terima Kasih"), WordItem("Apa Khabar"))),
            CategoryItem("Emosi & Perasaan", R.drawable.ic_emosi, listOf(WordItem("Gembira"), WordItem("Sedih"), WordItem("Marah"), WordItem("Takut")))
        )
    }

    val isSearching = searchText.isNotBlank()

    val filteredWords = remember(searchText) {
        if (!isSearching) emptyList<Pair<WordItem, String>>()
        else {
            val result = mutableListOf<Pair<WordItem, String>>()
            for (category in categoryList) {
                for (wordItem in category.wordsInside) {
                    if (wordItem.word.contains(searchText, ignoreCase = true)) {
                        result.add(Pair(wordItem, category.title))
                    }
                }
            }
            result
        }
    }

    Scaffold(
        bottomBar = {
            CustomBottomBar(
                selectedTab = currentTab,
                onTabSelected = { tab: String ->
                    currentTab = tab
                    when(tab) {
                        "Utama" -> onNavigateToUtama()
                        "Glosari" -> onNavigateToGlosari()
                        "Kuiz" -> onNavigateToKuiz()
                        "Terjemahan" -> onNavigateToTerjemah()
                        "Ejaan Jari" -> onNavigateToPenyemak()
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(GlosariBgColor)
                .padding(innerPadding)
        ) {

            // ================= TOP HEADER =================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onBack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = TeksGelap
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Image(
                    painter = painterResource(id = R.drawable.suara_tangan_logo),
                    contentDescription = "Logo",
                    modifier = Modifier.height(40.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Glosari BIM",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PinkUtama
                )
            }

            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)

            // ================= SEARCH CARD =================
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.5.dp, PinkBorder)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Glosari Bahasa Isyarat",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = TeksGelap
                    )
                    Text(
                        text = "Cari sebarang isyarat perkataan atau pilih kategori",
                        fontSize = 12.sp,
                        color = TeksKelabu,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = { Text("Cari perkataan (cth: Ayah, Rumah, A)...", color = TeksKelabu, fontSize = 13.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PinkUtama,
                            unfocusedBorderColor = Color.LightGray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        )
                    )
                }
            }

            if (isSearching) {
                // ================= PAPARAN HASIL CARIAN PERKATAAN =================
                Text(
                    text = "Hasil Carian Perkataan (${filteredWords.size})",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TeksGelap,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )

                if (filteredWords.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Perkataan tidak ditemui.", color = TeksKelabu)
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(filteredWords) { (wordItem, categoryTitle) ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        // 💡 Menghantar perkataan yang ditekan ke fungsi navigasi detail
                                        onNavigateToIsyaratDetail(wordItem.word)
                                    },
                                shape = RoundedCornerShape(24.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = BorderStroke(1.dp, Color(0xFFE0E0E0))
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFFFFF3F8)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(wordItem.word.take(1).uppercase(), color = PinkUtama, fontWeight = FontWeight.Bold)
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(text = wordItem.word, fontWeight = FontWeight.Bold, color = TeksGelap, fontSize = 15.sp)
                                    Text(text = "dlm $categoryTitle", fontSize = 10.sp, color = PinkUtama)
                                }
                            }
                        }
                    }
                }
            } else {
                // ================= PAPARAN GRID KATEGORI =================
                Text(
                    text = "Pilih Kategori Glosari",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TeksGelap,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(categoryList) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(0.9f)
                                .clickable {
                                    when (item.title) {
                                        "Abjad dan Nombor" -> onAbjadClick()
                                        "Tempat dan Arah" -> onTempatArahClick()
                                        "Orang" -> onOrangClick()

                                    }
                                },
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.5.dp, PinkBorder)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFFCE8F1))
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = item.iconRes),
                                        contentDescription = item.title,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = item.title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TeksGelap,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                    lineHeight = 16.sp,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}