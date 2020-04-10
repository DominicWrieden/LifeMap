package com.dominicwrieden.lifemap.feature.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.dominicwrieden.lifemap.R
import com.dominicwrieden.lifemap.feature.main.model.StartScreen
import com.dominicwrieden.lifemap.feature.main.viewmodel.MainViewModel
import com.dominicwrieden.lifemap.util.observeWith
import org.koin.android.viewmodel.ext.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        viewModel.startScreen.observeWith(this) {
            it.getContentIfNotHandled()?.let{startScreen ->
                when (startScreen) {
                    StartScreen.Login -> {
                        navController.navigate(R.id.loginFragment)
                        Toast.makeText(this, "Logged out!", Toast.LENGTH_LONG).show()
                    }
                    StartScreen.Map -> Toast.makeText(this, "Logged in!", Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.logout.observeWith(this) {
            it.getContentIfNotHandled()?.let {
                navController.navigate(R.id.loginFragment)
            }
        }
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