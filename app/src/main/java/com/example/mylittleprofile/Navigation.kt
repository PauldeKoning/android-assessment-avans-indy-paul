package com.example.mylittleprofile

import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.*
import com.example.mylittleprofile.ui.home.Home
import com.example.mylittleprofile.ui.home.PonyWeb
import com.example.mylittleprofile.ui.ponylist.PonyDetail
import com.example.mylittleprofile.ui.ponylist.PonyDetailIntent
import com.example.mylittleprofile.ui.ponylist.PonyDetailIntentError
import com.example.mylittleprofile.ui.ponylist.PonyList
import com.example.mylittleprofile.ui.settings.Settings

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.home)
    object PonyList : Screen("ponylist", R.string.ponylist)
    object Settings : Screen("settings", R.string.settings)
    object Browser : Screen("browser", R.string.browser)
    object PonyDetail : Screen("ponydetail", R.string.ponydetail)
    object PonyDetailIntent : Screen("ponydetailintent", R.string.ponydetailintent)
}

val items = mapOf(
    Screen.Home to Icons.Filled.Home,
    Screen.PonyList to Icons.Filled.FormatListBulleted,
    Screen.Settings to Icons.Filled.Settings
)

@Composable
fun AppNavigation(intent: Uri) {
    val navController = rememberNavController()

    var defaultRoute = Screen.Home.route

    if (intent != Uri.EMPTY) {
        defaultRoute = Screen.PonyDetailIntent.route
    }

    Scaffold(
        topBar = {
          TopAppBar(
              title = { Text ("My Little Profile") }
          )
        },
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
                                popUpTo = navController.graph.startDestination
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            Modifier.padding(bottom = padding.calculateBottomPadding())
        ) {
            NavHost(navController, startDestination = defaultRoute) {
                composable(Screen.Home.route) {
                    Home(navController)
                }
                composable(Screen.PonyList.route) {
                    PonyList(navController)
                }
                composable(Screen.Settings.route) { Settings() }
                composable(Screen.Browser.route) {
                    PonyWeb(navController.previousBackStackEntry?.arguments?.getString("url")!!)
                }
                composable(Screen.PonyDetail.route) {
                    PonyDetail(navController.previousBackStackEntry?.arguments?.getParcelable("pony")!!)
                }
                composable(Screen.PonyDetailIntent.route) {
                    val regex = "^[0-9]*$".toRegex()

                    val regexResult = regex.matches(intent.toString().substringAfter("welove.ponies/"))

                    if (regexResult) {
                        PonyDetailIntent(intent.toString().substringAfter("welove.ponies/").toInt())
                    } else {
                        PonyDetailIntentError()
                    }
                }
            }
        }
    }
}
