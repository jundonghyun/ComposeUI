package com.example.composesnackbar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.tooling.preview.Preview
import com.example.composesnackbar.ui.theme.ComposeSnackBarTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSnackBarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MySnackBar()
                }
            }
        }
    }
}

@Composable
fun MySnackBar() {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val buttonColor: (SnackbarData?) -> Color = { snackbarData ->
        if(snackbarData != null){
            Color.Black
        }
        else{
            Color.Blue
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonColor(snackbarHostState.currentSnackbarData),
                contentColor = Color.White
            ),
            onClick = {
            Log.d("Tag", "스낵바 생성")
            if(snackbarHostState.currentSnackbarData != null){
                Log.d("Tag", "이미 스낵바가 있음")
                snackbarHostState.currentSnackbarData?.dismiss()
                return@Button
            }

            coroutineScope.launch {
                val result = snackbarHostState.showSnackbar(
                    "오늘도 빡코딩!",
                    "확인",
                    SnackbarDuration.Short
                ).let {
                    when (it) {
                        SnackbarResult.Dismissed -> Log.d("Tag", "스낵바 닫아짐")
                        SnackbarResult.ActionPerformed -> Log.d("Tag", "스낵바 확인 버튼 클릭")
                    }
                }
            }
        })
        {
            Text("스낵바 보여주기")
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeSnackBarTheme {
        MySnackBar()
    }
}