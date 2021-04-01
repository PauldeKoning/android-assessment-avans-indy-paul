package com.example.mylittleprofile.ui.ponylist


import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite as Favourite
import androidx.compose.material.icons.outlined.FavoriteBorder as FavouriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.colorspace.ColorSpace
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigate
import com.example.mylittleprofile.*
import com.example.mylittleprofile.R
import com.example.mylittleprofile.api.PonyApi
import com.example.mylittleprofile.model.CharacterModel
import com.example.mylittleprofile.ui.theme.MyLittleProfileTheme

@Composable
fun PonyList(navController: NavController) {
    val viewModel = PonyListViewModel(LocalContext.current);

    val ponyList = remember { mutableStateListOf<CharacterModel>() }

    if(ponyList.isEmpty())
        viewModel.getPonyData { resp ->
            ponyList.addAll(resp);
        }

    LazyColumn {
        items(ponyList) { pony ->
            Column(
                Modifier.clickable(
                    onClick = {
                        navController.currentBackStackEntry
                            ?.arguments?.putParcelable("pony", pony)
                        navController.navigate("ponydetail")
                    }
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(16.dp, 12.dp)
                        .fillMaxWidth()
                ) {
                    Text(pony.name)
                    Row {
                        if (pony.id == viewModel.getYourFavouritePony()) {
                            Icon(Icons.Filled.Favourite, "Icon", modifier = Modifier.zIndex(1F).clickable {
                                viewModel.setYourFavouritePony(0)
                            })
                        } else if (viewModel.getYourFavouritePony() == 0) {
                            Icon(Icons.Outlined.FavouriteBorder, "Icon", modifier = Modifier.zIndex(1F).clickable {
                                viewModel.setYourFavouritePony(pony.id)
                            })
                        }
                        Spacer(Modifier.size(8.dp))
                        Icon(Icons.Filled.ArrowForward, "Icon")
                    }
                }
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}

@Composable
fun PonyDetail(pony: CharacterModel) {
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        PonyDetailInfoRow("Name:", pony.name)
        PonyDetailInfoRow("Gender:", pony.sex)
        PonyDetailInfoRow("Kind:", pony.kind.joinToString(", "))
        if (pony.alias !== null) {
            PonyDetailInfoRow("Alias:", pony.alias!!)
        }
        if (pony.residence !== null) {
            PonyDetailInfoRow("Residence:", pony.residence!!)
        }

        val api = PonyApi(LocalContext.current);

        LazyRow {
            items(pony.image) { url ->
                PonyImage(api, url);
            }
        }
    }

}

@Composable
fun PonyDetailInfoRow(key: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 12.dp)
            .fillMaxWidth()
    ) {
        Text(key, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp))
        Text(value, style = TextStyle(fontSize = 18.sp))
    }
}

@Composable
fun PonyImage(api: PonyApi, url: String) {
    var imageBitmapRemember by remember { mutableStateOf<ImageBitmap>(ImageBitmap(1, 1, ImageBitmapConfig.Argb8888)) }

    if(imageBitmapRemember.width == 1) {
        api.getImageData(url) { resp ->
            imageBitmapRemember = resp.asImageBitmap();
        }
    }

    Image(imageBitmapRemember, "Image", contentScale = ContentScale.Fit)
}