package com.example.yummynutrition.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yummynutrition.data.prefs.UserPrefs
import kotlinx.coroutines.launch

@Composable
fun NameScreen(
    onNameSaved: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var nameInput by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🎨 EMOJI/ICONO
        Text(
            text = "🍎",
            fontSize = 80.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // TÍTULO
        Text(
            text = "Bienvenido a YummyNutrition",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            ),
            color = Color(0xFF1C1C1C),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // SUBTÍTULO
        Text(
            text = "¿Cómo te llamas?",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF616161),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // CAMPO DE ENTRADA
        OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Tu nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF4CAF50),
                focusedLabelColor = Color(0xFF4CAF50),
                cursorColor = Color(0xFF4CAF50),
                unfocusedBorderColor = Color(0xFFBDBDBD)
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge
        )

        // BOTÓN CONTINUAR
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
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            ),
            shape = RoundedCornerShape(12.dp),
            enabled = nameInput.trim().isNotBlank() && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    "Continuar",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    color = Color.White
                )
            }
        }

        // TEXTO SECUNDARIO
        Text(
            text = "Esto es solo para personalizar tu experiencia",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF9E9E9E),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}