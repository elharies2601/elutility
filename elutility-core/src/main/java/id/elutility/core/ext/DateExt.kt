package id.elutility.core.ext

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getCurrentTime(): Date {
    return Date()
}

fun Date.toString(f: String, locale: Locale = Locale.getDefault()): String {
    val simpleDateFormat = SimpleDateFormat(f, locale)
    return simpleDateFormat.format(this)
}