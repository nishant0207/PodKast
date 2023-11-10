package com.project.podkast

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Preview
@Composable
fun SearchScreenFun()
{
    var navController = rememberNavController()
    SearchScreen(navController = navController)
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(navController: NavController)
{
    var text by remember{ mutableStateOf("")}
    var active by remember { mutableStateOf(false)}
    var items = remember { mutableStateListOf<String>()}

    Scaffold {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = text,
            onQueryChange = {text = it},
            onSearch = {
                items.add(text)
                active = false},
            active = active,
            onActiveChange = {active = it},
            placeholder = {
                Text(text = "Search")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Button", modifier = Modifier.clickable { navController.navigate("Home") })
            },
            trailingIcon = {
                if(active)
                { Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon",
                    modifier = Modifier.clickable{
                        if(text.isNotEmpty()){
                            text= ""
                        }
                        else
                        {
                            active = false
                        }
                    }
                )
                }
            }
        ) {
                items.forEach{podcastName ->
                    Row(modifier = Modifier.padding(all = 15.dp))
                    {
                        Icon(painter = painterResource(id = R.drawable.history), contentDescription = "History icon", modifier = Modifier.padding(end = 10.dp))
                        Text(text = podcastName, modifier = Modifier.clickable {
                            val selectedPodcast = podcastManager.getPodcastByName(podcastName)
                            if(selectedPodcast != null)
                            {
                                navController.navigate("com.project.podkast.Player/${selectedPodcast.id}")
                            }
                        })
                    }
                }
        }
    }
}