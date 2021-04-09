package com.kseyko.moodtracking.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      02,April,2021      ║
╚════════════════════════════╝
 */

@Entity(tableName = "mood_table")
data class MoodEntity(
    @PrimaryKey(autoGenerate = true)
    var moodId: Long = 0L,
    @ColumnInfo(name = "start_time")
    val startTimeMilli: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "end_time")
    var endTimeMilli: Long = startTimeMilli,
    @ColumnInfo(name = "mood_quality")
    var moodQuality: Int = -1
)
