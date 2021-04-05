package com.example.mylittleprofile.util

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
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
                    callback(response.toString())
                },
                { error ->
                    // Handle error
                    Log.d("ERROR", error.toString())
                })

// Add the request to the RequestQueue.
            requestQueue.add(stringRequest)
        }

        fun doImageRequest(context: Context, url: String, callback: (Bitmap) -> Unit) {
            val cache = DiskBasedCache(context.cacheDir, 1024 * 1024) // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
            val network = BasicNetwork(HurlStack())

// Instantiate the RequestQueue with the cache and network. Start the queue.
            val requestQueue = RequestQueue(cache, network).apply {
                start()
            }

// Formulate the request and handle the response.
            val imageRequest = ImageRequest(
                url,
                {bitmap -> // response listener
                    callback(bitmap)
                },
                0, // max width
                0, // max height
                ImageView.ScaleType.FIT_START, // image scale type
                Bitmap.Config.ARGB_8888, // decode config
                {error-> // error listener
                    Log.d("ERROR", error.toString())
                }
            )

// Add the request to the RequestQueue.
            requestQueue.add(imageRequest)
        }
    }


}