package com.example.mylittleprofile.ui.ponylist

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mylittleprofile.ui.theme.Nord3
import com.example.mylittleprofile.ui.theme.Nord5

@Composable
fun PonyImage(url: String) {
    var imageBitmapRemember by remember { mutableStateOf<ImageBitmap>(ImageBitmap(1, 1, ImageBitmapConfig.Argb8888)) }

    val viewModel = PonyImageViewModel(LocalContext.current)

    if(imageBitmapRemember.width == 1) {
        viewModel.loadImageFromURL(url) { resp ->
            imageBitmapRemember = resp.asImageBitmap()
        }
    }

    Image(
        imageBitmapRemember,
        "Pony",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .height(200.dp)
            .width(200.dp)
            .clip(RoundedCornerShape(6.dp))
            .border(1.dp, Nord5, RoundedCornerShape(6.dp))
    )
}