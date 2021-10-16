package com.example.workout_companion.view.inputfields

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation

/**
 * Checks if the input can be converted to a double
 *
 * @param T         The class of the input to check. Must contain a toString() method
 * @property input  The input to check
 *
 * @return True if it can convert to an integer, false otherwise
 */
fun <T> isValidInteger(input: T) : Boolean {
    val stringData = input.toString()

    // Empty strings will count as valid input
    return stringData.isEmpty() or (stringData.toIntOrNull() != null)
}

/**
 * An OutlinedTextField that only accepts integer inputs, and uses the numeric keyboard option.
 *
 * @see OutlinedTextField for descriptions of the parameters
 */
@Composable
fun IntegerTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardCapitalization: KeyboardCapitalization = KeyboardOptions.Default.capitalization,
    keyboardAutoCorrect: Boolean = false,
    keyboardImeAction: ImeAction = KeyboardOptions.Default.imeAction,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors()
): @Composable Unit
{
    OutlinedTextField(
        value = value,
        onValueChange = { new ->
            if (isValidInteger(new)) {
                onValueChange(new)
            }
        },
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(
            capitalization = keyboardCapitalization,
            autoCorrect = keyboardAutoCorrect,
            keyboardType = KeyboardType.Number,
            imeAction = keyboardImeAction,
        ),
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors)
}