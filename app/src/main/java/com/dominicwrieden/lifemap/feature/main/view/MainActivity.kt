package com.dominicwrieden.lifemap.feature.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.dominicwrieden.lifemap.R
import com.dominicwrieden.lifemap.core.Destination
import com.dominicwrieden.lifemap.core.Destination.Action
import com.dominicwrieden.lifemap.core.Destination.NavigationDirection
import com.dominicwrieden.lifemap.feature.main.viewmodel.MainViewModel
import com.dominicwrieden.lifemap.util.Event
import com.dominicwrieden.lifemap.util.observeWith
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    private val disposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        setUpNavigation()

        setUpNavigationDrawer()
    }

    @SuppressLint("ResourceType")
    private fun setUpNavigationDrawer() {
        //set up navigation drawer icon
        drawerIcon.clicks().subscribe {
            drawer_layout.openDrawer(Gravity.START)
        }.addTo(disposable)


    }

    private fun setUpNavigation() {
        val navHostFragment = nav_host_fragment as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.nav_graph)


        viewModel.startDestination.observeWith(this) { startDestinationEvent ->
            navGraph.startDestination =
                startDestinationEvent.getContentIfNotHandled() ?: R.id.loginFragment
            navController.graph = navGraph

            viewModel.destination.observeWith(this) { destinationEvent: Event<Destination> ->
                destinationEvent.getContentIfNotHandled()?.let { destination: Destination ->
                    when (destination) {
                        is Action -> navController.navigate(destination.actionId)
                        is NavigationDirection -> navController.navigate(destination.navigationDirection)

                    }
                }
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            drawerIcon.isVisible = destination.id != R.id.loginFragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}