package com.af.foodapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.af.foodapp.databinding.PopularItemsBinding
import com.af.foodapp.data.model.CategoryMeal
import com.bumptech.glide.Glide

class MostPopularMealsAdapter :
    RecyclerView.Adapter<MostPopularMealsAdapter.PopularMealViewHolder>() {
    lateinit var onItemClick: ((CategoryMeal) -> Unit)

    private var mealList = ArrayList<CategoryMeal>()
    @SuppressLint("NotifyDataSetChanged")
    fun setMeals(mealList: ArrayList<CategoryMeal>) {
        this.mealList = mealList
        notifyDataSetChanged()
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

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
    }

    inner class PopularMealViewHolder(val binding: PopularItemsBinding) :
        RecyclerView.ViewHolder(binding.root)
}