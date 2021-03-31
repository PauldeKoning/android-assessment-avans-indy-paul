package com.example.mylittleprofile.api

import android.content.Context
import com.example.mylittleprofile.model.CharacterModel
import com.example.mylittleprofile.model.CharactersModel
import com.example.mylittleprofile.util.ApiRequest
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder

import kotlinx.coroutines.*
import com.google.gson.Gson

class PonyApi(private var context: Context) {

    fun getData(callback: (CharactersModel) -> Unit) {
        ApiRequest.doRequest(context, "https://ponyweb.ml/v1/character/all") { resp ->
            val bruh = Gson().fromJson(resp, CharactersModel::class.java)
            callback(bruh)
        }
    }

}