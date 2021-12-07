package com.example.towdow

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase

class MyViewModel: ViewModel() {
    var forums = MutableLiveData<ArrayList<TowDowData>>()
    var forumItems: java.util.ArrayList<TowDowData>? = null

    init{
        forums.value = forumItems
        forumItems = java.util.ArrayList<TowDowData>()

    }
}