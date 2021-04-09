package com.kseyko.moodtracking.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.kseyko.moodtracking.R
import com.kseyko.moodtracking.convertDurationToFormatted
import com.kseyko.moodtracking.convertNumericQualityToString
import com.kseyko.moodtracking.database.MoodEntity


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      04,April,2021      ║
╚════════════════════════════╝
 */
@BindingAdapter("moodDurationFormatted")
fun TextView.setMotionDurationFormatted(item: MoodEntity?) {
    item?.let {
        text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, context.resources)
    }

}


@BindingAdapter("moodQualityString")
fun TextView.setMoodQualityString(item: MoodEntity?) {
    item?.let {
        text = convertNumericQualityToString(item.moodQuality, context.resources)
    }

}


@BindingAdapter("moodImage")
fun ImageView.setSleepImage(item: MoodEntity?) {
    item?.let {
        setImageResource(
            when (item.moodQuality) {
                0 -> R.drawable.food
                1 -> R.drawable.angry
                2 -> R.drawable.anxious
                3 -> R.drawable.crying1
                4 -> R.drawable.laughing
                5 -> R.drawable.expressionless
                6 -> R.drawable.thinking
                7 -> R.drawable.fall_in_love
                8 -> R.drawable.sleeping
                else -> R.drawable.hug
            }
        )
    }

}