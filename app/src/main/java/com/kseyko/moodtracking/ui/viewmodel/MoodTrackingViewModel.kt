package com.kseyko.moodtracking.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.kseyko.moodtracking.database.MoodDao
import com.kseyko.moodtracking.database.MoodEntity
import kotlinx.coroutines.launch

class MoodTrackingViewModel(
    private val moodDao: MoodDao,
    context: Application
) : ViewModel() { // Viewmodel ile degistir?
    private var lastMood = MutableLiveData<MoodEntity?>()
    val moods = moodDao.getAllMood()

    private val _navQualityFragment = MutableLiveData<MoodEntity?>()
    private val _navDetailFragment = MutableLiveData<Long?>()

    val navQualityFragment: LiveData<MoodEntity?> = _navQualityFragment
    val navDetailFragment: LiveData<Long?> = _navDetailFragment

    init {
        lastMoodIsFirstMood()
    }

//    val moodString = Transformations.map(moods){ moods->
//        convertMoodtoHtml(moods,context.resources)
//    }

    fun navFinished() {
        _navQualityFragment.value = null
        _navDetailFragment.value = null
    }

    private suspend fun getLastMoodFromDatabase(): MoodEntity? {
        var moodEntity = moodDao.getLastMood()

        if (moodEntity?.startTimeMilli != moodEntity?.endTimeMilli)
            moodEntity = null
        return moodEntity
    }

    private fun lastMoodIsFirstMood() {
        viewModelScope.launch {
            lastMood.value = getLastMoodFromDatabase()
        }
    }

    fun startMood() {
        viewModelScope.launch {
            val newMood = MoodEntity()
            lastMood.value = newMood
            moodDao.addMood(newMood)
        }
    }

    fun finishMood() {
        viewModelScope.launch {
            val lastMood = moodDao.getLastMood() ?: return@launch

            lastMood.endTimeMilli = System.currentTimeMillis()
            moodDao.updateMood(lastMood)
            _navQualityFragment.value = lastMood
        }
    }

    fun deleteAllMood() {
        viewModelScope.launch {
            moodDao.deleteAllMood()
        }
    }

    fun clickListener(id: Long) {
        _navDetailFragment.value = id
    }

    val startButtonIsEnable = Transformations.map(lastMood) {
        it == null
    }

    val finishButtonIsEnable = Transformations.map(lastMood) {
        it != null
    }

    val clearButtonIsEnable = Transformations.map(moods) {
        it.isNotEmpty()
    }


}