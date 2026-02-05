package com.example.probonoapp

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WritePostLayout(){
    Surface {
        makePost()
    }
}

@Composable
fun JobsPageLayout(posts: MutableList<Post>){
    Surface() {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ){
            items(posts.size) { i ->
                postVisuals(posts.get(i))
            }
        }
    }
}

@Composable
fun makePost() {
    Column() {
        val t = postInformation("Subject")
        Spacer(modifier = Modifier.size(10.dp))
        val cat = postInformation("Classification")
        Spacer(modifier = Modifier.size(10.dp))
        val due = postInformation("Date")
        Spacer(modifier = Modifier.size(10.dp))
        val desc = postInformation("Description")

        // submit then create post obj thru data manager
    }
}

@Composable()
fun postVisuals(post: Post){
    Column() {
        Text(
            text = post.title,
            fontSize = 30.sp,
        )
        Row(horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Needed by: " + post.postedAt,
                fontSize = 15.sp,
            )
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = "Case Specialities: ", // + post, // leave this alone ill implement this its not singular
                fontSize = 15.sp,
            )
        }
        Text(
            text = post.description,
            fontSize = 20.sp,
            modifier = Modifier.clip(RoundedCornerShape(16.dp))
                .background(Color(46, 74, 120))
        )
    }
}

@Composable
fun postInformation(prompt: String): String {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        label = { Text(prompt) },
    )
    return text
}
