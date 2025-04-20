package com.example.nownews

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.example.nownews.databinding.ActivityMainBinding
import com.example.nownews.local.AppDatabase
import com.example.nownews.utils.TextSizeUtils

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val TAG = "MainActivity"

    // Database instance (companion object makes it accessible globally)
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Room Database
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "nownews-db"
        ).build()

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_category, R.id.navigation_search
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        Log.d(TAG, "Menu inflated: bottom_navigation_menu")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_text_size -> {
                val isLargeText = !TextSizeUtils.isLargeTextEnabled(this)
                TextSizeUtils.setLargeTextEnabled(this, isLargeText)
                Log.d(TAG, "Text size toggled to ${if (isLargeText) "large" else "normal"}")
                notifyFragmentsToRefresh()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun notifyFragmentsToRefresh() {
        val currentFragmentId = navController.currentDestination?.id
        currentFragmentId?.let {
            navController.navigate(it)
        }
    }
}