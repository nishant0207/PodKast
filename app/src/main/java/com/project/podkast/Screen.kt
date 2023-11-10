package com.project.podkast

sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object CategoryList : Screen("CategoryList")
    data class PodcastList(val category: String) : Screen("PodcastList/${category}")
}
