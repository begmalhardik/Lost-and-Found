package com.example.lostandfound.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SplashScreen() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE0E0E0), // light grey
                contentColor = Color.DarkGray
            ),
            border = BorderStroke(1.dp, Color.Gray),
            elevation = ButtonDefaults.buttonElevation(4.dp),
            modifier = Modifier
                .width(260.dp)
                .padding(8.dp)
        ) {
            Text("CREATE A NEW ADVERT")
        }

        Button(
            onClick = { },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE0E0E0),
                contentColor = Color.DarkGray
            ),
            border = BorderStroke(1.dp, Color.Gray),
            elevation = ButtonDefaults.buttonElevation(4.dp),
            modifier = Modifier
                .width(260.dp)
                .padding(8.dp)
        ) {
            Text("SHOW ALL LOST & FOUND ITEMS")
        }

    }
}