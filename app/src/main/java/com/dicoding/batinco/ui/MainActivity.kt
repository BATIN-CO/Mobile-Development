package com.dicoding.batinco.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
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

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val navView: BottomNavigationView = binding.navView
//
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)

//        val appBarConfiguration = AppBarConfiguration.Builder(
//            R.id.navigation_home, R.id.navigation_discover
//        ).build()
//
//        setupActionBarWithNavController(navController, appBarConfiguration)

//        binding.fabMenuScan.setOnClickListener {
//            it.findNavController().navigate(R.id.navigation_scan)
//        }

//        navView.setupWithNavController(navController)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment_activity_main
        ) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.setupWithNavController(navController)

        // Setup the ActionBar with navController and 2 top level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_discover)
        )

//        binding.fabMenuScan.setOnClickListener {
//            supportFragmentManager
//                .beginTransaction().apply {
//                    replace(
//                        R.id.nav_host_fragment_activity_main,
//                        ScanFragment(),
//                        ScanFragment::class.java.simpleName
//                    )
//                    addToBackStack(null)
//                    commit()
//                }
//        }

        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            if (nd.id == R.id.navigation_scan || nd.id == R.id.detailActivity || nd.id == R.id.navigation_scan) {
                binding.navView.visibility = View.GONE
            } else {
                binding.navView.visibility = View.VISIBLE
            }
        }

//            setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

}