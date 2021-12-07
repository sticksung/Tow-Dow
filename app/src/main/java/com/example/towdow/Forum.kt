package com.example.towdow

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Forum(val name: String? = "", val description: String? = "", val users: ArrayList<String> = ArrayList<String>()) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}