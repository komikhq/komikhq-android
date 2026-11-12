package com.komikhq.app.ui.components.comic

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.komikhq.app.R
import com.komikhq.app.data.model.ComicItem

enum class ComicCardVariant {
    PORTRAIT_GRID,
    HORIZONTAL_HERO,
    COMPACT_ROW
}

@Composable
fun ComicCard(
    comic: ComicItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ComicCardVariant = ComicCardVariant.PORTRAIT_GRID
) {
    when (variant) {
        ComicCardVariant.PORTRAIT_GRID -> {
            PortraitGridComicCard(
                comic = comic,
                onClick = onClick,
                modifier = modifier
            )
        }
        ComicCardVariant.HORIZONTAL_HERO -> {
            HorizontalHeroComicCard(
                comic = comic,
                onClick = onClick,
                modifier = modifier
            )
        }
        ComicCardVariant.COMPACT_ROW -> {
            CompactRowComicCard(
                comic = comic,
                onClick = onClick,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun PortraitGridComicCard(
    comic: ComicItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.72f)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
        ) {
            AsyncImage(
                model = comic.coverUrl,
                placeholder = painterResource(id = R.drawable.ic_cover_placeholder),
                error = painterResource(id = R.drawable.ic_cover_placeholder),
                contentDescription = comic.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            if (!comic.type.isNullOrBlank()) {
                ComicTypeBadge(
                    type = comic.type,
                    modifier = Modifier
                        .padding(6.dp)
                        .align(Alignment.TopStart)
                )
            }

            ComicChapterBadge(
                totalChapters = comic.totalChapters,
                modifier = Modifier
                    .padding(6.dp)
                    .align(Alignment.BottomEnd)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = comic.title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun HorizontalHeroComicCard(
    comic: ComicItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .width(220.dp)
            .height(140.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = comic.coverUrl,
                placeholder = painterResource(id = R.drawable.ic_cover_placeholder),
                error = painterResource(id = R.drawable.ic_cover_placeholder),
                contentDescription = comic.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(95.dp)
                    .fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    if (!comic.type.isNullOrBlank()) {
                        Text(
                            text = comic.type.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 10.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                    }
                    Text(
                        text = comic.title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = "${comic.totalChapters} Ch.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun CompactRowComicCard(
    comic: ComicItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = comic.coverUrl,
                placeholder = painterResource(id = R.drawable.ic_cover_placeholder),
                error = painterResource(id = R.drawable.ic_cover_placeholder),
                contentDescription = comic.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(72.dp)
                    .fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    if (!comic.type.isNullOrBlank()) {
                        Text(
                            text = comic.type.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 10.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                    }
                    Text(
                        text = comic.title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = "${comic.totalChapters} Chapters",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

