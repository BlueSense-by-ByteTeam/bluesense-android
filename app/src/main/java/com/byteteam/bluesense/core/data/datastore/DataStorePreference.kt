package com.byteteam.bluesense.core.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStorePreference @Inject constructor(@ApplicationContext private val context: Context){
    private val AUTH_TOKEN = stringPreferencesKey("LOGIN_TOKEN")
    private val FIRST_OPEN = booleanPreferencesKey("FIRST_OPEN")
    private val USER_ID = stringPreferencesKey("USER_ID")
    fun getAuthToken(): Flow<String> = context.datastore.data.map { it[AUTH_TOKEN] ?: "" }
    fun getUserId(): Flow<String> = context.datastore.data.map { it[USER_ID] ?: "" }
    fun getFirstOpenStatus(): Flow<Boolean> = context.datastore.data.map { it[FIRST_OPEN] ?: true }
    suspend fun setAuthToken(token: String) = context.datastore.edit { it[AUTH_TOKEN] = token }
    suspend fun setUserId(token: String) = context.datastore.edit { it[USER_ID] = token }
    suspend fun setFirstOpenStatus() = context.datastore.edit { it[FIRST_OPEN] = false }
    companion object {
        @Volatile
        var INSTANCE: DataStorePreference? = null
        fun getInstances(context: Context): DataStorePreference = INSTANCE ?: synchronized(this) {
            val instance = DataStorePreference(context)
            INSTANCE = instance
            instance
        }
    }
}