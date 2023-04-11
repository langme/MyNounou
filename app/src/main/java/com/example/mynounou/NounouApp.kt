package com.example.mynounou

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.mynounou.compose.ChildPage
import com.example.mynounou.compose.DocumentPage
import com.example.mynounou.compose.HomePage
import com.example.mynounou.compose.NursePage
import com.example.mynounou.navigation.NavigationItem
import com.example.mynounou.ui.theme.MyNounouTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NounouApp : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNounouTheme {
                // A surface container using the 'background' color from the theme
                NavigationPage()
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationPage() {
    var navController = rememberNavController()
    Scaffold(
        topBar = {

        },
        bottomBar = {
            BottomAppBar(navController)
        }){
        Box(modifier = Modifier.padding(2.dp)) {
            NavHost(navController = navController, startDestination = "Home"){
                navigation(startDestination = "HomePage", route = "Home"){
                    composable("HomePage"){
                        HomePage(navController = navController)
                    }
                }
                navigation(startDestination = "ChildPage", route = "Child"){
                    composable("ChildPage"){
                        ChildPage(navController = navController)
                    }
                }
                navigation(startDestination = "NursePage", route = "Nurse"){
                    composable("NursePage"){
                        NursePage(navController = navController)
                    }
                }
                navigation(startDestination = "DocumentPage", route = "Document"){
                    composable("DocumentPage"){
                        DocumentPage(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(
) {

}
@Composable
fun BottomAppBar(
    navController: NavController
) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Child,
        NavigationItem.Nurse,
        NavigationItem.Document
    )

    var selectedItem = remember { mutableStateOf(0)}

    NavigationBar() {
        items.forEachIndexed {index,  item ->
            NavigationBarItem(
                selected = selectedItem.value == index,
                onClick = {
                    selectedItem.value = index
                    navController.navigate(item.route)
                },
                icon = {
                    when(item){
                        NavigationItem.Home -> Icon(painter = painterResource(NavigationItem.Home.icon), contentDescription = null)
                        NavigationItem.Child -> Icon(painter = painterResource(NavigationItem.Child.icon), contentDescription = null)
                        NavigationItem.Nurse -> Icon(painter = painterResource(NavigationItem.Nurse.icon), contentDescription = null)
                        NavigationItem.Document -> Icon(painter = painterResource(NavigationItem.Document.icon), contentDescription = null)
                    }
                }, label = { Text(text = item.title)})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyNounouTheme {

    }
}

@Preview(showBackground = true)
@Composable
fun BottomAppBarPreview() {
    BottomAppBar({ null })
}