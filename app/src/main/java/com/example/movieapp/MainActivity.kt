package com.example.movieapp

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.movieapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ManageProgressBar {
    lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController


    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun stopProgress() {
        binding.progressBar.visibility = View.INVISIBLE
    }

}

