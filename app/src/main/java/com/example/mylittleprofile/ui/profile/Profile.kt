package com.example.mylittleprofile.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.activity.compose.registerForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylittleprofile.PreferencesActivity
import com.google.android.gms.location.LocationServices


@Composable
fun Profile() {
    val localContext = LocalContext.current
    val viewModel = ProfileViewModel()

    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Button(
            onClick = {
                val intent = Intent(localContext, PreferencesActivity::class.java)
                localContext.startActivity(intent)
            },
            modifier = Modifier.padding(bottom = 16.dp),
        ) {
            Text("Open settings")
        }

        PermissionButton(model = viewModel)
    }
}

//private const val PERMISSIONS_REQUEST_CODE = 10
//private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

//fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
//    ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
//}

@SuppressLint("MissingPermission")
@Composable
fun PermissionButton(model: ProfileViewModel) {
    val context = LocalContext.current

    val requestPerm = registerForActivityResult(ActivityResultContracts.RequestPermission()) { success ->
        if (success) {
            model.getLastLocation(context)
        }
    }

//    if (!hasPermissions(context)) {
//          ActivityCompat.requestPermissions(
//                context as Activity,
//                PERMISSIONS_REQUIRED,
//                PERMISSIONS_REQUEST_CODE
//          )
//    }

    Button(onClick = {
        requestPerm.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }) {
        Text("Request location")
    }

    val locationState = model.currentLocation.observeAsState(null)
    if (locationState.value != null) {
        LocationInfo(loc = locationState.value!!)
    }

}


@Composable
fun LocationInfo(loc: Address) {
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        LocationDetailInfoRow("Country:", loc.countryName)
        LocationDetailInfoRow("City:", loc.adminArea)
        LocationDetailInfoRow("Address:", loc.postalCode)
    }
}

@Composable
fun LocationDetailInfoRow(key: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 12.dp)
            .fillMaxWidth()
    ) {
        Text(key, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp))
        Text(value, style = TextStyle(fontSize = 18.sp))
    }
}
