package com.bhimz.githubusers.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.bhimz.githubusers.domain.User
import com.bhimz.githubusers.ui.viewmodel.UserListViewModel
import com.bhimz.githubusers.ui.widget.UserItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    onNavigateToDetail: (User) -> Unit,
    userListViewModel: UserListViewModel = viewModel()
) {
    val users = userListViewModel.users.collectAsLazyPagingItems()
    val refreshState = rememberPullToRefreshState()

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
                },
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier.padding(innerPadding).fillMaxWidth(),
            state = refreshState,
            isRefreshing = users.loadState.refresh is LoadState.Loading,
            onRefresh = { users.refresh() }
        ) {
            LazyColumn {
                items(users.itemCount, key = users.itemKey { it.id }) { index ->
                    users[index]?.let {
                        UserItem(it) { user -> onNavigateToDetail(user) }
                    }
                }
                when {
                    users.loadState.refresh is LoadState.NotLoading -> {}

                }
            }
        }
    }
}