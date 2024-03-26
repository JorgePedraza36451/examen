package com.project.examen.data.response

import com.google.gson.annotations.SerializedName

data class InfoResponse(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("pages")
    val pages: Int? = null,
    @SerializedName("prev")
    val prev: String? = null
)