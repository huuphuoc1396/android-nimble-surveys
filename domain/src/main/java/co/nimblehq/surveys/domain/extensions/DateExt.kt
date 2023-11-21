package co.nimblehq.surveys.domain.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Date.format(pattern: String, timeZone: TimeZone? = null): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    timeZone?.let { formatter.timeZone = timeZone }
    return formatter.format(this)
}

fun String.parse(pattern: String, timeZone: TimeZone? = null): Date? {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    timeZone?.let { formatter.timeZone = timeZone }
    return try {
        formatter.parse(this)
    } catch (e: Exception) {
        null
    }
}
