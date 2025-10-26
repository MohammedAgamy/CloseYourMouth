package com.agamy.closeyourmouth.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.agamy.closeyourmouth.presentation.auth.login.LogInScreen
import com.agamy.closeyourmouth.presentation.splash.SplashScreen

@Composable
fun MyApp (){

    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    )
    {
        composable(Routes.SPLASH) { SplashScreen(navController) }
        composable ( Routes.LOGIN ){ LogInScreen() }
    }
}

