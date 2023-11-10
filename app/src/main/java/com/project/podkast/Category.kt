package com.project.podkast

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun Category(navController: NavController)
{

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    )
    {
        Column(modifier = Modifier.fillMaxHeight()
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp)
            .padding(bottom = 75.dp)
        )
        {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = "back",
                    modifier = Modifier
                        .clickable {
                            navController.navigate("Home")
                        }
                        .size(30.dp)
                )
                Spacer(Modifier.padding(horizontal = 45.dp))
                Text(text = "Category", fontSize = 28.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.padding(horizontal = 45.dp))
                Icon(
                    painter = painterResource(id = R.drawable.menu_dots),
                    contentDescription = "menu",
                    modifier = Modifier
                        .clickable {
                        }
                        .size(20.dp)
                )

            }



            // ------ Tiles for different catergories ---------
            val categories = podcastManager.getAllPodcasts().distinctBy { it.category }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(categories) { category ->
                    CategoryTile(category = category, navController = navController)
                }
            }


        }

        Box(modifier = Modifier
            .align(Alignment.BottomCenter)
            .height(75.dp)
            .drawWithContent {
                drawContent()
                drawRoundRect(
                    color = Color.Black,
                    size = size.copy(height = 0f),
                    topLeft = Offset(0f, -5f),
                    cornerRadius = CornerRadius.Zero
                )
            }
            .background(color = Color.White)
        )
        {
            BottomNavigation(navController = navController)
        }
    }

}

@Preview
@Composable
fun CategoryView()
{
    Category(navController = rememberNavController())
}


@Composable
fun CategoryTile(category: PodcastItem, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(8.dp)
            .clickable {
                Log.d("CategoryTile", "Category clicked: ${category.category}")
                navController.navigate(Screen.PodcastList(category.category).route)
            }
    ) {
        Image(
            painter = painterResource(id = category.thumbnail),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.primary)
                .blur(radiusX = 15.dp, radiusY = 15.dp)

        )
        Text(
            text = category.category,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(8.dp)
        )
    }
}



@Composable
fun PodcastListScreen(category: String, navController: NavController) {
    val podcastsByCategory = podcastManager.getAllPodcasts().filter { it.category == category }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(podcastsByCategory) { podcast ->
            PodcastCard(podcast = podcast, onClick = {
                // Navigate to the player screen when the card is clicked
                navController.navigate("Player/${podcast.id}")
            })
        }
    }

}


@Composable
fun PodcastCard(podcast: PodcastItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
            .shadow(elevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = podcast.thumbnail),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = podcast.podcastName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = podcast.creatorName, fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Views: ${podcast.view}", fontSize = 14.sp, color = Color.Gray)
        }
    }
}