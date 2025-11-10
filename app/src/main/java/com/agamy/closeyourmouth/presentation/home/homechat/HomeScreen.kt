package com.agamy.closeyourmouth.presentation.home.homechat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.agamy.closeyourmouth.data.model.ChatItem
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    navController: NavController,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        Spacer(modifier = Modifier.height(38.dp))
        Header()
        Chats(navController = navController)
    }

}

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // العنوان
        Text(
            text = "Chats",
            fontSize = 28.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )

        // مجموعة الأيقونات على اليمين
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = painterResource(id = com.agamy.closeyourmouth.R.drawable.addchat),
                contentDescription = "Add Chat",
                tint = Color.Black,
                modifier = Modifier.size(30.dp)
            )

            Icon(
                painter = painterResource(id = com.agamy.closeyourmouth.R.drawable.list),
                contentDescription = "Select All",
                tint = Color.Black,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun Chats(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // المستخدم الحالي (خده من FirebaseAuth)
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    // تحميل الشاتات أول ما الشاشة تفتح
    LaunchedEffect(Unit) {
        if (currentUserId.isNotEmpty()) {
            viewModel.sendIntent(HomeIntent.LoadChats(currentUserId))
        }
    }

    when {
        state.isLoading -> {
            Text("Loading chats...")
        }

        state.error != null -> {
            Text("Error: ${state.error}")
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(state.chats) { chat ->
                    ChatItemRow(chat) {
                        navController.navigate("chat/${chat.chatId}/${chat.otherUserId}/${chat.otherUserName}")
                    }
                }
            }
        }
    }
}


@Composable
fun ChatItemRow(chat: ChatItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
           /* if (chat.otherUserImage.isNotEmpty()) {
                AsyncImage(
                    model = chat.otherUserImage,
                    contentDescription = "User Image",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
            }
*/
            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(text = chat.otherUserName, fontSize = 18.sp)
                Text(
                    text = chat.lastMessage,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 1
                )
            }
        }

        Text(
            text = SimpleDateFormat("HH:mm", Locale.getDefault())
                .format(Date(chat.lastMessageTime)),
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}