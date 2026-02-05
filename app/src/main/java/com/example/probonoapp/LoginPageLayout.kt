package com.example.probonoapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun LoginPageLayout(navController: NavController){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var userName by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isClient by remember { mutableStateOf(true) }
        val legalBlue1 = Color(52, 51, 101)
        val legalBlue2 = Color(0, 100, 160)

        Spacer(modifier = Modifier.size(30.dp))
        Image(
            painter = painterResource(id = R.drawable.pro_bono),
            contentDescription = "Logo"
        )

        createTextField("Username", userName) { userName = it }
        Spacer(modifier = Modifier.size(10.dp))
        createTextField("Password", password) { password = it }
        Spacer(modifier = Modifier.size(30.dp))
        ClassicText("I am a", 20, legalBlue1)
        ClassicText("${if (isClient) "client" else "volunteer"}", 25, legalBlue2)
        Switch(
            checked = isClient,
            onCheckedChange = {
                isClient = !isClient
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = legalBlue2, // Color of the thumb when checked
                checkedTrackColor = legalBlue1,   // Color of the track when checked
                uncheckedThumbColor = Color.LightGray, // Color of the thumb when unchecked
                uncheckedTrackColor = Color.DarkGray
            )
        )
    }
}

@Composable
fun ClassicText(text: String, fontSize: Int, color: Color) {
    Text(
        text = text,
        fontSize = fontSize.sp,
        fontWeight = FontWeight.SemiBold,
        color = color,
        fontFamily = FontFamily.Serif,
        letterSpacing = 0.1.sp
    )
}

@Composable
fun createTextField(prompt: String, value: String, changeVal: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = {
            changeVal(it)
        },
        label = { Text(prompt) },
    )
}

@Preview(showSystemUi = true)
@Composable
fun PreviewLogin() {
    val navController = rememberNavController()
    LoginPageLayout(navController)
}
