package com.clean.data.repository

import com.clean.data.mapper.MainMapper
import com.clean.data.repository.remote.datasource.MainDataSource
import com.clean.domain.model.DomainLoveResponse
import com.clean.domain.repository.MainRepository
import com.clean.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainDataSource: MainDataSource
) : MainRepository {
    override suspend fun checkLoveCalculator(
        remoteErrorEmitter: RemoteErrorEmitter,
        host: String,
        key: String,
        mName: String,
        wName: String
    ): DomainLoveResponse? {
        //mapper를 이용해 data 계층의 response를 domain 계층의 response로 변환해 주자
        return MainMapper.loveMapper(mainDataSource.checkLoveCalculator(remoteErrorEmitter, host, key, mName, wName))
    }
}