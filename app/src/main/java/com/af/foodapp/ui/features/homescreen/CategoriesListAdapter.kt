package com.af.foodapp.ui.features.homescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.af.foodapp.data.source.remote.model.Category
import com.af.foodapp.databinding.CategoryItemBinding
import com.bumptech.glide.Glide

//this adapter is used to display the categories of food in home screen
class CategoriesListAdapter(private val onItemClick: ((Category) -> Unit)) :
    ListAdapter<Category, CategoriesListAdapter.CategoriesViewHolder>(DiffCallback()) {

    inner class CategoriesViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    val differ = AsyncListDiffer(this, DiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    // I used only 12 because there is a problem with the last two items _pictures_ so i ignored them
    override fun getItemCount() = differ.currentList.size.coerceAtMost(12)

    override fun onBindViewHolder(
        holder: CategoriesViewHolder,
        position: Int
    ) {
        val category = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(category.strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = category.strCategory

        holder.itemView.setOnClickListener {
            onItemClick.invoke(category)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.strCategory == newItem.strCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
}

