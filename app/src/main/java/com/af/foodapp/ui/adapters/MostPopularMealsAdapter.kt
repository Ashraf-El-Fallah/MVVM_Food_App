package com.af.foodapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.af.foodapp.databinding.PopularItemsBinding
import com.af.foodapp.data.source.remote.model.MealsByCategory
import com.bumptech.glide.Glide

class MostPopularMealsAdapter :
    ListAdapter<MealsByCategory, MostPopularMealsAdapter.PopularMealViewHolder>(DiffCallback()) {

    lateinit var onItemClick: ((MealsByCategory) -> Unit)
    private var mealList = ArrayList<MealsByCategory>()
    private val differ = AsyncListDiffer(this, DiffCallback())

    fun setMeals(mealList: ArrayList<MealsByCategory>) {
        this.mealList = mealList
        differ.submitList(mealList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(
            PopularItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    //set the picture of each meal and make it clickable
    override fun onBindViewHolder(
        holder: MostPopularMealsAdapter.PopularMealViewHolder,
        position: Int
    ) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
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