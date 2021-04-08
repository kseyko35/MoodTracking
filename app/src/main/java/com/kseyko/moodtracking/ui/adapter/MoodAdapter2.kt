package com.kseyko.moodtracking.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kseyko.moodtracking.R
import com.kseyko.moodtracking.database.MoodEntity
import com.kseyko.moodtracking.databinding.MoodItem2Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      03,April,2021      ║
╚════════════════════════════╝
 */

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class MoodAdapter2(private val onClickListener: MoodListener2) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(MoodAdapterDiffCallBack()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //return ViewHolder.from(parent)
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw  ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.MoodItem -> ITEM_VIEW_TYPE_ITEM
            else -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.bind(item, onClickListener)
        when (holder) {
            is ViewHolder -> {
                val item = getItem(position) as DataItem.MoodItem
                holder.bind(item.moodItem, onClickListener)
            }
        }
    }

    fun addHeaderAndSubmitList(list: List<MoodEntity>?) {
        adapterScope.launch {//We used corutine in adapter
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.MoodItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }


    class ViewHolder private constructor(private val binding: MoodItem2Binding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MoodEntity, onClickListener: MoodListener2) {
            binding.mood = item
            binding.clickListener = onClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MoodItem2Binding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header, parent, false)
                return TextViewHolder(view)
            }
        }
    }
}

sealed class DataItem {
    abstract val id: Long

    data class MoodItem(val moodItem: MoodEntity) : DataItem() {
        override val id = moodItem.moodId
    }

    object Header : DataItem() {
        override val id = Long.MIN_VALUE
    }
}

class MoodListener2(val clickListener: (moodId: Long) -> Unit) {
    fun onClick(mood: MoodEntity) = clickListener(mood.moodId)
}

class MoodAdapterDiffCallBack : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}