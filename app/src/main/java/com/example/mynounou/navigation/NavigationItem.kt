package com.example.mynounou.navigation

import  com.example.mynounou.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String){
    object Home: NavigationItem("Home", R.drawable.ic_home, "Home")
    object Child: NavigationItem("Child", R.drawable.ic_child, "Child")
    object Nurse: NavigationItem("Nurse", R.drawable.ic_nurse, "Nurse")
    object Document: NavigationItem("Document", R.drawable.ic_document, "Document")
}
