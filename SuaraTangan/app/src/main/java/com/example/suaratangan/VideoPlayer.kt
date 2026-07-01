package com.example.suaratangan.ui.screen

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun VideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    val exoPlayer = remember {

        ExoPlayer.Builder(context).build().apply {

            setMediaItem(
                MediaItem.fromUri(videoUrl)
            )

            prepare()

            playWhenReady = false
        }
    }

    DisposableEffect(Unit) {

        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = {

            PlayerView(context).apply {
                player = exoPlayer
            }
        }
    )
}