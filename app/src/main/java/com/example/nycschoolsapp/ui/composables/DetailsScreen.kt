package com.example.nycschoolsapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nycschoolsapp.ui.ScreenState
import com.example.nycschoolsapp.ui.viewmodels.SchoolsListViewModel

@Composable
fun DetailsScreen(id: String) {

    val viewModel: SchoolsListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect("schoolsInfo") {
        viewModel.getSchoolDetails(id)
    }

    when (uiState.state) {

        ScreenState.LOADING, ScreenState.IDLE -> {
            LoadingScreen(loadingMessage = "")
        }

        ScreenState.SUCCESS -> {
            SchoolDetails(
                name = uiState.schoolName,
                mathScore = uiState.satMathScore,
                writingScore = uiState.satWritingScore,
                readingScore = uiState.satReadingScore,
                description = uiState.schoolDescription
            )
        }

        ScreenState.ERROR -> {
            ErrorScreen(
                errorMessage = uiState.errorMessage,
                onRetry = { viewModel.retry() }
            )
        }
    }
}

@Composable
fun SchoolDetails(
    name: String,
    mathScore: String,
    writingScore: String,
    readingScore: String,
    description: String
) {

    val gradientColors = listOf(Color.Gray, Color.White)
    val gradient = Brush.verticalGradient(gradientColors)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradient)
    ) {
        Spacer(
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .padding(top = 64.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth()
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "SAT Reading Score: $readingScore")
                    Text(text = "SAT Writing Score: $writingScore")
                    Text(text = "SAT Math Score: $mathScore")
                }
            }

            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth()
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(modifier = Modifier.padding(16.dp), text = "Description: $description")
            }
        }
    }
}
