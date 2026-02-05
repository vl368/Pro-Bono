package com.example.probonoapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun JobsPageLayout(navController: NavController, dataManager: DataManager, scope: CoroutineScope){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val posts: List<Post> = dataManager.getPosts().collectAsState(initial = emptyList(), scope.coroutineContext) as List<Post>
        Spacer(Modifier.height(30.dp))
        Text(
            text = "Open Jobs",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        LazyColumn(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 30.dp),
        ) {
            items(posts.size) { i ->
                PostVisuals(posts[i])
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun PostVisuals(post: Post) {
    Column(Modifier.fillMaxWidth()
        .background(Color(186, 200, 222))
        .padding(all = 5.dp)
    ) {
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
                text = "Case Specialities: ", // @TO DO
                fontSize = 15.sp,
            )
        }
        Text(
            text = post.description,
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.clip(RoundedCornerShape(5.dp))
                .background(Color(46, 74, 120))
                .padding(all = 3.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewJobs() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val dataManager = remember { DataManager(context) }
    val navController = rememberNavController()
    JobsPageLayout(navController, dataManager, coroutineScope)
}
