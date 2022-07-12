package com.clean.domain.model

import com.google.gson.annotations.SerializedName

data class DomainLoveResponse(
    @SerializedName("fname") val fName: String,
    @SerializedName("sname") val sName: String,
    @SerializedName("percentage") val percentage: Int,
    @SerializedName("result") val result: String
)