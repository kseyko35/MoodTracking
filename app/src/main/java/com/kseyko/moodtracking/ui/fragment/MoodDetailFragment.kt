package com.kseyko.moodtracking.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kseyko.moodtracking.R
import com.kseyko.moodtracking.database.MoodDatabase
import com.kseyko.moodtracking.databinding.MoodDetailFragmentBinding
import com.kseyko.moodtracking.ui.viewmodel.MoodDetailViewModel
import com.kseyko.moodtracking.ui.viewmodelfactory.MoodDetailViewModelFactory

class MoodDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: MoodDetailFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.mood_detail_fragment, container, false)


        val args = MoodDetailFragmentArgs.fromBundle(requireArguments())
        val moodDao = MoodDatabase.getDatabaseObject(requireContext())!!.moodDao

        val viewModelFactory = MoodDetailViewModelFactory(args.moodId, moodDao)
        val moodDetailViewModel = ViewModelProvider(this, viewModelFactory).get(
            MoodDetailViewModel::class.java
        )
        binding.lifecycleOwner = this
        binding.moodDetailViewModel = moodDetailViewModel

        moodDetailViewModel.navTrackingFragment.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController()
                    .navigate(MoodDetailFragmentDirections.actionMoodDetailFragmentToMoodTrackingFragment())
                moodDetailViewModel.navFinished()
            }

        })

        return binding.root
    }


}