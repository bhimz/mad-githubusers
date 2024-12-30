package com.bhimz.githubusers.usersearch

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bhimz.githubusers.domain.User
import com.bhimz.githubusers.ui.widget.SearchBar
import com.bhimz.githubusers.ui.widget.UserItem
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun UserSearchScreen(
    viewModel: UserSearchViewModel = viewModel(),
    onNavigateUp: () -> Unit,
    onNavigateToDetail: (User) -> Unit,
) {
    val userPage by viewModel.users.collectAsState(UserPage("", 0, listOf()))
    val listState = rememberLazyListState()
    val loadNext = remember {
        derivedStateOf {
            val info = listState.layoutInfo
            val itemCount = info.totalItemsCount
            val lastVisibleIndex = (info.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1
            itemCount > 0 && lastVisibleIndex > itemCount - 10
        }
    }
    LaunchedEffect(loadNext) {
        snapshotFlow { loadNext.value }.distinctUntilChanged().collect {
            if (it) {
                viewModel.loadMore(userPage.page + 1)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchBar(onClose = onNavigateUp) { viewModel.searchUsers(it) }
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding), state = listState) {
            items(
                items = userPage.users,
                key = { user -> user.id }
            ) { user ->
                UserItem(user) { onNavigateToDetail(it) }
            }
        }
    }
}