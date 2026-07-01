package com.example.suaratangan.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.suaratangan.model.Question
import com.example.suaratangan.repository.QuizRepository
import android.net.Uri
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.draw.clip
// 💡 Mengimport warna tema rasmi anda dari ui.theme
import com.example.suaratangan.ui.theme.GlosariBgColor
import com.example.suaratangan.ui.theme.PinkBorder
import com.example.suaratangan.ui.theme.PinkUtama
import com.example.suaratangan.ui.theme.TeksGelap
import com.example.suaratangan.ui.theme.TeksKelabu

@Composable
fun SoalanKuizScreen(
    onBack: () -> Unit,
    onFinish: (Int) -> Unit
) {

    val repository = remember {
        QuizRepository()
    }

    var quizQuestions by remember {
        mutableStateOf<List<Question>>(emptyList())
    }

    var currentIndex by remember {
        mutableStateOf(0)
    }

    var selectedAnswer by remember {
        mutableStateOf("")
    }

    var score by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(Unit) {
        try {
            val questions = repository.getQuizQuestions()
            quizQuestions = questions.shuffled().take(5)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // =======================
    // LOADING STATE
    // =======================
    if (quizQuestions.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(GlosariBgColor),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = PinkUtama)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Soalan sedang dimuatkan...",
                    color = TeksKelabu,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        return
    }

    val currentQuestion = quizQuestions[currentIndex]

    val options = remember(currentQuestion) {
        listOf(
            currentQuestion.option1 ?: "",
            currentQuestion.option2 ?: "",
            currentQuestion.option3 ?: "",
            currentQuestion.option4 ?: ""
        ).shuffled()
    }

    val progress = (currentIndex + 1) / quizQuestions.size.toFloat()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GlosariBgColor) // 💡 Ditukar ke kelabu lembut tema (GlosariBgColor)
            .padding(16.dp)
    ) {

        // ================= TOP PROGRESS SECTION =================
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.5.dp, PinkBorder, RoundedCornerShape(24.dp))
                .background(Color.White, RoundedCornerShape(24.dp))
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Soalan ${currentIndex + 1} daripada ${quizQuestions.size}",
                    fontWeight = FontWeight.Bold,
                    color = TeksGelap,
                    fontSize = 15.sp
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    fontWeight = FontWeight.Bold,
                    color = PinkUtama,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Custom Progress Indicator berwarna PinkUtama
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = PinkUtama,
                trackColor = PinkBorder.copy(alpha = 0.3f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ================= QUESTION & CONTENT SECTION =================
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(1.5.dp, PinkBorder, RoundedCornerShape(24.dp))
                .background(Color.White, RoundedCornerShape(24.dp))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = currentQuestion.question ?: "",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = TeksGelap,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // MEDIA AREA (IMAGE / VIDEO)
            val context = LocalContext.current

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(GlosariBgColor.copy(alpha = 0.5f))
            ) {
                if (currentQuestion.media_type == "image") {
                    AsyncImage(
                        model = currentQuestion.media_url ?: "",
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Fit
                    )
                } else if (currentQuestion.media_type == "video") {
                    val exoPlayer = remember {
                        ExoPlayer.Builder(context).build()
                    }

                    DisposableEffect(currentQuestion.media_url) {
                        exoPlayer.setMediaItem(
                            MediaItem.fromUri(Uri.parse(currentQuestion.media_url ?: ""))
                        )
                        exoPlayer.prepare()
                        exoPlayer.playWhenReady = true
                        onDispose { exoPlayer.stop() }
                    }

                    DisposableEffect(Unit) {
                        onDispose { exoPlayer.release() }
                    }

                    AndroidView(
                        factory = {
                            PlayerView(context).apply {
                                player = exoPlayer
                                useController = true // Membolehkan pengguna main/henti semula video
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // OPTIONS LIST (Ubah warna dinamik pilihan jawapan)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                options.forEach { option ->
                    // 💡 Logik Warna Kad Jawapan: PinkUtama jika dipilih, Putih jika tidak dipilih
                    val cardBgColor = if (selectedAnswer == option) PinkUtama else Color.White
                    val textColor = if (selectedAnswer == option) Color.White else TeksGelap
                    val cardBorderColor = if (selectedAnswer == option) PinkUtama else PinkBorder.copy(alpha = 0.5f)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .border(1.5.dp, cardBorderColor, RoundedCornerShape(16.dp))
                            .background(cardBgColor, RoundedCornerShape(16.dp))
                            .clickable { selectedAnswer = option }
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = option,
                            color = textColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // NEXT / FINISH BUTTON (Menggunakan gaya butang PinkUtama)
            Button(
                onClick = {
                    if (selectedAnswer.isNotEmpty()) {
                        // SEMAK JAWAPAN
                        if (selectedAnswer == (currentQuestion.answer ?: "")) {
                            score++
                        }

                        // JAWAPAN SETERUSNYA ATAU TAMAT
                        if (currentIndex < quizQuestions.size - 1) {
                            currentIndex++
                            selectedAnswer = ""
                        } else {
                            onFinish(score)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PinkUtama,
                    disabledContainerColor = Color.LightGray
                ),
                enabled = selectedAnswer.isNotEmpty() // Butang hanya aktif selepas pengguna memilih jawapan
            ) {
                Text(
                    text = if (currentIndex == quizQuestions.size - 1) "TAMAT KUIZ" else "SOALAN SETERUSNYA",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // KEMBALI ACTION
            Text(
                text = "← Kembali ke Menu",
                modifier = Modifier.clickable { onBack() },
                color = TeksKelabu,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}