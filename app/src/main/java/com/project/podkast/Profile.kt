package com.project.podkast

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch


@Composable
fun Profile(navController: NavController)
{
    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser

    Box(modifier = Modifier
        .fillMaxSize()
    )
    {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)
            .padding(bottom = 75.dp)
            .padding(horizontal = 15.dp),
        )
        {
            if (currentUser != null) {
                // User is logged in
                val email = currentUser.email
                // Use the 'email' variable to get the user's email address
                if (email != null) {
                    // User's email is not null, you can use it here
                    Text(text = email, Modifier.align(Alignment.CenterHorizontally))
                } else {
                    // User's email is null, handle the case accordingly
                    Text(text = "User's email is null")
                }
            } else {
                // User is not logged in, handle the case accordingly
                Text(text = "User is not logged in")
            }


            Image(painter = painterResource(id = R.drawable.user), contentDescription = "placeholder image",
                Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally))


            val coroutineScope = rememberCoroutineScope()
            val imageUri = remember { mutableStateOf<Uri?>(null) } // Remember the selected image URI


            val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val resultCode = result.resultCode
                val data: Intent? = result.data
                if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                    val selectedImageUri = data.data
                    // Handle the selected image URI (upload it, display it, etc.)
                    if (selectedImageUri != null) {
                        imageUri.value = selectedImageUri // Set the selected image URI to the state variable
                        coroutineScope.launch {
                            // You can perform operations with the selected image URI here
                            // For example, upload the image to Firebase Storage
                        }
                    }
                }
            }

            Button(
                onClick = {
                // Launch gallery picker
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                imagePickerLauncher.launch(galleryIntent)
                    },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            {
                Text(text = "Upload Image")
            }

            Text(text = "Downloads", fontSize = 18.sp, modifier= Modifier.align(Alignment.Start))
            Text(text = "History", fontSize = 18.sp, modifier= Modifier.align(Alignment.Start))
            Text(text = "Paid Membership", fontSize = 18.sp, modifier= Modifier.align(Alignment.Start))
            Text(text = "Settings", fontSize = 18.sp, modifier= Modifier.align(Alignment.Start))
            Text(text = "Help & feedback", fontSize = 18.sp, modifier= Modifier.align(Alignment.Start))
            Text(text = "Creator Mode", fontSize = 18.sp, modifier= Modifier.align(Alignment.Start))

            Button(
                onClick = { Firebase.auth.signOut()
                    navController.navigate("Login")
                          },
                Modifier.align(Alignment.CenterHorizontally),

            )
            {
                Text(text = "Logout")
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
fun Profileview()
{
    Profile(navController = rememberNavController())
}