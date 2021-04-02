package com.example.mylittleprofile.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationServices

class ProfileViewModel() : ViewModel() {
    val currentLocation: MutableLiveData<Address> by lazy {
        MutableLiveData<Address>()
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation(context: Context) {
        val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
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