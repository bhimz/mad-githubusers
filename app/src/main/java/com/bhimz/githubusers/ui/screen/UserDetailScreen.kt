package com.bhimz.githubusers.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.bhimz.githubusers.ui.theme.Typography
import com.bhimz.githubusers.ui.viewmodel.UserDetailPageState
import com.bhimz.githubusers.ui.viewmodel.UserDetailViewModel
import com.bhimz.githubusers.ui.viewmodel.UserDetailViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    username: String,
    viewModel: UserDetailViewModel = viewModel(factory = UserDetailViewModelFactory(username)),
    onNavigateUp: () -> Unit,
) {
    val pageState by viewModel.pageState.collectAsState(UserDetailPageState.Loading)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                title = {
                    Text("User Detail")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateUp
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when (pageState) {
            is UserDetailPageState.Initialized -> {
                val user = (pageState as UserDetailPageState.Initialized).user
                Column(modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()) {
                    Spacer(modifier = Modifier.height(18.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(user.avatarUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(24.dp))
                        if (user.name == null) {
                            Text(
                                user.username,
                                style = Typography.titleLarge
                                    .copy(fontWeight = FontWeight.Bold)
                            )
                        } else {
                            Column {
                                Text(
                                    user.name,
                                    style = Typography.titleLarge
                                        .copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    user.username,
                                    style = Typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
            else -> {}
        }
    }
}