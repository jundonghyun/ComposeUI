package com.example.composetextfield

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.composetextfield.ui.theme.ComposeTextFieldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTextFieldTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TextFieldTest()
                }
            }
        }
    }
}

@Composable
fun TextFieldTest(){
    var text by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var phoneNumberInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }

    val passwordResource: (Boolean) -> Int = {
        if(it){
            R.drawable.ic_visibility
        }
        else{
            R.drawable.ic_visibility_off
        }
    }

    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("???????????????") },
            placeholder = { Text("???????????? ???????????????") }
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("???????????? ??????") },
            placeholder = { Text("??????????????? ???????????????") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation =
                if(passwordVisible){
                    VisualTransformation.None
                }
                else{
                    PasswordVisualTransformation()
                }
            ,
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = painterResource(id = passwordResource(passwordVisible)), contentDescription = null)
                }
            }

        )

        OutlinedTextField(
            value = phoneNumberInput,
            singleLine = true,
            onValueChange = { phoneNumberInput = it },
            label = { Text("????????????") },
            placeholder = { Text("010-1234-5678") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = emailInput,
            singleLine = true,
            onValueChange = { emailInput = it },
            label = { Text("????????? ??????") },
            placeholder = { Text("test@test.com") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = null)
            },
            trailingIcon = {
                IconButton(onClick = {
                    Log.d("Tag", "???????????? ??????")
                }) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
                }
            }
        )
    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTextFieldTheme {
        TextFieldTest()
    }
}