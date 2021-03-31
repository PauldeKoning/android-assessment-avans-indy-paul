package com.example.mylittleprofile.util

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*

class ApiRequest() {

    companion object {
        fun doRequest(context: Context, url: String, callback: (String) -> Unit) {
            val cache = DiskBasedCache(context.cacheDir, 1024 * 1024) // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
            val network = BasicNetwork(HurlStack())

// Instantiate the RequestQueue with the cache and network. Start the queue.
            val requestQueue = RequestQueue(cache, network).apply {
                start()
            }

// Formulate the request and handle the response.
            val stringRequest = StringRequest(Request.Method.GET, url,
                { response ->
                    callback(response.toString());
                },
                { error ->
                    // Handle error
                    Log.d("TEST", error.toString())
                })

// Add the request to the RequestQueue.
            requestQueue.add(stringRequest)

            Log.d("TEST", "Opened!")
        }
    }


}