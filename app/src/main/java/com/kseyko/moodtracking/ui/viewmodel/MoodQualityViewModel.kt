package com.kseyko.moodtracking.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kseyko.moodtracking.database.MoodDao
import kotlinx.coroutines.launch

class MoodQualityViewModel(private val moodDao: MoodDao, private val moodId: Long) :
    ViewModel() {

    private val _navTrackingFragment = MutableLiveData<Boolean?>()

    val navTrackingFragment: LiveData<Boolean?>
        get() = _navTrackingFragment

    fun navFinished() {
        _navTrackingFragment.value = null
    }

    fun chooseMood(moodQuality: Int) {
        viewModelScope.launch {
            val mood = moodDao.getMood(moodId) ?: return@launch
            mood.moodQuality = moodQuality
            moodDao.updateMood(mood)
            _navTrackingFragment.value = true
        }
    }

}