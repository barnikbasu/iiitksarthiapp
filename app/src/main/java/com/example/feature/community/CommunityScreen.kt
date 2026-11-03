package com.example.feature.community

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.designsystem.SarthiAvatar
import com.example.core.designsystem.SarthiCard
import com.example.core.designsystem.SarthiChip
import com.example.core.designsystem.SarthiEmptyState
import com.example.core.designsystem.SarthiOfflineIndicator
import com.example.core.designsystem.SarthiSectionHeader
import com.example.core.designsystem.SarthiSourceBadge
import com.example.core.model.CampusNotice
import com.example.core.model.FacultyMember
import com.example.core.model.MarketplaceItem
import com.example.core.model.SourceType
import com.example.data.CampusData
import com.example.ui.viewmodel.SarthiAppViewModel

@Composable
fun CommunityScreen(
    viewModel: SarthiAppViewModel,
    onSelectNotice: (CampusNotice) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Faculty Directory", "Notices", "Marketplace")

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("community_screen")
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = MaterialTheme.colorScheme.primary,
                    height = 2.5.dp
                )
            },
            divider = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.outline)
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    modifier = Modifier.testTag("community_tab_$index"),
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedTab == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }

        Crossfade(
            targetState = selectedTab,
            animationSpec = tween(200),
            label = "CommunityTabTransition"
        ) { tabIndex ->
            when (tabIndex) {
                0 -> FacultyTab()
                1 -> NoticesTab(viewModel = viewModel, onSelectNotice = onSelectNotice)
                2 -> MarketplaceTab(viewModel)
            }
        }
    }
}

@Composable
private fun FacultyTab() {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    val allFaculty = CampusData.facultyList

    val filtered = allFaculty.filter { fac ->
        searchQuery.isBlank() ||
                fac.name.contains(searchQuery, ignoreCase = true) ||
                fac.department.contains(searchQuery, ignoreCase = true) ||
                fac.researchInterests.any { it.contains(searchQuery, ignoreCase = true) }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Leadership Highlight Card
        item {
            SarthiCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("director_leadership_card"),
                backgroundColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f),
                borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.45f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "INSTITUTE LEADERSHIP",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Leadership",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Prof. Suman Chakraborty",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = "Director, IIIT Kalyani & Shanti Swarup Bhatnagar Awardee",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Leading IIIT Kalyani towards world-class research excellence, industry collaboration, and permanent campus infrastructure development.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                try {
                                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                        data = Uri.parse("mailto:director@iiitkalyani.ac.in")
                                        putExtra(Intent.EXTRA_SUBJECT, "Communication to Director - IIIT Kalyani")
                                    }
                                    context.startActivity(Intent.createChooser(emailIntent, "Email Director Office"))
                                } catch (e: Exception) {
                                    Toast.makeText(context, "No email app found", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email Director",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "director@iiitkalyani.ac.in",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        item {
            SarthiSectionHeader(
                title = "Faculty Directory",
                subtitle = "ACADEMIC SCHOLARS & INSTRUCTORS"
            )
        }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("faculty_search_input"),
                placeholder = { Text("Search by name, department, or research area…") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Faculty",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear Search",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )
        }

        if (filtered.isEmpty()) {
            item {
                SarthiEmptyState(
                    icon = Icons.Default.Person,
                    title = "No Faculty Found",
                    description = "No faculty matches for '$searchQuery'. Try searching by surname or department."
                )
            }
        } else {
            items(filtered, key = { it.id }) { fac ->
                FacultyCard(
                    fac = fac,
                    onEmailClick = {
                        try {
                            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:${fac.email}")
                                putExtra(Intent.EXTRA_SUBJECT, "Inquiry from IIITK Sarthi")
                            }
                            context.startActivity(Intent.createChooser(emailIntent, "Email ${fac.name}"))
                        } catch (e: Exception) {
                            Toast.makeText(context, "No email client found", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun FacultyCard(
    fac: FacultyMember,
    onEmailClick: () -> Unit
) {
    SarthiCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("faculty_card_${fac.id}"),
        onClick = onEmailClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    SarthiAvatar(name = fac.name, size = 42.dp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = fac.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "${fac.designation} · ${fac.department}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(
                    onClick = onEmailClick,
                    modifier = Modifier
                        .size(48.dp)
                        .testTag("email_fac_${fac.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email ${fac.name}",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "📍 ${fac.cabin}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Research: " + fac.researchInterests.joinToString(" · "),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun NoticesTab(
    viewModel: SarthiAppViewModel,
    onSelectNotice: (CampusNotice) -> Unit
) {
    val notices by viewModel.notices.collectAsState()
    var selectedCategory by remember { mutableStateOf("ALL") }
    val categories = listOf("ALL", "ACADEMIC", "EXAMINATION", "HOSTEL", "PLACEMENT")

    val filtered = if (selectedCategory == "ALL") {
        notices
    } else {
        notices.filter { it.category == selectedCategory }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            SarthiSectionHeader(
                title = "Official Bulletins & Notices",
                subtitle = "CAMPUS ARCHIVE"
            )
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { cat ->
                    SarthiChip(
                        text = if (cat == "ALL") "All Categories" else cat.lowercase().replaceFirstChar { it.uppercase() },
                        isSelected = selectedCategory == cat,
                        onClick = { selectedCategory = cat }
                    )
                }
            }
        }

        if (filtered.isEmpty()) {
            item {
                SarthiEmptyState(
                    icon = Icons.Default.Notifications,
                    title = "No Notices in Category",
                    description = "No campus bulletins published under $selectedCategory."
                )
            }
        } else {
            items(filtered, key = { it.id }) { notice ->
                SarthiCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("notice_card_${notice.id}"),
                    onClick = { onSelectNotice(notice) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SarthiSourceBadge(
                                sourceType = SourceType.OFFICIAL,
                                sourceName = notice.sourceName
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = notice.publishedDate,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                IconButton(
                                    onClick = { viewModel.toggleNoticeSave(notice.id) },
                                    modifier = Modifier
                                        .size(48.dp)
                                        .testTag("save_notice_${notice.id}")
                                ) {
                                    Icon(
                                        imageVector = if (notice.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                        contentDescription = if (notice.isSaved) "Notice Bookmarked" else "Bookmark Notice",
                                        tint = if (notice.isSaved) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = notice.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = notice.content,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MarketplaceTab(viewModel: SarthiAppViewModel) {
    val items by viewModel.marketplace.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    var titleInput by remember { mutableStateOf("") }
    var priceInput by remember { mutableStateOf("") }
    var categoryInput by remember { mutableStateOf("BOOKS") }
    var conditionInput by remember { mutableStateOf("Good condition") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "STUDENT-TO-STUDENT",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Campus Marketplace",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.testTag("post_listing_btn")
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Sell Item")
                }
            }
        }

        item {
            SarthiSourceBadge(
                sourceType = SourceType.COMMUNITY,
                sourceName = "Peer Student Listings · Zero Commission"
            )
        }

        if (items.isEmpty()) {
            item {
                SarthiEmptyState(
                    icon = Icons.Default.ShoppingBag,
                    title = "No Items Listed Yet",
                    description = "Be the first to list textbooks, lab equipment, or cycles for campus peers.",
                    actionLabel = "Sell an Item",
                    onActionClick = { showDialog = true }
                )
            }
        } else {
            items(items, key = { it.id }) { item ->
                SarthiCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("mkt_item_${item.id}")
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = item.category,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Text(
                                text = item.price,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Condition: ${item.condition}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Sold by ${item.sellerName} · ${item.postedDate}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            TextButton(
                                onClick = {
                                    try {
                                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                            data = Uri.parse("mailto:${item.sellerContact}")
                                            putExtra(Intent.EXTRA_SUBJECT, "Interest in Marketplace listing: ${item.title}")
                                        }
                                        context.startActivity(Intent.createChooser(emailIntent, "Contact Seller"))
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "No email app found", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier
                                    .defaultMinSize(minHeight = 48.dp)
                                    .testTag("contact_seller_${item.id}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "Email Seller",
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Contact", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("List an Item for Peers") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = titleInput,
                        onValueChange = { titleInput = it },
                        label = { Text("Item Name (e.g. Textbook / Cycle)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = priceInput,
                        onValueChange = { priceInput = it },
                        label = { Text("Expected Price (e.g. ₹500)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = conditionInput,
                        onValueChange = { conditionInput = it },
                        label = { Text("Condition / Notes") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val sanitizedTitle = titleInput.trim()
                        if (sanitizedTitle.isNotBlank()) {
                            val formattedPrice = if (priceInput.startsWith("₹")) priceInput.trim() else "₹${priceInput.trim().ifBlank { "0" }}"
                            viewModel.addMarketplaceItem(
                                MarketplaceItem(
                                    id = "mkt_${System.currentTimeMillis()}",
                                    title = sanitizedTitle,
                                    price = formattedPrice,
                                    category = categoryInput,
                                    condition = conditionInput.trim().ifBlank { "Good condition" },
                                    sellerName = userProfile.name,
                                    sellerContact = userProfile.email,
                                    postedDate = "Just now"
                                )
                            )
                            titleInput = ""
                            priceInput = ""
                            showDialog = false
                        }
                    }
                ) {
                    Text("Publish Listing")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
