package com.example.keepnotes.Function

import android.util.Log

class GenericFunction {


    fun CheckAllFields(list: List<String>): Boolean {

        for (element in list) {
            if (element.isEmpty()) {
                Log.d("null", "CheckAllFields: ${element}")
                return false
            }

        }
        return true


    }
}