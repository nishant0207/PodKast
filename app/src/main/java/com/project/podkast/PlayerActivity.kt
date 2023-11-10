package com.project.podkast

import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun Player(selectedPodcast: PodcastItem?, navController: NavController) {
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer() }
    var isPlayingState by remember { mutableStateOf(true) }
    var currentTime by remember { mutableLongStateOf(0L) }
    var totalTime by remember { mutableLongStateOf(0L) }

    var seekBarProgress by remember { mutableFloatStateOf(0f) }


    DisposableEffect(selectedPodcast) {
        if (selectedPodcast != null) {
            val uri = Uri.parse("android.resource://${context.packageName}/${selectedPodcast.url}")
            mediaPlayer.setDataSource(context, uri)
            mediaPlayer.prepare()
            mediaPlayer.start()
            mediaPlayer.setOnPreparedListener{
                player -> totalTime = player.duration.toLong()
            }
        }

        onDispose {
            mediaPlayer.release()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
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
                Text(text = "Player", fontSize = 28.sp, fontWeight = FontWeight.Medium)
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
            Spacer(Modifier.height(20.dp))
            //-------------------------------- Podcast Thumbnail --------------------------------
            selectedPodcast?.thumbnail?.let {
                Box(
                    modifier = Modifier
                        .size(350.dp)
                        .background(Color.Black)
                        .shadow(elevation = 50.dp)
                        .drawWithContent {
                            drawContent()
                            drawRoundRect(
                                color = Color.Black,
                                size = size.copy(height = 0f),
                                topLeft = Offset(0f, -5f),
                                cornerRadius = CornerRadius.Zero
                            )
                        }
                        .shadow(elevation = 70.dp)
                    )
                {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        modifier = Modifier.fillMaxHeight()
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            )
            {
                selectedPodcast?.let {
                    Text(
                        text = it.podcastName, // Display podcast name
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(Modifier.height(5.dp))
                    Text(
                        text = it.creatorName, // Display creator name
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            var isHeartIconBlack by remember { mutableStateOf(true) }

            val heartIconColor = if (isHeartIconBlack) Color.Black else Color.Red


            var isBookmarkIconBlack by remember { mutableStateOf(true) }

            val BookmarkIconColor = if (isBookmarkIconBlack) Color.Black else Color(128,0,128)

            Row(modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly)
            {
                Icon(
                    painter = painterResource(id = R.drawable.heart),
                    contentDescription = "",
                    Modifier
                        .size(35.dp)
                        .clickable {
                            isHeartIconBlack = !isHeartIconBlack
                        },
                    tint = heartIconColor
                )
                Spacer(Modifier.padding(horizontal = 35.dp))
                Icon(
                    painter = painterResource(id = R.drawable.share),
                    contentDescription = "",
                    Modifier.size(35.dp)
                )
                Spacer(Modifier.padding(horizontal = 35.dp))
                Icon(painter = painterResource(
                    id = R.drawable.bookmark),
                    contentDescription = "",
                    Modifier
                        .size(35.dp)
                        .clickable {
                            isBookmarkIconBlack = !isBookmarkIconBlack
                        },
                    tint = BookmarkIconColor
                )

            }
            
            Spacer(modifier = Modifier.height(10.dp))

            Slider(
                value = seekBarProgress,
                onValueChange = {
                    seekBarProgress = it
                    val newPosition = (it * mediaPlayer.duration).toInt()
                    mediaPlayer.seekTo(newPosition)
                },
                valueRange = 0f..1f,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            val currentTimeMillis = (seekBarProgress * mediaPlayer.duration).toLong()


            Spacer(modifier = Modifier.height(2.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    text = formatTime(currentTimeMillis),
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Text(
                    text = formatTime(totalTime),
                    fontSize = 14.sp,
                    color = Color.Gray
                )

            }


            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.backward),
                    contentDescription = "Backward",
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {
                            val currentPosition = mediaPlayer.currentPosition
                            val newPosition = currentPosition - 10000 // 10 seconds in milliseconds
                            val clampedPosition = newPosition.coerceAtLeast(0)
                            mediaPlayer.seekTo(clampedPosition)
                            seekBarProgress = clampedPosition.toFloat() / mediaPlayer.duration
                            currentTime = clampedPosition.toLong()
                        }
                )

                Spacer(Modifier.padding(horizontal = 35.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(48.dp)
                        .clickable {
                            if (isPlayingState) {
                                mediaPlayer.pause()
                            } else {
                                mediaPlayer.start()
                            }
                            isPlayingState = !isPlayingState
                        }
                ) {
                    val playPauseIcon = if (isPlayingState) {
                        painterResource(id = R.drawable.pause_icon)
                    } else {
                        painterResource(id = R.drawable.play)
                    }
                    Icon(
                        painter = playPauseIcon,
                        contentDescription = null,
                        modifier = Modifier.size(35.dp),
                        tint = Color(64, 1, 134, 255)
                    )
                }
                Spacer(Modifier.padding(horizontal = 35.dp))
                Icon(
                    painter = painterResource(id = R.drawable.forward),
                    contentDescription = "Forward",
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {
                            val currentPosition = mediaPlayer.currentPosition
                            val newPosition = currentPosition + 10000 // 10 seconds in milliseconds
                            val clampedPosition = newPosition.coerceAtMost(mediaPlayer.duration)
                            mediaPlayer.seekTo(clampedPosition)
                            seekBarProgress = clampedPosition.toFloat() / mediaPlayer.duration
                            currentTime = clampedPosition.toLong()
                        }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PlayerPreview() {
    Player(selectedPodcast = PodcastItem(
            id = 1,
            podcastName = "Sample Podcast",
            creatorName = "Sample Creator",
            category = "Sample Category",
            url = R.raw.legacy,
            view = 100,
            thumbnail = R.drawable.gaurgopaldas_returns_to_trs_life_monkhood_spirituality), navController = rememberNavController()
    )
}


@Composable
fun formatTime(milliseconds: Long): String {
    val totalSeconds = milliseconds / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}
