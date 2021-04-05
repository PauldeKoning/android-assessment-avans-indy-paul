package com.example.mylittleprofile.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.registerForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.mylittleprofile.model.CharacterModel
import com.example.mylittleprofile.ui.ponylist.PonyImage

@Composable
fun Home(navController: NavController) {
    var favouritePony by remember { mutableStateOf<CharacterModel?>(null) }

    val context = LocalContext.current
    val viewModel = HomeViewModel(LocalContext.current)

    if (favouritePony == null) {
        viewModel.getPonyInfo { resp ->
            if (resp != null) {
                favouritePony = resp
            }
        }

        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()) {
            Text("Select a favourite pony in the ponies list to view more info",
                modifier = Modifier.padding(16.dp),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
            )

            Text("(If you have already selected a pony, please wait a second)",
                modifier = Modifier.padding(16.dp),
                style = TextStyle(fontStyle = FontStyle.Italic, fontSize = 12.sp)
            )
        }

    } else {
        val requestPerm = registerForActivityResult(ActivityResultContracts.RequestPermission()) { success ->
            var locationString = ""
            if (success) {
                viewModel.getLastLocation(context)
                if (viewModel.currentLocation.value != null) {
                     locationString = " in the region ${viewModel.currentLocation.value!!.adminArea}"
                }
            }

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "My favourite pony$locationString is ${favouritePony!!.name}.\n\nhttp://welove.ponies/${favouritePony!!.id}")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }

        Column(
            Modifier
                .padding(16.dp)
                .fillMaxHeight()
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Your favourite pony is:",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(end = 16.dp),
                )

                Text(
                    favouritePony!!.name,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                )
            }

            Row(
                modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)) {
                Button(
                    modifier = Modifier.padding(end = 16.dp),
                    onClick = {
                        navController.currentBackStackEntry?.arguments?.putString("url", favouritePony!!.url)
                        navController.navigate("browser")
                    },
                ) {
                    Icon(Icons.Filled.Launch, "Launch icon", modifier = Modifier.padding(end = 6.dp))
                    Text("See Wiki")
                }

                Button(
                    onClick = {
                        requestPerm.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    },
                ) {
                    Icon(Icons.Filled.Share, "Share icon", modifier = Modifier.padding(end = 6.dp))
                    Text("Share")
                }
            }

            if (favouritePony!!.image.isNotEmpty()) {
                PonyImage(favouritePony!!.image[0])
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun PonyWeb(url: String) {
    return AndroidView({ context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true // Setting it to false does disable the cookie banner tho T_T
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    return false
                }
            }
            loadUrl(url)
        }
    })
}