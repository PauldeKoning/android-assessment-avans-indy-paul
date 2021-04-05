package com.example.mylittleprofile.ui.ponylist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylittleprofile.model.CharacterModel

@Composable
fun PonyDetail(pony: CharacterModel) {
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
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

        LazyRow(modifier = Modifier.padding(bottom = 50.dp)) {
            items(pony.image) { url ->
                PonyImage(url);
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