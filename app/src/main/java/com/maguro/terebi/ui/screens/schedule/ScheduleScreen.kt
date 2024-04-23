package com.maguro.terebi.ui.screens.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.maguro.terebi.R
import com.maguro.terebi.data.model.Channel
import com.maguro.terebi.data.model.ScheduleItem
import com.maguro.terebi.ui.components.LoadingScreen
import com.maguro.terebi.ui.screens.LocalSystemTimeFormat
import com.maguro.terebi.ui.screens.LocalSystemTimeOffset
import com.maguro.terebi.ui.screens.SetTopAppBar
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    onScheduleItemClick: (ScheduleItem) -> Unit,
    viewModel: ScheduleViewModel = koinViewModel()
) {

    var showDatePicker by remember { mutableStateOf(false) }

    SetTopAppBar {
        TopAppBar(
            title = {
                Text(
                    text = viewModel.date
                        .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
                )
            },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null
                    )
                }
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        imageVector = Icons.Rounded.DateRange,
                        contentDescription = null
                    )
                }
            }
        )

        if (showDatePicker) {
            ScheduleDatePicker(
                showDatePicker = showDatePicker,
                onDismiss = { showDatePicker = false },
                onDateSelected = {
                    it?.let { viewModel.setDate(it) }
                }
            )
        }

    }

    when (val schedule = viewModel.schedule.collectAsStateWithLifecycle().value) {
        is ScheduleViewModel.State.Idle, ScheduleViewModel.State.Loading -> {
            LoadingScreen()
        }
        is ScheduleViewModel.State.Success -> {
            ScheduleList(
                schedule = schedule.data,
                onScheduleItemClick = onScheduleItemClick
            )
        }
        is ScheduleViewModel.State.Error -> {

        }
    }
}

@Composable
private fun ScheduleList(
    schedule: Map<Channel, List<ScheduleItem>>,
    onScheduleItemClick: (ScheduleItem) -> Unit
) {
    val channels = schedule.keys.toList()

    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            itemsIndexed(
                items = channels,
                key = { _, item -> item.id }
            ) {index, item ->
                ChannelSchedule(
                    channel = item,
                    schedule = schedule.getOrDefault(item, emptyList()),
                    onScheduleItemClick = onScheduleItemClick
                )
                if (index != channels.lastIndex) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun ChannelSchedule(
    channel: Channel,
    schedule: List<ScheduleItem>,
    onScheduleItemClick: (ScheduleItem) -> Unit
) {
    Column(
        modifier = Modifier
    ) {
        Text(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            text = channel.name,
            style = MaterialTheme.typography.headlineSmall
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            itemsIndexed(
                items = schedule,
                key = { _, item -> item.id }
            ) {index, item ->
                ChannelScheduleItem(
                    item = item,
                    onClick = onScheduleItemClick
                )
                if (index != schedule.lastIndex) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}


@Composable
private fun ChannelScheduleItem(
    item: ScheduleItem,
    onClick: (ScheduleItem) -> Unit
) {
    
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(160.dp)
            .clickable { onClick(item) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .wrapContentWidth()
                .weight(0.5f),
            model = item.show.image?.toString(),
            contentDescription = item.show.name,
            error = painterResource(id = R.drawable.img_poster_fallback),
            contentScale = ContentScale.FillHeight
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = item.show.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = item.airingDateTime
                .withOffsetSameInstant(LocalSystemTimeOffset.current)
                .format(LocalSystemTimeFormat.current),
            textAlign = TextAlign.Center
        )
    }
    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScheduleDatePicker(
    showDatePicker: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (LocalDate?) -> Unit
) {
    val datePickerState = rememberDatePickerState()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                Button(
                    onClick = {
                        onDateSelected(
                            datePickerState.selectedLocalDate
                        )
                        onDismiss()
                    },
                    content = {
                        Text(text = "OK")
                    }
                )
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private val DatePickerState.selectedLocalDate: LocalDate?
    get() = selectedDateMillis?.let {
        Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
    }
