package com.example.mylittleprofile

import androidx.fragment.app.Fragment
import java.security.InvalidParameterException

enum class Screen {  }

fun Fragment.navigate(to: Screen, from: Screen) {
    if (to == from) {
        throw InvalidParameterException("Can't navigate to $to")
    }

    when (to) {
        //hier iets doen ofzo met navigation?
    }
}