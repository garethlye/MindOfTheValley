package com.example.mindofthevalley.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mindofthevalley.data.Series
import com.example.mindofthevalley.databinding.ViewSeriesItemSingleBinding

class SeriesItemAdapter(val seriesList: List<Series>): RecyclerView.Adapter<SeriesItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesItemAdapter.ViewHolder {
        return ViewHolder(ViewSeriesItemSingleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SeriesItemAdapter.ViewHolder, position: Int) {
        holder.bind(seriesList[position])
    }

    override fun getItemCount(): Int {
        return if(seriesList.size > 6) 6 else seriesList.size
    }

    inner class ViewHolder(private val binding: ViewSeriesItemSingleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Series) = with (binding) {
            binding.courseTitle = item.title
            binding.courseUrl = item.coverAsset?.coverUrl
            binding.isLastItem = (seriesList.size - 1) == adapterPosition
        }
    }
}