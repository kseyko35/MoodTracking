package com.kseyko.moodtracking.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kseyko.moodtracking.database.MoodDao
import com.kseyko.moodtracking.database.MoodEntity
import kotlinx.coroutines.launch

class MoodDetailViewModel(
    private val moodId: Long = 0L,
    val moodDao: MoodDao
) : ViewModel() {

    private val _navTrackingFragment = MutableLiveData<Boolean?>()
    val navTrackingFragment: LiveData<Boolean?> = _navTrackingFragment

    val mood: LiveData<MoodEntity?> = moodDao.getMoodLive(moodId)

    fun navFinished() {
        _navTrackingFragment.value = null
    }

    fun deleteMood() {
        viewModelScope.launch {
            val mood = moodDao.getMood(moodId) ?: return@launch
            moodDao.deleteMood(mood)
            _navTrackingFragment.value = true
        }
    }

    fun onClickClose() {
        _navTrackingFragment.value = true
    }

}