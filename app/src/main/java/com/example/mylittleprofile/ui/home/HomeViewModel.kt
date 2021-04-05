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
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            val addresses = Geocoder(context).getFromLocation(location.latitude, location.longitude, 1)
            if (addresses.size > 0) {
                this.currentLocation.value = addresses.first()
            }
        }

        // https://developers.google.com/android/reference/com/google/android/gms/location/FusedLocationProviderClient.html
        // FusedClient is supposed to be safer but it's slower and less precise??

//        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener { location -> // GPS location can be null if GPS is switched off
//                if (location != null) {
//                    val addresses = Geocoder(context).getFromLocation(location.latitude, location.longitude, 1)
//                    if (addresses.size > 0) {
//                        this.currentLocation.value = addresses.first()
//                    }
//                }
//            }
//            .addOnFailureListener { e ->
//                Log.d("MapDemoActivity", "Error trying to get last GPS location")
//                e.printStackTrace()
//            }
    }

}