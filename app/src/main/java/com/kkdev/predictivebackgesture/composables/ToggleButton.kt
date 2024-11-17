package com.kkdev.predictivebackgesture.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kkdev.predictivebackgesture.ui.theme.AppTheme
import com.kkdev.predictivebackgesture.ui.theme.poppinsFontFamily

@Composable
fun ToggleButton(
    text: String, isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier,
    btnImg: ImageVector?
) {
    val backgroundColor = if (isSelected) AppTheme.colorScheme.primary else AppTheme.colorScheme.surface
    val contentColor = if (isSelected) AppTheme.colorScheme.onPrimary else AppTheme.colorScheme.primary

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
    ) {
        val myIcon: ImageVector? = btnImg
        if (myIcon != null) {
            Icon(imageVector = myIcon, contentDescription = text)
            if(isSelected){
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = text,
                    fontFamily = poppinsFontFamily,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
            }
        }else{
            Text(
                text = text,
                fontFamily = poppinsFontFamily,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium
            )
        }
    }
}