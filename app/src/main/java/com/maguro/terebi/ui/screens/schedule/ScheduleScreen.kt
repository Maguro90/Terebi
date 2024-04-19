package com.maguro.terebi.ui.screens.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.maguro.terebi.R
import com.maguro.terebi.data.model.Channel
import com.maguro.terebi.data.model.ScheduleItem
import com.maguro.terebi.ui.screens.LocalSystemTimeFormat
import com.maguro.terebi.ui.screens.LocalSystemTimeOffset
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel = koinViewModel()
) {
    
    when (val schedule = viewModel.schedule.collectAsState().value) {
        is ScheduleViewModel.State.Idle, ScheduleViewModel.State.Loading -> {
            LoadingList()
        }
        is ScheduleViewModel.State.Success -> {
            ScheduleList(
                schedule = schedule.data
            )
        }
        is ScheduleViewModel.State.Error -> {

        }
    }


}

@Composable
private fun LoadingList() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Loading...")
        }
    }
}

@Composable
private fun ScheduleList(
    schedule: Map<Channel, List<ScheduleItem>>,
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
                    schedule = schedule.getOrDefault(item, emptyList())
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
    schedule: List<ScheduleItem> 
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
                key = { _, item -> item.lazyItemKey }
            ) {index, item ->
                ChannelScheduleItem(
                    item = item
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
    item: ScheduleItem
) {
    
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(160.dp),
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

private val ScheduleItem.lazyItemKey: String
    get() = "${channel.id}_${show.id}_${episode?.id}_${airingDateTime}"