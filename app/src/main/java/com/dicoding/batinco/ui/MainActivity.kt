package com.dicoding.batinco.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.batinco.R
import com.dicoding.batinco.databinding.ActivityMainBinding
import com.dicoding.batinco.ui.discover.DiscoverFragment
import com.dicoding.batinco.ui.home.HomeFragment
import com.dicoding.batinco.ui.scan.ScanFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

//        val appBarConfiguration = AppBarConfiguration.Builder(
//            R.id.navigation_home, R.id.navigation_discover
//        ).build()
//
//        setupActionBarWithNavController(navController, appBarConfiguration)

//        binding.fabMenuScan.setOnClickListener {
//            it.findNavController().navigate(R.id.navigation_scan)
//        }

//        val fragmentManager = supportFragmentManager
//        // Ambil fragment yang saat ini ditampilkan
//        // Percabangan untuk memeriksa fragment
//        when (fragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)) {
//            is HomeFragment -> {
//                binding.navView.visibility = View.VISIBLE
//            }
//
//            is DiscoverFragment -> {
//                binding.navView.visibility = View.VISIBLE
//            }
//
//            else -> {
//                binding.navView.visibility = View.GONE
//            }
//        }

        navView.setupWithNavController(navController)
    }
}