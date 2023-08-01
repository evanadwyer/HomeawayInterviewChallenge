package com.expedia.homeawayinterviewchallenge.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.expedia.homeawayinterviewchallenge.navigation.FoursquareNavHost
import com.expedia.homeawayinterviewchallenge.ui.theme.HomeawayInterviewChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeawayInterviewChallengeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeawayChallengeApp()
                }
            }
        }
    }
}

@Composable
fun HomeawayChallengeApp() {
    val navController = rememberNavController()

    FoursquareNavHost(
        navController = navController
    )
}
