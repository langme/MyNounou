package com.example.mynounou.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomePage(navController: NavHostController){

    Box(modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .fillMaxSize()
    ){
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "HomePage")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview(){
    var navController = rememberNavController()
    HomePage (navController)
}