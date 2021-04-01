package com.example.mylittleprofile.ui.home

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun Home() {
    var url by remember { mutableStateOf("") }

    val viewModel = HomeViewModel(LocalContext.current)

    if (url == "") {
        viewModel.getPonyInfoURL { resp ->
            url = resp;
        }

        Text("Select a favourite pony in the ponies list to view more info (If you have already selected a pony, please wait a second)",
            modifier = Modifier.padding(16.dp),
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
        )
    } else {
        Column(Modifier.fillMaxWidth().fillMaxHeight()) {
            PonyWeb(url);
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun PonyWeb(url: String) {
    return AndroidView({ context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
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