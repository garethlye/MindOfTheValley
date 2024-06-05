package com.example.mindofthevalley.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mindofthevalley.data.Course
import com.example.mindofthevalley.databinding.ViewCourseItemSingleBinding

class CourseItemAdapter(val courseList: List<Course>): RecyclerView.Adapter<CourseItemAdapter.CourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseItemAdapter.CourseViewHolder {
        return CourseViewHolder(ViewCourseItemSingleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CourseItemAdapter.CourseViewHolder, position: Int) {
        holder.bind(courseList[position])
    }

    override fun getItemCount(): Int {
        return if(courseList.size > 6) 6 else courseList.size
    }

    inner class CourseViewHolder(val binding: ViewCourseItemSingleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Course) = with (binding) {
            binding.courseTitle = item.title
            binding.courseUrl = item.coverAsset?.coverUrl
            binding.isLastItem = (itemCount - 1) == adapterPosition
            binding.courseSubtitle = item.channelMini?.title
        }
    }
}