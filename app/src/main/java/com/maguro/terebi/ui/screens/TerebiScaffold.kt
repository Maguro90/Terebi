package com.maguro.terebi.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.maguro.terebi.R
import com.maguro.terebi.ui.navigation.Navigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerebiScaffold() {
    Scaffold(
        topBar = {
             TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
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
