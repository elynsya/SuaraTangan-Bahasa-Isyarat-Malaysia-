package com.example.suaratangan.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.suaratangan.R
// 💡 Mengimport warna tema rasmi anda dari ui.theme
import com.example.suaratangan.ui.theme.GlosariBgColor
import com.example.suaratangan.ui.theme.PinkBorder
import com.example.suaratangan.ui.theme.PinkUtama
import com.example.suaratangan.ui.theme.TeksGelap
import com.example.suaratangan.ui.theme.TeksKelabu

@Composable
fun KuizScreen(
    onNavigateToUtama: () -> Unit = {},
    onNavigateToGlosari: () -> Unit = {},
    onNavigateToKuiz: () -> Unit = {},
    onNavigateToTerjemah: () -> Unit = {},
    onNavigateToPenyemak: () -> Unit = {},
    onBack: () -> Unit,
    onStartQuiz: () -> Unit
) {
    var currentTab by remember { mutableStateOf("Kuiz") }

    Scaffold(
        bottomBar = {
            CustomBottomBar(
                selectedTab = currentTab,
                onTabSelected = { tab ->
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
                .background(GlosariBgColor) // 💡 Ditukar ke kelabu lembut tema (GlosariBgColor)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {

            // ================= TOP BAR (Kemaskan gaya teks & warna) =================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White) // Letak background putih pada topbar supaya kemas
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "← Kembali",
                    modifier = Modifier.clickable { onBack() },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TeksGelap
                )

                Image(
                    painter = painterResource(id = R.drawable.suara_tangan_logo),
                    contentDescription = null,
                    modifier = Modifier.height(40.dp) // Dikecilkan sikit (40.dp) biar sama dengan glosari
                )

                Text(
                    text = "Kuiz BIM",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PinkUtama
                )
            }

            HorizontalDivider(thickness = 1.dp, color = Color.LightGray.copy(alpha = 0.5f))

            // ================= KANDUNGAN UTAMA =================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, PinkBorder, RoundedCornerShape(24.dp)) // 💡 Menggunakan PinkBorder
                        .background(Color.White, RoundedCornerShape(24.dp)) // 💡 Kad ditukar ke Putih Bersih
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "🧠",
                        fontSize = 60.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "KUIZ BIM",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TeksGelap
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Uji pengetahuan anda tentang\nBahasa Isyarat Malaysia",
                        fontSize = 13.sp,
                        color = TeksKelabu,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Kotak Info Kuiz (Ditukar kepada variasi pink lembut lembut)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, PinkBorder.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                            .background(Color(0xFFFFF3F8), RoundedCornerShape(16.dp)) // 💡 Latar belakang pink cair comel
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Maklumat Kuiz :",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = TeksGelap
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("✅  5 soalan rawak", color = TeksGelap, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("✅  Aneka pilihan jawapan", color = TeksGelap, fontSize = 13.sp)

                        Spacer(modifier = Modifier.height(20.dp))

                        // 💡 Butang Mula Kuiz ditukar sepenuhnya kepada warna tema PinkUtama + Teks Putih
                        Button(
                            onClick = { onStartQuiz() },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PinkUtama)
                        ) {
                            Text(
                                text = "MULA KUIZ",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KuizScreenPreview() {
    MaterialTheme {
        KuizScreen(
            onBack = {},
            onStartQuiz = {}
        )
    }
}