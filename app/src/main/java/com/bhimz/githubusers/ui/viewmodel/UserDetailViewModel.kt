package com.bhimz.githubusers.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bhimz.githubusers.data.GitUserRepository
import com.bhimz.githubusers.di.Configurator
import com.bhimz.githubusers.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserDetailViewModel(private val username: String) : ViewModel() {
    private val gitUserRepository: GitUserRepository = Configurator.gitUserRepository

    private val _pageState = MutableStateFlow<UserDetailPageState>(UserDetailPageState.Loading)
    val pageState: Flow<UserDetailPageState> = _pageState

    init {
        initUser()
    }

    private fun initUser() = viewModelScope.launch {
        try {
            val user = gitUserRepository.get(username)
            if (user == null) {
                _pageState.value = UserDetailPageState.Error(Exception("no user found"))
            } else {
                _pageState.value = UserDetailPageState.Initialized(user)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _pageState.value = UserDetailPageState.Error(e)
        }
    }
}

sealed class UserDetailPageState {
    data object Loading : UserDetailPageState()
    data class Initialized(val user: User) : UserDetailPageState()
    data class Error(val error: Exception) : UserDetailPageState()
}

@Suppress("UNCHECKED_CAST")
class UserDetailViewModelFactory(private val username: String) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        UserDetailViewModel(username) as T
}