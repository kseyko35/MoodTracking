package com.kseyko.moodtracking.ui.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kseyko.moodtracking.database.MoodDao
import com.kseyko.moodtracking.ui.viewmodel.MoodQualityViewModel


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      03,April,2021      ║
╚════════════════════════════╝
 */
class MoodQualityViewModelFactory(
    private val moodDao: MoodDao,
    private val moodId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoodQualityViewModel::class.java))
            return MoodQualityViewModel(moodDao, moodId) as T
        throw IllegalAccessException("ViewModel is unknown")
    }

}