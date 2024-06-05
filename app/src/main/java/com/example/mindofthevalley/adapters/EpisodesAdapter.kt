package com.example.mindofthevalley.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mindofthevalley.data.Course
import com.example.mindofthevalley.databinding.ViewCourseItemSingleBinding

class EpisodesAdapter(): RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Course>() {

        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return false
            //use this to check duplicates during pagination but since there aren't any usable identifiers, return false
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return false
            //use this to check duplicates during pagination but since there aren't any usable identifiers, return false
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(list: List<Course>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesAdapter.ViewHolder {
        return ViewHolder(ViewCourseItemSingleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: EpisodesAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return if(differ.currentList.size > 6) 6 else differ.currentList.size
    }

    inner class ViewHolder(private val binding: ViewCourseItemSingleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Course) = with (binding) {
            binding.courseTitle = item.title
            binding.courseSubtitle = item.channelMini?.title
            binding.courseUrl = item.coverAsset?.coverUrl
            binding.isLastItem = (itemCount - 1) == adapterPosition
        }
    }

}