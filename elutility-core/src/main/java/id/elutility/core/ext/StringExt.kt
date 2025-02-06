package id.elutility.core.ext

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern

fun String.toNumberOnly(): String {
    val regex = Regex("[^0-9,]")
    return regex.replace(this, "")
}

fun String.toDate(format: String, locale: Locale = Locale.getDefault()): Date {
    return try {
        SimpleDateFormat(format, locale).parse(this) ?: Date()
    } catch (e: Exception) {
        Date()
    }
}


fun String.clearComma(): String {
    var string = this
    string = string
        .replace(" ", "")
        .replace(",", "")
        .replace(".", "")
        .replace("I", "")
        .replace("D", "")
        .replace("R", "")
        .replace("p", "")
    return string
}

fun String.capitalize(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.ROOT
        ) else it.toString()
    }
}

fun String.nameAlias(): String {
    val words = this.trim().split(" ").filter { it.isNotEmpty() }
    return when {
        words.isEmpty() -> ""
        words.size == 1 -> {
            val word = words[0]
            if (word.length >= 2) {
                word.take(2).uppercase()
            } else {
                word.uppercase()
            }
        }
        words.size > 2 -> {
            words.take(2).joinToString("") { it.first().uppercase() }
        }
        else -> {
            words.joinToString("") { it.first().uppercase() }
        }
    }
}

fun emptyString() = ""

fun String.isValidEmail(): Boolean {
    val emailPattern = ("^[_A-Za-z0-9-\\+]{3,}+(\\.[_A-Za-z0-9-]{3,}+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,3}){1,3}$")
    val isValidEmail: Boolean
    val pattern = Pattern.compile(emailPattern)
    val matcher: Matcher = pattern.matcher(this)
    isValidEmail = matcher.matches()
    return isValidEmail
}


