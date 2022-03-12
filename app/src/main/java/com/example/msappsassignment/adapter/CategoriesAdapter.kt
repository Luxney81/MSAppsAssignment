package com.example.msappsassignment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.msappsassignment.R
import com.example.msappsassignment.models.Category
import com.example.msappsassignment.models.Data
import kotlinx.android.synthetic.main.item_category.view.*

class CategoriesAdapter(private val categories: ArrayList<Category>) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    var onItemClick: ((Category) -> Unit)? = null

    inner class CategoriesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(categories[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val categoryView = inflater.inflate(R.layout.item_category, parent, false)
        return CategoriesViewHolder(categoryView)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val category: Category = categories[position]

        holder.itemView.apply {
            buttonCategory.text = category.name
                setOnClickListener {
                    onItemClickListener?.let { it(category) }
                }
            buttonCategory.setOnClickListener {
                onItemClickListener?.let { it(category) }
            }
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    private var onItemClickListener: ((Category) -> Unit)? = null


    fun setOnItemClickListener(listener: (Category) -> Unit) {
        onItemClickListener = listener
    }
}