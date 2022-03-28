package com.example.composebutton

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composebutton.ui.theme.ComposeButtonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeButtonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ButtonContainer()
                }
            }
        }
    }
}

@Composable
fun ButtonContainer(){
    val buttonBorderGradient = Brush.horizontalGradient(listOf(Color.Yellow, Color.Red))
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressStatusTitle = if(isPressed) "버튼을 누르고있다" else "버튼에서 손을 땠다"

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp, //그림자주는것
                pressedElevation = 0.dp, //눌렀을때 그림자 사라지게
                disabledElevation = 0.dp //버튼이 disable됬을때 어떻게 처리되는지
            ),
            shape = CircleShape, //버튼의 모양
            border = BorderStroke(5.dp, Color.Green), //버튼의 테두리 설정
            onClick = {
            Log.d("TAG", "버튼1클릭")
        }) {
            Text(text = "버튼 1")
        }

        Button(
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp, //그림자주는것
                pressedElevation = 0.dp, //눌렀을때 그림자 사라지게
                disabledElevation = 0.dp //버튼이 disable됬을때 어떻게 처리되는지
            ),
            colors = ButtonDefaults.buttonColors( //버튼의 색
                backgroundColor = Color.Black,
                disabledBackgroundColor = Color.LightGray
            ),
            contentPadding = PaddingValues(10.dp),
            border = BorderStroke(5.dp, buttonBorderGradient),
            interactionSource = interactionSource,
            onClick = {
                Log.d("TAG", "버튼2클릭")
            }) {
            Text(
                text = "버튼 2",
                color = Color.White
            )
        }
        Text(text = pressStatusTitle)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeButtonTheme {
        ButtonContainer()
    }
}