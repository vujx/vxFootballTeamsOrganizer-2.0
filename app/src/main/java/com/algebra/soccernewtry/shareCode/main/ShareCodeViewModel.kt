package com.algebra.soccernewtry.shareCode.main

import androidx.lifecycle.ViewModel
import com.algebra.soccernewtry.shareCode.model.ShareCode
import com.algebra.soccernewtry.shareCode.repository.ShareCodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShareCodeViewModel @Inject constructor(private val shareCodeRepository: ShareCodeRepository): ViewModel() {

    fun getAllShareCode() = shareCodeRepository.getAllCodesRepo()

    fun deleteCode(id: Int){
        shareCodeRepository.deleteShareCodeById(id)
    }

    fun deleteAll(){
        shareCodeRepository.deleteAllCodes()
    }

    fun addCode(shareCode: ShareCode){
        shareCodeRepository.addShareCode(shareCode)
    }
}