package com.bhimz.githubusers.userdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.bhimz.githubusers.domain.UserDetail
import com.bhimz.githubusers.ui.theme.Typography
import com.bhimz.githubusers.ui.widget.RepoItem
import com.bhimz.githubusers.ui.widget.TopBar
import java.text.SimpleDateFormat
import java.util.Locale


private val dateFormat = SimpleDateFormat("MMM yyyy", Locale.US)

@Composable
fun UserDetailScreen(
    username: String,
    viewModel: UserDetailViewModel = viewModel(factory = UserDetailViewModelFactory(username)),
    onNavigateUp: () -> Unit,
) {
    val pageState by viewModel.pageState.collectAsState(UserDetailPageState.Loading)
    val repoSectionState by viewModel.repoSectionState.collectAsState(RepoSectionState.Loading)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar("User Details") { onNavigateUp() }
        }
    ) { innerPadding ->
        when (pageState) {
            is UserDetailPageState.Initialized -> {
                val userDetail = (pageState as UserDetailPageState.Initialized).userDetail
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(18.dp))

                    HeaderSection(userDetail)

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RectangleShape
                            ),
                    ) {
                        when (repoSectionState) {
                            is RepoSectionState.Initialized -> {
                                val repos = (repoSectionState as RepoSectionState.Initialized).repos
                                items(repos.count()) { index ->
                                    RepoItem(repos[index])
                                }
                            }

                            else -> {}
                        }
                    }
                }
            }

            else -> {}
        }
    }
}

@Composable
fun HeaderSection(userDetail: UserDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(userDetail.avatarUrl)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(24.dp))
        if (userDetail.name == null) {
            Text(
                userDetail.username,
                style = Typography.titleLarge
                    .copy(fontWeight = FontWeight.Bold)
            )
        } else {
            Column {
                Text(
                    userDetail.name,
                    style = Typography.titleLarge
                        .copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    userDetail.username,
                    style = Typography.bodyLarge
                )
                userDetail.location?.let {
                    Text(
                        it,
                        style = Typography.titleMedium
                            .copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "MEMBER SINCE",
                style = Typography.titleMedium
                    .copy(fontWeight = FontWeight.Bold)
            )
            userDetail.memberSince?.let {
                Text(
                    dateFormat.format(it).uppercase(),
                    style = Typography.labelMedium
                )
            }
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "FOLLOWERS",
                style = Typography.titleMedium
                    .copy(fontWeight = FontWeight.Bold)
            )
            Text(
                userDetail.followers.toString(),
                style = Typography.labelMedium
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "FOLLOWING",
                style = Typography.titleMedium
                    .copy(fontWeight = FontWeight.Bold)
            )
            Text(
                userDetail.following.toString(),
                style = Typography.labelMedium
            )
        }
    }
    Spacer(modifier = Modifier.height(18.dp))
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(color = Color.LightGray)
    )
}