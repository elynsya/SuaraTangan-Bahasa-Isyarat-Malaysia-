package com.example.suaratangan.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.tooling.preview.Preview
import com.example.suaratangan.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.example.suaratangan.ui.theme.GlosariBgColor
import com.example.suaratangan.ui.theme.PinkBorder
import com.example.suaratangan.ui.theme.PinkUtama
import com.example.suaratangan.ui.theme.TeksGelap
import com.example.suaratangan.ui.theme.TeksKelabu

fun isValidEmail(email: String): Boolean {
    return email.contains("@")
}

fun isValidPassword(password: String): Boolean {
    val hasLetter = password.any { it.isLetter() }
    val hasDigit = password.any { it.isDigit() }
    return hasLetter && hasDigit && password.length >= 6
}

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    var registerError by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

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
                modifier = Modifier.size(110.dp)
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

            Spacer(modifier = Modifier.height(24.dp))

            // ================= KAD INPUT DAFTAR akaun =================
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
                        text = "Daftar Akaun",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TeksGelap
                    )
                    Text(
                        text = "Sila isi maklumat di bawah untuk mula belajar",
                        fontSize = 13.sp,
                        color = TeksKelabu
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // INPUT NAMA PENULIS
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nama Penuh") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Ikon Nama",
                                tint = PinkUtama
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        // 💡 KEMASKINI DI SINI: Ditambah focusedTextColor & unfocusedTextColor supaya teks hitam pekat/jelas
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TeksGelap,
                            unfocusedTextColor = TeksGelap,
                            focusedBorderColor = PinkUtama,
                            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.7f),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // INPUT EMEL
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = if (!isValidEmail(it) && it.isNotEmpty()) "Emel tidak sah" else ""
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
                        // 💡 KEMASKINI DI SINI: Ditambah focusedTextColor & unfocusedTextColor
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TeksGelap,
                            unfocusedTextColor = TeksGelap,
                            focusedBorderColor = PinkUtama,
                            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.7f),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
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

                    Spacer(modifier = Modifier.height(14.dp))

                    // INPUT KATA LALUAN
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = if (!isValidPassword(it) && it.isNotEmpty())
                                "Min 6 aksara, mesti gabungan huruf & nombor"
                            else ""
                        },
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
                        // 💡 KEMASKINI DI SINI: Ditambah focusedTextColor & unfocusedTextColor
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TeksGelap,
                            unfocusedTextColor = TeksGelap,
                            focusedBorderColor = PinkUtama,
                            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.7f),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
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

                    Spacer(modifier = Modifier.height(14.dp))

                    // INPUT SAHKAN KATA LALUAN
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            confirmPasswordError = if (it != password && it.isNotEmpty()) "Kata laluan tidak sama" else ""
                        },
                        label = { Text("Sahkan Kata Laluan") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Ikon Sahkan Kata Laluan",
                                tint = PinkUtama
                            )
                        },
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                Icon(
                                    imageVector = if (confirmPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = if (confirmPasswordVisible) "Sembunyikan Kata Laluan" else "Papar Kata Laluan",
                                    tint = TeksKelabu
                                )
                            }
                        },
                        isError = confirmPasswordError.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                       
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TeksGelap,
                            unfocusedTextColor = TeksGelap,
                            focusedBorderColor = PinkUtama,
                            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.7f),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        singleLine = true
                    )

                    if (confirmPasswordError.isNotEmpty()) {
                        Text(
                            text = confirmPasswordError,
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 6.dp, top = 4.dp)
                        )
                    }

                    if (registerError.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = registerError,
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            registerError = ""
                            when {
                                name.isEmpty() || email.isEmpty() || password.isEmpty() -> {
                                    registerError = "Sila isi semua maklumat"
                                    return@Button
                                }
                                !isValidEmail(email) -> {
                                    emailError = "Emel tidak sah"
                                    return@Button
                                }
                                !isValidPassword(password) -> {
                                    passwordError = "Kata laluan mesti gabungan huruf & nombor"
                                    return@Button
                                }
                                password != confirmPassword -> {
                                    confirmPasswordError = "Kata laluan tidak sama"
                                    return@Button
                                }
                            }
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val user = auth.currentUser
                                        val profileUpdates = userProfileChangeRequest {
                                            displayName = name
                                        }
                                        user?.updateProfile(profileUpdates)?.addOnCompleteListener { profileTask ->
                                            if (profileTask.isSuccessful) {
                                                println("Firebase Register berjaya dan Nama disimpan: ${user.displayName}")
                                            }
                                            onRegisterSuccess()
                                        }
                                    } else {
                                        registerError = task.exception?.message ?: "Pendaftaran gagal. Sila cuba lagi."
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
                            text = "Daftar Akaun",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // BAHAGIAN NAVIGASI KEMBALI KE LOGIN
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Sudah ada akaun? ",
                            fontSize = 13.sp,
                            color = TeksKelabu
                        )
                        Text(
                            text = "Log masuk di sini",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = PinkUtama,
                            modifier = Modifier.clickable {
                                onLoginClick()
                            }
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        onRegisterSuccess = {},
        onLoginClick = {}
    )
}
