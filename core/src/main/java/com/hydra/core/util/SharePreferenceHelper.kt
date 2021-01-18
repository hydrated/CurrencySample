package com.hydra.core.util

import android.content.Context
import android.content.SharedPreferences

class SharePreferenceHelper constructor(private val context: Context) {

    companion object {
        private const val SHARED_PREF_NAME = "share_pref"
        const val KEY_LAST_CACHE = "last_cache_timestamp"
    }

    private val sharedPreferences: SharedPreferences
        get() = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    fun getLong(key: String): Long {
        return sharedPreferences.getLong(key, 0)
    }

    fun setLong(key: String, long: Long) {
        sharedPreferences.edit().putLong(key, long).apply()
    }
}