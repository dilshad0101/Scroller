package com.app.scoller.ui.screen

import android.os.Looper
import androidx.annotation.MainThread
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.SimpleBasePlayer
import androidx.media3.common.listen
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.compose.PlayerSurface
import androidx.media3.ui.compose.SURFACE_TYPE_SURFACE_VIEW
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val list = listOf("1","2","3")
@Composable
fun MainScreen(
    innerPadding: PaddingValues
){
    val pagerState = rememberPagerState(pageCount ={4})
    val state = rememberLazyListState()
    val fling = rememberSnapFlingBehavior(state)
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {

    }
    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
            .padding(innerPadding),
        userScrollEnabled = true
    ) {
        AppPlayer(
            contentCount = it,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun MainScreenPreview(){
    MainScreen(PaddingValues(0.dp))
}

@Composable
fun AppPlayer(
    contentCount: Int,
    modifier: Modifier = Modifier){
    var player by remember { mutableStateOf<Player?>(null) }
    val context = LocalContext.current
    LifecycleStartEffect(Unit) {
        player = ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videos[contentCount]))
            prepare()
            play()
        }
        onStopOrDispose {
            player?.apply {
                release()
            }
            player = null

        }
    }
    if(player != null){
        PlayerSurface(
            player = player!!,
        )
    }else{
        Surface(modifier =
        modifier.fillMaxSize()
            .wrapContentSize(Alignment.Center)) {
            CircularProgressIndicator()
        }
    }

}

val videos =
    listOf(
        "https://html5demos.com/assets/dizzy.mp4",
        "https://storage.googleapis.com/exoplayer-test-media-0/shortform_2.mp4",
        "https://storage.googleapis.com/exoplayer-test-media-1/gen-3/screens/dash-vod-single-segment/video-vp9-360.webm",
        "https://storage.googleapis.com/exoplayer-test-media-0/shortform_3.mp4",
    )