package com.example.mindofthevalley.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mindofthevalley.data.Category
import com.example.mindofthevalley.databinding.ViewCategoryItemSingleBinding

class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.ViewHolder>()  {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Category>() {

        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return false
            //use this to check duplicates during pagination but since there aren't any usable identifiers, return false
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return false
            //use this to check duplicates during pagination but since there aren't any usable identifiers, return false
        }
    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(list: List<Category>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        return ViewHolder(ViewCategoryItemSingleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ViewCategoryItemSingleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            binding.categoryName = item.categoryName
        }
    }
}