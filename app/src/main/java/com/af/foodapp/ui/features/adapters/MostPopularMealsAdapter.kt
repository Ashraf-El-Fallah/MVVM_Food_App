package com.af.foodapp.ui.features.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.af.foodapp.databinding.PopularItemsBinding
import com.af.foodapp.data.source.remote.model.MealsByCategory
import com.bumptech.glide.Glide

class MostPopularMealsAdapter(private val onItemClick: ((MealsByCategory) -> Unit)) :
    ListAdapter<MealsByCategory, MostPopularMealsAdapter.PopularMealViewHolder>(DiffCallback()) {

    val differ = AsyncListDiffer(this, DiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(
            PopularItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = differ.currentList.size

    //set the picture of each meal and make it clickable
    override fun onBindViewHolder(
        holder: PopularMealViewHolder,
        position: Int
    ) {
        val mostPopularMeal = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(mostPopularMeal.strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mostPopularMeal)
        }
    }

    inner class PopularMealViewHolder(val binding: PopularItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    private class DiffCallback : DiffUtil.ItemCallback<MealsByCategory>() {
        override fun areItemsTheSame(oldItem: MealsByCategory, newItem: MealsByCategory): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(
            oldItem: MealsByCategory,
            newItem: MealsByCategory
        ): Boolean {
            return oldItem == newItem
        }
    }
}