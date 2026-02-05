package com.example.probonoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainLayout()
        }
    }
}

@Composable
fun MainLayout() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val dataManager = remember { DataManager(context) }
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login_page") {
        composable("login_page") {
            LoginPageLayout(
                navController = navController,
                dataManager = dataManager,
                scope = coroutineScope
            )
        }
        composable("jobs_page") {
            JobsPageLayout(
                navController = navController,
                dataManager = dataManager,
                scope = coroutineScope
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    MainLayout()
}
