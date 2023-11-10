package com.project.podkast


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

var auth: FirebaseAuth = FirebaseAuth.getInstance()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }
}


@Composable
fun Navigation()
{
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "LoadingScreen")
    {
        composable("LoadingScreen"){
            LoadingScreen(navController = navController)
        }
        composable("Login")
        {
            Loginpage(navController = navController)
        }
        composable("Signup")
        {
            Signup(navController = navController)
        }
        composable("Home")
        {
            HomeScreen(navController = navController)
        }
        composable("Profile")
        {
            Profile(navController = navController)
        }
        composable("Category")
        {
            Category(navController = navController)
        }
        composable("Player/{podcastId}",
            arguments = listOf(navArgument("podcastId") { type = NavType.StringType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val podcastId = arguments.getString("podcastId")
            val selectedPodcast = podcastManager.getPodcastById(podcastId)
            Player(selectedPodcast = selectedPodcast, navController = navController)
        }
        composable("Search")
        {
            SearchScreen(navController = navController)
        }
    }

}


@Composable
fun LoadingScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(1200)
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser!=null)
        {
            navController.navigate("Home")
        }
        else
        {
            navController.navigate("Login")
        }
    }

    AnimatedVisibility(visible = true, exit = slideOutHorizontally())
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        )
        {

            Image(
                painter = painterResource(id = R.drawable.vecteezy_close_up_portrait_of_gorilla_wearing_glasses_and_headset_23161016_448),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(30.dp)
                    .clip(shape = RoundedCornerShape(100.dp))
            )
            // ---------------------------- mice logo -----------------------------------
            Image(painter = painterResource(id = R.drawable.mic_logo), contentDescription = null,
                modifier = Modifier
                    .padding(top = 250.dp)
                    .padding(start = 290.dp)
                    .size(80.dp)
                    )

            Spacer(modifier = Modifier.padding(20.dp))

            Text(
                text = "Pod-Kast",
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 20.dp)

            )

            Text(text = "Your favourite podcast, anytime, anywhere – because boring is so last season!", fontSize = 14.sp, modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 130.dp)
                .padding(30.dp), textAlign = TextAlign.Center)

            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 100.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}


