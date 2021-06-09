package com.algebra.soccernewtry.networking.repository

import android.util.Log
import com.algebra.soccernewtry.di.DatabaseIoExecutor
import com.algebra.soccernewtry.networking.SocialNetworkService
import com.algebra.soccernewtry.networking.model.ModelJson
import com.algebra.soccernewtry.shareCode.model.ShareCode
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.Executor
import javax.inject.Inject

class ServiceRepository @Inject constructor(private val socialNetworkService: SocialNetworkService) {

    interface Listener{
        fun onSuccess(code: String)
        fun onFailure(error: String)
    }

    fun generateCode(value: String, listener: Listener){
        socialNetworkService.postCode(value).enqueue(object: retrofit2.Callback<ModelJson>{
            override fun onFailure(call: Call<ModelJson>, t: Throwable) {
                t.message?.let { listener.onFailure(it) }
            }

            override fun onResponse(call: Call<ModelJson>, response: Response<ModelJson>) {
                response.body()?.code?.let { listener.onSuccess(it) }
            }
        })
    }

    fun deleteCode(code: String, listener: Listener){
        socialNetworkService.deleteCode("7h15157h3b3770k3n3v3rf0r3v3r", code).enqueue(object: retrofit2.Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("nijedobro", t.message.toString())

                t.message?.let { listener.onFailure(it) }
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("jeldobro", response.isSuccessful.toString())
                 listener.onSuccess("codara")
            }
        })
    }

    fun getValues(code: String, listener: Listener){
        socialNetworkService.getCode("53cr37d0wnl04d70k3n4fuc4", code).enqueue(object: retrofit2.Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                t.message?.let { listener.onFailure(it) }
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let { listener.onSuccess(it) }
            }
        })
    }
}