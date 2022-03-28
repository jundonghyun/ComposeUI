package com.example.composecheckbox

import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composecheckbox.ui.theme.ComposeCheckBoxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCheckBoxTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CheckBoxContainer()
                }
            }
        }
    }
}

@Composable
fun CheckBoxContainer(){

    val checkStatusForFirst = remember { mutableStateOf(false) }//체크박스를 체크하고 해제하는 3가지 방법
//    var checkStatusForSecond by remember { mutableStateOf(false)}
//    val (checkStatusForThird, setCheckStatusForThird) = remember { mutableStateOf(false)}
//    var (checkStatusForFourth, setCheckStatusForFourth) = remember{ mutableStateOf(false) }

    val checkStatusForSecond = remember { mutableStateOf(false) }
    val checkStatusForThird = remember { mutableStateOf(false) }



    val checkedStatesArray = listOf(
        checkStatusForFirst,
        checkStatusForSecond,
        checkStatusForThird)

    val checkStatusForFourth: Boolean = checkedStatesArray.all {
        it.value
    }

    val allBoxChecked: (Boolean) -> Unit = { isAllBoxChecked ->
        checkedStatesArray.forEach{
            it.value = isAllBoxChecked
        }
    }




    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(1.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CheckBoxWithTitle("1번 확인사항", checkStatusForFirst)
        CheckBoxWithTitle("2번 확인사항", checkStatusForSecond)
        CheckBoxWithTitle("3번 확인사항", checkStatusForThird)
        checkBoxAllAgree("모두 동의하십니까?", checkStatusForFourth, allBoxChecked)
        Spacer(modifier = Modifier.height(30.dp))
        MyCustomCheckBox(title = "커스텀 체크박스 입니다", withRipple = true)
        MyCustomCheckBox(title = "커스텀 체크박스 입니다", withRipple = false)


//        Checkbox(checked = checkStatusForFirst.value,
//            onCheckedChange = { isChecked ->
//            Log.d("Tag", "CheckBoxContainer: isChecked : ${isChecked}")
//            checkStatusForFirst.value = isChecked
//            })
//        Checkbox(
//            checked = checkStatusForSecond,
//            onCheckedChange = { isChecked ->
//                Log.d("Tag", "CheckBoxContainer: isChecked : ${isChecked}")
//                checkStatusForSecond = isChecked
//            })
//        Checkbox(
//            checked = checkStatusForThird,
//            onCheckedChange = {isChecked ->
//            Log.d("Tag", "CheckBoxContainer: isChecked : ${isChecked}")
//            setCheckStatusForThird.invoke(isChecked)
//            })
//
//        Spacer(modifier = Modifier.height(30.dp))
//
//        Checkbox(
//            colors = CheckboxDefaults.colors(
//                checkedColor = Color.Red, //선택됬을때 색깔 설정
//                uncheckedColor = Color.Gray, //선택해제 됬을때 색깔 설정
//                checkmarkColor = Color.Blue, //선택하는 체크색깔 설정
//                disabledColor = Color.Yellow //선택이 disable상태일때 색상 설정
//            ),
//            checked = checkStatusForFourth,
//            onCheckedChange = {isChecked ->
//                Log.d("Tag", "CheckBoxContainer: isChecked : ${isChecked}")
//                setCheckStatusForFourth.invoke(isChecked)
//            })
    }
}

@Composable
fun MyCustomCheckBox(title: String, withRipple: Boolean = false){

    var isChecked by remember { mutableStateOf(false) }
    var togglePainter = if(isChecked){R.drawable.ic_checked_icon}else{R.drawable.ic_unchecked_icon}
    var checkedInfoString = if(isChecked) "체크됨" else "체크안됨"
    var rippleEffect = if(withRipple){rememberRipple(
        radius = 40.dp,//커지는 범위
        bounded = false, //눌렀을때 물결표시같은걸 없애줌
        color = Color.Blue
    )}else null

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(30.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(50.dp)
                .clickable(
                    indication = rippleEffect, //리플이라해서 체크될때 모션같은걸 없애줌
        interactionSource = remember { MutableInteractionSource() }
        ) {
        isChecked = !isChecked }
        )
        {
            Image(
                painter = painterResource(id = togglePainter),
                contentDescription = null,
                modifier = Modifier

            )
        }


//        Checkbox(checked = isChecked,
//            onCheckedChange = {
//                Log.d("Tag", "CheckBoxContainer: isChecked : ${it}")
//                isChecked = it
//            })
        Text(text = "${checkedInfoString}")
    }
}

@Composable
fun CheckBoxWithTitle(title: String, isChecked: MutableState<Boolean>){
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(30.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Checkbox(checked = isChecked.value,
            onCheckedChange = {
                Log.d("Tag", "CheckBoxContainer: isChecked : ${it}")
                isChecked.value = it
            })
        Text(text = "${title}")
    }
}

@Composable
fun checkBoxAllAgree(title: String, shouldChecked: Boolean, //체크박스가 모두 체크되었을때
                     allBoxChecked: (Boolean) -> Unit){
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(30.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Checkbox(checked = shouldChecked,
            onCheckedChange = { itChecked ->
                Log.d("Tag", "CheckBoxContainer: isChecked : ${itChecked}")
                allBoxChecked(itChecked)
            })
        Text(text = "${title}")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeCheckBoxTheme {
        CheckBoxContainer()
    }
}