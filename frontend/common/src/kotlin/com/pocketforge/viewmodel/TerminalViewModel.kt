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
    
    // Store the active WebSocket session
    private var session: DefaultClientWebSocketSession? = null

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
                // Establish a single, persistent WebSocket session
                session = client.webSocketSession(
                    method = HttpMethod.Get,
                    host = "localhost", // TODO: Replace with actual backend URL from config
                    port = 8080,
                    path = "/containers/$containerId/terminal"
                )
                
                terminalOutput += "Connection established. Type 'help' and press Enter.\n"
                
                // Start listening for incoming messages on the session
                for (frame in session!!.incoming) {
                    if (frame is Frame.Text) {
                        terminalOutput += frame.readText()
                    }
                }
                
            } catch (e: ClosedReceiveChannelException) {
                terminalOutput += "\n[Connection closed by server]\n"
            } catch (e: Exception) {
                terminalOutput += "\n[Failed to connect: ${e.message}]\n"
            } finally {
                session?.close()
                session = null
            }
        }
    }

    fun onInputChanged(newInput: String) {
        terminalInput = newInput
    }

    fun onEnterPressed() {
        val command = terminalInput.trim()
        if (command.isNotEmpty()) {
            // Send command to the persistent WebSocket session
            scope.launch {
                try {
                    // Send the command followed by a newline to simulate pressing Enter
                    session?.send(Frame.Text("$command\n"))
                } catch (e: Exception) {
                    terminalOutput += "\n[Error sending command: ${e.message}]\n"
                }
            }
            
            // Clear input. The output will be echoed back from the server/PTY.
            terminalInput = ""
        }
    }
    
    // Placeholder for AI Agent interaction
    fun runAiAgent(prompt: String) {
        // Logic to call the MTC API endpoint
        terminalOutput += "\n[AI Agent called with prompt: $prompt]\n"
    }
}
