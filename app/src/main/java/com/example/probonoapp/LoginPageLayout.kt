package com.example.probonoapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoginPageLayout(){
    var experience = false
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(30.dp))
        Image(
            painter = painterResource(id = R.drawable.pro_bono),
            contentDescription = "Logo"
        )
//        accountInformation("Username")
//        Spacer(modifier = Modifier.size(10.dp))
//        accountInformation("Password")
//        Spacer(modifier = Modifier.size(30.dp))
//        Button(onClick = {experience = !experience}){
//            Text(
//                text = if (!experience) "Legal Advisor Mode" else "User Mode"
//            )
//        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginPreview() {
    LoginPageLayout()
}
