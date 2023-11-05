package com.af.foodapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.af.foodapp.data.source.remote.model.Category
import com.af.foodapp.data.source.remote.model.MealsByCategory
import com.af.foodapp.databinding.MealItemBinding
import com.bumptech.glide.Glide

//this adapter is used to display the meals after click on each category
class CategoryMealAdapter(private val onItemClick: ((MealsByCategory) -> Unit)) :
    ListAdapter<MealsByCategory, CategoryMealAdapter.CategoryMealViewHolder>(DiffCallback()) {
    inner class CategoryMealViewHolder(val binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root)
    private var mealsList = ArrayList<MealsByCategory>()
    private val differ = AsyncListDiffer(this, DiffCallback())

    //set the meals in each category
    fun setMeals(mealsList: List<MealsByCategory>) {
        this.mealsList = mealsList as ArrayList<MealsByCategory>
        differ.submitList(mealsList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealViewHolder {
        return CategoryMealViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    //set the picture and the name of each meal and make it clickable
    override fun onBindViewHolder(
        holder: CategoryMealAdapter.CategoryMealViewHolder,
        position: Int
    ) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealsList[position].strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }
    }

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

