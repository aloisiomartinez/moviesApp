package com.example.movieapp.movieHome

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.movieapp.*
import com.example.movieapp.databinding.FragmentMovieListBinding
import com.example.movieapp.MovieViewModel


class MovieFragment : Fragment(), MovieItemListener {

    private lateinit var adapter: MyItemRecyclerViewAdapter
    private val viewModel by hiltNavGraphViewModels<MovieViewModel>(R.id.movie_graph)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMovieListBinding.inflate(inflater)
        val view = binding.root
        val recyclerView = binding.list

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        adapter = MyItemRecyclerViewAdapter(this)

        recyclerView.apply {
            this.adapter = this@MovieFragment.adapter
            this.layoutManager = LinearLayoutManager(context)
        }
        initObservers()

        return view
    }

    private fun initObservers() {
        viewModel.movieListLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.updateData(it)
            }
        })

        viewModel.navigationToDetailLiveData.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                val action = MovieFragmentDirections.actionMovieFragmentToMovieDetails()
                findNavController().navigate(action)
            }
        })


    }

    override fun onItemSelected(position: Int) {
        viewModel.onMovieSelected(position)
    }

}