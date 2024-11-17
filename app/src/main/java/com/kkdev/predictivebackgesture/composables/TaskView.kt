package com.kkdev.predictivebackgesture.composables

import androidx.activity.BackEventCompat
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kkdev.predictivebackgesture.ui.theme.AppTheme
import com.kkdev.predictivebackgesture.ui.theme.poppinsFontFamily
import com.kkdev.predictivebackgesture.ui.theme.tGreen
import com.kkdev.predictivebackgesture.ui.theme.tOrange
import com.kkdev.predictivebackgesture.ui.theme.tPurple
import com.kkdev.predictivebackgesture.ui.theme.tYellow

@Composable
fun TaskView(
    tTitle: String,
    tDescription: String? = null,
    tCategory: String,
    tTime: String?
) {
    val cColor: Color = when (tCategory) {
        "Work" -> tOrange
        "Personal" -> tGreen
        "Finance" -> tPurple
        else -> tYellow
    }

    // To add text expand effect
    var isTextExpanded by remember { mutableStateOf(false) } //Maintain the state of text expansion
    var backProgress: Float? by remember { mutableStateOf(null) }
    var textHeightExpanded by remember { mutableStateOf(0.dp) }
    var textHeightCollapsed by remember { mutableStateOf(0.dp) }

    val density = LocalDensity.current

    val onBackCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackProgressed(backEvent: BackEventCompat) {
                backProgress = backEvent.progress
            }

            override fun handleOnBackPressed() {
                isTextExpanded = false
                backProgress = null
            }

            override fun handleOnBackCancelled() {
                backProgress = null
            }
        }
    }

    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher
    DisposableEffect(backPressedDispatcher, isTextExpanded) {
        if (isTextExpanded) {
            backPressedDispatcher.addCallback(onBackCallback)
        }

        onDispose {
            onBackCallback.remove()
        }
    }

    OutlinedCard(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            contentColor = AppTheme.colorScheme.onBackground,
            containerColor = cColor
        ),
        border = BorderStroke(1.dp, AppTheme.colorScheme.onBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = tTitle,
                maxLines = 1,
                modifier = Modifier.padding(10.dp),
                fontFamily = poppinsFontFamily,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Bold
            )
            tDescription?.let {
                Text(
                    text = it,
                    maxLines = if (isTextExpanded) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .onGloballyPositioned {
                            when {
                                isTextExpanded && backProgress == null -> {
                                    textHeightExpanded = with(density) { it.size.height.toDp() }
                                }
                                !isTextExpanded -> {
                                    textHeightCollapsed = with(density) { it.size.height.toDp() }
                                }
                            }
                        }
                        .then(
                            if (backProgress != null) {
                                Modifier
                                    .heightIn(min = textHeightCollapsed)
                                    .height(textHeightExpanded * (1f - (backProgress ?: 0f)))
                            } else Modifier
                        )
                        .fillMaxWidth()
                        .clickable { isTextExpanded = !isTextExpanded }
                        .animateContentSize()
                        .padding(end = 10.dp, start = 10.dp, bottom = 5.dp),
                    fontFamily = poppinsFontFamily,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tCategory,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily,
                    color = AppTheme.colorScheme.background,
                    modifier = Modifier
                        .background(
                            color = AppTheme.colorScheme.onBackground,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                )
                tTime?.let {
                    Text(
                        text = it,
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        fontWeight = FontWeight.Medium,
                        fontFamily = poppinsFontFamily
                    )
                }
            }
        }
    }
}
