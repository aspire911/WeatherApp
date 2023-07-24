package com.mdmx.weatherapp.presentation


sealed class Screens(val route: String) {
    object Main : Screens("Main")
    object Detail : Screens("Detail")

}



