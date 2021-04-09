package com.kseyko.moodtracking

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kseyko.moodtracking.database.MoodDao
import com.kseyko.moodtracking.database.MoodDatabase
import com.kseyko.moodtracking.database.MoodEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      03,April,2021      ║
╚════════════════════════════╝
 */
@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var databaseDao: MoodDao
    private lateinit var database: MoodDatabase

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        database =
            Room.inMemoryDatabaseBuilder(context, MoodDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        databaseDao = database.moodDao
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    @Throws(IOException::class)
    fun addMoodAndRead() {
        runBlocking {
            val mood = MoodEntity()
            databaseDao.addMood(mood)
            val lastMood = databaseDao.getLastMood()
            assertEquals(lastMood?.moodQuality, -1)
        }
    }
}