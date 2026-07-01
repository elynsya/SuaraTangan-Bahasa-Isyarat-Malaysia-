package com.example.suaratangan.ui.screen

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.suaratangan.data.getGlosari
import com.example.suaratangan.model.Glosari
import com.example.suaratangan.ui.theme.*

@Composable
fun OrangScreen(
    wordToShow: String = "",
    onBackToHome: () -> Unit
) {

    var data by remember { mutableStateOf(listOf<Glosari>()) }
    var error by remember { mutableStateOf<String?>(null) }
    var selectedItem by remember { mutableStateOf<Glosari?>(null) }

    LaunchedEffect(Unit) {

        try {

            val result = getGlosari()

            val filtered =
                result.filter {
                    it.category.trim()
                        .equals(
                            "Orang",
                            ignoreCase = true
                        )
                }
                    .sortedBy { it.word }

            data = filtered

            if (wordToShow.isNotBlank()) {
                selectedItem =
                    filtered.find {
                        it.word.equals(
                            wordToShow,
                            ignoreCase = true
                        )
                    }
            }

        } catch (e: Exception) {
            error = e.message
        }

    }

    if (error != null) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "ERROR: $error",
                color = Color.Red
            )
        }
        return
    }

    if (selectedItem != null) {

        BackHandler {
            selectedItem = null
        }

        OrangDetailScreen(
            item = selectedItem!!,
            onBack = {
                selectedItem = null
            }
        )

        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F4F6))    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF8F8F8))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackToHome
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    null
                )
            }

            Spacer(
                Modifier.width(8.dp)
            )

            Text(
                text = "Orang",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TeksGelap
            )

        }

        Spacer(
            Modifier.height(12.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(
                horizontal = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            items(data) { item ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedItem = item
                        },
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                            ),
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        if (!item.image_url.isNullOrEmpty()) {

                            AsyncImage(
                                model = item.image_url,
                                contentDescription = item.word,
                                modifier = Modifier
                                    .size(110.dp)
                                    .clip(
                                        RoundedCornerShape(18.dp)
                                    ),
                                contentScale = ContentScale.Crop
                            )

                        }

                        Spacer(
                            Modifier.height(10.dp)
                        )

                        Text(
                            text = item.word,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                    }

                }

            }

        }

    }

}


@Composable
fun OrangDetailScreen(
    item: Glosari,
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F4F6))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF8F8F8))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBack
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    null
                )
            }

            Text(
                text = "Orang",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

        }

        Spacer(
            Modifier.height(18.dp)
        )

        Text(
            text = item.word,
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            ),
            fontSize = 54.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )

        Spacer(
            Modifier.height(16.dp)
        )

        if (!item.image_url.isNullOrEmpty()) {

            AsyncImage(
                model = item.image_url,
                contentDescription = item.word,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .padding(20.dp),
                contentScale = ContentScale.Fit
            )

        }

        val video = item.video_url

        if (!video.isNullOrEmpty()) {

            Spacer(
                Modifier.height(12.dp)
            )

            Text(
                text = "Video Isyarat",
                modifier = Modifier.padding(
                    start = 20.dp
                ),
                fontWeight = FontWeight.Bold,
                color = TeksGelap
            )

            Spacer(
                Modifier.height(8.dp)
            )

            VideoPlayer(
                url = video
            )

        }

        else {

            Text(
                text = "Tiada video tersedia",
                modifier = Modifier.padding(20.dp),
                color = TeksKelabu
            )

        }

    }

}