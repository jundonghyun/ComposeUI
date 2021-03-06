package com.example.airqualitycompose

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.PaintDrawable
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.media.Image
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collection.mutableVectorOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.airqualitycompose.retrofit.AirQualityResponse
import com.example.airqualitycompose.retrofit.AirQualityService
import com.example.airqualitycompose.retrofit.RetrofitConnection
import com.example.airqualitycompose.ui.theme.AirQualityComposeTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.IllegalArgumentException
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : ComponentActivity() {
    private lateinit var locationProvider: LocationProvider
    private val PERMISSIONS_REQUEST_CODE = 100
    var REQUIRED_PERMISSION = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    var latitude: Double = 0.0
    var longitude: Double = 0.0

    lateinit var getGPSPermissionLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {


            checkAllPermissions()
            updateUi()
            //setRefreshButton()

            AirQualityComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    updateUi()
                }
            }
        }
    }
    @Composable
    private fun checkAllPermissions(){
        if(!isLocationServiceAvailable()){
            ShowDialogForLocationServiceSetting()
        }
        else isRunTimePermissionsGranted()
    }

    private fun isLocationServiceAvailable(): Boolean{
        val locationManger = getSystemService(LOCATION_SERVICE) as LocationManager
        return (locationManger.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManger.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }

    private fun isRunTimePermissionsGranted(){
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            this@MainActivity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            this@MainActivity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if(hasFineLocationPermission != PackageManager.PERMISSION_GRANTED || hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this@MainActivity, REQUIRED_PERMISSION, PERMISSIONS_REQUEST_CODE)
        }
    }

    @Composable
    private fun ShowDialogForLocationServiceSetting(){

        getGPSPermissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            //???????????? ????????? ??????
            if(result.resultCode == Activity.RESULT_OK){
                //???????????? GPS??? ????????? ????????? ??????
                if(isLocationServiceAvailable()){
                    isRunTimePermissionsGranted() //????????? ?????? ??????(AirQuality??? ??? ??????????????? ?????????????????? ???????????????????????? ?????????)
                }
                else{
                    //GPS????????? ?????? ???????????? ??????
                    Toast.makeText(this@MainActivity, "?????????????????? ????????? ??? ????????????", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }


        AlertDialog(
            onDismissRequest = {  },
            confirmButton = {
                TextButton(
                    onClick = {
                        val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        getGPSPermissionLauncher.launch(callGPSSettingIntent)
                    }) {
                    Text(text = "??????")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        Toast.makeText(this, "???????????? ???????????????(GPS) ?????? ??? ????????? ?????????", Toast.LENGTH_SHORT).show()
                        finish()
                    }) {
                    Text(text = "??????")
                }
            },
            title = {
                Text(text = "?????? ????????? ????????????")
            },
            text = { Text(text = "?????? ???????????? ?????? ????????????. ???????????? ?????? ????????? ??? ????????????.")}
        )
    }

    @Composable
    private fun updateUi(){
        var locationTitle by remember { mutableStateOf("??????1???") }
        var locationSubTitle by remember { mutableStateOf("???????????? ???????????????") }
        var tvDateTime by remember { mutableStateOf("2022-04-11 11:00") }
        var tvCount by remember { mutableStateOf("100") }
        var tvTitle by remember { mutableStateOf("??????") }
        var imgBg by remember { mutableStateOf (R.drawable.bg_worst) }
        var img by remember { mutableStateOf(R.drawable.bg_worst) }


        locationProvider = LocationProvider(this)

        if(latitude == 0.0 || longitude == 0.0){
            latitude = locationProvider.getLocationLatitude()
            longitude = locationProvider.getLocationLongitude()
        }

        if(latitude != 0.0 || longitude != 0.0){
            val address = getcurrentAddress(latitude, longitude)
            address?.let{
                if(it.thoroughfare == null){
                    locationTitle = it.subLocality
                }
                else{
                    locationTitle = it.thoroughfare
                }
                locationSubTitle = it.countryName + it.adminArea
            }

            val retrofitAPI = RetrofitConnection.getInstance().create(AirQualityService::class.java)

            retrofitAPI.getAirQualityData(
                latitude.toString(),
                longitude.toString(),
                "40ec59db-e876-4513-94ba-d4d143eb6ec1"
            ).enqueue(object : Callback<AirQualityResponse>{
                override fun onResponse(
                    call: Call<AirQualityResponse>,
                    response: Response<AirQualityResponse>
                ) {
                    if(response.isSuccessful){
                        Toast.makeText(this@MainActivity, "???????????? ???????????? ??????", Toast.LENGTH_SHORT).show()
                        response.body()?.let {
                            val pollutionData = it.data.current.pollution

                            tvCount = pollutionData.aqius.toString()

                            val dateTime = ZonedDateTime.parse(pollutionData.ts).withZoneSameInstant(
                                ZoneId.of("Asia/Seoul")).toLocalDateTime()

                            val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm")

                            tvDateTime = dateTime.format(dateFormatter).toString()

                            when(pollutionData.aqius){
                                in 0..50 -> {
                                   tvTitle = "??????"
                                    imgBg = R.drawable.bg_good
                                }

                                in 51..150 -> {
                                    tvTitle = "??????"
                                    imgBg = R.drawable.bg_soso
                                }

                                in 151..200 -> {
                                    tvTitle = "??????"
                                    imgBg = R.drawable.bg_bad
                                }

                                else -> {
                                    tvTitle = "?????? ??????"
                                    imgBg = R.drawable.bg_worst
                                }
                            }
                        }
                    }
                    else{
                        Toast.makeText(this@MainActivity, "???????????? ??????", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AirQualityResponse>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@MainActivity, "??????????????? ??????????????????", Toast.LENGTH_SHORT).show()
                }
            })
        }
        else{
            //Toast.makeText(this, "??????, ??????????????? ????????? ??? ????????????. ??????????????? ???????????????", Toast.LENGTH_SHORT).show()
        }

        val context = LocalContext.current

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    backgroundColor = colorResource(id = R.color.orange),
                    contentColor = Color.Black,
                    onClick = { /*TODO*/ },
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_map_24),
                        contentDescription = null
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(x = 45.dp, y = 100.dp)
                ) {
                    Column() {
                        Text(text = locationTitle, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                        Text(text = locationSubTitle, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = imgBg),
                        contentDescription = null
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = tvCount, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                        Text(text = tvTitle, fontSize = 14.sp)
                    }

                }
                Column(
                    modifier = Modifier.offset(y = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "????????????", fontSize = 13.sp)
                    Text(text = tvDateTime, fontSize = 13.sp)

                    Column(
                        modifier = Modifier.offset(y = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_refresh),
                            contentDescription = null,
                            modifier = Modifier
                                .size(28.dp)
                                .padding(5.dp)
                                .clickable(
                                    enabled = true,
                                    onClickLabel = null,
                                    onClick = {
                                        Toast
                                            .makeText(context, "Refresh", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                )
                        )
                    }
                }
            }

        }
    }

    private fun getcurrentAddress(latitude: Double, longitude: Double): Address? {
        val geocoder = Geocoder(this, Locale.getDefault())

        val addresses: List<Address>? = try{
            geocoder.getFromLocation(latitude, longitude, 5)
        }catch (ioException: IOException){
            Toast.makeText(this, "???????????? ????????? ???????????? ?????????",Toast.LENGTH_SHORT).show()
            return null
        }catch (illegalArgumentException: IllegalArgumentException){
            Toast.makeText(this, "????????? ??????, ?????? ?????????", Toast.LENGTH_SHORT).show()
            return null
        }

        if(addresses == null || addresses.isEmpty()){
            Toast.makeText(this, "????????? ???????????? ???????????????", Toast.LENGTH_SHORT).show()
            return null
        }

        val address: Address = addresses[0]
        return address
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        AirQualityComposeTheme {
            updateUi()
        }
    }
}


