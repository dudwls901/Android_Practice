package com.clean.data.repository.remote.datasourceimpl

import com.clean.data.remote.api.LoveCalculatorApi
import com.clean.data.remote.model.DataLoveResponse
import com.clean.data.repository.remote.datasource.MainDataSource
import com.clean.data.utils.base.BaseDataSource
import com.clean.domain.utils.RemoteErrorEmitter
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class MainDataSourceImpl @Inject constructor(
    private val loveCalculatorApi: LoveCalculatorApi,
    private val firebaseRtDb: FirebaseDatabase,
    private val firestore: FirebaseFirestore
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

    override fun getStatistics(): Task<DataSnapshot> {
        return firebaseRtDb.reference.child("statistics").get()
    }

    override fun setStatistics(plusValue: Int): Task<Void> {
        return firebaseRtDb.reference.child("statistics").setValue(plusValue)
    }

}