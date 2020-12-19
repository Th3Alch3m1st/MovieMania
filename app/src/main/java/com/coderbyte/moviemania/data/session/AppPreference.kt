package com.coderbyte.moviemania.data.session

import android.content.SharedPreferences
import com.coderbyte.moviemania.data.model.WishListItem
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class AppPreference @Inject constructor(private val preferences: SharedPreferences) :
    Session {
    private val gson by lazy { GsonBuilder().create() }

    override var wishListItem: MutableList<WishListItem>
        get() {
            val listType = object : TypeToken<MutableList<WishListItem>>() {}.type
            return try {
                Gson().fromJson(
                    getString(Session.WISH_LIST_ITEM),
                    listType
                ) as MutableList<WishListItem>? ?: mutableListOf()
            } catch (ex: Exception) {
                mutableListOf()
            }
        }
        set(value) {
            saveObject(Session.WISH_LIST_ITEM, value)
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