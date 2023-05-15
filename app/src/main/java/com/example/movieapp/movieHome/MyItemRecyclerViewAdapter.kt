package com.example.movieapp

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.data.Movie
import com.example.movieapp.databinding.FragmentMovieBinding

interface MovieItemListener {
    fun onItemSelected(position: Int)
}

class MyItemRecyclerViewAdapter(
    private val listener: MovieItemListener
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    private var values: List<Movie> = ArrayList()

    fun updateData(movieList: List<Movie>) {
        values = movieList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bindItem(item)

        holder.view.setOnClickListener {
            listener.onItemSelected(position)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: FragmentMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        val view: View = binding.root

        fun bindItem(item: Movie) {
            Log.d("MovieItemViewHolder", "${item}")
            binding.movieItem = item
            binding.executePendingBindings()
        }
    }

}