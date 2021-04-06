package com.example.mylittleprofile.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.example.mylittleprofile.api.PonyApi
import com.example.mylittleprofile.model.CharacterModel

class HomeViewModel(context: Context) : ViewModel() {
    val currentLocation: MutableLiveData<Address> by lazy {
        MutableLiveData<Address>()
    }
    private val api = PonyApi(context)
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

    @SuppressLint("MissingPermission")
    fun getLastLocation(context: Context) {
        val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        }
        if (location != null) {
            val addresses = Geocoder(context).getFromLocation(location.latitude, location.longitude, 1)
            if (addresses.size > 0) {
                this.currentLocation.value = addresses.first()
            }
        }
    }

}