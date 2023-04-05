package com.example.mynounou.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.himanshoe.kalendar.Kalendar
import com.himanshoe.kalendar.color.KalendarThemeColor
import com.himanshoe.kalendar.model.KalendarType

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
            Row(modifier = Modifier.weight(3f)) {
                Calendar(modifier = Modifier.fillMaxSize())
            }
            Row(modifier = Modifier.weight(1f)) {
                Text(text = "HomePage")
            }

        }
    }
}
@Composable
fun Calendar(modifier : Modifier){
    val CalendarThemeColor = KalendarThemeColor(
        backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
        dayBackgroundColor = MaterialTheme.colorScheme.inversePrimary,
        headerTextColor = MaterialTheme.colorScheme.primary
    )
    val listCalendarThemeColor = mutableListOf<KalendarThemeColor>()
    for (i in 1..12) {
        listCalendarThemeColor.add(CalendarThemeColor)
    }

    Kalendar(
        modifier = modifier,
        kalendarType = KalendarType.Firey,
        kalendarThemeColors = listCalendarThemeColor
    )

}
@Preview(showBackground = true)
@Composable
fun HomePagePreview(){
    var navController = rememberNavController()
    HomePage (navController)
}