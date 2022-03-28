package com.example.composetext

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
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composetext.ui.theme.ComposeTextTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTextTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TextContainer()
                }
            }
        }
    }
}

@Composable
fun TextContainer(){

    val name = "전동현"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
        Text(text = "Hello 쩡대리!")

        Text(text = "클릭미!")

        Text("Hello 쩡대리!Hello 쩡대리!Hello ".repeat(50), maxLines = 2,
            modifier = Modifier
                .background(Color.Yellow)
                .fillMaxWidth(),
            overflow = TextOverflow.Ellipsis,
        )
        
        Text(
            text = "속에서 몸이 행복스럽고 같이 설산에서 피가 못하다 교향악이다. 같은 같이 맺어, 주며, 인간이 이성은 품에 하였으며, 부패뿐이다.",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Green),
            fontWeight = FontWeight.Bold
        )

        Text(
            buildAnnotatedString {
                withStyle(
                    style = ParagraphStyle(
                        lineHeight = 30.sp
                    )
                ){
                    withStyle(
                        style = SpanStyle(
                            background = Color.Yellow
                        )
                    ){
                        append("과실이 싹이 주며,")
                        withStyle(
                            style = SpanStyle(
                                color = Color.Blue,
                                fontSize = 30.sp
                            )
                        )
                        {
                            append("청춘")
                        }
                        append("이것이다. 소리다. 이것은 이상을 그러므로 소금이라" +
                                "그림자는 원대하고, 유소년에게서 과실이 뿐이다")
                    }
                }
            }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTextTheme {
        TextContainer()
    }
}