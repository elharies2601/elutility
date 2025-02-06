package id.elutility.core.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.elutility.core.ext.emptyString

class PreferenceManagerImpl(private val context: Context): PreferenceManager {

    private fun sharedPref(): SharedPreferences {
        return androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun getBoolean(key: String): Boolean {
        return sharedPref().getBoolean(key, false)
    }

    override fun getFloat(key: String, default: Float): Float {
        return sharedPref().getFloat(key, default)
    }

    override fun getDouble(key: String, default: Double): Double {
        return Double.fromBits(getLong(key, 0L))
    }

    override fun getInt(key: String, default: Int): Int {
        return sharedPref().getInt(key, default)
    }

    override fun getLong(key: String, default: Long): Long {
        return sharedPref().getLong(key, default)
    }

    override fun getString(key: String, default: String): String {
        return sharedPref().getString(key, default) ?: emptyString()
    }

    override fun <T> getObject(key: String): T? {
        val objectStrings = getString(key, emptyString())
        return Gson().fromJson(objectStrings, object : TypeToken<T>(){}.type)
    }

    override fun clear() {
        sharedPref().edit(commit = true) {
            clear()
        }
    }

    override fun remove(key: String) {
        sharedPref().edit(commit = true) {
            remove(key)
        }
    }

    override fun save(key: String, value: Any) {
        sharedPref().edit(commit = true) {
            when(value) {
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                is Double -> putLong(key, value.toRawBits())
            }
        }
    }
}