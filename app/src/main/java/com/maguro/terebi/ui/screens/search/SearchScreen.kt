package com.maguro.terebi.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.maguro.terebi.R
import com.maguro.terebi.data.model.Show
import com.maguro.terebi.ui.components.LoadingScreen
import com.maguro.terebi.ui.screens.SetTopAppBar
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel()
) {

    SetTopAppBar {
        var active by remember { mutableStateOf(true) }
        
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            SearchBar(
                query = viewModel.query.collectAsStateWithLifecycle().value,
                onQueryChange = { viewModel.updateQuery(it) },
                onSearch = {
                    viewModel.search()
                   active = false
                },
                active = active,
                onActiveChange = { active = it },
                leadingIcon = {
                    if (active) {
                        IconButton(
                            onClick = {
                                active = false
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = null
                            )
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = null
                        )
                    }
                }
            ) {
                LazyColumn {
                    itemsIndexed(
                        items =  viewModel.searchHistory,
                        key = {index, _ -> index}
                    ) {index, item ->
                        SearchHistoryEntry(
                            index = index,
                            entry = item,
                            onClick = { entryIndex ->
                                viewModel.search(entryIndex)
                                active = false
                            }
                        )
                    }
                }
            }
        }
    }
    
    when (val state = viewModel.state.collectAsStateWithLifecycle().value) {
        is SearchViewModel.State.Idle -> {
            // Do nothing
        }
        is SearchViewModel.State.Loading -> {
            LoadingScreen()
        }
        is SearchViewModel.State.Success -> {
            ShowGrid(shows = state.data)
        }
        is SearchViewModel.State.Error -> {
            
        }
    }
    
}


@Composable
private fun ShowGrid(
    shows: List<Show>
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        content = {
            items(
                items = shows,
                key = { show -> show.id }
            ) {
                ShowItem(it)
            }
        }
    )
}


@Composable
private fun ShowItem(
    show: Show
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            modifier = Modifier
                .height(200.dp)
                .padding(8.dp),
            model = show.image.toString(),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            fallback = painterResource(id = R.drawable.img_poster_fallback)
        )
        Text(text = show.name)
    }
}

@Composable
private fun SearchHistoryEntry(
    index: Int,
    entry: String,
    onClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.dp)
            .clickable {
               onClick(index)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.Refresh,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = entry, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}