package com.clean.data.repository.remote.datasourceimpl

import com.clean.data.repository.remote.datasource.SplashDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class SplashDataSourceImpl @Inject constructor(
    private val firebaseRtDb : FirebaseDatabase,
    private val fireStore : FirebaseFirestore
) : SplashDataSource {
    override fun checkAppVersion(): Task<DataSnapshot> {
        return firebaseRtDb.reference.child("version").get()
    }
}