package com.example.probonoapp

import androidx.compose.runtime.Composable

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
fun makePost(): Post {
    var p = Post()
    Column() {
        val t = postInformation("Subject")
        Spacer(modifier = Modifier.size(10.dp))
        val cat = postInformation("Classification")
        Spacer(modifier = Modifier.size(10.dp))
        val due = postInformation("Date")
        Spacer(modifier = Modifier.size(10.dp))
        val desc = postInformation("Description")
        p = Post(t, cat, due, desc)
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
