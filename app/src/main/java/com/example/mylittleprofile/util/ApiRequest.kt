package com.example.mylittleprofile.util

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*

class ApiRequest {

    companion object {
        fun doRequest(context: Context, url: String, callback: (String) -> Unit) {
            val cache = DiskBasedCache(context.cacheDir, 1024 * 1024)

            val network = BasicNetwork(HurlStack())

            val requestQueue = RequestQueue(cache, network).apply {
                start()
            }

            val stringRequest = StringRequest(Request.Method.GET, url,
                { response ->
                    callback(response.toString())
                },
                { error ->
                    Log.d("ERROR", error.toString())
                })

            requestQueue.add(stringRequest)
        }

        fun doImageRequest(context: Context, url: String, callback: (Bitmap) -> Unit) {
            val cache = DiskBasedCache(context.cacheDir, 1024 * 1024)

            val network = BasicNetwork(HurlStack())

            val requestQueue = RequestQueue(cache, network).apply {
                start()
            }

            val imageRequest = ImageRequest(
                url,
                {bitmap ->
                    callback(bitmap)
                },
                0,
                0,
                ImageView.ScaleType.FIT_START,
                Bitmap.Config.ARGB_8888,
                {error->
                    Log.d("ERROR", error.toString())
                }
            )

            requestQueue.add(imageRequest)
        }
    }


}