package com.dominicwrieden.lifemap

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {


    private val navController by lazy { findNavController(R.id.nav_host_fragment) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        setUpToolbar(menu)
        return true
    }

    @SuppressLint("RestrictedApi")
    private fun setUpToolbar(menu: Menu?) {
        setupActionBarWithNavController(navController)
        supportActionBar?.setShowHideAnimationEnabled(false)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id != R.id.loginFragment) {
                menu?.clear()
                menuInflater.inflate(R.menu.toolbar, menu)
                supportActionBar?.show()
            } else {
                menu?.clear()
                supportActionBar?.hide()
            }
        }
    }

    //TODO Still to discuss on which screen an up button is present
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}