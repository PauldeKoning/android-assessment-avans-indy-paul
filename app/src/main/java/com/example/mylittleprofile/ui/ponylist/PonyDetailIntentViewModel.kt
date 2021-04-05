package com.example.mylittleprofile.ui.ponylist

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mylittleprofile.api.PonyApi
import com.example.mylittleprofile.model.CharacterModel

class PonyDetailIntentViewModel(context: Context, private val id: Int) : ViewModel() {
    private val api = PonyApi(context)

    fun getPonyData(callback: (CharacterModel?) -> Unit) {
        api.getPonyData(id) { resp ->
            if(resp.data.isNullOrEmpty()) {
                callback(null)
            } else {
                callback(resp.data[0])
            }
        }
    }
}