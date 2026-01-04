package com.example.yummynutrition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.yummynutrition.navigation.AppNavigation
import com.example.yummynutrition.ui.theme.YummyNutritionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppRoot()
        }
    }
}

@Composable
fun AppRoot() {
    YummyNutritionTheme {
        Surface(modifier = Modifier) {
            val navController = rememberNavController()
            AppNavigation(navController)
        }
    }
}
