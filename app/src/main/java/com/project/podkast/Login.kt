package com.project.podkast


import android.content.Context
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth



class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val currentUser = FirebaseAuth.getInstance().currentUser
            if(currentUser != null)
            {
                navController.navigate("Home")
            }
            else
            {
                Loginpage(navController = navController)
            }

        }
    }
}


@Composable
fun Loginpage(navController: NavController)
{

    val context = LocalContext.current ?: error("No Context provided")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)

    )
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(10.dp)
        )
        {
            Image(
                painter = painterResource(id = R.drawable.vecteezy_close_up_portrait_of_gorilla_wearing_glasses_and_headset_23161016_448),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(30.dp)
                    .clip(shape = RoundedCornerShape(100.dp))
                    .blur(10.dp),
            )
        }
        Text(
            text = "Login", fontSize = 70.sp, fontWeight = FontWeight.Bold, color = Color.White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp)
            )
            Box(
                modifier = Modifier
                    .height(500.dp)
                    .fillMaxWidth()
                    .background(color = Color(0.89f, 0.89f, 0.89f, 0.4f))
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                contentAlignment = Alignment.TopCenter

            )
            {
                Column {
                    var email by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }
                    var showPassword by remember { mutableStateOf(value = false) }

                    Spacer(modifier = Modifier.padding(20.dp))
                    // ------------------------ Email Text Field ------------------------------------------------
                    TextField(
                        value = email, onValueChange = { newEmail -> email = newEmail },
                        label = {
                            Text("Email", color = Color.Black)
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        leadingIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_email_24),
                                contentDescription = null
                            )
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.border(1.dp, color = Color.Gray, shape = RoundedCornerShape(20.dp))
                    )
                    Spacer(modifier = Modifier.padding(20.dp))

                    // ------------------------ Password Text Field ------------------------------------------------
                    TextField(
                        value = password, onValueChange = { newPassword -> password = newPassword },
                        label = {
                            Text("Password", color = Color.Black)
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        leadingIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_lock_24),
                                contentDescription = null
                            )
                        },
                        visualTransformation = if (showPassword) {

                            VisualTransformation.None

                        } else {

                            PasswordVisualTransformation()

                        },

                        keyboardOptions = KeyboardOptions.Default.copy
                            (
                            keyboardType = KeyboardType.Password
                        ),

                        trailingIcon = {
                            if (showPassword) {
                                IconButton(onClick = { showPassword = false }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_visibility_24),
                                        contentDescription = "hide_password"
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = { showPassword = true }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_visibility_off_24),
                                        contentDescription = "hide_password"
                                    )
                                }
                            }
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.border(1.dp, color = Color.Gray, shape = RoundedCornerShape(20.dp))
                    )

                    Spacer(modifier = Modifier.padding(10.dp))

                    var showNotification by remember { mutableStateOf(false)}
                    if(showNotification)
                    {
                            Text(text = "Please enter the credentials", color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
                    }

                    Spacer(modifier = Modifier.padding(20.dp))

                    Button(
                        onClick = {
                            if(email.isNotEmpty() && password.isNotEmpty())
                            {
                                auth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener() { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT,
                                            ).show();

                                            // ------------------------ Will navigate to home screen --------------------------------
                                            navController.navigate("Home")
                                        } else {
                                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT,
                                            ).show()
                                        }
                                    }
                            }
                            else{ showNotification= true }},
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .width(280.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors
                            (
                            containerColor = Color(95,72,207),
                            contentColor = Color.White),
                        shape = RoundedCornerShape(20.dp),
                    )
                    {
                        Text(text = "Log In", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }


                    Spacer(modifier = Modifier.padding(20.dp))

                        Row(
                            modifier = Modifier
                                .width(280.dp)
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        )
                        {
                            Text(text = "Don't have an account?",color = Color.Gray)
                            Spacer(modifier = Modifier.padding(5.dp))
                           Text(text = "Sign Up", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { navController.navigate("Signup")
                           },
                               style = TextStyle(color = Color(95,72,207))
                           )
                        }
                }
            }
    }
}