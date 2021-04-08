package com.kseyko.moodtracking.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
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
║      03,April,2021      ║
╚════════════════════════════╝
 */
class MoodAdapter(private val cellClickListener: CellClickListener) :
    RecyclerView.Adapter<MoodAdapter.ViewHolder>() {

    var data = listOf<MoodEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, cellClickListener)
    }


    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val moodImage: ImageView = itemView.findViewById(R.id.mood_image)
        private val moodDuration: TextView = itemView.findViewById(R.id.mood_duration)
        private val moodQuality: TextView = itemView.findViewById(R.id.mood_quality)
        private val moodConst: ConstraintLayout = itemView.findViewById(R.id.moodConstraint)

        fun bind(item: MoodEntity, cellClickListener: CellClickListener) {
            val res = itemView.context.resources
            moodDuration.text =
                convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            moodQuality.text = convertNumericQualityToString(item.moodQuality, res)
            moodImage.setImageResource(
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
            moodConst.setOnClickListener {
                cellClickListener.onCellClickListener(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.mood_item, parent, false)
                return ViewHolder(view)
            }
        }
    }

}

interface CellClickListener {
    fun onCellClickListener(mood: MoodEntity)
}