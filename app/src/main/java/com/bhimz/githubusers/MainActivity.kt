package com.bhimz.githubusers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bhimz.githubusers.userdetail.UserDetailScreen
import com.bhimz.githubusers.userlist.UserListScreen
import com.bhimz.githubusers.ui.theme.AppTheme
import com.bhimz.githubusers.usersearch.UserSearchScreen
import kotlinx.serialization.Serializable

@Serializable
object UserList

@Serializable
data class UserDetail(val username: String)

@Serializable
object SearchUser

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = UserList) {
                    composable<UserList> {
                        UserListScreen(
                            onNavigateToDetail = {
                                navController.navigate(route = UserDetail(it.username))
                            },
                            onNavigateToSearch = {
                                navController.navigate(route = SearchUser)
                            }
                        )
                    }

                    composable<UserDetail> {
                        val userDetail: UserDetail = it.toRoute()
                        UserDetailScreen(userDetail.username) {
                            navController.navigateUp()
                        }
                    }

                    composable<SearchUser> {
                        UserSearchScreen(onNavigateUp = { navController.navigateUp() })
                    }
                }
            }
        }
    }
}