package com.example.yummynutrition.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yummynutrition.data.prefs.UserPrefs
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.yummynutrition.R
import kotlinx.coroutines.launch

@Composable
fun NameScreen(
    onNameSaved: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var nameInput by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // 🎨 Colores basados en la imagen
    val greenYummy = Color(0xFF34C759)  // Verde del logo
    val redNutrition = Color(0xFFFF3B30) // Rojo del texto
    val lightGreen = Color(0xFF52D869)
    val darkGreen = Color(0xFF2D8A47)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🥦 LOGO IMAGEN MÁS GRANDE - ARRIBA
            Box(
                modifier = Modifier
                    .size(400.dp)
                    .padding(10.dp)
                    .offset(y = (-15).dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.yummi),
                    contentDescription = "Yummy Nutrition Logo",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            // TARJETA CON PREGUNTA
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¿Cómo te llamas?",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        ),
                        color = Color(0xFF2D3748),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Personaliza tu experiencia saludable",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF718096),
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // CAMPO DE ENTRADA CON DISEÑO COLORIDO
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        label = { Text("Tu nombre") },
                        placeholder = { Text("Escribe aquí...") },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = greenYummy,
                            focusedLabelColor = greenYummy,
                            cursorColor = greenYummy,
                            unfocusedBorderColor = Color(0xFFCBD5E0),
                            focusedContainerColor = Color(0xFFF0FFF4),
                            unfocusedContainerColor = Color(0xFFFAFAFA)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp
                        )
                    )
                }
            }

            // BOTÓN CONTINUAR CON GRADIENTE
            Button(
                onClick = {
                    val clean = nameInput.trim()
                    if (clean.isNotBlank()) {
                        isLoading = true
                        scope.launch {
                            UserPrefs.setName(context, clean)
                            onNameSaved()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                        spotColor = greenYummy.copy(alpha = 0.4f)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(16.dp),
                enabled = nameInput.trim().isNotBlank() && !isLoading,
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    greenYummy,
                                    lightGreen
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(28.dp),
                            strokeWidth = 3.dp
                        )
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                "Continuar",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                ),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}