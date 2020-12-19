package com.coderbyte.moviemania.data.session

import android.content.SharedPreferences
import com.coderbyte.moviemania.data.session.Session.Companion.FCM_TOKEN
import com.coderbyte.moviemania.data.session.Session.Companion.REFRESH_TOKEN
import com.coderbyte.moviemania.data.session.Session.Companion.TOKEN
import com.google.gson.GsonBuilder
import javax.inject.Inject

class AppPreference @Inject constructor(private val preferences: SharedPreferences) :
    Session {
    private val gson by lazy { GsonBuilder().create() }

    override var token: String
        get() = getString(TOKEN)
        set(value) {
            write(TOKEN, value)
        }

    override var fcmToken: String
        get() = getString(FCM_TOKEN)
        set(value) {
            write(FCM_TOKEN, value)
        }

    override var refreshToken: String
        get() = getString(REFRESH_TOKEN)
        set(value) {
            write(REFRESH_TOKEN, value)
        }

    private fun write(key: String, value: Any): Boolean {
        with(preferences.edit())
        {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Boolean -> putBoolean(key, value)
            }

            return commit()
        }
    }

    private fun saveObject(key: String, o: Any) {
        write(key, gson.toJson(o))
    }

    private fun getString(key: String): String {
        return preferences.getString(key, "") ?: ""
    }

    private fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    private fun getLong(key: String): Long {
        return preferences.getLong(key, 0)
    }

    private fun getBooleanWithDefValue(key: String, defValue: Boolean): Boolean {
        return preferences.getBoolean(key, defValue)
    }

    private fun removeKey(key: String) {
        with(preferences.edit()) {
            remove(key).apply()
        }
    }
}