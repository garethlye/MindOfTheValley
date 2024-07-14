package com.example.mindofthevalley.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mindofthevalley.data.Channel
import com.example.mindofthevalley.databinding.ViewCourseItemBinding
import com.example.mindofthevalley.databinding.ViewSeriesItemBinding

class ChannelsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val Course = 0
        const val Series = 1
    }
    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Channel>() {

        override fun areItemsTheSame(oldItem: Channel, newItem: Channel): Boolean {
            return false
            //use this to check duplicates during pagination but since there aren't any usable identifiers, return false
        }

        override fun areContentsTheSame(oldItem: Channel, newItem: Channel): Boolean {
            return false
            //use this to check duplicates during pagination but since there aren't any usable identifiers, return false
        }
    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            Course -> {
                CourseViewHolder(ViewCourseItemBinding.inflate(inflater, parent, false))
            }
            Series -> {
                SeriesViewHolder(ViewSeriesItemBinding.inflate(inflater, parent, false))
            }
            else -> throw IllegalArgumentException("Invalid view holder type")
        }
    }

    fun submitList(list: List<Channel>) {
        differ.submitList(list)
    }

    override fun getItemViewType(position: Int): Int {
        return if (differ.currentList[position].series != null && differ.currentList[position].series?.isNotEmpty() == true) {
            Series
        } else {
            Course
        }
    }

    override fun getItemCount(): Int {
        //limit to 6 as per requirement
        return if(differ.currentList.size > 6) 6 else differ.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CourseViewHolder -> {
                holder.bind(differ.currentList[position])
            }
            is SeriesViewHolder -> {
                holder.bind(differ.currentList[position])
            }
            else -> throw IllegalArgumentException("Invalid view holder type")
        }
    }

    //same general object but using two different views depending on values received, should use the same adapter
    inner class CourseViewHolder(private val binding: ViewCourseItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Channel) {
            binding.courseTitle = data.title
            binding.courseSeriesNum = data.mediaCount.toString() + " episodes"
            binding.roundedLogoUrl = data.iconAsset?.thumbnailUrl
            binding.episodeFlag = false
            if(data.courses != null ) {
                binding.channelsRv.adapter = CourseItemAdapter(data.courses)
            }
        }
    }

    inner class SeriesViewHolder(private val binding: ViewSeriesItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Channel) = with (binding) {
            binding.courseTitle = data.title
            binding.courseSeriesNum = data.mediaCount.toString() + " series"
            binding.roundedLogoUrl = data.iconAsset?.thumbnailUrl
            if(data.series != null ) {
                binding.seriesRv.adapter = SeriesItemAdapter(data.series)
            }
        }
    }
}