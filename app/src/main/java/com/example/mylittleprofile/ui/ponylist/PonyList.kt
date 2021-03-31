package com.example.mylittleprofile.ui.ponylist

import android.content.Context
import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.StringRequest
import com.example.mylittleprofile.api.PonyApi
import com.example.mylittleprofile.util.ApiRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun PonyList() {
    var message by remember{ mutableStateOf("kanker") }

    val api = PonyApi(LocalContext.current);

    Text(text = message);

    api.getData { resp ->
        message = resp;
    }


    /*
    if (message.isNotEmpty()) {
        Text(text= message)
    } else {
        Text(text= "FRICKING MOOIE PONY LIJST A NIFFO")
    }
     */
}

@Composable
fun LazyRowItemsDemo() {
    LazyColumn {
        items((1..1000).toList()) {
            Text(text = "Item $it")
        }
    }
}