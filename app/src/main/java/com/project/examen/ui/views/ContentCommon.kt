package com.project.examen.ui.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseContentView(
    title: String,
    requireFloatingButton: Boolean = false,
    onBackPressed: () -> Unit = {},
    onClickFloatingButton: () -> Unit = {},
    content: @Composable (Modifier) -> Unit = {},
) {
    Scaffold(
        topBar = {
            Surface(shadowElevation = 8.dp) {
                TopAppBar(
                    title = { Text(text = title) },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                        }
                    }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            if(requireFloatingButton) {
                ExtendedFloatingActionButton(
                    onClick = { onClickFloatingButton() }
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
                }
            }
        },
        content = {
            content(Modifier.padding(top = it.calculateTopPadding().plus(16.dp)))
        }
    )

}