package com.example.msappsassignment.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.msappsassignment.R
import com.example.msappsassignment.adapter.CategoriesAdapter
import com.example.msappsassignment.adapter.NewsAdapter
import com.example.msappsassignment.models.Category
import com.example.msappsassignment.ui.NewsActivity
import com.example.msappsassignment.ui.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_news.*

class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    lateinit var viewModel: NewsViewModel
    lateinit var categoriesAdapter: CategoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        setupRecyclerView()

        categoriesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("category", it)
            }
            findNavController().navigate(
                R.id.action_categoriesFragment_to_newsFragment,
                bundle
            )
        }
    }

    private fun setupRecyclerView(){
        categoriesAdapter = CategoriesAdapter(createCategories())
        rvCategories.apply {
            adapter = categoriesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun createCategories() : ArrayList<Category> {
        val categories: ArrayList<Category> = arrayListOf()
        categories.add(Category("general"))
        categories.add(Category("business"))
        categories.add(Category("entertainment"))
        categories.add(Category("health"))
        categories.add(Category("science"))
        categories.add(Category("sports"))
        categories.add(Category("technology"))
        return categories
    }
}