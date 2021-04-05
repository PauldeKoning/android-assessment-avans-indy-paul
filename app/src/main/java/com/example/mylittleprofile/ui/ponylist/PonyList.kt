package com.example.mylittleprofile.ui.ponylist


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.mylittleprofile.model.CharacterModel
import androidx.compose.material.icons.filled.Favorite as Favourite
import androidx.compose.material.icons.outlined.FavoriteBorder as FavouriteBorder

@Composable
fun PonyList(navController: NavController) {
    val viewModel = PonyListViewModel(LocalContext.current)

    val ponyList = remember { mutableStateListOf<CharacterModel>() }

    if(ponyList.isEmpty()) {
        viewModel.getPonyData { resp ->
            ponyList.addAll(resp)
        }
    }

    var searchInput by remember { mutableStateOf("") }

    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchInput,
            onValueChange = { value -> searchInput = value },
            label = { Text("Search") },
            leadingIcon = { Icon(Icons.Filled.Search, "Search Icon") }
        )

        LazyColumn {
            items(ponyList.filter {
                    p -> p.name.toLowerCase().contains(searchInput.toLowerCase())
            }) { pony ->
                Column(
                    Modifier.clickable(
                        onClick = {
                            navController.currentBackStackEntry?.arguments?.putParcelable("pony", pony)
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
                                Icon(Icons.Filled.Favourite, "Icon", modifier = Modifier
                                    .zIndex(1F)
                                    .clickable {
                                        viewModel.setYourFavouritePony(0)
                                    })
                            } else if (viewModel.getYourFavouritePony() == 0) {
                                Icon(Icons.Outlined.FavouriteBorder, "Icon", modifier = Modifier
                                    .zIndex(1F)
                                    .clickable {
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
}
