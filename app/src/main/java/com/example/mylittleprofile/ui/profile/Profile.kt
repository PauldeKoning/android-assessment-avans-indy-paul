package com.example.mylittleprofile.ui.profile

import android.content.Intent
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.mylittleprofile.PreferencesActivity
import com.example.mylittleprofile.api.PonyApi

@Composable
fun Profile() {
    var message by remember { mutableStateOf("Profile") }

    val localContext = LocalContext.current
    val api = PonyApi(localContext);

    Text(text = message);

    Button(onClick = {
        val intent = Intent(localContext, PreferencesActivity::class.java)
        localContext.startActivity(intent)
    }) {
        Text("Open settings")
    }
}
