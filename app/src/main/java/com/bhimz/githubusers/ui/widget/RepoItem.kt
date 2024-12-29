package com.bhimz.githubusers.ui.widget

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.bhimz.githubusers.domain.Repo

@Composable
fun RepoItem(repo: Repo) {
    Card {
        Text(repo.name)
        Text(repo.description.orEmpty())
    }
}