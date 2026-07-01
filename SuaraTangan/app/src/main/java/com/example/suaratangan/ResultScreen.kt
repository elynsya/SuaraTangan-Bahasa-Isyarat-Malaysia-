package com.example.suaratangan.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
// 💡 Mengimport warna tema rasmi anda dari ui.theme
import com.example.suaratangan.ui.theme.GlosariBgColor
import com.example.suaratangan.ui.theme.PinkBorder
import com.example.suaratangan.ui.theme.PinkUtama
import com.example.suaratangan.ui.theme.TeksGelap
import com.example.suaratangan.ui.theme.TeksKelabu

@Composable
fun ResultScreen(
    score: Int,
    total: Int = 5,
    onRetry: () -> Unit,
    onBack: () -> Unit
) {
    val percent = (score * 100) / total

    // Mesej mengikut prestasi dengan emoji yang bersesuaian
    val (message, emoji) = when {
        percent >= 80 -> Pair("Tahniah! Cemerlang", "🏆")
        percent >= 50 -> Pair("Bagus! Hebat Semangat", "✨")
        else -> Pair("Teruskan Berlatih!", "💪")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GlosariBgColor) // 💡 Menggunakan kelabu lembut tema (GlosariBgColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Susun item di tengah skrin dengan seimbang
    ) {

        // ================= ICON REWARD CONTAINER =================
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
                .border(1.5.dp, PinkBorder, RoundedCornerShape(30.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(emoji, fontSize = 60.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Kuiz Selesai!",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TeksGelap
        )

        Text(
            text = "Berikut adalah keputusan prestasi anda",
            fontSize = 14.sp,
            color = TeksKelabu,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        // ================= MAIN CARD RESULT =================
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.5.dp, PinkBorder, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Paparan Markah Besar (Contoh: 4 / 5)
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "$score",
                        fontSize = 64.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PinkUtama
                    )
                    Text(
                        text = " / $total",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = TeksKelabu,
                        modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
                    )
                }

                // Bar kemajuan peratusan ringkas
                Text(
                    text = "Skor Keseluruhan: $percent%",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TeksGelap
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Mesej Berwarna Mengikut Markah
                Text(
                    text = message,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PinkUtama,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)
                Spacer(modifier = Modifier.height(20.dp))

                // ================= STATS GRID MINI =================
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Kotak Betul
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("✅ Betul", fontSize = 12.sp, color = TeksKelabu)
                        Text(
                            text = "$score",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32) // Hijau Profesional
                        )
                    }

                    // Kotak Salah
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("❌ Salah", fontSize = 12.sp, color = TeksKelabu)
                        Text(
                            text = "${total - score}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFC62828) // Merah Profesional
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // ================= ACTION BUTTONS =================
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Butang Cuba Lagi (Utama - PinkUtama)
            Button(
                onClick = onRetry,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PinkUtama)
            ) {
                Text(
                    text = "Cuba Lagi",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            // Butang Kembali (Secondary - Garisan Pink / Outlined)
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, PinkUtama),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = PinkUtama)
            ) {
                Text(
                    text = "Kembali ke Menu",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = PinkUtama
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    ResultScreen(
        score = 4,
        total = 5,
        onRetry = {},
        onBack = {}
    )
}