package com.dominicwrieden.lifemap.feature.main.view

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.dominicwrieden.lifemap.R
import com.dominicwrieden.lifemap.feature.main.viewmodel.MainViewModel
import com.dominicwrieden.lifemap.ui.LifeMapTheme
import org.koin.androidx.compose.getViewModel

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
                ?: R.id.loginComposeFragment
        )
    }


    @Composable
    fun MainScreen(startDestination: Int) {

        Scaffold(
            drawerContent = {
                Text(text = "Hi")
            },
            content = {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        View.inflate(context, R.layout.view_nav, null)
                    },
                    update = { _ ->
                        val navHostFragment =
                            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                        val navController = navHostFragment.findNavController()
                        val graphInflater = navController.navInflater
                        val navGraph = graphInflater.inflate(R.navigation.nav_graph)
                        navGraph.setStartDestination(startDestination)
                        navController.graph = navGraph
                    }
                )

                IconButton(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                        .background(
                            color = MaterialTheme.colors.secondary,
                            shape = RoundedCornerShape(bottomEnd = 8.dp),
                        ), onClick = { }
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