package com.kseyko.moodtracking.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kseyko.moodtracking.R
import com.kseyko.moodtracking.database.MoodDatabase
import com.kseyko.moodtracking.databinding.MoodQualityFragmentBinding
import com.kseyko.moodtracking.ui.viewmodel.MoodQualityViewModel
import com.kseyko.moodtracking.ui.viewmodelfactory.MoodQualityViewModelFactory

class MoodQualityFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: MoodQualityFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.mood_quality_fragment, container, false)

        val args = MoodQualityFragmentArgs.fromBundle(requireArguments())

        val moodDao = MoodDatabase.getDatabaseObject(requireContext())!!.moodDao
        val moodQualityViewModelFactory = MoodQualityViewModelFactory(moodDao, args.moodId)

        val viewModel = ViewModelProvider(
            this,
            moodQualityViewModelFactory
        ).get(MoodQualityViewModel::class.java)
        binding.moodQualityViewModel = viewModel

        viewModel.navTrackingFragment.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController()
                    .navigate(MoodQualityFragmentDirections.actionMoodQualityFragmentToMoodTrackingFragment())
                viewModel.navFinished()
            }
        })
        return binding.root
    }


}