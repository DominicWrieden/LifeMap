package com.dominicwrieden.lifemap.feature.main.view

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.dominicwrieden.lifemap.R
import com.dominicwrieden.lifemap.core.Destination
import com.dominicwrieden.lifemap.feature.main.viewmodel.MainViewModel
import com.dominicwrieden.lifemap.ui.LifeMapTheme
import com.dominicwrieden.lifemap.util.observeWith
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

@ExperimentalMaterialApi
class MainComposeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContent {
            LifeMapTheme {
                Screen()
            }
        }
    }

    @Composable
    fun Screen(viewModel: MainViewModel = getViewModel()) {
        val startDestination by viewModel.startDestination.observeAsState()

        MainScreen(
            startDestination = startDestination?.getContentIfNotHandled()
        )
    }


    @Composable
    fun MainScreen(startDestination: Int?) {

        val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
        val scope = rememberCoroutineScope()

        Scaffold(
            drawerShape = RoundedCornerShape(
                bottomEndPercent = 100
            ),
            scaffoldState = scaffoldState,
            drawerContent = {

                // TODO 2022-03-03: Only show drawer if logged in
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = MaterialTheme.colors.surface
                        )
                ) {
                    Text(text = "Hi")
                }
            },
            content = {

                startDestination?.let {

                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { context ->
                            View.inflate(context, R.layout.view_nav, null)
                        },
                        update = { view ->
                            val navHostFragment =
                                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                            val navController = navHostFragment.findNavController()
                            val graphInflater = navController.navInflater
                            val navGraph = graphInflater.inflate(R.navigation.nav_graph)
                            navGraph.setStartDestination(startDestination)
                            navController.graph = navGraph

                            view.findViewTreeLifecycleOwner()?.let { lifecycleOwner ->
                                getViewModel<MainViewModel>().destination.observeWith(lifecycleOwner) { destinationEvent ->
                                    destinationEvent.getContentIfNotHandled()?.let {
                                        when (it) {
                                            is Destination.Action -> navController.navigate(it.actionId)
                                            is Destination.NavigationDirection -> navController.navigate(
                                                it.navigationDirection
                                            )

                                        }
                                    }
                                }
                            }
                        }
                    )
                }

                IconButton(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                        .background(
                            color = MaterialTheme.colors.secondary,
                            shape = RoundedCornerShape(bottomEnd = 8.dp),
                        ), onClick = {

                        if (scaffoldState.drawerState.isOpen) {
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        } else {
                            scope.launch {
                                scaffoldState.drawerState.animateTo(
                                    targetValue = DrawerValue.Open,
                                    anim = spring(
                                        dampingRatio = 2f,
                                        visibilityThreshold = 0.8f
                                    )
                                )
                            }
                        }

                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_menu_black_24dp),
                        tint = MaterialTheme.colors.onSecondary,
                        contentDescription = null
                    )
                }
            }
        )
    }

    @Composable
    fun Drawer() {

        //TODO 2022-03-03: Make clickable list for brutgebiete
        Text(text = "Brutgebiete")
    }


    @Preview(widthDp = 720, heightDp = 1080)
    @Composable
    fun PreviewLoginScreen() {
        LifeMapTheme {
            MainScreen(
                startDestination = R.id.loginFragment
            )
        }
    }
}