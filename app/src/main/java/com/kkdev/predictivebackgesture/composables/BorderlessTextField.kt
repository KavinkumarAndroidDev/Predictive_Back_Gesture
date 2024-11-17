package com.kkdev.predictivebackgesture.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.kkdev.predictivebackgesture.ui.theme.poppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorderlessTextField(
    modifier: Modifier = Modifier,
    value: String,
    txtStyle: TextStyle,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        textStyle = txtStyle, // Change text color as per your requirement
        placeholder = {
            Text(
                text = placeholder,
                style = txtStyle,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold
            ) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = { onImeAction() }
        ),
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black, // Set focused text color
            unfocusedTextColor = Color.Black, // Set unfocused text color
            disabledTextColor = Color.Gray, // Set disabled text color
            cursorColor = Color.Black, // Cursor color
            focusedContainerColor = Color.Transparent, // Transparent background when focused
            unfocusedContainerColor = Color.Transparent, // Transparent background when unfocused
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}