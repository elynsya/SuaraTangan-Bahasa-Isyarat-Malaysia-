package com.example.suaratangan.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import com.example.suaratangan.R
import com.google.firebase.auth.FirebaseAuth
import com.example.suaratangan.ui.theme.GlosariBgColor
import com.example.suaratangan.ui.theme.PinkBorder
import com.example.suaratangan.ui.theme.PinkUtama
import com.example.suaratangan.ui.theme.TeksGelap
import com.example.suaratangan.ui.theme.TeksKelabu

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf("") }

    val auth = FirebaseAuth.getInstance()
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GlosariBgColor)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ================= BRANDING LOGO SECTION =================
            Image(
                painter = painterResource(id = R.drawable.suara_tangan_logo),
                contentDescription = "Logo Suara Tangan",
                modifier = Modifier.size(130.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "SUARA TANGAN",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TeksGelap,
                letterSpacing = 1.sp
            )

            Text(
                text = "Aplikasi Pembelajaran Bahasa Isyarat Malaysia",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TeksKelabu,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ================= KAD INPUT LOG MASUK =================
            Card(
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                border = BorderStroke(1.5.dp, PinkBorder.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Selamat Datang",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TeksGelap
                    )
                    Text(
                        text = "Log masuk ke akaun anda untuk terus belajar",
                        fontSize = 13.sp,
                        color = TeksKelabu
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // INPUT EMEL
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = if (!it.contains("@") && it.isNotEmpty()) "Format emel tidak sah" else ""
                        },
                        label = { Text("Emel") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Ikon Emel",
                                tint = PinkUtama
                            )
                        },
                        isError = emailError.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        // 💡 KEMASKINI: Menggunakan Color(0xFF212121) (Hitam Pekat Semulajadi) terus untuk text color
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color(0xFF212121),
                            unfocusedTextColor = Color(0xFF212121),
                            focusedBorderColor = PinkUtama,
                            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.7f),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            errorTextColor = Color.Red
                        ),
                        singleLine = true
                    )

                    if (emailError.isNotEmpty()) {
                        Text(
                            text = emailError,
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 6.dp, top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // INPUT KATA LALUAN
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Kata Laluan") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Ikon Kata Laluan",
                                tint = PinkUtama
                            )
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = if (passwordVisible) "Sembunyikan Kata Laluan" else "Papar Kata Laluan",
                                    tint = TeksKelabu
                                )
                            }
                        },
                        isError = passwordError.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        // 💡 KEMASKINI: Menggunakan Color(0xFF212121) terus untuk text color
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color(0xFF212121),
                            unfocusedTextColor = Color(0xFF212121),
                            focusedBorderColor = PinkUtama,
                            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.7f),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            errorTextColor = Color.Red
                        ),
                        singleLine = true
                    )

                    if (passwordError.isNotEmpty()) {
                        Text(
                            text = passwordError,
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 6.dp, top = 4.dp)
                        )
                    }

                    if (loginError.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = loginError,
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // BUTANG LOG MASUK
                    Button(
                        onClick = {
                            if (email.isEmpty() || password.isEmpty()) {
                                loginError = "Sila isi semua maklumat"
                                return@Button
                            }

                            auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        loginError = ""
                                        onLoginSuccess()
                                    } else {
                                        loginError = "Emel atau kata laluan salah. Sila daftar jika belum ada akaun."
                                    }
                                }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PinkUtama),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = "Log Masuk",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // BUTANG DAFTAR
                    OutlinedButton(
                        onClick = { onNavigateToRegister() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.5.dp, PinkUtama),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = PinkUtama)
                    ) {
                        Text(
                            text = "Daftar Akaun Baru",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = PinkUtama
                        )
                    }
                }
            }
        }
    }
}