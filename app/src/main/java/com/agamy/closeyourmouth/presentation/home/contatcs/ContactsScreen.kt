package com.agamy.closeyourmouth.presentation.home.contatcs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.agamy.closeyourmouth.data.model.User


@Composable
fun ContactsScreen(
    navController: NavController,
    viewModel: ContactChatViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadContacts()
    }

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Text("Start New Chat", style = MaterialTheme.typography.headlineSmall)

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn {
                items(state.contacts) { user ->
                    UserItem(user) {
                        navController.navigate("chat/${user.id}/${user.name}")
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
       /* Image(
            painter = rememberAsyncImagePainter(user.profileImage),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )*/
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(user.name, style = MaterialTheme.typography.titleMedium)
            Text(user.phone, style = MaterialTheme.typography.bodySmall)
        }
    }
}
