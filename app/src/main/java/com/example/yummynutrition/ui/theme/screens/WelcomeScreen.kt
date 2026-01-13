package com.example.yummynutrition.ui.theme.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 🎨 Colores mejorados para YUMMI - Marca personal
object YummiColors {
    val Primary = Color(0xFF27AE60)         // Verde vibrante y fresco
    val Secondary = Color(0xFFE74C3C)       // Rojo cálido para contraste
    val Tertiary = Color(0xFFF39C12)        // Naranja dorado
    val Accent = Color(0xFF3498DB)          // Azul suave para detalles

    val Background = Color(0xFFF8F9FA)      // Gris muy claro
    val Surface = Color(0xFFFFFFFF)         // Blanco puro
    val DarkText = Color(0xFF1A1A1A)        // Gris casi negro
    val LightText = Color(0xFF5A5A5A)       // Gris medio
    val SubText = Color(0xFF9CA3AF)         // Gris claro
}

@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {
    val showTitle = remember { mutableStateOf(false) }
    val showSubtitle = remember { mutableStateOf(false) }
    val showButton = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showTitle.value = true
    }

    LaunchedEffect(showTitle.value) {
        if (showTitle.value) {
            kotlinx.coroutines.delay(300)
            showSubtitle.value = true
        }
    }

    LaunchedEffect(showSubtitle.value) {
        if (showSubtitle.value) {
            kotlinx.coroutines.delay(300)
            showButton.value = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YummiColors.Background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 🎯 Logotipo o emoji representativo
        AnimatedVisibility(
            visible = showTitle.value,
            enter = slideInVertically(initialOffsetY = { -40.dp.value.toInt() }) + fadeIn()
        ) {
            Text(
                text = "🥦",
                fontSize = 80.sp,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }

        // 🎯 Subtítulo "Bienvenido a"
        AnimatedVisibility(
            visible = showTitle.value,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn()
        ) {

        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🎯 Título Principal "YUMMI NUTRITION"
        AnimatedVisibility(
            visible = showSubtitle.value,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "YUMMI",
                    fontSize = 64.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = YummiColors.Primary,
                    textAlign = TextAlign.Center,
                    letterSpacing = 2.sp
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "N",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = YummiColors.Secondary,
                    )
                    Text(
                        text = "UTRITION",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = YummiColors.Secondary,
                    )
                }
            }
        }

        // 🎯 Descripción/Tagline
        AnimatedVisibility(
            visible = showSubtitle.value,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn()
        ) {
            Text(
                text = "Your path to a healthy life",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = YummiColors.DarkText,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(64.dp))

        // 🎯 Botón "Comenzar" mejorado
        AnimatedVisibility(
            visible = showButton.value,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn()
        ) {
            Button(
                onClick = onStartClick,
                modifier = Modifier
                    .width(200.dp)
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = YummiColors.Primary
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Start",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    letterSpacing = 0.5.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}