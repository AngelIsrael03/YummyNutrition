package com.example.yummynutrition.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yummynutrition.R
import com.example.yummynutrition.ui.theme.md_theme_dark_background
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onFinish: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1800)
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(md_theme_dark_background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.yummi),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.85f)   // 🔥 OCUPA CASI TODA LA PANTALLA
                .aspectRatio(1f)       // mantiene proporción tipo logo
        )
    }
}
