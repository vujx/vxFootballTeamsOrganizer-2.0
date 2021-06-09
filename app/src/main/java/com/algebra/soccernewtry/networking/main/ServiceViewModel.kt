package com.algebra.soccernewtry.networking.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.algebra.soccernewtry.networking.repository.ServiceRepository
import com.algebra.soccernewtry.shareCode.main.ShareCodeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(private val serviceRepository: ServiceRepository): ViewModel() {

    var errorObserver: MutableLiveData<String> = MutableLiveData()
    var getCode: MutableLiveData<String> = MutableLiveData()
    var deleteCode: MutableLiveData<String> = MutableLiveData()
    var getValue: MutableLiveData<String> = MutableLiveData()

    fun getCode(value: String){
        Log.d("ispis", value)
        getCode = MutableLiveData()
        serviceRepository.generateCode(value, object: ServiceRepository.Listener{
            override fun onSuccess(code: String) {
                getCode.postValue(code)
            }
            override fun onFailure(error: String) {
                errorObserver.postValue("")
                Log.d("IspisiMiError", error.toString())
            }
        })
    }

    fun deleteCode(code: String){
        deleteCode = MutableLiveData()
        serviceRepository.deleteCode(code, object: ServiceRepository.Listener{
            override fun onSuccess(code: String) {
                deleteCode.postValue(code)
            }

            override fun onFailure(error: String) {
                errorObserver.postValue("")
            }
        })
    }

    fun getValueToGenerateDatabase(code: String){
        getValue = MutableLiveData()
        serviceRepository.getValues(code, object: ServiceRepository.Listener{
            override fun onSuccess(code: String) {
                getValue.postValue(code)
            }

            override fun onFailure(error: String) {
                errorObserver.postValue("")
            }
        })
    }
}