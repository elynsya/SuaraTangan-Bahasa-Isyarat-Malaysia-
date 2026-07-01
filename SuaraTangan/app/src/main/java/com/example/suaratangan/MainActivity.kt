package com.example.suaratangan

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.suaratangan.ui.screen.*
import com.example.suaratangan.ui.theme.SuaraTanganTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestCameraPermission(this)

        setContent {
            SuaraTanganTheme {
                // 1. Kawalan skrin menggunakan State manual sedia ada anda
                var currentScreen by remember { mutableStateOf("login") }
                var quizScore by remember { mutableStateOf(0) }

                // 💡 2. Tambah state ini untuk menyimpan perkataan yang dipilih dari hasil carian
                var selectedWordForDetail by remember { mutableStateOf("") }

                when (currentScreen) {
                    "login" -> {
                        LoginScreen(
                            onLoginSuccess = { currentScreen = "home" },
                            onNavigateToRegister = { currentScreen = "register" }
                        )
                    }

                    "register" -> {
                        RegisterScreen(
                            onRegisterSuccess = { currentScreen = "login" },
                            onLoginClick = { currentScreen = "login" }
                        )
                    }

                    "home" -> {
                        val user = FirebaseAuth.getInstance().currentUser
                        val nameFromFirebase = user?.displayName ?: "Pengguna"

                        HomeScreen(
                            userName = nameFromFirebase,
                            onNavigateToGlosari = { currentScreen = "glosari" },
                            onNavigateToKuiz = { currentScreen = "kuiz" },
                            onNavigateToPenyemak = { currentScreen = "camera" },
                            onNavigateToTerjemahan = { currentScreen = "terjemahan" }
                        )
                    }

                    "glosari" -> {
                        GlosariBIMScreen(
                            onNavigateToUtama = { currentScreen = "home" },
                            onNavigateToGlosari = { currentScreen = "glosari" },
                            onNavigateToKuiz = { currentScreen = "kuiz" },
                            onNavigateToTerjemah = { currentScreen = "terjemahan" },
                            onNavigateToPenyemak = { currentScreen = "camera" },
                            onBack = { currentScreen = "home" },
                            onAbjadClick = { currentScreen = "abjad" },
                            onTempatArahClick = { currentScreen = "tempat" },
                            onOrangClick = {currentScreen = "orang"},
                            onNavigateToIsyaratDetail = { namaPerkataan ->
                                // 💡 Simpan nama perkataan dan tukar skrin ke paparan detail (AbjadScreen)
                                selectedWordForDetail = namaPerkataan
                                currentScreen = "abjad"
                            }
                        )
                    }

                    "kuiz" -> {
                        KuizScreen(
                            onNavigateToUtama = { currentScreen = "home" },
                            onNavigateToGlosari = { currentScreen = "glosari" },
                            onNavigateToKuiz = { currentScreen = "kuiz" },
                            onNavigateToTerjemah = { currentScreen = "terjemahan" },
                            onNavigateToPenyemak = { currentScreen = "camera" },
                            onBack = { currentScreen = "home" },
                            onStartQuiz = { currentScreen = "quiz_questions" }
                        )
                    }

                    "quiz_questions" -> {
                        SoalanKuizScreen(
                            onBack = { currentScreen = "kuiz" },
                            onFinish = { score ->
                                quizScore = score
                                currentScreen = "result"
                            }
                        )
                    }

                    "result" -> {
                        ResultScreen(
                            score = quizScore,
                            total = 5,
                            onRetry = { currentScreen = "quiz_questions" },
                            onBack = { currentScreen = "home" }
                        )
                    }

                    "abjad" -> {
                        // 💡 Hantar parameter data perkataan yang disimpan tadi ke dalam AbjadScreen
                        AbjadScreen(
                            // Sila pastikan AbjadScreen anda menerima parameter String ini (pilihan jika perlu dirombak)
                            // Jika belum ada, anda boleh gunakan state global ini di dalam AbjadScreen kemudian.
                            onBackToHome = {
                                selectedWordForDetail = "" // Reset semula perkataan apabila keluar
                                currentScreen = "glosari"
                            }
                        )
                    }

                    "tempat" -> {
                        TempatScreen(
                            onBackToHome = { currentScreen = "glosari" }
                        )
                    }
                    "orang"->{
                        OrangScreen(
                            onBackToHome = {currentScreen ="glosari"}
                        )
                    }

                    "terjemahan" -> {
                        TerjemahanBIMScreen(
                            onNavigateToUtama = { currentScreen = "home" },
                            onNavigateToGlosari = { currentScreen = "glosari" },
                            onNavigateToKuiz = { currentScreen = "kuiz" },
                            onNavigateToTerjemah = { currentScreen = "terjemahan" },
                            onNavigateToPenyemak = { currentScreen = "camera" },
                            onBack = { currentScreen = "home" },
                        )
                    }

                    "camera" -> {
                        CameraScreen(
                            onNavigateToUtama = { currentScreen = "home" },
                            onNavigateToGlosari = { currentScreen = "glosari" },
                            onNavigateToKuiz = { currentScreen = "kuiz" },
                            onNavigateToTerjemah = { currentScreen = "terjemahan" },
                            onNavigateToPenyemak = { currentScreen = "camera" }
                        )
                    }

                    else -> {
                        Text("Screen not found")
                    }
                }
            }
        }
    }
}

fun requestCameraPermission(activity: ComponentActivity) {
    if (ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.CAMERA),
            100
        )
    }
}