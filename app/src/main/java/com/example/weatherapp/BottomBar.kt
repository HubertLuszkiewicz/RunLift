package com.example.weatherapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomBar(
    navController : NavHostController,
    iconsViewModel : IconsViewModel,
    darkThemeViewModel : DarkThemeViewModel
) {
    val selectedItemIndex by iconsViewModel.selectedItemIndex.observeAsState()
    val isDark by darkThemeViewModel.isDark.observeAsState()
    NavigationBar(
        containerColor =
        if (isDark == false)
            MaterialTheme.colorScheme.primaryContainer
        else
            Color.Black,
        contentColor =
        if (isDark == false)
            MaterialTheme.colorScheme.primary
        else
            Color.White,
    ) {
        items.forEachIndexed {index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    iconsViewModel.updateSelectedItemIndex(index)
                    navController.navigate(item.title)

                },
                icon = {
                    Icon(
                        imageVector = if(index == selectedItemIndex)
                            item.selectedIcon
                        else
                            item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) },

            )

        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

val items = listOf(
    BottomNavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    ),
    BottomNavigationItem(
        title = "Weather",
        selectedIcon = Icons.Filled.Cloud,
        unselectedIcon = Icons.Outlined.Cloud
    ),
    BottomNavigationItem(
        title = "Stoper",
        selectedIcon = Icons.Filled.Timer,
        unselectedIcon = Icons.Outlined.Timer
    )
)

@Preview
@Composable
fun BottomBarPreview() {
    BottomBar(
        navController = rememberNavController(), iconsViewModel = IconsViewModel(), darkThemeViewModel = DarkThemeViewModel())
}