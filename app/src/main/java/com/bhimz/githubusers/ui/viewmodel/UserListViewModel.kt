package com.bhimz.githubusers.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhimz.githubusers.di.Configurator
import com.bhimz.githubusers.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserListViewModel : ViewModel() {
    private val gitUserRepository = Configurator.gitUserRepository

    private val _users = MutableStateFlow<List<User>>(listOf())
    val users: Flow<List<User>> = _users

    init {
        initUsers()
    }

    private fun initUsers() = viewModelScope.launch {
        val result = gitUserRepository.fetch()
        _users.value = result
    }
}