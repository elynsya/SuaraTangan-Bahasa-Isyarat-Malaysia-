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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun TempatScreen(
    onBackToHome: () -> Unit
) {

    var data by remember { mutableStateOf(listOf<Glosari>()) }
    var error by remember { mutableStateOf<String?>(null) }
    var selectedItem by remember { mutableStateOf<Glosari?>(null) }

    // ================= LOAD DATA =================
    LaunchedEffect(Unit) {
        try {
            val result = getGlosari()

            data = result.filter {
                it.category.trim().equals("Tempat dan Arah", ignoreCase = true)
            }.sortedBy { it.word }

        } catch (e: Exception) {
            error = e.message
        }
    }

    // ================= ERROR =================
    if (error != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("ERROR: $error")
        }
        return
    }

    // ================= DETAIL SCREEN =================
    if (selectedItem != null) {

        BackHandler {
            selectedItem = null
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFD7F2F8))
        ) {

            // TOP BAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFC6EDF5))
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = { selectedItem = null }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "Tempat & Arah",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = selectedItem?.word ?: "",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 60.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ================= IMAGE (SAFE) =================
            val image = selectedItem?.image_url

            if (!image.isNullOrEmpty()) {
                AsyncImage(
                    model = image,
                    contentDescription = selectedItem?.word,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .height(250.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // ================= VIDEO (CENTERED + SAFE) =================
            val videoUrl = selectedItem?.video_url

            if (!videoUrl.isNullOrEmpty()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    VideoPlayer(url = videoUrl)
                }

            } else {
                Text(
                    text = "Tiada video untuk item ini",
                    modifier = Modifier.padding(16.dp),
                    color = Color.Gray
                )
            }
        }

        return
    }

    // ================= LIST SCREEN =================
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD7F2F8))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFC6EDF5))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onBackToHome) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Tempat & Arah",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 30.dp)
        ) {

            items(data) { item ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedItem = item },
                    shape = RoundedCornerShape(24.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        val img = item.image_url

                        if (!img.isNullOrEmpty()) {
                            AsyncImage(
                                model = img,
                                contentDescription = item.word,
                                modifier = Modifier
                                    .size(110.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = item.word,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun VideoPlayer(url: String) {

    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            prepare()
            playWhenReady = false
        }
    }

    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }

    AndroidView(
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}