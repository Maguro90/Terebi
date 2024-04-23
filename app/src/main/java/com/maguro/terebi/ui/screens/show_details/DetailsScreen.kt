package com.maguro.terebi.ui.screens.show_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.maguro.terebi.R
import com.maguro.terebi.data.model.ScheduleItem
import com.maguro.terebi.ui.components.LoadingScreen
import com.maguro.terebi.ui.screens.LocalSystemTimeOffset
import com.maguro.terebi.ui.screens.SetTopAppBar
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    onBackClick: () -> Unit,
    viewModel: DetailsViewModel = koinViewModel()
) {

    SetTopAppBar {
        TopAppBar(
            title = { Text(text = stringResource(R.string.screen_title_details)) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
    }

    when (val state = viewModel.details.collectAsStateWithLifecycle().value) {
        is DetailsViewModel.State.Idle, DetailsViewModel.State.Loading -> {
            LoadingScreen()
        }
        is DetailsViewModel.State.Success -> {
            if (state.data != null) {
                DetailsContent(scheduleItem = state.data)
            }
        }
        is DetailsViewModel.State.Error -> {

        }
    }

}

@Composable
private fun DetailsContent(
    scheduleItem: ScheduleItem
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        ) {

            AsyncImage(
                modifier = Modifier
                    .width(160.dp)
                    .fillMaxHeight(),
                model = scheduleItem.show.image?.toString(),
                contentDescription = scheduleItem.show.name,
                contentScale = ContentScale.FillWidth,
                fallback = painterResource(id = R.drawable.img_poster_fallback)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = scheduleItem.show.name,
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = scheduleItem.episode.name ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = scheduleItem.airingDateTime
                        .withOffsetSameInstant(LocalSystemTimeOffset.current)
                        .format(
                            DateTimeFormatter
                                .ofLocalizedDateTime(FormatStyle.MEDIUM)
                                .withLocale(Locale.getDefault())
                        ),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                val genres = scheduleItem.show
                    .genres?.joinToString(", ")
                    ?: stringResource(id = R.string.no_genres_available)

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = genres,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                
                val rating = scheduleItem.show
                    .rating
                    ?.let {
                        stringResource(id = R.string.rating, it)
                    } ?: stringResource(id = R.string.no_rating_available)

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = rating,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                )
                
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.episode_summary),
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            text = scheduleItem.episode.summary
                ?: stringResource(id = R.string.no_summary_available)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.show_summary),
            style = MaterialTheme.typography.titleLarge
        )
        
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            text = scheduleItem.show.summary
                ?: stringResource(id = R.string.no_summary_available)
        )
        
    }

}