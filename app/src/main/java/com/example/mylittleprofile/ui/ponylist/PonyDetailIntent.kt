package com.example.mylittleprofile.ui.ponylist

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.mylittleprofile.model.CharacterModel

@Composable
fun PonyDetailIntent(id: Int) {
    val viewModel = PonyDetailIntentViewModel(LocalContext.current, id)

    val ponyList = remember { mutableStateListOf<CharacterModel>() }

    if(ponyList.isEmpty()) {
        viewModel.getPonyData { resp ->
            ponyList.add(resp);
        }

        Text("Hey there! We're fetching your pony!")
    } else {
        PonyDetail(pony = ponyList[0])
    }
    
}