package com.kseyko.moodtracking

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.kseyko.moodtracking.database.MoodEntity
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      03,April,2021      ║
╚════════════════════════════╝
 */
private val ONE_MINUTE_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES)
private val ONE_HOUR_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

fun convertDurationToFormatted(startTimeMilli: Long, endTimeMilli: Long, res: Resources): String {
    val durationMilli = endTimeMilli - startTimeMilli
    val weekdayString = SimpleDateFormat("EEEE", Locale.getDefault()).format(startTimeMilli)
    return when {
        durationMilli < ONE_MINUTE_MILLIS -> {
            val seconds = TimeUnit.SECONDS.convert(durationMilli, TimeUnit.MILLISECONDS)
            res.getString(R.string.seconds_length, seconds, weekdayString)
        }
        durationMilli < ONE_HOUR_MILLIS -> {
            val minutes = TimeUnit.MINUTES.convert(durationMilli, TimeUnit.MILLISECONDS)
            res.getString(R.string.minutes_length, minutes, weekdayString)
        }
        else -> {
            val hours = TimeUnit.HOURS.convert(durationMilli, TimeUnit.MILLISECONDS)
            res.getString(R.string.hours_length, hours, weekdayString)
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun miliSecondsToDate(milisecond: Long): String {
    return SimpleDateFormat("EEEE dd-MMM-yyyy' Time: ' HH:mm").format(milisecond).toString()
}

fun convertNumericQualityToString(mood: Int, resources: Resources): String {
    var qualityString = resources.getString(R.string.mood_5)
    when (mood) {
        -1 -> qualityString = "--"
        0 -> qualityString = resources.getString(R.string.mood_0)
        1 -> qualityString = resources.getString(R.string.mood_1)
        2 -> qualityString = resources.getString(R.string.mood_2)
        3 -> qualityString = resources.getString(R.string.mood_3)
        4 -> qualityString = resources.getString(R.string.mood_4)
        6 -> qualityString = resources.getString(R.string.mood_6)
        7 -> qualityString = resources.getString(R.string.mood_7)
        8 -> qualityString = resources.getString(R.string.mood_8)
    }
    return qualityString
}

fun convertMoodtoHtml(duygular: List<MoodEntity>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.header))
        duygular.forEach {
            append("<br>")
            append(resources.getString(R.string.start_time))
            append("\t${miliSecondsToDate(it.startTimeMilli)}<br>")
            if (it.endTimeMilli != it.startTimeMilli) {
                val ms = it.endTimeMilli.minus(it.startTimeMilli)
                val sc = ms / 1000
                val mn = sc / 60
                val hr = mn / 60

                append(resources.getString(R.string.finish_time))
                append("\t${miliSecondsToDate(it.endTimeMilli)}<br>")
                append(resources.getString(R.string.mood))
                append("\t${convertNumericQualityToString(it.moodQuality, resources)}<br>")
                append(resources.getString(R.string.mood_duration))
                // Hours
                append("\t ${hr}:")
                // Minutes
                append("${mn % 60}:")
                // Seconds
                append("${sc % 60}<br><br>")
            }
        }
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}
