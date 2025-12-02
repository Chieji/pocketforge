package com.pocketforge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pocketforge.viewmodel.TerminalViewModel
import kotlinx.coroutines.launch

@Composable
fun TerminalView(
    modifier: Modifier = Modifier,
    viewModel: TerminalViewModel
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    
    // Split output into lines for display
    val outputLines = viewModel.terminalOutput.split('\n')

    Column(modifier = modifier.background(Color(0xFF1E1E1E))) {
        // 1. Terminal Output Area
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            state = scrollState
        ) {
            items(outputLines) { line ->
                Text(
                    text = line,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            }
        }

        // Auto-scroll to bottom on new output
        LaunchedEffect(outputLines.size) {
            if (outputLines.isNotEmpty()) {
                coroutineScope.launch {
                    // Scroll to the last item (the input line)
                    scrollState.animateScrollToItem(outputLines.size - 1)
                }
            }
        }

        // 2. Terminal Input Area
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display the current prompt (e.g., user@container$)
            // In a real PTY, the prompt is part of the terminalOutput.
            // For this simulation, we use a fixed prompt for the input line.
            Text(
                text = "$ ",
                color = Color.Green,
                fontSize = 14.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
            )
            
            BasicTextField(
                value = viewModel.terminalInput,
                onValueChange = viewModel::onInputChanged,
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                ),
                singleLine = true,
                cursorBrush = androidx.compose.ui.graphics.SolidColor(Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .onKeyEvent {
                        if (it.key == Key.Enter && it.type == KeyEventType.KeyUp) {
                            viewModel.onEnterPressed()
                            true
                        } else {
                            false
                        }
                    }
            )
        }
    }
}
