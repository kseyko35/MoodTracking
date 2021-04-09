package com.kseyko.moodtracking.ui.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kseyko.moodtracking.database.MoodDao
import com.kseyko.moodtracking.ui.viewmodel.MoodTrackingViewModel


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      03,April,2021      ║
╚════════════════════════════╝
 */
@Suppress("UNCHECKED_CAST")
class MoodTrackingViewModelFactory(
    private val moodDao: MoodDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoodTrackingViewModel::class.java))
            return MoodTrackingViewModel(moodDao, application) as T
        throw IllegalAccessException("ViewModel is unknown")
    }

}