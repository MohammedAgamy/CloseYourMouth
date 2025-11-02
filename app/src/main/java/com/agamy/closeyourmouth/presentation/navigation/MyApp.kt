package com.agamy.closeyourmouth.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.agamy.closeyourmouth.presentation.auth.login.phone.LogInScreen
import com.agamy.closeyourmouth.presentation.auth.login.otp.OtpScreen
import com.agamy.closeyourmouth.presentation.home.HomeContiner
import com.agamy.closeyourmouth.presentation.splash.SplashScreen

@Composable
fun MyApp() {

    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    )
    {
        composable(Routes.SPLASH) { SplashScreen(navController) }
        composable(Routes.LOGIN) { LogInScreen(navController = navController) }
        composable(
            route = Routes.OTP,
            arguments = listOf(
                navArgument("verificationId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val verificationId = backStackEntry.arguments?.getString("verificationId") ?: ""
            OtpScreen(
                verificationId = verificationId,
                onScess = { navController.popBackStack(Routes.LOGIN, inclusive = true) },
                onVerified = { navController.navigate(Routes.Home) }
            )
        }
        composable(Routes.Home) { HomeContiner() }
    }

}

