package com.kkdev.predictivebackgesture.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kkdev.predictivebackgesture.composables.HomeNotesView
import com.kkdev.predictivebackgesture.composables.ToggleButton
import com.kkdev.predictivebackgesture.composables.ToggleButtonList
import com.kkdev.predictivebackgesture.ui.theme.AppTheme
import com.kkdev.predictivebackgesture.ui.theme.poppinsFontFamily
import kotlinx.coroutines.launch


data class TaskItem(
    val tTitle: String,
    val tCat: String,
    val time: String,
    val tdes: String? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (
    onButtonClick: () -> Unit
){
    //Category list
    val categories = listOf(
        ToggleButtonList("All list"),
        ToggleButtonList("Pinned")
    )

    //Toggle button selection
    var selectedButton by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()


    //sample data
    val allTasks = listOf(
        TaskItem("Remaining tasks","Work","12-11-2024","Need to complete the task tomorrow and summit the Report to the manager by 25th Sept. Otherwise the project will be cancelled. The team must report by today. The work need to be equally separated for every team members and the code must be published to the production."),
        TaskItem("Task note done yet", "Personal", "10-11-2024","This task may be completed by tomorrow and not by today okay?"),
        TaskItem("To do list", "Finance", "15-11-2024")
    )
    val pinnedTasks = listOf(
        TaskItem("Pinned Task", "Work", "14-04-2024")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Taskmaster",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onButtonClick ,
                containerColor = AppTheme.colorScheme.primary,
                contentColor = AppTheme.colorScheme.onPrimary,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add new task")
            }
        }
    ) {it ->
        Column (modifier = Modifier
            .padding(it)
            .fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(AppTheme.colorScheme.surface)
            ) {
                categories.forEachIndexed { index, category ->
                    ToggleButton(
                        text = category.btnText,
                        btnImg = category.btnIcon,
                        isSelected = selectedButton == index,
                        onClick = {
                            selectedButton = index
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(selectedButton)
                            }
                        },
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(start = 5.dp, end = 5.dp, top = 2.dp, bottom = 2.dp)
                    )
                }
            }
            HomeNotesView(
                allTasks = allTasks,
                pinnedTasks = pinnedTasks,
                pagerState = pagerState
            )
        }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                selectedButton = page
            }
        }
    }
}