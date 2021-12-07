package com.example.towdow

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ForumsViewModel(application: Application): AndroidViewModel(application) {

    var currentUserUID = ""

    fun setUserUID(UID: String) {
        currentUserUID = UID
    }
}