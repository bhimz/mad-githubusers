package com.bhimz.githubusers.ui.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.bhimz.githubusers.domain.User
import com.bhimz.githubusers.ui.theme.AppTheme
import com.bhimz.githubusers.ui.theme.Typography

@Composable
fun UserItem(user: User, modifier: Modifier = Modifier, onSelected: (User) -> Unit, ) {

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 10.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 6.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.surfaceVariant
        ),
        onClick = { onSelected(user) }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.avatarUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(18.dp))
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
                        style = Typography.labelLarge
                    )
                }
            }
        }
    }

}