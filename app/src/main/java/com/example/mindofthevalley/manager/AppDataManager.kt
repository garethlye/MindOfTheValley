package com.example.mindofthevalley.manager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.mindofthevalley.data.Data
import com.example.mindofthevalley.manager.AppDataManager.PreferencesDataKeys.LAST_CONNECTED_DATA
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking


class AppDataManager(private val lastConnectedDataStore: DataStore<Preferences>) {

    private val gson = Gson()

    private object PreferencesDataKeys {
        val LAST_CONNECTED_DATA = stringPreferencesKey(com.example.mindofthevalley.di.LAST_CONNECTED_DATA)
    }

    fun updateLastConnectedData(data: Data?) {
        val jsonData = gson.toJson(data)
        return runBlocking {
            lastConnectedDataStore.edit {
                pref -> pref[LAST_CONNECTED_DATA] = jsonData
            }
        }
    }

    fun getLastConnectedData(): Data? {
        var storedData: Data? = null
        runBlocking {
            val storedDataJson = lastConnectedDataStore.data.map {
                    pref -> pref[LAST_CONNECTED_DATA] ?: ""
            }.first()
            if(storedDataJson.isNotEmpty()) {
                storedData = gson.fromJson(storedDataJson, Data::class.java)
            }
        }
        return storedData
    }
}