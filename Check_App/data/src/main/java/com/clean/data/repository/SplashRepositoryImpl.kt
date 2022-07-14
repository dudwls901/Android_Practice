package com.clean.data.repository

import com.clean.data.repository.remote.datasource.SplashDataSource
import com.clean.domain.repository.SplashRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
    private val splashDataSource: SplashDataSource
) : SplashRepository{
    override fun checkAppVersion(): Task<DataSnapshot> {
        return splashDataSource.checkAppVersion()
    }
}