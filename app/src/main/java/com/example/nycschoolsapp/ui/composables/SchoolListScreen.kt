package com.example.nycschoolsapp.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nycschoolsapp.data.repository.localdatabase.SchoolModel
import com.example.nycschoolsapp.ui.ScreenState
import com.example.nycschoolsapp.ui.nav.DestinationConstants
import com.example.nycschoolsapp.ui.viewmodels.SchoolsListViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: SchoolsListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect("schoolsInfo") {
        viewModel.fetchSchoolsInformation()
    }

    when (uiState.state) {

        ScreenState.LOADING -> {
            LoadingScreen(loadingMessage = "Fetching schools...")
        }

        ScreenState.SUCCESS -> {
            SchoolList(navController, schools = uiState.list)
        }

        ScreenState.ERROR -> {
            ErrorScreen(
                errorMessage = uiState.errorMessage,
                onRetry = { viewModel.retry() }
            )
        }

        ScreenState.IDLE -> {
            LoadingScreen(loadingMessage = "Welcome")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text("NycSchools")
        }
    )
}

@Composable
fun SchoolList(
    navController: NavController,
    schools: List<SchoolModel>
) {

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {

        // todo: fix this
        TopBar()

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            itemsIndexed(schools) { index, school ->
                SchoolListItem(navController, school.name, school.dbn)
                if (index < schools.lastIndex) {
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}

@Composable
fun SchoolListItem(
    navController: NavController,
    schoolName: String,
    id: String
) {
    Text(
        text = schoolName,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { navController.navigate(DestinationConstants.DETAILS_SCREEN + "/" + id) },
        textAlign = TextAlign.Start
    )
}
