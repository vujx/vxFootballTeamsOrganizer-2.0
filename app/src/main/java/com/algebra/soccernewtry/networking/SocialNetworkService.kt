package com.algebra.soccernewtry.networking

import com.algebra.soccernewtry.networking.model.ModelJson
import retrofit2.http.*

const val downloadToken = "53cr37d0wnl04d70k3n4fuc4"

interface SocialNetworkService {

    @GET("/api/file/download")
    fun getCode(
        @Query("token") token: String,
        @Query("code") code: String): retrofit2.Call<String>

    @Headers("ContentType: text/plain")
    @POST("/api/file/upload?token=7h15157h3b3770k3n3v3rf0r3v3r")
    fun postCode(@Body value: String): retrofit2.Call<ModelJson>

    @DELETE("/api/file/delete")
    fun deleteCode(
        @Query("token") token: String,
        @Query("code") code: String): retrofit2.Call<Void>

}