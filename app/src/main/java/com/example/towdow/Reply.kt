package com.example.towdow

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Reply(val reply: String? = "", val username: String? = "") {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}