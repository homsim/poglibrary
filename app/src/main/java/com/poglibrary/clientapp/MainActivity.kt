package com.poglibrary.clientapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.poglibrary.clientapp.client.api.*
import com.poglibrary.clientapp.client.types.*
import com.poglibrary.clientapp.ui.theme.PogLibraryTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PogLibraryTheme {

                val scope = rememberCoroutineScope()
                var text by remember { mutableStateOf("Loading") }
                LaunchedEffect(true) {
                    scope.launch {
                        text = try {
                            "..."
                        } catch (e: Exception) {
                            e.localizedMessage ?: "error"
                        }
                    }
                }
                View(text)
            }

        }

    }
}

@Composable
fun View(text: String) {
    Text(text = text)
}
