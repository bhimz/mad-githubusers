package com.bhimz.githubusers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bhimz.githubusers.domain.User
import com.bhimz.githubusers.ui.screen.UserDetailScreen
import com.bhimz.githubusers.ui.screen.UserListScreen
import com.bhimz.githubusers.ui.theme.AppTheme
import kotlinx.serialization.Serializable

@Serializable
object UserList

@Serializable
data class UserDetail(val username: String)

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
                            }
                        )
                    }

                    composable<UserDetail> {
                        val userDetail: UserDetail = it.toRoute()
                        UserDetailScreen(userDetail.username) {
                            navController.navigateUp()
                        }
                    }
                }
            }
        }
    }
}