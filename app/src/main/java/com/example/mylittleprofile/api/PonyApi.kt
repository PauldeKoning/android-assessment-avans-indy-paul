package com.example.mylittleprofile.api

import android.content.Context
import com.example.mylittleprofile.util.ApiRequest

import kotlinx.coroutines.*


class PonyApi(private var context: Context) {

    fun getData(callback: (String) -> Unit) {
        ApiRequest.doRequest(context, "https://ponyweb.ml/v1/character/all") { resp ->
            callback(resp)
        }
    }

}