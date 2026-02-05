package com.example.probonoapp

import androidx.compose.runtime.Composable

@Composable
fun LoginPageLayout(){
    var experience = false
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(30.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo"
        )
        accountInformation("Username")
        Spacer(modifier = Modifier.size(10.dp))
        accountInformation("Password")
        Spacer(modifier = Modifier.size(30.dp))
        Button(onClick = {experience = !experience}){
            Text(
                text = if (!experience) "Legal Advisor Mode" else "User Mode"
            )
        }
    }
}
