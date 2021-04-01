package com.example.mylittleprofile.ui.ponylist

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.example.mylittleprofile.api.PonyApi
import com.example.mylittleprofile.model.CharacterModel
import com.example.mylittleprofile.model.CharactersModel

class PonyListViewModel(context: Context) : ViewModel() {
    private val api = PonyApi(context);
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    var favouritePony by mutableStateOf(0)

    fun setYourFavouritePony(id: Int) {
        val editor = sharedPreferences.edit();
        editor.putInt("FAVOURITE_PONY", id)
        editor.apply();
        favouritePony = id;
    }

    fun getYourFavouritePony(): Int {
        if (favouritePony == 0) {
            favouritePony = sharedPreferences.getInt("FAVOURITE_PONY", 0)
        }
        return favouritePony;
    }

    fun getPonyData(callback: (List<CharacterModel>) -> Unit) {
        api.getPonyData { resp ->
            callback(resp.data);
        }
    }

}