package com.agamy.closeyourmouth.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.agamy.closeyourmouth.presentation.auth.login.phone.LogInScreen
import com.agamy.closeyourmouth.presentation.auth.login.otp.OtpScreen
import com.agamy.closeyourmouth.presentation.home.HomeContainer
import com.agamy.closeyourmouth.presentation.home.homechat.chat.ChatScreen
import com.agamy.closeyourmouth.presentation.splash.SplashScreen
import com.google.firebase.auth.FirebaseAuth

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

        // OTP Screen with argument for verificationId
        // This screen is accessed after the user initiates the login process.
        // It requires the verificationId to verify the OTP entered by the user.
        // The onScess callback navigates back to the login screen upon success,
        // while the onVerified callback navigates to the Home screen and clears the back stack.
        // The verificationId is retrieved from the navigation arguments.
        // The screen is defined within the NavHost to manage navigation flow.
        // The use of navArgument ensures type safety for the passed argument.
        // This setup allows for a seamless user experience during the OTP verification process.
        // The popUpTo(0) call in onVerified ensures that all previous screens are removed from the back stack,
        // preventing the user from navigating back to them.
        composable(
            route = Routes.OTP,
            arguments = listOf(
                navArgument("verificationId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Retrieve the verificationId from the navigation arguments
            // and pass it to the OtpScreen.
            // If the argument is not found, an empty string is used as a fallback.
            // This ensures that the OtpScreen always receives a valid string,
            // even if the navigation was not set up correctly.
            // The OtpScreen handles the OTP verification process,
            // utilizing the provided verificationId to validate the user's input.
            // The navigation controller is used to manage screen transitions
            // based on the verification outcome.
            val verificationId = backStackEntry.arguments?.getString("verificationId") ?: ""
            OtpScreen(
                verificationId = verificationId,
                onScess = { navController.popBackStack(Routes.LOGIN, inclusive = true) },
                onVerified = {
                    navController.navigate(Routes.HomeContainer)
                    {
                        //clear back stack
                        popUpTo(0)
                    }
                }
            )
        }
        composable(Routes.HomeContainer) { HomeContainer(navController = navController) }
        composable(
            route = "chat/{chatId}/{receiverId}/{receiverName}",
            arguments = listOf(
                navArgument("chatId") { type = NavType.StringType },
                navArgument("receiverId") { type = NavType.StringType },
                navArgument("receiverName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            val receiverId = backStackEntry.arguments?.getString("receiverId") ?: ""
            val receiverName = backStackEntry.arguments?.getString("receiverName") ?: ""
            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

            ChatScreen(
                chatId = chatId,
                senderId = currentUserId.toString(),
                receiverId = receiverId
            )
}
    }

}

