package com.project.podkast



import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


class Home : AppCompatActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    BottomNavigation(navController = navController)
                },
                content = {
                    NavHost(navController = navController, startDestination = "LoadingScreen") {
                        composable("Home") { HomeScreen(navController = navController) }
                        composable("Category") { Category(navController = navController) }
                        composable("Profile") { Profile(navController = navController) }
                        composable("Player/{podcastId}",
                            arguments = listOf(navArgument("podcastId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val arguments = requireNotNull(backStackEntry.arguments)
                            val podcastId = arguments.getString("podcastId")
                            val selectedPodcast = podcastManager.getPodcastById(podcastId)
                            Player(selectedPodcast = selectedPodcast, navController = rememberNavController())
                        }
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview()
{
    val navController = rememberNavController();
    HomeScreen(navController = navController)
}

val podcastManager = PodcastManager()
val topPodcast = podcastManager.getTopPodcast()
val otherPodcasts = podcastManager.getAllPodcasts()

@Composable
fun HomeScreen(navController: NavController) {

    var selectedPodcast by remember {mutableStateOf<PodcastItem?>(null)}
    val playlist = remember { mutableStateListOf<PodcastItem>() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    )
    {
        Column ()
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            )
            {
                Box(modifier = Modifier.width(80.dp))
                {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Image(painter = painterResource(id = R.drawable.mic_logo), contentDescription = "Mic Logo")
                        Text(text = "PodKast", fontSize = 14.sp)
                    }

                }
                Text(text = "Home", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.padding(horizontal = 0.5.dp))
                Icon(painter = painterResource(id = R.drawable.search_icon), contentDescription = "Search Icon",
                    modifier = Modifier
                        .clickable { navController.navigate("Search") }
                        .size(30.dp)
                )
            }

            Column(
                modifier = Modifier.padding(bottom = 75.dp)
            )
            {
                Text(
                    text = "Trending Podcast",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Box( modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .shadow(elevation = 2.dp)
                )
                {
                    topPodcast?.let{podcast -> PodcastItemCard(podcast = podcast,
                        onPlayClicked = {selectedPodcast = podcast; navController.navigate("Player/${podcast.id}")},
                        onAddToPlaylistClicked = {playlist.add(podcast)}
                    )
                    }
                }
                LazyColumn() {
                    items(otherPodcasts) { podcast ->
                        PodcastItemCard(podcast = podcast,
                            onPlayClicked = {selectedPodcast = podcast; navController.navigate("Player/${podcast.id}")},
                            onAddToPlaylistClicked = {playlist.add(podcast)}
                        )
                    }
                }
            }

        }
        // ___________________________ nav bar box ____________________________________
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


@SuppressLint("RememberReturnType")
@Composable
fun PodcastItemCard(podcast: PodcastItem, onPlayClicked: () -> Unit, onAddToPlaylistClicked: ()-> Unit)
{
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White)
    ) {

        if(podcast == topPodcast)
        {
            // top podcast
                Column(
                    modifier = Modifier.align(Alignment.TopCenter)
                )
                {
                    Image(
                        painter = painterResource(id = podcast.thumbnail),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(30.dp))
                            .shadow(elevation = 30.dp, shape = RoundedCornerShape(30.dp))
                    )
                    Row(
                        // top podcast play options
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                            .clickable { onPlayClicked() },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    )
                    {
                        Icon(
                            painter = painterResource(id = R.drawable.icons8_play_button_100),
                            contentDescription = "Play",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    onPlayClicked()
                                    podcastManager.playPodcast(podcast.id)
                                }
                        )

                        Column(
                            modifier = Modifier.width(250.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        )
                        {
                            val maxCharacter = 70
                            val displayedName = if(podcast.podcastName.length> maxCharacter)
                            {
                                podcast.podcastName.take(maxCharacter)+"..."
                            }
                            else    {
                                podcast.podcastName
                            }
                            Text(text = podcast.creatorName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text(text = displayedName, fontSize =  12.sp, fontWeight = FontWeight.Light)
                        }

                        Icon(painter = painterResource(id = R.drawable.add_playlist), contentDescription = "Add to Playlist Icon", modifier = Modifier
                            .size(30.dp)
                            .clickable { onAddToPlaylistClicked() }
                        )
                    }
                }
        }

        else
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {

                Image(painter = painterResource(id = podcast.thumbnail), contentDescription = null, modifier = Modifier
                    .size(70.dp)
                    .padding(horizontal = 5.dp)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp, end = 8.dp)
                )
                {
                    Text(text = podcast.podcastName, fontWeight = FontWeight.Medium, fontSize = 18.sp)
                    Text(text = podcast.creatorName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                Column(
                        modifier = Modifier.padding(5.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                )
                {
                    Icon(
                        painter = painterResource(id = R.drawable.icons8_play_button_100),
                        contentDescription = "Play",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onPlayClicked()
                                podcastManager.playPodcast(podcast.id)
                            }
                    )
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.add_playlist),
                        contentDescription = "Add to playlist",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onAddToPlaylistClicked() }
                    )
                }

            }
        }
    }
}