package com.bhimz.githubusers.userlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.bhimz.githubusers.domain.User
import com.bhimz.githubusers.ui.widget.UserItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    onNavigateToDetail: (User) -> Unit,
    onNavigateToSearch: () -> Unit,
    userListViewModel: UserListViewModel = viewModel()
) {
    val users = userListViewModel.users.collectAsLazyPagingItems()
    val refreshState = rememberPullToRefreshState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                title = {
                    Text("Github Search")
                },
                actions = {
                    IconButton(
                        onClick = { onNavigateToSearch() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            tint = Color.White,
                            contentDescription = "Search"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
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
                    users.loadState.hasError -> {
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(
                                message = "Uh oh, something went wrong! Try again later.",
                                actionLabel = "OK",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
            }
        }
    }
}