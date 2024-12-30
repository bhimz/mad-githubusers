package com.bhimz.githubusers.usersearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhimz.githubusers.data.GitUserRepository
import com.bhimz.githubusers.di.Configurator
import com.bhimz.githubusers.domain.User
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserSearchViewModel : ViewModel() {
    private val repository: GitUserRepository = Configurator.gitUserRepository
    private val _userPage = MutableStateFlow(UserPage("",0, listOf()))
    val users: Flow<UserPage> = _userPage
    private var searchJob: Job? = null
    private var loadMoreJob: Job? = null

    fun searchUsers(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            try {
                val matchedUsers = repository.search(query, 0, 30)
                loadMoreJob?.cancel()
                _userPage.value = UserPage(query, 0, matchedUsers)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadMore(page: Int) {
        println("=============> load more")
        val pageData = _userPage.value
        if (page == pageData.page) return
        loadMoreJob = viewModelScope.launch {
            val matchedUsers = repository.search(pageData.query, page, 30)
            val newUserList = pageData.users + matchedUsers
            _userPage.value = UserPage(pageData.query, page, newUserList)
        }
    }
}

data class UserPage(val query: String, val page: Int,val users: List<User>)