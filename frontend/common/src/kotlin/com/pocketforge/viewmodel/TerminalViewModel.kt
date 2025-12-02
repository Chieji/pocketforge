package com.pocketforge.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.launch

class TerminalViewModel(private val containerId: String) {
    private val scope = CoroutineScope(Dispatchers.Default + Job())
    private val client = HttpClient {
        install(WebSockets)
    }

    var terminalOutput by mutableStateOf("Connecting to container $containerId...\n")
        private set
    
    var terminalInput by mutableStateOf("")
        private set

    init {
        connectToTerminal()
    }

    private fun connectToTerminal() {
        scope.launch {
            try {
                client.webSocket(
                    method = HttpMethod.Get,
                    host = "localhost", // Replace with actual backend URL
                    port = 8080,
                    path = "/containers/$containerId/terminal"
                ) {
                    terminalOutput += "Connection established. Type 'help' and press Enter.\n"
                    
                    // Incoming messages
                    val incomingJob = launch {
                        try {
                            for (frame in incoming) {
                                if (frame is Frame.Text) {
                                    terminalOutput += frame.readText()
                                }
                            }
                        } catch (e: ClosedReceiveChannelException) {
                            terminalOutput += "\n[Connection closed by server]\n"
                        } catch (e: Exception) {
                            terminalOutput += "\n[Error: ${e.message}]\n"
                        }
                    }
                    
                    // Keep the connection alive until the scope is cancelled
                    incomingJob.join()
                }
            } catch (e: Exception) {
                terminalOutput += "\n[Failed to connect: ${e.message}]\n"
            }
        }
    }

    fun onInputChanged(newInput: String) {
        terminalInput = newInput
    }

    fun onEnterPressed() {
        val command = terminalInput.trim()
        if (command.isNotEmpty()) {
            // Send command to WebSocket
            scope.launch {
                try {
                    client.webSocketSession(
                        method = HttpMethod.Get,
                        host = "localhost",
                        port = 8080,
                        path = "/containers/$containerId/terminal"
                    ).send(Frame.Text(command))
                } catch (e: Exception) {
                    terminalOutput += "\n[Error sending command: ${e.message}]\n"
                }
            }
            
            // Clear input and append to output immediately for local echo effect
            terminalOutput += "$command\n"
            terminalInput = ""
        }
    }
    
    // Placeholder for AI Agent interaction
    fun runAiAgent(prompt: String) {
        // Logic to call the MTC API endpoint
        terminalOutput += "\n[AI Agent called with prompt: $prompt]\n"
    }
}
