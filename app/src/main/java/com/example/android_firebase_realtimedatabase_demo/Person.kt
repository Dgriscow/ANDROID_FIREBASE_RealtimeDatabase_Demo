package com.example.android_firebase_realtimedatabase_demo

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties //Ignores extra properties that might be retrieved from firebase

data class Person(
    var firstName:String? = null,
    var bio:String? = null
)
