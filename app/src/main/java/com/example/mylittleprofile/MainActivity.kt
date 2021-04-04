package com.example.mylittleprofile

import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.example.mylittleprofile.ui.theme.MyLittleProfileTheme


class MainActivity : ComponentActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    private val currentTheme = MutableLiveData("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
            Do intent stuff
         */
        val intent = intent
        var uri: Uri? = intent.data
        if (uri == null) {
            uri = Uri.EMPTY;
        }

        /*
            Do preference stuff
         */
        val sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(applicationContext)

        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        onSharedPreferenceChanged(sharedPreferences, "theme")

        setContent {
            val theme by currentTheme.observeAsState()
            MyLittleProfileTheme(darkTheme = when(theme) {
                "light" -> false
                "dark" -> true
                else -> isSystemInDarkTheme()
            }) {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    AppNavigation(uri!!)
                }
            }
        }


    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (sharedPreferences != null && key == "theme") {
            currentTheme.value = sharedPreferences.getString(key, "system")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Column {
        Text(text = "Hello $name!", textAlign = TextAlign.Center)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyLittleProfileTheme {
        Greeting("Android")
    }
}