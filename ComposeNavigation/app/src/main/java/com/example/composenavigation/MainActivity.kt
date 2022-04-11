package com.example.composenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composenavigation.ui.theme.ComposeNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavigationGraph()
                }
            }
        }
    }
}

enum class NAV_ROUTE(val routeName: String, val description: String, val btnColor: Color){
    MAIN("MAIN", "메인 화면", Color(0xFB62222)),
    LOGIN("LOGIN", "로그인 화면", Color(0xFF3F51B5)),
    REGISTER("REGISTER", "회원가입 화면", Color(0xFF4CAF50)),
    USER_PROFILE("USER_PROFILE", "유저 프로필 화면", Color(0xFF009688)),
    SETTING("SETTING", "설정 화면", Color(0xFFFF5722))
}

// 네비게이션 라우트 액션
class RouteAction(navHostController: NavHostController){

    //특정 라우트로 이동
    val navTo: (NAV_ROUTE) -> Unit ={ route ->
        navHostController.navigate(route.routeName)
    }

    //뒤로가기 이동
    val goBack: () -> Unit = {
        navHostController.navigateUp()
    }
}

@Composable
fun NavigationGraph(startRoute: String = NAV_ROUTE.MAIN.routeName){
    //네비게이션 컨트롤러
    val navController = rememberNavController()

    //네비게이션 라우트 액션
    val routeAction = remember(navController) { RouteAction(navController) }

    //NavHost로 네비게이션 결정
    //네비게이션 연결할 것들을 결정
    NavHost(navController, startRoute){

        //라우트 이름 = 화면의 키
        composable(NAV_ROUTE.MAIN.routeName){
            //화면
            MainScreen(routeAction = routeAction)
        }

        composable(NAV_ROUTE.LOGIN.routeName){
            LoginScreen(routeAction = routeAction)
        }

        composable(NAV_ROUTE.REGISTER.routeName){
            RegisterScreen(routeAction = routeAction)
        }

        composable(NAV_ROUTE.USER_PROFILE.routeName){
            UserProfileScreen(routeAction = routeAction)
        }

        composable(NAV_ROUTE.SETTING.routeName){
            SettingScreen(routeAction = routeAction)
        }
    }
}

//메인화면
@Composable
fun MainScreen(routeAction: RouteAction){
    Surface(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.padding(16.dp)) {
            NavButton(route = NAV_ROUTE.MAIN, routeAction = routeAction)
            NavButton(route = NAV_ROUTE.LOGIN, routeAction = routeAction)
            NavButton(route = NAV_ROUTE.REGISTER, routeAction = routeAction)
            NavButton(route = NAV_ROUTE.USER_PROFILE, routeAction = routeAction)
            NavButton(route = NAV_ROUTE.SETTING, routeAction = routeAction)
        }
    }
}

//로그인 화면
@Composable
fun LoginScreen(routeAction: RouteAction){
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.padding(8.dp), Alignment.Center){
            Text(text = "로그인 화면", style = TextStyle(Color.White, 22.sp, FontWeight.Medium))
            Button(onClick = routeAction.goBack,
                Modifier
                    .padding(16.dp)
                    .offset(y = 100.dp)) {
                Text(text = "뒤로가기")
            }
        }
    }
}

//로그인 화면
@Composable
fun RegisterScreen(routeAction: RouteAction){
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.padding(8.dp), Alignment.Center){
            Text(text = "로그인 화면", style = TextStyle(Color.White, 22.sp, FontWeight.Medium))
            Button(onClick = routeAction.goBack,
                Modifier
                    .padding(16.dp)
                    .offset(y = 100.dp)) {
                Text(text = "뒤로가기")
            }
        }
    }
}

//로그인 화면
@Composable
fun UserProfileScreen(routeAction: RouteAction){
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.padding(8.dp), Alignment.Center){
            Text(text = "로그인 화면", style = TextStyle(Color.White, 22.sp, FontWeight.Medium))
            Button(onClick = routeAction.goBack,
                Modifier
                    .padding(16.dp)
                    .offset(y = 100.dp)) {
                Text(text = "뒤로가기")
            }
        }
    }
}

//로그인 화면
@Composable
fun SettingScreen(routeAction: RouteAction){
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.padding(8.dp), Alignment.Center){
            Text(text = "로그인 화면", style = TextStyle(Color.White, 22.sp, FontWeight.Medium))
            Button(onClick = routeAction.goBack,
                Modifier
                    .padding(16.dp)
                    .offset(y = 100.dp)) {
                Text(text = "뒤로가기")
            }
        }
    }
}

// 컬럼에 있는 네비게이션 버튼
@Composable
fun ColumnScope.NavButton(route: NAV_ROUTE, routeAction: RouteAction){
    Button(onClick = {
        routeAction.navTo(route)
    }, colors = buttonColors(backgroundColor = route.btnColor),
        modifier = Modifier
            .weight(1f)
            .padding(8.dp)
            .fillMaxSize()) {
        Text(text = route.description,
        style = TextStyle(Color.White, 22.sp, FontWeight.Medium)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeNavigationTheme {
        NavigationGraph()
    }
}