package com.example.mylittleprofile.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mylittleprofile.PreferencesActivity

@Composable
fun Settings() {
    val context = LocalContext.current

    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Button(
            onClick = {
                val intent = Intent(context, PreferencesActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.padding(bottom = 16.dp),
        ) {

            if (MaterialTheme.colors.isLight) {
                Icon(Icons.Filled.LightMode, "", modifier = Modifier.padding(end = 6.dp))
            } else {
                Icon(Icons.Filled.DarkMode, "", modifier = Modifier.padding(end = 6.dp))
            }
            Text("Theme Settings")
        }

        Button(onClick = {
            val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            context.startActivity(intent)
        }) {
            Icon(Icons.Filled.Info, "", modifier = Modifier.padding(end = 6.dp))
            Text("Open App Details")
        }
    }
}