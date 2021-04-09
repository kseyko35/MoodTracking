package com.kseyko.moodtracking.ui.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kseyko.moodtracking.database.MoodDao
import com.kseyko.moodtracking.ui.viewmodel.MoodDetailViewModel


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
class MoodDetailViewModelFactory(private val args: Long, private val moodDao: MoodDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoodDetailViewModel::class.java))
            return MoodDetailViewModel(args, moodDao) as T
        throw IllegalAccessException("ViewModel is unknown")
    }

}