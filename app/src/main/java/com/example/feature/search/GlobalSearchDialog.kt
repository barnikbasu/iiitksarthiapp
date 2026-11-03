package com.example.feature.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.designsystem.SarthiCard
import com.example.core.designsystem.SarthiChip
import com.example.core.designsystem.SarthiSectionHeader
import com.example.data.CampusData
import com.example.ui.viewmodel.SarthiAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlobalSearchDialog(
    viewModel: SarthiAppViewModel,
    onDismiss: () -> Unit,
    onSelectNotice: (String) -> Unit = {},
    onSelectEvent: (String) -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var query by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("ALL") }
    val filters = listOf("ALL", "FACULTY", "EVENTS", "CLUBS", "NOTICES", "CAMPUS")

    val q = query.trim()

    val matchedFaculty = if (selectedFilter in listOf("ALL", "FACULTY") && q.isNotEmpty()) {
        CampusData.facultyList.filter {
            it.name.contains(q, ignoreCase = true) ||
            it.department.contains(q, ignoreCase = true) ||
            it.researchInterests.any { r -> r.contains(q, ignoreCase = true) }
        }
    } else emptyList()

    val matchedEvents = if (selectedFilter in listOf("ALL", "EVENTS") && q.isNotEmpty()) {
        CampusData.eventsList.filter {
            it.title.contains(q, ignoreCase = true) ||
            it.category.contains(q, ignoreCase = true) ||
            it.organizer.contains(q, ignoreCase = true)
        }
    } else emptyList()

    val matchedClubs = if (selectedFilter in listOf("ALL", "CLUBS") && q.isNotEmpty()) {
        CampusData.clubsList.filter {
            it.name.contains(q, ignoreCase = true) ||
            it.description.contains(q, ignoreCase = true) ||
            it.interests.any { i -> i.contains(q, ignoreCase = true) }
        }
    } else emptyList()

    val matchedNotices = if (selectedFilter in listOf("ALL", "NOTICES") && q.isNotEmpty()) {
        CampusData.noticesList.filter {
            it.title.contains(q, ignoreCase = true) ||
            it.summary.contains(q, ignoreCase = true)
        }
    } else emptyList()

    val matchedLocations = if (selectedFilter in listOf("ALL", "CAMPUS") && q.isNotEmpty()) {
        CampusData.campusLocations.filter {
            it.name.contains(q, ignoreCase = true) ||
            it.landmark.contains(q, ignoreCase = true)
        }
    } else emptyList()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .testTag("global_search_dialog")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CAMPUS COMMAND & SEARCH",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.testTag("close_search_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Search",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Search Bar Input
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("global_search_input"),
                placeholder = { Text("Search faculty, clubs, events, notices, locations...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Filter Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(filters) { f ->
                    SarthiChip(
                        text = if (f == "ALL") "All Campus" else f.lowercase().replaceFirstChar { it.uppercase() },
                        isSelected = selectedFilter == f,
                        onClick = { selectedFilter = f }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Results List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (q.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Try searching for:",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            val hints = listOf("Dr. Debasish Bera", "StatusCode", "Renesa", "FreeScape", "Academic Block", "Mid-Sem Notice")
                            hints.forEach { hint ->
                                Surface(
                                    modifier = Modifier
                                        .padding(vertical = 4.dp)
                                        .clickable { query = hint },
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "🔍 $hint",
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                        }
                    }
                } else {
                    val total = matchedFaculty.size + matchedEvents.size + matchedClubs.size + matchedNotices.size + matchedLocations.size
                    if (total == 0) {
                        item {
                            Text(
                                text = "No campus results found for \"$q\"",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }

                    if (matchedFaculty.isNotEmpty()) {
                        item {
                            SearchResultHeader("FACULTY", Icons.Default.Person)
                        }
                        items(matchedFaculty) { fac ->
                            SearchResultItem(
                                title = fac.name,
                                subtitle = "${fac.designation} · ${fac.department}",
                                extra = "📍 ${fac.cabin}"
                            )
                        }
                    }

                    if (matchedEvents.isNotEmpty()) {
                        item {
                            SearchResultHeader("EVENTS", Icons.Default.Explore)
                        }
                        items(matchedEvents) { ev ->
                            SearchResultItem(
                                title = ev.title,
                                subtitle = "${ev.tagline} · ${ev.date}",
                                extra = "By ${ev.organizer}",
                                onClick = {
                                    onDismiss()
                                    onSelectEvent(ev.id)
                                }
                            )
                        }
                    }

                    if (matchedClubs.isNotEmpty()) {
                        item {
                            SearchResultHeader("CLUBS", Icons.Default.Groups)
                        }
                        items(matchedClubs) { club ->
                            SearchResultItem(
                                title = club.name,
                                subtitle = club.category,
                                extra = club.description
                            )
                        }
                    }

                    if (matchedNotices.isNotEmpty()) {
                        item {
                            SearchResultHeader("NOTICES", Icons.Default.Notifications)
                        }
                        items(matchedNotices) { notice ->
                            SearchResultItem(
                                title = notice.title,
                                subtitle = "${notice.publishedDate} · ${notice.sourceName}",
                                extra = notice.summary,
                                onClick = {
                                    onDismiss()
                                    onSelectNotice(notice.id)
                                }
                            )
                        }
                    }

                    if (matchedLocations.isNotEmpty()) {
                        item {
                            SearchResultHeader("CAMPUS LOCATIONS", Icons.Default.Place)
                        }
                        items(matchedLocations) { loc ->
                            SearchResultItem(
                                title = loc.name,
                                subtitle = "📍 ${loc.landmark}",
                                extra = loc.description
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchResultHeader(title: String, icon: ImageVector) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 1.sp
        )
    }
}

@Composable
private fun SearchResultItem(
    title: String,
    subtitle: String,
    extra: String? = null,
    onClick: (() -> Unit)? = null
) {
    SarthiCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            if (!extra.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = extra,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
            }
        }
    }
}
