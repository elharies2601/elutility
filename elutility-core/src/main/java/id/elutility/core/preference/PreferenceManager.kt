package id.elutility.core.preference

import id.elutility.core.ext.emptyString

interface PreferenceManager {
    fun getBoolean(key: String): Boolean
    fun getFloat(key: String, default: Float = 0f): Float
    fun getDouble(key: String, default: Double = 0.0): Double
    fun getInt(key: String, default: Int = 0): Int
    fun getLong(key: String, default: Long = 0): Long
    fun getString(key: String, default: String = emptyString()): String
    fun<T> getObject(key: String): T?
    fun clear()
    fun remove(key: String)
    fun save(key: String, value: Any)
}