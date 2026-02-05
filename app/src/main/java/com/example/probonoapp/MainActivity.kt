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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

@Composable
fun makePost(): Post {
    val p = Post()
    Column() {
        postInformation("Subject", p)
        Spacer(modifier = Modifier.size(10.dp))
        postInformation("Classification", p)
        Spacer(modifier = Modifier.size(10.dp))
        postInformation("Date", p)
        Spacer(modifier = Modifier.size(10.dp))
        postInformation("Description", p)
    }
    return p
}

@Composable()
fun postVisuals(p: Post){
    Column() {
        Text(
            text = p.title,
            fontSize = 30.sp,
        )
        Row(horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Needed by: " + p.date,
                fontSize = 15.sp,
            )
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = "Case Category: " + p.category,
                fontSize = 15.sp,
            )
        }
        Text(
            text = p.description,
            fontSize = 20.sp,
            modifier = Modifier.clip(RoundedCornerShape(16.dp))
            .background(Color(46, 74, 120))
        )
    }
}

@Composable
fun JobsPageLayout(posts: MutableList<Post>){
    LazyColumn(modifier = Modifier.padding(16.dp)){
        items(posts.size) { i ->
            postVisuals(posts.get(i))
        }
    }
}

@Composable
fun postInformation(prompt: String, p: Post) {
    var text by remember { mutableStateOf("") }
    when (prompt){
        "title" -> p.title = text
        "classification" -> p.category = text
        "date" -> p.date = text
        "description" -> p.description = text
    }
    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        label = { Text(prompt) },
    )
}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    MainLayout()
}
