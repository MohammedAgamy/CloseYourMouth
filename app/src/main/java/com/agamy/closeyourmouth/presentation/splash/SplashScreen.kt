package com.agamy.closeyourmouth.presentation.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.agamy.closeyourmouth.presentation.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen (navController: NavController){

    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(Routes.LOGIN) {
            popUpTo(Routes.SPLASH) { inclusive = true }
        }

    }


}