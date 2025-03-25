package com.example.weatherapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(title: String,
           navController: NavHostController,
           darkThemeViewModel: DarkThemeViewModel) {
    val isDark by darkThemeViewModel.isDark.observeAsState()
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            actionIconContentColor =
                MaterialTheme.colorScheme.primary,
            navigationIconContentColor =
                MaterialTheme.colorScheme.primary,
            containerColor =
            if (isDark == false)
                MaterialTheme.colorScheme.primaryContainer
            else
                Color.Black
            ,
            titleContentColor =
                MaterialTheme.colorScheme.primary
        ),
        title = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(title)
            }

        },
        navigationIcon = {
            IconButton(onClick = { navController.navigate("ProfilePage") }) {
                Icon(Icons.Filled.AccountBox, contentDescription = "Navigation Icon")
            }
        },
        actions = {
            IconButton(onClick = { darkThemeViewModel.updateIsDark() }) {
                Icon(
                    if (isDark == false)
                        Icons.Filled.DarkMode
                    else
                        Icons.Filled.LightMode
                    , contentDescription = "Action Icon")
            }
        }
    )
}