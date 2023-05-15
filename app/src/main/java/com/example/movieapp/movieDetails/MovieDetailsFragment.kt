package com.example.movieapp.movieDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.navGraphViewModels
import com.example.movieapp.MovieViewModel
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


class MovieDetailsFragment : Fragment() {

    private val viewModel by navGraphViewModels<MovieViewModel>(R.id.movie_graph) {defaultViewModelProviderFactory}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMovieDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_details,
            container,
            false
        )

        binding.lifecycleOwner = this
        binding.movieDetail = viewModel

        val carousel: ImageCarousel = binding.carousel
        carousel.registerLifecycle(lifecycle)
        val listCarousel = mutableListOf<CarouselItem>()

        listCarousel.add(
            CarouselItem(
                imageUrl = "https://br.web.img3.acsta.net/medias/nmedia/18/90/93/20/20120876.jpg",
                caption = "Poderoso chefão"
            )
        )

        listCarousel.add(
            CarouselItem(
                imageUrl = "https://br.web.img2.acsta.net/medias/nmedia/18/90/16/48/20083748.jpg",
                caption = "Um sonho de liberdade"
            )
        )

        carousel.setData(listCarousel)

        return binding.root
    }


}