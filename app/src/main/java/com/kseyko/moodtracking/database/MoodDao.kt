package com.kseyko.moodtracking.database

import androidx.lifecycle.LiveData
import androidx.room.*


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      02,April,2021      ║
╚════════════════════════════╝
 */

@Dao // CRUD
interface MoodDao {

    @Insert
    suspend fun addMood(entity: MoodEntity)

    @Query("Select * from mood_table order by moodId DESC")
    fun getAllMood(): LiveData<List<MoodEntity>>

    @Query("Select * from mood_table where moodId = :moodId")
    suspend fun getMood(moodId: Long): MoodEntity?

    @Query("Select * from mood_table where moodId = :moodId")
    fun getMoodLive(moodId: Long): LiveData<MoodEntity?>

    @Query("Select * from mood_table order by moodId desc limit 1")
    suspend fun getLastMood(): MoodEntity?

    @Update
    suspend fun updateMood(entity: MoodEntity)

    @Query("Delete from mood_table")
    suspend fun deleteAllMood()

//    @Query("Delete from mood_table where moodId = :moodId")
//    fun deleteMood(moodId: Long)

    @Delete
    suspend fun deleteMood(entity: MoodEntity)
}