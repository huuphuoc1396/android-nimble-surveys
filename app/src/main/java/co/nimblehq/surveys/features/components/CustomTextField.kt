package co.nimblehq.surveys.features.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nimblehq.surveys.ui.theme.SurveysTheme


@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = Color.White.copy(alpha = 0.18f),
                shape = RoundedCornerShape(10.dp)
            ),
        value = value,
        placeholder = {
            Text(
                hint, style = MaterialTheme.typography.body1.copy(
                    Color.White.copy(alpha = 0.3f)
                )
            )
        },
        onValueChange = onValueChange,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
        ),
        textStyle = MaterialTheme.typography.body1,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

@Preview
@Composable
fun CustomTextFieldPreview() {
    SurveysTheme {
        CustomTextField(
            value = "",
            onValueChange = {},
            hint = "Email"
        )
    }
}