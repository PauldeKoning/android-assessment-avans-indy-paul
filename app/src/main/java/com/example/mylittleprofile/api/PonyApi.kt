package com.example.mylittleprofile.api

import android.content.Context
import android.graphics.Bitmap
import com.example.mylittleprofile.model.CharacterModel
import com.example.mylittleprofile.model.CharactersModel
import com.example.mylittleprofile.util.ApiRequest
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder

import kotlinx.coroutines.*
import com.google.gson.Gson

class PonyApi(private var context: Context) {

    fun getPonyData(id: Int, callback: (CharactersModel) -> Unit) {
        ApiRequest.doRequest(context, "https://ponyweb.ml/v1/character/$id") { resp ->
            callback(Gson().fromJson(resp, CharactersModel::class.java))
        }
    }

    fun getPoniesData(callback: (CharactersModel) -> Unit) {
        ApiRequest.doRequest(context, "https://ponyweb.ml/v1/character/all") { resp ->
            callback(Gson().fromJson(resp, CharactersModel::class.java))
        }
    }

    fun getImageData(url: String, callback: (Bitmap) -> Unit) {
        ApiRequest.doImageRequest(context, url) { resp ->
            callback(resp)
        }
    }

}