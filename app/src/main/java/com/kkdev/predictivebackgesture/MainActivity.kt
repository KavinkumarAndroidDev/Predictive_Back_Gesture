package com.kkdev.predictivebackgesture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kkdev.predictivebackgesture.screens.AddScreen
import com.kkdev.predictivebackgesture.screens.HomeScreen
import com.kkdev.predictivebackgesture.ui.theme.AppTheme
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreen

@Serializable
data object AddScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        startDestination = com.kkdev.predictivebackgesture.HomeScreen
                    ) {
                        composable<com.kkdev.predictivebackgesture.HomeScreen> {
                            HomeScreen(
                                onButtonClick = {
                                    navController.navigate(com.kkdev.predictivebackgesture.AddScreen)
                                }
                            )
                        }
                        composable<com.kkdev.predictivebackgesture.AddScreen> {
                            AddScreen()
                        }
                    }
                }
            }
        }
    }
}

