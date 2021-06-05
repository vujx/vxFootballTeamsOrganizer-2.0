package com.algebra.soccernewtry.networking.model

import com.squareup.moshi.Json

data class ModelJson(
    @field: Json(name = "success")
    val success: Boolean,

    @field:Json(name = "code")
    val code: String

)