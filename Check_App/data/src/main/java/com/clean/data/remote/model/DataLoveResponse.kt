package com.clean.data.remote.model

import com.google.gson.annotations.SerializedName

data class DataLoveResponse(
    @SerializedName("fname") val fName: String,
    @SerializedName("sname") val sName: String,
    @SerializedName("percentage") val percentage: Int,
    @SerializedName("result") val result: String
)