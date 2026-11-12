package com.komikhq.app.feature.reader

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.komikhq.app.R
import com.komikhq.app.core.designsystem.HQIcon
import com.komikhq.app.core.designsystem.HQIcons
import com.komikhq.app.core.designsystem.HQOrange
import com.komikhq.app.core.designsystem.HQTheme
import com.komikhq.app.core.designsystem.InstrumentSansFamily
import com.komikhq.app.core.designsystem.InterFamily
import com.komikhq.app.core.designsystem.Stone950
import com.komikhq.app.core.ui.HQButton
import com.komikhq.app.core.ui.HQButtonSize
import com.komikhq.app.core.ui.HQButtonVariant
import com.komikhq.app.core.ui.HQCircularProgressIndicator

/**
 * KHQ 56 · Reader Screen (Figma 2:43)
 * Gapless vertical scroll + paged mode
 * KHQ 57 · Reader Controls Overlay
 */
@Composable
fun ReaderScreen(
    viewModel: ReaderViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val controlsVisible by viewModel.controlsVisible.collectAsState()
    val readingMode by viewModel.readingMode.collectAsState()
    val currentPage by viewModel.currentPage.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Stone950)
    ) {
        when (val state = uiState) {
            is ReaderUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    HQCircularProgressIndicator(color = HQOrange, size = 40.dp, strokeWidth = 3.dp)
                }
            }
            is ReaderUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    BasicText(
                        text = state.message,
                        style = TextStyle(
                            fontFamily = InterFamily,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    HQButton(
                        text = stringResource(R.string.action_retry),
                        onClick = { viewModel.loadChapter(viewModel.uiState.value.let { "" }) },
                        fullWidth = false
                    )
                }
            }
            is ReaderUiState.Success -> {
                // Page content
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { viewModel.toggleControls() }
                        )
                ) {
                    when (readingMode) {
                        ReadingMode.VERTICAL -> {
                            VerticalReader(
                                pages = state.data.pages,
                                onPageChanged = viewModel::onPageChanged
                            )
                        }
                        ReadingMode.PAGED -> {
                            PagedReader(
                                pages = state.data.pages,
                                onPageChanged = viewModel::onPageChanged
                            )
                        }
                    }
                }

                // Controls Overlay (KHQ 57)
                if (controlsVisible) {
                    ReaderControlsOverlay(
                        data = state.data,
                        currentPage = currentPage,
                        readingMode = readingMode,
                        onBack = onNavigateBack,
                        onModeToggle = {
                            viewModel.setReadingMode(
                                if (readingMode == ReadingMode.VERTICAL) ReadingMode.PAGED else ReadingMode.VERTICAL
                            )
                        },
                        onPrevChapter = viewModel::navigateToPrevChapter,
                        onNextChapter = viewModel::navigateToNextChapter
                    )
                }
            }
        }
    }
}

@Composable
private fun VerticalReader(
    pages: List<String>,
    onPageChanged: (Int) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { onPageChanged(it) }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(pages) { _, pageUrl ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            // End of chapter
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BasicText(
                    text = stringResource(R.string.reader_end_of_chapter),
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

@Composable
private fun PagedReader(
    pages: List<String>,
    onPageChanged: (Int) -> Unit
) {
    if (pages.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { pages.size })

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect { onPageChanged(it) }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pages[page])
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun ReaderControlsOverlay(
    data: ReaderData,
    currentPage: Int,
    readingMode: ReadingMode,
    onBack: () -> Unit,
    onModeToggle: () -> Unit,
    onPrevChapter: () -> Unit,
    onNextChapter: () -> Unit
) {
    // Top Bar
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Stone950.copy(alpha = 0.9f))
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.1f))
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center
            ) {
                HQIcon(
                    imageVector = HQIcons.ArrowBack,
                    contentDescription = stringResource(R.string.action_back),
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                BasicText(
                    text = data.comicTitle,
                    style = TextStyle(
                        fontFamily = InstrumentSansFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = Color.White
                    ),
                    maxLines = 1
                )
                BasicText(
                    text = stringResource(R.string.reader_chapter_format, data.chapterNumber),
                    style = TextStyle(
                        fontFamily = InterFamily,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Bottom Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Stone950.copy(alpha = 0.9f))
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Page progress
            BasicText(
                text = stringResource(R.string.reader_page_progress, currentPage + 1, data.pages.size),
                style = TextStyle(
                    fontFamily = InterFamily,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Chapter navigation + mode toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HQButton(
                    text = stringResource(R.string.reader_prev_chapter),
                    onClick = onPrevChapter,
                    enabled = data.hasPrevChapter,
                    variant = HQButtonVariant.GHOST,
                    size = HQButtonSize.SM,
                    fullWidth = false
                )

                // Mode toggle
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.1f))
                        .clickable(onClick = onModeToggle)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    BasicText(
                        text = if (readingMode == ReadingMode.VERTICAL)
                            stringResource(R.string.reader_mode_vertical)
                        else stringResource(R.string.reader_mode_paged),
                        style = TextStyle(
                            fontFamily = InterFamily,
                            fontSize = 11.sp,
                            color = Color.White
                        )
                    )
                }

                HQButton(
                    text = stringResource(R.string.reader_next_chapter),
                    onClick = onNextChapter,
                    enabled = data.hasNextChapter,
                    variant = HQButtonVariant.GHOST,
                    size = HQButtonSize.SM,
                    fullWidth = false
                )
            }
        }
    }
}

