package com.example.composepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composepractice.ui.theme.ComposePracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold( topBar = {
                        TopAppBar(title = {Text("TopAppBar")}
                            ,backgroundColor = MaterialTheme.colors.primary ) }
                    ) {
                        Conversation(SampleData.conversationSample)
                    }
                }
            }
        }
    }
}

data class Message(val author : String, val body : String)

@Composable
fun Conversation(messageList : List<Message>){
    LazyColumn{
        items(messageList) { message ->
            MessageCard(message)
        }
    }
}

@Composable
fun MessageCard(msg : Message){
    Row() {
        Image(
            //xxxResource를 통해 리소스를 가져올 수 있다.
            painter = painterResource(id = R.drawable.ongdin),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)
                .padding(top = 5.dp, start = 5.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.primary, CircleShape)
        )

        Spacer(modifier = Modifier.width(10.dp))

        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor: Color by animateColorAsState(
            if (isExpanded)
                MaterialTheme.colors.primarySurface
            else
                MaterialTheme.colors.surface
        )

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.h6
            )

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier.animateContentSize().padding(5.dp)
            ) {
                Text(text = msg.body)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposePracticeTheme {
        Scaffold( topBar = {
            TopAppBar(title = {Text("TopAppBar")}
                ,backgroundColor = MaterialTheme.colors.primary ) }
        ) {
            Conversation(SampleData.conversationSample)
        }
    }
}