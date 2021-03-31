package com.example.mylittleprofile.ui.ponylist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.mylittleprofile.api.PonyApi
import com.example.mylittleprofile.model.CharacterModel

@Composable
fun PonyList() {
    var message by remember { mutableStateOf("kanker") }
    var ponyList = remember { mutableStateListOf<CharacterModel>() }

    val api = PonyApi(LocalContext.current);

    Text(text = message);
    api.getData { resp ->
        message = resp.status.toString();
        ponyList.addAll(resp.data)
    }

    PonyRowList(list = ponyList)
}

@Composable
fun PonyRowList(list: List<CharacterModel>) {
    LazyColumn() {
        items(list) { pony ->
            Text(pony.name)
        }
    }
}