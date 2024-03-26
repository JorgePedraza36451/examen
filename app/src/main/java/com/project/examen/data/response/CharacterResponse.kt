package com.project.examen.data.response

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info")
    val info: InfoResponse? = null,
    @SerializedName("results")
    val results: List<ResultResponse>? = null
)