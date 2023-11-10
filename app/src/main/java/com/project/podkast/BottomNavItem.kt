package com.project.podkast

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource

sealed class BottomNavItem(
    var title: String,
    var icon: Int
){
    object Home: BottomNavItem("Home", R.drawable.home)
    object Category: BottomNavItem("Category", R.drawable.category)

    object Player: BottomNavItem("Player",R.drawable.playlist)

    object Profile: BottomNavItem("Profile",R.drawable.profile)
}