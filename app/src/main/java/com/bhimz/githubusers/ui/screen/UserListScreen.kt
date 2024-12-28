package com.bhimz.githubusers.ui.screen

import android.content.res.Resources.Theme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.bhimz.githubusers.domain.User
import com.bhimz.githubusers.ui.widget.UserItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                title = {
                    Text("Github Search")
                }
            )
        }
    ) { innerPadding ->
        val users = listOf(
            User(
                227784409,
                "bhimz",
                "https://github.com/bhimz",
                "Aria Bima",
                "bhimz2001@gmail.com",
                "https://avatars.githubusercontent.com/u/5744350?v=4"
            ),
            User(
                227784408,
                "bhimz",
                "https://github.com/bhimz",
                null,
                "bhimz2001@gmail.com",
                "https://avatars.githubusercontent.com/u/5744350?v=4"
            )
        )
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(
                items = users,
                key = { user -> user.id }
            ) { user ->
                UserItem(user)
            }
        }

    }
}