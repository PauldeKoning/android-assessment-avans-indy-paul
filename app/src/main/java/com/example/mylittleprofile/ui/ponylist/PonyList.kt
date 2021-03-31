package com.example.mylittleprofile.ui.ponylist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import java.time.Duration
import java.time.LocalDateTime
import java.time.OffsetDateTime

@Composable
fun PonyList() {
    Text(text= "FRICKING MOOIE PONY LIJST A NIFFO")
}

@Composable
fun LazyRowItemsDemo() {
    LazyColumn {
        items((1..1000).toList()) {
            Text(text = "Item $it")
        }
    }
}