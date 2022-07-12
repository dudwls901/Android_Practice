package com.clean.data.repository.remote.datasourceimpl

import com.clean.data.remote.api.LoveCalculatorApi
import com.clean.data.remote.model.DataLoveResponse
import com.clean.data.repository.remote.datasource.MainDataSource
import com.clean.data.utils.base.BaseDataSource
import com.clean.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class MainDataSourceImpl @Inject constructor(
    private val loveCalculatorApi: LoveCalculatorApi
) : BaseDataSource(), MainDataSource {
    override suspend fun checkLoveCalculator(
        remoteErrorEmitter: RemoteErrorEmitter,
        host: String,
        key: String,
        mName: String,
        wName: String
    ) : DataLoveResponse? {
        return safeApiCall(remoteErrorEmitter){
            loveCalculatorApi.getPercentage(host = host, key = key, fName = mName, sName = wName)
        }?.body()
    }
}