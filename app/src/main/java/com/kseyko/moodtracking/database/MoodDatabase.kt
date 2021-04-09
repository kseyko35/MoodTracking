package com.kseyko.moodtracking.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      03,April,2021      ║
╚════════════════════════════╝
 */
@Database(entities = [MoodEntity::class], version = 1, exportSchema = false)
abstract class MoodDatabase : RoomDatabase() {

    abstract val moodDao: MoodDao

    companion object {
        @Volatile
        private var database: MoodDatabase? = null

        fun getDatabaseObject(context: Context): MoodDatabase? {
            synchronized(this) {
                var sampleDatabase = database
                if (sampleDatabase == null) {
                    sampleDatabase =
                        Room.databaseBuilder(
                            context,
                            MoodDatabase::class.java,
                            "mood_table"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    database = sampleDatabase
                }
                return database
            }
        }
    }
}