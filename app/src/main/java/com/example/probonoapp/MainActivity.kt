package com.example.probonoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.probonoapp.ui.theme.ProBonoAppTheme

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
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login_page") {
        composable("login_page") {
            LoginPageLayout()
        }
        composable("jobs_page") {
            JobsPageLayout()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    MainLayout()
}