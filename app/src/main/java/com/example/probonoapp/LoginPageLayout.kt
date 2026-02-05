package com.example.probonoapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginPageLayout(navController: NavController, dataManager: DataManager, scope: CoroutineScope){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        var userName by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var emailAddress by remember { mutableStateOf("") }
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var zipCode  by remember { mutableStateOf("") }
        var firmName by remember { mutableStateOf("") }
        var education by remember { mutableStateOf("") }
        var barNumber by remember { mutableStateOf("") }
        val specialitiesList = listOf("Immigration Law", "Family Law", "Criminal Defense", "Employment Law", "Housing Law")
        val specialitiesToggleList = remember {
            mutableStateListOf(*Array(specialitiesList.size) { false })
        }
        var occupation by remember { mutableStateOf("") }
        var isClient by remember { mutableStateOf(false) }
        var moveOn by remember { mutableStateOf(false) }
        val legalBlue1 = Color(52, 51, 101)
        val legalBlue2 = Color(0, 100, 160)

        if (!moveOn) {
            Spacer(modifier = Modifier.size(60.dp))
            Image(
                painter = painterResource(id = R.drawable.pro_bono),
                contentDescription = "Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(300.dp)
            )
            CreateTextField("Username", userName) { userName = it }
            CreateTextField("Password", password) { password = it }
            Spacer(modifier = Modifier.size(30.dp))
            ClassicText("I am a", 20, legalBlue1)
            ClassicText(if (isClient) "client" else "volunteer", 25, legalBlue2)
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
            Spacer(modifier = Modifier.size(30.dp))
            Button(
                onClick = {
                    moveOn = true
                    scope.launch {
                        if (dataManager.findUser(userName, password, isClient)) {
                            navController.navigate("jobs_page")
                        }
                    }
                }
            ) {
                ClassicText("Next", 20, Color.White)
            }
        }
        else {
            Spacer(modifier = Modifier.size(60.dp))
            val textFieldsToDisplay = if (isClient) 8 else 13
            CreateTextField("Email Address", emailAddress) { emailAddress = it }
            CreateTextField("First Name", firstName) { firstName = it }
            CreateTextField("Last Name", lastName) { lastName = it }
            CreateTextField("Zip Code", zipCode) { zipCode = it }
            if(isClient) {
                CreateTextField("Occupation", occupation) { occupation = it }
            }
            else {
                CreateTextField("Firm Name", firmName) { firmName = it }
                CreateTextField("Education", education) { education = it }
                CreateTextField("Bar Number", barNumber) { barNumber = it }
                ClassicText("Please scroll to select your specialities: ", 15, legalBlue1)
                Spacer(modifier = Modifier.size(20.dp))
                Row {
                    Spacer(modifier = Modifier.size(40.dp))
                    LazyColumn(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        items(specialitiesList.size) { index ->
                            Button(
                                onClick = {
                                    specialitiesToggleList[index] = !specialitiesToggleList[index]
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (specialitiesToggleList[index]) legalBlue1 else legalBlue2,
                                    contentColor = legalBlue2,
                                    disabledContentColor = legalBlue2,
                                    disabledContainerColor = legalBlue2
                                )
                            ) {
                                ClassicText(specialitiesList[index], 15, Color.White)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.size(20.dp))
            }
            Button(
                    onClick = {
//                        scope.launch {
//                            if (isClient) {
//                                dataManager.addUser(
//                                    userName,
//                                    password,
//                                    emailAddress,
//                                    firstName,
//                                    lastName,
//                                    zipCode,
//                                    !isClient,
//                                    isClient,
//                                    occupation = occupation
//                                )
//                            } else {
//                                dataManager.addUser(
//                                    userName,
//                                    password,
//                                    emailAddress,
//                                    firstName,
//                                    lastName,
//                                    zipCode,
//                                    !isClient,
//                                    isClient,
//                                    firmName,
//                                    education,
//                                    barNumber,
//                                    specialitiesToggleList
//                                        .mapIndexedNotNull { index, value ->
//                                            if (value) specialitiesList[index] else null
//                                        }
//                                )
//                            }
//                        }
                        navController.navigate("jobs_page")
                    },
            colors = ButtonDefaults.buttonColors(
                containerColor = legalBlue1,
                contentColor = legalBlue1,
                disabledContentColor = legalBlue1,
                disabledContainerColor = legalBlue1
            )
            ) {
                ClassicText("Submit", 20, Color.White)
            }
        }
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
fun CreateTextField(prompt: String, value: String, changeVal: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = {
            changeVal(it)
        },
        label = { Text(prompt) },
        modifier = Modifier.width(300.dp)
    )
    Spacer(modifier = Modifier.size(20.dp))
}

@Preview(showSystemUi = true)
@Composable
fun PreviewLogin() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val dataManager = remember { DataManager(context) }
    val navController = rememberNavController()
    LoginPageLayout(navController, dataManager, coroutineScope)
}
