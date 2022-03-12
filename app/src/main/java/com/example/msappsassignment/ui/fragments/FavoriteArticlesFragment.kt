package com.example.msappsassignment.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.msappsassignment.R
import com.example.msappsassignment.adapter.NewsAdapter
import com.example.msappsassignment.ui.NewsActivity
import com.example.msappsassignment.ui.viewmodel.NewsViewModel
import com.example.msappsassignment.util.Utilities
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_favorite_articles.*


class FavoriteArticlesFragment : Fragment(R.layout.fragment_favorite_articles) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        if (GoogleSignIn.getLastSignedInAccount(view.context) != null) {
            signOutButton.setOnClickListener {
                signOut()
            }

            newsAdapter.setOnItemClickListener {
                val bundle = Bundle().apply {
                    putSerializable("article", it)
                }
                findNavController().navigate(
                    R.id.action_favoriteArticlesFragment_to_articleFragment,
                    bundle
                )
            }

            val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val article = newsAdapter.differ.currentList[position]
                    viewModel.deleteArticle(article)
                    Snackbar.make(view, "Successfully deleted article", Snackbar.LENGTH_LONG)
                        .apply {
                            setAction("Undo") {
                                viewModel.saveArticle(article)
                            }
                            show()
                        }
                }
            }

            ItemTouchHelper(itemTouchHelperCallback).apply {
                attachToRecyclerView(rvFavoriteNews)
            }

            viewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articles ->
                newsAdapter.differ.submitList(articles)
            })
        } else findNavController().navigate(R.id.action_favoriteArticlesFragment_to_googleSignInFragment)
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvFavoriteNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun signOut(){
        val googleSignInClient = view?.context?.let { GoogleSignIn.getClient(it, Utilities.googleSignInOptions) }

        googleSignInClient?.signOut()?.addOnCompleteListener {
            Toast.makeText(view?.context, "Signed out!", Toast.LENGTH_LONG).show()
            findNavController().navigate(
                R.id.action_favoriteArticlesFragment_to_googleSignInFragment
            )
        }
    }
}