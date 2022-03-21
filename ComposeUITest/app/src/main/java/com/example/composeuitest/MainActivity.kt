package com.example.composeuitest

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.print.PrintAttributes
import android.util.Log
import android.view.RoundedCorner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.*
import coil.request.ImageRequest
import coil.size.Size
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.composeuitest.ui.theme.ComposeUITestTheme
import com.example.composeuitest.utils.DummyDataProvider
import com.example.composeuitest.utils.RandomUser
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeUITestTheme {
                // A surface container using the 'background' color from the theme

            }
        }
    }
}

@Composable
fun ContentView(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            backgroundColor = Color.White,
            topBar = { MyAppBar() }
        ) {
            RandomUserListView(randomUsers = DummyDataProvider.userList)
        }
    }
}

@Composable
fun MyAppBar(){
    TopAppBar(elevation = 10.dp,
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.height(58.dp)) {
        Text(
            text = stringResource(id = R.string.app_bar_title),
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically),
            fontSize = 18.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
fun RandomUserListView(randomUsers: List<RandomUser>){
    LazyColumn(){
        items(randomUsers){
            RandomUserView(it)
        }
    }
}

@Composable
fun RandomUserView(randomUser: RandomUser){

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation = 10.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            Modifier
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_empty_user_img),
                contentDescription = null,
                modifier = Modifier.padding(10.dp)
            )
            //ProfileImage(source = randomUser.profileImage, modifier = Modifier)
            Column(
            ) {
                Text(
                    text = randomUser.name,
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = randomUser.description,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Composable
fun ProfileImage(source: String, modifier: Modifier){
    var bitmap: MutableState<Bitmap>? = null
    val imageModifier = modifier
        .size(50.dp, 50.dp)
        .clip(RoundedCornerShape(10.dp))

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(source)
        .into(object: CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmap?.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                
            }
        })

    bitmap?.value?.asImageBitmap()?.let{
        Image(
            bitmap = it,
            contentScale = ContentScale.Fit,
            contentDescription = null,
            modifier = imageModifier
        )
    } ?: Image(
        painter = painterResource(id = R.drawable.ic_empty_user_img),
        contentScale = ContentScale.Fit,
        contentDescription = null,
        modifier = imageModifier
    )
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeUITestTheme {
        ContentView()
    }
}