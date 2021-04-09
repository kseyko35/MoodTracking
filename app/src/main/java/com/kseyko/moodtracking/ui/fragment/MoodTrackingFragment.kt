package com.kseyko.moodtracking.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kseyko.moodtracking.R
import com.kseyko.moodtracking.database.MoodDatabase
import com.kseyko.moodtracking.database.MoodEntity
import com.kseyko.moodtracking.databinding.MoodTrackingFragmentBinding
import com.kseyko.moodtracking.ui.adapter.CellClickListener
import com.kseyko.moodtracking.ui.adapter.MoodAdapter2
import com.kseyko.moodtracking.ui.adapter.MoodListener2
import com.kseyko.moodtracking.ui.viewmodel.MoodTrackingViewModel
import com.kseyko.moodtracking.ui.viewmodelfactory.MoodTrackingViewModelFactory

class MoodTrackingFragment : Fragment(), CellClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: MoodTrackingFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.mood_tracking_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val moodDao = MoodDatabase.getDatabaseObject(requireContext())!!.moodDao
        val viewModelFactory = MoodTrackingViewModelFactory(moodDao, application)
        val moodTrackingViewModel =
            ViewModelProvider(this, viewModelFactory).get(MoodTrackingViewModel::class.java)

//        val adapter = MoodAdapter(this)
//        binding.moodRecyclerList.adapter = adapter
//
//        moodTrackingViewModel.moods.observe(viewLifecycleOwner,{
//            it?.let {
//                adapter.data = it
//            }
//        })

        val adapter = MoodAdapter2(MoodListener2 { moodId ->
            // Toast.makeText(context,"${moodId}",Toast.LENGTH_LONG).show()
            moodTrackingViewModel.clickListener(moodId)
        })

        binding.moodRecyclerList.adapter = adapter

        moodTrackingViewModel.moods.observe(viewLifecycleOwner, {
            adapter.addHeaderAndSubmitList(it)
        })

        binding.lifecycleOwner = this

        binding.moodTrackingViewModel = moodTrackingViewModel


        moodTrackingViewModel.navQualityFragment.observe(viewLifecycleOwner, { mood ->
            mood?.let {
                this.findNavController().navigate(
                    MoodTrackingFragmentDirections.actionMoodTrackingFragmentToMoodQualityFragment(
                        mood.moodId
                    )
                )
                moodTrackingViewModel.navFinished()
            }
        })
        moodTrackingViewModel.navDetailFragment.observe(viewLifecycleOwner, { moodId ->
            moodId?.let {
                this.findNavController().navigate(
                    MoodTrackingFragmentDirections.actionMoodTrackingFragmentToMoodDetailFragment(
                        moodId
                    )
                )
                moodTrackingViewModel.navFinished()
            }
        })
//        val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)


        val manager = GridLayoutManager(activity, 3)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = when (position) {
                0 -> 3
                else -> 1
            }
        }
        binding.moodRecyclerList.layoutManager = manager


        return binding.root
    }

    override fun onCellClickListener(mood: MoodEntity) {
        Toast.makeText(context, "${mood.moodId}", Toast.LENGTH_LONG).show()
        this.findNavController().navigate(
            MoodTrackingFragmentDirections.actionMoodTrackingFragmentToMoodDetailFragment(mood.moodId)
        )
    }


}