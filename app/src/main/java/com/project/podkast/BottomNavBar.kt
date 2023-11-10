package com.project.podkast

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@SuppressLint("SuspiciousIndentation")
@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(BottomNavItem.Home, BottomNavItem.Category, BottomNavItem.Player, BottomNavItem.Profile)

    NavigationBar(
        modifier = Modifier.background(Color.White),
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        items.forEach { item ->
            NavigationBarItem(
                label = { Text(text = item.title, fontSize = 16.sp, fontWeight = FontWeight.Medium, modifier = Modifier.align(Alignment.Bottom)) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier
                            .size(28.dp)
                            .background(Color.White)
                    )
                },
                selected = false,
                alwaysShowLabel = true,
                onClick = {
                    when (item) {
                        BottomNavItem.Home -> navController.navigate("Home")
                        BottomNavItem.Category -> navController.navigate("Category")
                        BottomNavItem.Player -> navController.navigate("Player/{selectedPodcast.id}")
                        BottomNavItem.Profile -> navController.navigate("Profile")
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .background(Color.Transparent),
            )
        }
    }
}
