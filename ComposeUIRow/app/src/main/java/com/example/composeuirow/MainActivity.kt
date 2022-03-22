package com.example.composeuirow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeuirow.ui.theme.ComposeUIRowTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeUIRowTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BoxContainer()
                }
            }
        }
    }
}

@Composable
fun Container(){
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly, //row 일때는 horizontal만 사용가능
        verticalAlignment = Alignment.Bottom
    ) {
        DummyBox()
        DummyBox()
        DummyBox()
    }
}

@Composable
fun DummyBox(modifier: Modifier = Modifier, color: Color? = null){

    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)

    val randomColor = color?.let {
        it
    } ?: Color(red, green, blue)

    Box(modifier = modifier
        .size(100.dp)
        .background(randomColor)) {

    }
}

@Composable
fun ColumnContainer(){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DummyBox()
        DummyBox()
        DummyBox()
    }
}

@Composable
fun BoxContainer(){
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.CenterStart

    ) {
        DummyBox(modifier = Modifier.size(200.dp), color = Color.Cyan)
        DummyBox(modifier = Modifier.size(150.dp), color = Color.Yellow)
        DummyBox(color = Color.Black)
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeUIRowTheme {
        BoxContainer()
    }
}