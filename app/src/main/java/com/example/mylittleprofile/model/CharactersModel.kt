package com.example.mylittleprofile.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class CharactersModel (
    var status: Int,
    var data: List<CharacterModel?>
)

@Parcelize
data class CharacterModel (
    var id: Int,
    var name: String,
    var alias: String?,
    var url: String,
    var sex: String,
    var residence: String?,
    var occupation: String?,
    var kind: List<String>,
    var image: List<String>,
) : Parcelable
