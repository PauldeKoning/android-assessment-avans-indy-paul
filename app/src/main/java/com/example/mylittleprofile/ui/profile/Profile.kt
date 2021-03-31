package com.example.mylittleprofile.ui.profile

import android.content.Context
import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.mylittleprofile.api.PonyApi
import com.example.mylittleprofile.util.ApiRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun Profile() {
    var message by remember { mutableStateOf("Profile") }

    val api = PonyApi(LocalContext.current);

    Text(text = message);
//
//    api.getData { resp ->
//        message = resp.status.toString();
//    }
}
