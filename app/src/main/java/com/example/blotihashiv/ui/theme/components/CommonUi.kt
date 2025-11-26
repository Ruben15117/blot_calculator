package com.example.blotihashiv.ui.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blotihashiv.R
import com.example.blotihashiv.Round
import com.example.blotihashiv.ui.theme.activeBackground
import com.example.blotihashiv.ui.theme.activeText
import com.example.blotihashiv.ui.theme.inactiveBackground
import com.example.blotihashiv.ui.theme.inactiveText


@Composable
fun TargetModifierButton(
    isToggled: Boolean,
    buttonText: String = stringResource(R.string.n_a),
    fontSize: TextUnit = 12.sp,
    buttonSize: Dp = 36.dp,
    isInactive: Boolean = false,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .size(buttonSize),
        shape = CircleShape,
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            containerColor = if (isToggled) activeBackground else inactiveBackground,
            contentColor = if (isToggled) activeText else inactiveText
        ),
        contentPadding = PaddingValues(0.dp),
        enabled = !isInactive,
    ) {
        Text(
            buttonText,
            fontSize = fontSize,
        )
    }
}

@Composable
fun TargetRowList(
    targetValue: Int,
    isInactive: Boolean,
    onNumberClick: (Int) -> Unit,
) {
    LazyRow(
        modifier = Modifier.padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(42) { number ->
            val isSelected = (number + 8) == targetValue
            TargetModifierButton(
                isInactive = isInactive,
                isToggled = isSelected,
                buttonText = (number + 8).toString(),
                fontSize = 10.sp,
                buttonSize = 32.dp
            ) {
                onNumberClick(number + 8)
            }
        }
    }
}

@Composable
fun TargetColumnList(
    outsideValue: Int,
    valueStart: Int = 2,
    valueEnd: Int = 39,
    onNumberClick: (Int) -> Unit,
) {

    LazyColumn(
        modifier = Modifier
            .padding(4.dp)
            .height(300.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(valueEnd) { number ->
            val isSelected = (number + valueStart) == outsideValue
            TargetModifierButton(
                isToggled = isSelected,
                buttonText = (number + valueStart).toString(),
                fontSize = 10.sp,
                buttonSize = 32.dp
            ) {
                onNumberClick(number + valueStart)
            }
        }
    }

}

@Composable
fun ScoreText(
    text: String,
    textColor: Color = Color.Unspecified,
    modifier: Modifier = Modifier
) {
    Text(
        text,
        textAlign = TextAlign.Center,
        color = textColor,
        modifier = modifier//if (team2Total > 301 && team2Total > team1Total) winningColor else defaultScoreColor
    )
}

@Composable
fun DeleteButton(
    isInactive: Boolean,
    rounds: SnapshotStateList<Round>
) {
    var showDialog by remember { mutableStateOf(false) }
    IconButton(
        enabled = !isInactive,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = Color(0xFFBE1818)
        ),
        onClick = { showDialog = !showDialog }
    ) {
        Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete))
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.clear_all)) },
            confirmButton = {
                Button(
                    onClick = {
                        rounds.clear()
                        showDialog = false
                    }
                ) {
                    Text(stringResource(R.string.ok))
                }
            }
        )
    }
}