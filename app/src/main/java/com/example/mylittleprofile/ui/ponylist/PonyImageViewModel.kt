package com.example.mylittleprofile.ui.ponylist

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.mylittleprofile.api.PonyApi

class PonyImageViewModel(context: Context) : ViewModel() {


    private val api = PonyApi(context)

    fun loadImageFromURL(url: String, callback: (Bitmap) -> Unit) {
        api.getImageData(url) { resp ->
            callback(resp)
        }
    }

}