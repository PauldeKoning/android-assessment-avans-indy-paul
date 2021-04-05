package com.example.mylittleprofile.ui.ponylist

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mylittleprofile.model.CharacterModel

@Composable
fun PonyDetailIntent(id: Int) {
    val viewModel = PonyDetailIntentViewModel(LocalContext.current, id)

    val ponyList = remember { mutableStateListOf<CharacterModel>() }

    val (isFetched, setFetched) = remember { mutableStateOf(false) }

    if(ponyList.isEmpty() && !isFetched) {
        viewModel.getPonyData { resp ->
            if (resp != null) {
                ponyList.add(resp)
            }
            setFetched(true)
        }

        Text("Hey there! We're fetching your pony!", Modifier.padding(16.dp))
    } else if(ponyList.isNotEmpty() && isFetched) {
        PonyDetail(pony = ponyList[0])
    } else {
        PonyDetailIntentError()
    }
}

@Composable
fun PonyDetailIntentError() {
    Text("We couldn't fetch your pony.", Modifier.padding(16.dp))
}