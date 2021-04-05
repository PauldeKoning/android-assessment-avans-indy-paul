package com.example.mylittleprofile.ui.ponylist

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.example.mylittleprofile.api.PonyApi
import com.example.mylittleprofile.model.CharacterModel

class PonyListViewModel(context: Context) : ViewModel() {
    private val api = PonyApi(context)
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private var favouritePony by mutableStateOf(0)

    fun setYourFavouritePony(id: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("FAVOURITE_PONY", id)
        editor.apply()
        favouritePony = id
    }

    fun getYourFavouritePony(): Int {
        if (favouritePony == 0) {
            favouritePony = sharedPreferences.getInt("FAVOURITE_PONY", 0)
        }
        return favouritePony
    }

    fun getPonyData(callback: (List<CharacterModel?>) -> Unit) {
        api.getPoniesData { resp ->
            callback(resp.data)
        }
    }

}