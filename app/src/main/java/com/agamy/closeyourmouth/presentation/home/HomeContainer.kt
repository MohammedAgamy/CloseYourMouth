package com.agamy.closeyourmouth.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.agamy.closeyourmouth.presentation.home.chat.ChatScreen
import com.agamy.closeyourmouth.presentation.home.contatcs.ContactsScreen
import com.agamy.closeyourmouth.presentation.home.more.MoreScreen
import com.agamy.closeyourmouth.presentation.navigation.Routes
import com.agamy.closeyourmouth.presentation.navigation.bottomNavItems

@Composable
fun HomeContainer() {


    var selectedTab by remember { mutableStateOf(Routes.Contacts) }


    Scaffold(
        modifier = Modifier.background(Color.White),
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    val isSelected = selectedTab == item.route
                    NavigationBarItem(
                        selected = selectedTab == item.route,
                        onClick = {
                            selectedTab = item.route
                        },
                        icon = {
                            // ✅ لو متحدد نخفي الأيقونة، لو مش متحدد نظهرها
                            if (!isSelected) {
                                androidx.compose.material3.Icon(
                                    modifier = Modifier
                                        .size(26.dp)
                                        .padding(2.dp),
                                    painter = androidx.compose.ui.res.painterResource(id = item.icon),
                                    contentDescription = item.title
                                )
                            }
                        },
                        label = {    // ✅ لو متحدد نظهر الاسم، لو مش متحدد نخفيه
                            if (isSelected) {
                                androidx.compose.material3.Text(
                                    text = item.title,
                                    color = Color.Black
                                )
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                Routes.Contacts -> ContactsScreen()
                Routes.CHAT -> ChatScreen()
                Routes.MORE -> MoreScreen()

                else -> {}
            }
        }

    }

}