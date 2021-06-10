package com.zannardyapps.mynotesapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object DataStoreMenager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "DataSettings")

    //SAVE
    suspend fun saveNote(context: Context, key:String, value: String){
        val prefKey = stringPreferencesKey(key)

        context.dataStore.edit {
            it[prefKey] = value
        }
    }

    //GET
    suspend fun getToNote(context: Context, key: String, default: String = ""):String{
        val prefKey = stringPreferencesKey(key)
        val valueFlow: Flow<String> = context.dataStore.data.map {
            it[prefKey] ?: default
        }
        return valueFlow.first()
    }


}