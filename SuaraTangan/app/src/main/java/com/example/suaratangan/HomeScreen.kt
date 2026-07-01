package com.example.suaratangan.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.suaratangan.R
import com.example.suaratangan.ui.theme.SuaraTanganTheme

// Warna Sedia Ada
val BackgroundColor = Color(0xFFF5F7F7)
val PrimaryPink = Color(0xFFFF4F87)
val SoftPink = Color(0xFFFFD6E5)
val BorderPink = Color(0xFFFF8DB6)
val TextGray = Color(0xFF757575)
val DarkText = Color(0xFF003B4A)
val LightText = Color(0xFF7A8A8F)

// Warna Kad Komuniti
val MintBg = Color(0xFFEAF9F6)
val MintBorder = Color(0xFFBFF1E7)
val MintText = Color(0xFF005B5C)

// Warna Spesifik Ikon Ciri-Ciri
val GlosariIconBg = Color(0xFFEFFBF8)
val KuizIconBg = Color(0xFFFFF9EE)
val PenyemakIconBg = Color(0xFFEEF5FF)
val TerjemahanIconBg = Color(0xFFFFEEF0)
val ButtonBgPink = Color(0xFFFCE8F1)

@Composable
fun HomeScreen(
    userName: String,
    onNavigateToGlosari: () -> Unit = {},
    onNavigateToKuiz: () -> Unit = {},
    onNavigateToPenyemak: () -> Unit = {},
    onNavigateToTerjemahan: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    // State untuk mengawal tab aktif pada Bottom Navigation Bar
    var currentTab by remember { mutableStateOf("Utama") }

    // 1. Menggunakan Scaffold untuk menguruskan ruang Bottom Bar dengan selamat
    Scaffold(
        bottomBar = {
            CustomBottomBar(
                selectedTab = currentTab,
                onTabSelected = { tab ->
                    currentTab = tab
                    // Tambah logik navigasi sebenar di sini jika perlu
                    when(tab) {
                        "Glosari" -> onNavigateToGlosari()
                        "Kuiz" -> onNavigateToKuiz()
                        "Terjemahan" -> onNavigateToTerjemahan()
                        "Ejaan Jari" -> onNavigateToPenyemak()
                    }
                }
            )
        }
    ) { innerPadding -> // innerPadding ini sangat penting!

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(innerPadding) // 2. Memastikan kandungan tidak tenggelam di bawah bar
                .verticalScroll(scrollState)
        ) {

            // Top Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.suara_tangan_logo),
                    contentDescription = "Logo",
                    modifier = Modifier.height(40.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Bahasa Isyarat Malaysia",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Serif
                )
            }

            HorizontalDivider(color = Color.Black, thickness = 1.dp)

            Spacer(modifier = Modifier.height(16.dp))

            // Kad Selamat Datang
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(2.dp, Color(0xFFFF8DB6)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Selamat Datang,",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkText
                        )

                        Text(
                            text = userName,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = PrimaryPink
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Mari belajar Bahasa Isyarat Malaysia \n Bersama-sama!",
                            fontSize = 14.sp,
                            color = TextGray,
                            lineHeight = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Image(
                        painter = painterResource(id = R.drawable.gambar_tangan),
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Kad Bantu Komuniti
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MintBg),
                border = BorderStroke(2.dp, MintBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_bantu_komuniti),
                        contentDescription = "Icon Bantu Komuniti",
                        modifier = Modifier.size(44.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Bantu Komuniti Pekak & Bisu",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MintText
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Pelajari BIM untuk memudahkan komunikasi mesra dan inklusif setiap hari.",
                            fontSize = 12.sp,
                            color = TextGray,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Ciri - Ciri Utama",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Grid Ciri-Ciri Utama
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    FeatureCard(
                        modifier = Modifier.weight(1f),
                        title = "GLOSARI",
                        subtitle = "BAHASA ISYARAT MALAYSIA",
                        iconRes = R.drawable.ic_glosari,
                        iconBgColor = GlosariIconBg,
                        onClick = onNavigateToGlosari
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    FeatureCard(
                        modifier = Modifier.weight(1f),
                        title = "KUIZ",
                        subtitle = "UJI KEFAHAMAN ANDA",
                        iconRes = R.drawable.ic_kuiz,
                        iconBgColor = KuizIconBg,
                        onClick = onNavigateToKuiz
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    FeatureCard(
                        modifier = Modifier.weight(1f),
                        title = "PENYEMAK\nEJAAN JARI",
                        subtitle = "ANIMASI SPELLED",
                        iconRes = R.drawable.ic_penyemak,
                        iconBgColor = PenyemakIconBg,
                        onClick = onNavigateToPenyemak
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    FeatureCard(
                        modifier = Modifier.weight(1f),
                        title = "TERJEMAHAN",
                        subtitle = "SUARA & TEKS KE BIM",
                        iconRes = R.drawable.ic_terjemahan,
                        iconBgColor = TerjemahanIconBg,
                        onClick = onNavigateToTerjemahan
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Log Keluar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 24.dp, end = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                TextButton(
                    onClick = onLogout,
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                ) {
                    Text(
                        text = "Log Keluar",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// ================= KOMPONEN BOTTOM NAVIGATION BAR TERSUAI =================
@Composable
fun CustomBottomBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    val items = listOf(
        NavigationItem("Utama", R.drawable.ic_home),
        NavigationItem("Glosari", R.drawable.ic_book),
        NavigationItem("Kuiz", R.drawable.ic_kuiz_navi),
        NavigationItem("Terjemahan", R.drawable.ic_terjemahan_navi),
        NavigationItem("Ejaan Jari", R.drawable.ic_penyemak_navi)
    )

    Surface(
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp), // Bucu bulat di atas mengikut gambar
        color = Color.White,
        shadowElevation = 16.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = selectedTab == item.title
                val tintColor = if (isSelected) PrimaryPink else LightText

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onTabSelected(item.title) }
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.title,
                        tint = tintColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.title,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = tintColor
                    )
                }
            }
        }
    }
}
data class NavigationItem(val title: String, val iconRes: Int)

@Composable
fun FeatureCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String?,
    iconRes: Int,
    iconBgColor: Color,
    onClick: () -> Unit
) {
    var selected by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .aspectRatio(0.8f) // Ketinggian optimum bagi mengelakkan butang hilang
            .clickable {
                selected = !selected
                onClick()
            },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color(0xFFFFF3F8) else Color.White
        ),
        border = BorderStroke(
            if (selected) 3.dp else 2.dp,
            if (selected) PrimaryPink else BorderPink
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(iconBgColor, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = title,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DarkText,
                    lineHeight = 18.sp
                )

                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = LightText,
                        lineHeight = 12.sp
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(50),
                color = ButtonBgPink,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(
                    text = "Mula Sekarang →",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    color = PrimaryPink,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Home Screen Preview"
)
@Composable
fun HomeScreenPreview() {
    SuaraTanganTheme {
        HomeScreen(
            userName = "Amalin",
            onNavigateToGlosari = {},
            onNavigateToKuiz = {},
            onNavigateToPenyemak = {},
            onNavigateToTerjemahan = {},
            onLogout = {}
        )
    }
}