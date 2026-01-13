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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {
    val showContent = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showContent.value = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = showContent.value,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn()
        ) {
            Text(
                text = "Bienvenido a",
                fontSize = 24.sp,
                fontWeight = FontWeight.Light,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(
            visible = showContent.value,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn()
        ) {
            Text(
                text = "YUMMI NUTRITION",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        AnimatedVisibility(
            visible = showContent.value,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn()
        ) {
            Button(
                onClick = onStartClick,
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                )
            ) {
                Text(
                    text = "Comenzar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}