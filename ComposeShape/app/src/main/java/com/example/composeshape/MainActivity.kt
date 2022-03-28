package com.example.composeshape

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.composeshape.ui.theme.ComposeShapeTheme
import java.util.*
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeShapeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ShapeContainer()
                }
            }
        }
    }
}

@Composable
fun ShapeContainer(){

    var polySides by remember {
        mutableStateOf(3)
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DummyBox(modifier = Modifier.clip(RectangleShape))
        DummyBox(modifier = Modifier.clip(CircleShape))
        DummyBox(modifier = Modifier.clip(RoundedCornerShape(10.dp)))
        DummyBox(modifier = Modifier.clip(CutCornerShape(10.dp)))
        DummyBox(modifier = Modifier.clip(TrangleShape()))

        Text(text = "polySides:${polySides}")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){

            Button(onClick = {
                polySides += 1
            }) {
                Text(text = "polySides + 1")
            }
            Button(onClick = {
                polySides += 3
            }) {
                Text(text = "polySides + 3")
            }
        }
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

class TrangleShape(): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path = path)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeShapeTheme {
        ShapeContainer()
    }
}