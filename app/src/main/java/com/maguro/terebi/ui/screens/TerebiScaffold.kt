package com.maguro.terebi.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maguro.terebi.ui.navigation.Navigation
import kotlinx.coroutines.flow.MutableStateFlow

private val topAppBar = MutableStateFlow<(@Composable () -> Unit)?>(null)

@Composable
fun SetTopAppBar(key: Any? = null, block: (@Composable () -> Unit)?) {
    LaunchedEffect(key) {
        topAppBar.value = block
    }
}

@Composable
fun TerebiScaffold() {
    Scaffold(
        topBar = {
            topAppBar.collectAsStateWithLifecycle().value?.invoke()
        },
        content = {
            Box(modifier = Modifier
                .padding(it)
                .fillMaxSize()
            ) {
                Navigation()
            }
        }
    )
}
