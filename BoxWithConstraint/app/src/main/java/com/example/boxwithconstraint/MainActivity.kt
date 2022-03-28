package com.example.boxwithconstraint

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
import com.example.boxwithconstraint.ui.theme.BoxWithConstraintTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoxWithConstraintTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BoxWithConstraintContainer()
                }
            }
        }
    }
}

@Composable
fun BoxWithConstraintContainer(){
    BoxWithConstraints(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        Alignment.Center,
        propagateMinConstraints = false
    ) {

        Column() {
            BoxWithConstraintsItems(modifier = Modifier
                .size(200.dp)
                .background(Color.Yellow)
            )
            BoxWithConstraintsItems(modifier = Modifier
                .size(300.dp)
                .background(Color.Green)
            )
        }
    }
}

@Composable
fun BoxWithConstraintsItems(modifier: Modifier){
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
        propagateMinConstraints = false
    ) {
        if(this.minWidth > 200.dp){
            Text(text = "이것은 큰 상자이다")
        }
        else Text(text = "이것은 작은 상자이다")
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BoxWithConstraintTheme {
        BoxWithConstraintContainer()
    }
}