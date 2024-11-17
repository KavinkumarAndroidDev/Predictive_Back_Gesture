package com.kkdev.predictivebackgesture.screens

import androidx.activity.BackEventCompat
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kkdev.predictivebackgesture.R
import com.kkdev.predictivebackgesture.composables.BorderlessTextField
import com.kkdev.predictivebackgesture.composables.ToggleButton
import com.kkdev.predictivebackgesture.ui.theme.AppTheme
import com.kkdev.predictivebackgesture.ui.theme.onPrimaryColor
import com.kkdev.predictivebackgesture.ui.theme.poppinsFontFamily
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(){

    //Current Task Data
    val focusRequester = remember { FocusRequester() }
    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var isPinned: Boolean by remember { mutableStateOf(false) }

    //Bottom sheets
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val sheetItems = listOf(
        Icons.Default.DateRange to "Set reminder",
        Icons.Default.Favorite to "Pin",
        Icons.Default.Share to "Share",
        Icons.Default.Delete to "Delete"
    )

    //Predictive back gesture
    var backProgress: Float? by remember {
        mutableStateOf(null)
    }

    val onBackCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackProgressed(backEvent: BackEventCompat) {
                backProgress = backEvent.progress
            }

            override fun handleOnBackPressed() {
                backProgress = null
            }

            override fun handleOnBackCancelled() {
                backProgress = null
            }
        }
    }

    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher
    DisposableEffect(backPressedDispatcher) {
        onDispose {
            onBackCallback.remove()
        }
    }


    Scaffold(
        containerColor = AppTheme.colorScheme.onPrimary,
        topBar = {

            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = AppTheme.colorScheme.onPrimary,
                    titleContentColor = AppTheme.colorScheme.onBackground,
                    actionIconContentColor = AppTheme.colorScheme.onBackground,
                    scrolledContainerColor = AppTheme.colorScheme.onBackground,
                    navigationIconContentColor = AppTheme.colorScheme.onBackground
                ),
                title = {
                    Text(
                        text = "Add Task",
                        fontFamily = poppinsFontFamily,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )},
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_left),
                            contentDescription = "navigate back to home",
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                isSheetOpen = true
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More settings"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar (
                actions = {
                    ToggleButtonGroup()
                },
                containerColor = AppTheme.colorScheme.onPrimary,
            )
        }
    ) { it ->


        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .padding(it)
        ) {
            BorderlessTextField(
                value = taskTitle,
                txtStyle = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.colorScheme.onBackground,
                    fontFamily = poppinsFontFamily
                ),
                onValueChange = {
                    taskTitle = it
                },
                placeholder =  "Title"
            )
            BasicTextField(
                value = taskDescription,
                onValueChange = { taskDescription = it },
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontFamily = poppinsFontFamily,
                    color = AppTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium
                ),
                singleLine = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .heightIn(min = 56.dp)
                    .focusRequester(focusRequester)
                    .verticalScroll(rememberScrollState())

            )
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }

        }
    }
    if (isSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                isSheetOpen = false
            },
            containerColor = onPrimaryColor
        ) {
            Column {
                sheetItems.forEach { item ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(32.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp)
                            .clickable(onClick = { /* handle action */ })
                    ) {
                        Icon(
                            imageVector = item.first,
                            contentDescription = item.second,
                            tint = AppTheme.colorScheme.onBackground
                        )
                        Text(
                            text = item.second,
                            color = AppTheme.colorScheme.onBackground,
                            fontFamily = poppinsFontFamily,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

            }
        }
    }

}


data class ToggleButtonList(
    val btnText: String,
    val btnIcon: ImageVector? = null
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ToggleButtonGroup() {
    var selectedButton by remember { mutableIntStateOf(0) }

    val categories = listOf(
        ToggleButtonList("Personal", Icons.Filled.Person),
        ToggleButtonList("Work", Icons.Filled.Email),
        ToggleButtonList("Finance", Icons.Filled.ShoppingCart),
        ToggleButtonList("Others", Icons.Filled.MoreVert)
    )
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
    ) {
        categories.forEachIndexed { index, category ->
            ToggleButton(
                text = category.btnText,
                btnImg = category.btnIcon,
                isSelected = selectedButton == index,
                onClick = { selectedButton = index },
                modifier = Modifier.padding(4.dp)
            )
        }

    }
}