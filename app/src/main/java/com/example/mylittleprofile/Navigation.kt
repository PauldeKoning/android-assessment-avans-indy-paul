package com.example.mylittleprofile

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.mylittleprofile.model.CharacterModel
import com.example.mylittleprofile.ui.home.Home
import com.example.mylittleprofile.ui.ponylist.PonyDetail
import com.example.mylittleprofile.ui.ponylist.PonyList
import com.example.mylittleprofile.ui.profile.Profile
import kotlin.reflect.typeOf

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.home)
    object PonyList : Screen("ponylist", R.string.ponylist)
    object Profile : Screen("profile", R.string.profile)
    object PonyDetail : Screen("ponydetail", R.string.ponydetail)
}

val items = mapOf(
    Screen.Home to Icons.Filled.Home,
    Screen.PonyList to Icons.Filled.List,
    Screen.Profile to Icons.Filled.AccountCircle
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.value, "Icon") },
                        label = { Text(stringResource(screen.key.resourceId)) },
                        selected = currentRoute == screen.key.route,
                        onClick = {
                            navController.navigate(screen.key.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo = navController.graph.startDestination
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        NavHost(navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) { Home() }
            composable(Screen.PonyList.route) { PonyList(navController) }
            composable(Screen.Profile.route) { Profile() }
            composable(Screen.PonyDetail.route) {
                PonyDetail(navController.previousBackStackEntry?.arguments?.getParcelable("pony")!!)
            }
        }
    }
}
