package com.nixsolutions.firebaseui

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("id") val id: String,
    @SerializedName("counter") val counter: Int?
)