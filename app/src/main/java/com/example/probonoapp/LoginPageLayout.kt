package com.example.probonoapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoginPageLayout(){
    var experience = false
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var userName by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Spacer(modifier = Modifier.size(30.dp))
        Image(
            painter = painterResource(id = R.drawable.pro_bono),
            contentDescription = "Logo"
        )
        createTextField("Username", userName) { userName = it }
        Spacer(modifier = Modifier.size(10.dp))
        createTextField("Password", password) { password = it }
        Spacer(modifier = Modifier.size(30.dp))
        Button(onClick = {experience = !experience}){
            Text(
                text = if (!experience) "Legal Advisor Mode" else "User Mode"
            )
        }
    }
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
    LoginPageLayout()
}
