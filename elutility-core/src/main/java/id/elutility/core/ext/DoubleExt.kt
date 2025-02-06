package id.elutility.core.ext

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

fun Double.toFormat(locale: Locale = Locale("id","ID")): String {
    val nf = NumberFormat.getNumberInstance(locale)
    val formatter = nf as DecimalFormat
    nf.roundingMode = RoundingMode.DOWN
    formatter.applyPattern("#,###,###,###,###")
    return formatter.format(this)
}