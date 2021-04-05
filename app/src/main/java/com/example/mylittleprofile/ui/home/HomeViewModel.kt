package com.example.mylittleprofile.ui.home

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.example.mylittleprofile.api.PonyApi
import com.example.mylittleprofile.model.CharacterModel

class HomeViewModel(context: Context) : ViewModel() {

    private val api = PonyApi(context);
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getPonyInfo(callback: (CharacterModel?) -> Unit) {
        val favouritePony = sharedPreferences.getInt("FAVOURITE_PONY", 0)

        if (favouritePony == 0) {
            callback(null)
            return
        }

        api.getPonyData(favouritePony) { resp ->
            callback(resp.data[0])
        }

    }

}