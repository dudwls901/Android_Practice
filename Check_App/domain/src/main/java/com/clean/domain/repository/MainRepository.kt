package com.clean.domain.repository

import com.clean.domain.model.DomainLoveResponse
import com.clean.domain.utils.RemoteErrorEmitter

interface MainRepository {
    suspend fun checkLoveCalculator(
        remoteErrorEmitter: RemoteErrorEmitter,
        host: String,
        key: String,
        //fName = 남자 이름
        mName: String,
        //sName = 여자 이름
        wName: String
    ) : DomainLoveResponse?
}