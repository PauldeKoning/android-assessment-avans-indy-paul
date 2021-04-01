package com.example.mylittleprofile.ui.home

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.example.mylittleprofile.api.PonyApi

class HomeViewModel(context: Context) : ViewModel() {

    private val api = PonyApi(context);
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getPonyInfoURL(callback: (String) -> Unit) {
        val favouritePony = sharedPreferences.getInt("FAVOURITE_PONY", 0)

        if (favouritePony == 0) {
            callback("")
            return
        }

        api.getPonyData(favouritePony) { resp ->
            callback(resp.data[0].url)
        }

    }

}