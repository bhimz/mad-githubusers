package com.bhimz.githubusers.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bhimz.githubusers.di.Configurator
import com.bhimz.githubusers.domain.User
import com.bhimz.githubusers.paging.UserListPagingSource
import kotlinx.coroutines.flow.Flow

class UserListViewModel : ViewModel() {
    private val gitUserRepository = Configurator.gitUserRepository


    val users: Flow<PagingData<User>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            prefetchDistance = 20
        ),
        pagingSourceFactory = { UserListPagingSource(gitUserRepository) }
    ).flow.cachedIn(viewModelScope)
}