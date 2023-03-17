package com.example.nycschoolsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nycschoolsapp.ui.composables.TopBar
import com.example.nycschoolsapp.ui.nav.NavGraph
import com.example.nycschoolsapp.ui.theme.NycSchoolsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavController

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NycSchoolsAppTheme {
                navController = rememberNavController()
                Scaffold(
                    topBar = { TopBar() }
                ) { paddingValues ->
                    NavGraph(
                        navController = navController as NavHostController,
                        paddingValues
                    )
                }
            }
        }
    }
}