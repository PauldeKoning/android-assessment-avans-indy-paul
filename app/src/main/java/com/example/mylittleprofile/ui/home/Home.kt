package com.example.mylittleprofile.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mylittleprofile.PreferencesActivity
import com.example.mylittleprofile.model.CharacterModel
import com.example.mylittleprofile.ui.ponylist.PonyImage


@Composable
fun Home() {
    var favouritePony by remember { mutableStateOf<CharacterModel?>(null) }

    val context = LocalContext.current
    val viewModel = HomeViewModel(LocalContext.current)

    if (favouritePony == null) {
        viewModel.getPonyInfo { resp ->
            if (resp != null) {
                favouritePony = resp;
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
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxHeight()
                .fillMaxWidth()) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    "Your favourite pony is:",
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(end = 16.dp),
                )

                Text(
                    favouritePony!!.name,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                )
            }
            Row(Modifier.fillMaxWidth().padding(16.dp)) {
                Button(
                    modifier = Modifier.padding(end = 16.dp),
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(favouritePony!!.url)
                        context.startActivity(intent)
                    },
                ) {
                    Text("See wiki")
                }

                Button(
                    onClick = {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "My favourite pony is ${favouritePony!!.name}.\n\nhttp://welove.ponies/${favouritePony!!.id}")
                            type = "text/plain"
                        }

                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    },
                ) {
                    Text("Share")
                }
            }

            if (favouritePony!!.image.isNotEmpty()) {
                PonyImage(favouritePony!!.image[0])
            }
        }

        // Open actual browser instead maybe?


//        val intent = Intent(Intent.ACTION_VIEW)
//        intent.data = Uri.parse(url)
//        LocalContext.current.startActivity(intent)
//        Column(
//            Modifier
//                .fillMaxWidth()
//                .fillMaxHeight()) {
//            PonyWeb(url);
//        }
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