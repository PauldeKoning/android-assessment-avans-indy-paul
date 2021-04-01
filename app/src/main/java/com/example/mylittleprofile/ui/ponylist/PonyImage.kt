package com.example.mylittleprofile.ui.ponylist

import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

@Composable
fun PonyImage(url: String) {
    var imageBitmapRemember by remember { mutableStateOf<ImageBitmap>(ImageBitmap(1, 1, ImageBitmapConfig.Argb8888)) }

    val viewModel = PonyImageViewModel(LocalContext.current)

    if(imageBitmapRemember.width == 1) {
        viewModel.loadImageFromURL(url) { resp ->
            imageBitmapRemember = resp.asImageBitmap();
        }
    }

    Image(imageBitmapRemember, "Pony", contentScale = ContentScale.Fit)
}