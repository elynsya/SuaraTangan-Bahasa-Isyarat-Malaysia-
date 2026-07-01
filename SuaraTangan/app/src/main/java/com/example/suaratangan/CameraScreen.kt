package com.example.suaratangan.ui.screen

import android.content.Context
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.suaratangan.ui.theme.*



@Composable
fun CameraScreen(
    onNavigateToUtama: () -> Unit = {},
    onNavigateToGlosari: () -> Unit = {},
    onNavigateToKuiz: () -> Unit = {},
    onNavigateToTerjemah: () -> Unit = {},
    onNavigateToPenyemak: () -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val tfliteHelper = remember { TFLiteHelper(context) }

    var detectedLetter by remember { mutableStateOf("-") }
    var inputList by remember { mutableStateOf(listOf<Float>()) }

    val letters = listOf(
        "A","B","C","D","E","F","G","H","I",
        "K","L","M","N","O","P","Q","R",
        "S","T","U","V","W","X","Y"
    )

    var currentIndex by remember { mutableStateOf(0) }
    val targetLetter = letters[currentIndex]

    // 💡 Ditukar kepada "Ejaan Jari" supaya selaras dengan item CustomBottomBar
    var currentTab by remember { mutableStateOf("Ejaan Jari") }

    val handLandmarker = remember {
        HandLandmarkerHelper(context) { result ->
            if (result.landmarks().isNotEmpty()) {
                val landmarks = result.landmarks()[0]
                val tempList = mutableListOf<Float>()

                for (point in landmarks) {
                    tempList.add(point.x())
                    tempList.add(point.y())
                    tempList.add(point.z())
                }
                inputList = tempList

                val prediction = tfliteHelper.predict(inputList)
                detectedLetter = when (prediction) {
                    0 -> "A"; 1 -> "B"; 2 -> "C"; 3 -> "D"; 4 -> "E"
                    5 -> "F"; 6 -> "G"; 7 -> "H"; 8 -> "I"; 9 -> "K"
                    10 -> "L"; 11 -> "M"; 12 -> "N"; 13 -> "O"; 14 -> "P"
                    15 -> "Q"; 16 -> "R"; 17 -> "S"; 18 -> "T"; 19 -> "U"
                    20 -> "V"; 21 -> "W"; 22 -> "X"; 23 -> "Y"
                    else -> "-"
                }
            } else {
                detectedLetter = "-"
            }
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
                        "Ejaan Jari" -> onNavigateToPenyemak() // 💡 Ditukar ke "Ejaan Jari"
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
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "PENYEMAK EJAAN JARI",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TeksGelap
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Tunjukkan Huruf",
                fontSize = 18.sp,
                color = TeksGelap
            )

            Text(
                text = targetLetter,
                fontSize = 56.sp,
                fontWeight = FontWeight.ExtraBold,
                color = PinkUtama // Ditukar ke PinkUtama supaya lebih menyerlah
            )

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White), // 💡 Diperbetulkan (Guna Color.White)
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = {
                        PreviewView(context).apply {
                            startCamera(
                                previewView = this,
                                context = context,
                                lifecycleOwner = lifecycleOwner,
                                handLandmarker = handLandmarker
                            )
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White) // 💡 Diperbetulkan (Huruf besar 'C' pada Color)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Huruf Dikesan",
                        fontSize = 16.sp,
                        color = TeksKelabu
                    )
                    Text(
                        text = if (detectedLetter == "-") "?" else detectedLetter,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = TeksGelap
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (detectedLetter == targetLetter) "✔ BETUL" else "✘ CUBA LAGI",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = if (detectedLetter == targetLetter) Color(0xFF00875A) else Color.Red
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (detectedLetter == targetLetter && currentIndex < letters.size - 1) {
                        currentIndex++
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PinkUtama), // Diselaraskan tema pink
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("SETERUSNYA", fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (currentIndex == letters.size - 1 && detectedLetter == targetLetter) {
                Text(
                    text = "🎉 Semua Huruf Selesai",
                    fontSize = 18.sp,
                    color = TeksGelap,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

fun startCamera(
    previewView: PreviewView,
    context: Context,
    lifecycleOwner: LifecycleOwner,
    handLandmarker: HandLandmarkerHelper
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    val imageAnalysis = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()

    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build()
        preview.setSurfaceProvider(previewView.surfaceProvider)

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
            try {
                val bitmap = imageProxy.toBitmap()
                if (bitmap != null) {
                    handLandmarker.detect(bitmap, System.currentTimeMillis())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                imageProxy.close()
            }
        }

        val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalysis)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }, ContextCompat.getMainExecutor(context))
}