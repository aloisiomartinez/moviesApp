package com.example.movieapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.movieapp.databinding.FragmentMovieListBinding
import com.google.android.material.snackbar.Snackbar


class MovieFragment : Fragment(), MovieItemListener {

    private lateinit var adapter: MyItemRecyclerViewAdapter
    private val viewModel by navGraphViewModels<MovieViewModel>(R.id.movie_graph) { defaultViewModelProviderFactory }
    private lateinit var view: RecyclerView
    private var progressBar: ManageProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMovieListBinding.inflate(inflater)
        view = binding.root as RecyclerView

        adapter = MyItemRecyclerViewAdapter(this)
        view.apply {
            this.adapter = this@MovieFragment.adapter
            this.layoutManager = LinearLayoutManager(context)
        }
        initObservers()

        return view
    }

    private fun initObservers() {
        viewModel.movieListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.updateData(it)
        })

        viewModel.navigationToDetailLiveData.observe(viewLifecycleOwner, Observer {
            val action = MovieFragmentDirections.actionMovieFragmentToMovieDetails()
            findNavController().navigate(action)
        })

        viewModel.movieStateStatus.observe(viewLifecycleOwner, Observer {
            if (it == DataState.Error) {
                progressBar?.stopProgress()
                Snackbar.make(view, "Erro no aplicativo", Snackbar.LENGTH_SHORT).show()
            } else if (it == DataState.Loading) {
                progressBar?.showProgress()
            } else {
                progressBar?.stopProgress()
            }
        })
    }

    override fun onItemSelected(position: Int) {
        viewModel.onMovieSelected(position)
    }

}