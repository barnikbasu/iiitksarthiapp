package com.example.feature.discover

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.designsystem.SarthiAmber
import com.example.core.designsystem.SarthiAmberContainer
import com.example.core.designsystem.SarthiAvatar
import com.example.core.designsystem.SarthiBlueContainer
import com.example.core.designsystem.SarthiEmerald
import com.example.core.designsystem.SarthiEmeraldContainer
import com.example.core.designsystem.SarthiGold
import com.example.core.designsystem.SarthiRedContainer
import com.example.core.designsystem.SarthiRedSOS
import com.example.core.designsystem.SarthiRoyalBlue
import com.example.core.model.CampusEvent
import com.example.core.model.FacultyMember
import com.example.core.model.MarketplaceItem
import com.example.core.model.Opportunity
import com.example.data.CampusData
import com.example.ui.viewmodel.SarthiAppViewModel

@Composable
fun DiscoverScreen(
    viewModel: SarthiAppViewModel,
    onSelectEvent: (CampusEvent) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilterIndex by remember { mutableIntStateOf(0) }
    val filterChips = listOf(
        "All Highlights",
        "Events (${CampusData.eventsList.size})",
        "Renesa '26 Fest",
        "Institutional Atlas (8)",
        "Clubs (${CampusData.clubsList.size})",
        "Study Matchmaker",
        "Opportunities (${CampusData.opportunitiesList.size})",
        "Marketplace (${CampusData.marketplaceList.size})",
        "Campus Facilities"
    )

    var showFacultyDialog by remember { mutableStateOf(false) }
    var showRsvpSuccessDialog by remember { mutableStateOf<String?>(null) }
    var showApplicationDialog by remember { mutableStateOf<String?>(null) }
    var showPostMarketplaceDialog by remember { mutableStateOf(false) }

    // Follow status state for all 11 clubs
    val followedClubs = remember {
        mutableStateMapOf(
            "FreeScape" to true,
            "GDG Campus" to true,
            "Spotlight" to false,
            "PiXeL" to false,
            "SEAL" to true,
            "Udaan" to false,
            "Groovz" to false,
            "IEEE SB" to false,
            "Sports Club" to true,
            "E-Sports Club" to false,
            "SMC (Student Media Cell)" to false
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("discover_screen")
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 1. SEARCH BAR
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("discover_search_input"),
                placeholder = {
                    Text(
                        text = "Search clubs, hackathons, faculty, labs...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 13.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Explore,
                        contentDescription = "Search",
                        tint = SarthiRoyalBlue,
                        modifier = Modifier.size(20.dp)
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            Toast.makeText(context, "Voice Search is ready on device microphone", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Voice search",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SarthiRoyalBlue,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                singleLine = true
            )
        }

        // 2. HORIZONTAL FILTER CHIPS
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filterChips.size) { index ->
                val isSelected = selectedFilterIndex == index
                Surface(
                    onClick = { selectedFilterIndex = index },
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) SarthiRoyalBlue else MaterialTheme.colorScheme.surface,
                    border = BorderStroke(
                        1.dp,
                        if (isSelected) SarthiRoyalBlue else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier.testTag("discover_filter_chip_$index")
                ) {
                    Text(
                        text = filterChips[index],
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // VIEW ROUTING BASED ON SELECTED FILTER CHIP
        Crossfade(
            targetState = selectedFilterIndex,
            animationSpec = tween(200),
            label = "DiscoverFilterTransition"
        ) { filterIndex ->
            when (filterIndex) {
                0 -> {
                    // ALL HIGHLIGHTS (Exact Screenshot View)
                    AllHighlightsView(
                        searchQuery = searchQuery,
                        followedClubs = followedClubs,
                        onRsvp = { showRsvpSuccessDialog = it },
                        onApply = { showApplicationDialog = it },
                        onBrowseFaculty = { showFacultyDialog = true },
                        context = context
                    )
                }
                1 -> {
                    // ALL EVENTS (8 Events with registration and filter)
                    EventsDirectoryView(
                        searchQuery = searchQuery,
                        onSelectEvent = onSelectEvent,
                        onRegister = { showRsvpSuccessDialog = it }
                    )
                }
                2 -> {
                    // RENESA 2026 FESTIVAL DEEP DIVE (Part 3 Section 8)
                    RenesaFestivalView(context = context)
                }
                3 -> {
                    // INSTITUTIONAL ATLAS (Part 3 Section 7 - 8 Apex Pillars)
                    InstitutionalAtlasView(searchQuery = searchQuery)
                }
                4 -> {
                    // ALL CLUBS (11 Clubs)
                    ClubsDirectoryView(
                        searchQuery = searchQuery,
                        followedClubs = followedClubs,
                        context = context
                    )
                }
                5 -> {
                    // PEER STUDY MATCHMAKER (Part 3 Section 10)
                    StudyMatchmakerView(context = context)
                }
                6 -> {
                    // OPPORTUNITIES RADAR
                    OpportunitiesView(
                        searchQuery = searchQuery,
                        onApply = { showApplicationDialog = it }
                    )
                }
                7 -> {
                    // MARKETPLACE & PEER BUY/SELL
                    MarketplaceView(
                        searchQuery = searchQuery,
                        onPostItem = { showPostMarketplaceDialog = true },
                        context = context
                    )
                }
                8 -> {
                    // CAMPUS FACILITIES & MAP
                    CampusFacilitiesView(context = context)
                }
            }
        }
    }

    // Faculty Directory Dialog (preserves definitive 16 faculty records with search and direct mail)
    if (showFacultyDialog) {
        var facultySearch by remember { mutableStateOf("") }
        val filteredFaculty = CampusData.facultyList.filter {
            it.name.contains(facultySearch, ignoreCase = true) ||
            it.department.contains(facultySearch, ignoreCase = true) ||
            it.cabin.contains(facultySearch, ignoreCase = true)
        }

        AlertDialog(
            onDismissRequest = { showFacultyDialog = false },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Faculty Directory & Hours",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${CampusData.facultyList.size} Permanent Faculty Members",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = { showFacultyDialog = false }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = facultySearch,
                        onValueChange = { facultySearch = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Search professor by name, dept or cabin...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filteredFaculty, key = { it.id }) { fac ->
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.surface,
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    SarthiAvatar(name = fac.name, size = 36.dp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = fac.name,
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "${fac.designation} · ${fac.department}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = "Cabin: ${fac.cabin} · ${fac.email}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = SarthiRoyalBlue,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                                data = Uri.parse("mailto:${fac.email}")
                                                putExtra(Intent.EXTRA_SUBJECT, "Query regarding academic guidance")
                                            }
                                            context.startActivity(intent)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Email,
                                            contentDescription = "Email ${fac.name}",
                                            tint = SarthiRoyalBlue
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showFacultyDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = SarthiRoyalBlue)
                ) {
                    Text("Done")
                }
            }
        )
    }

    // Post Marketplace Dialog
    if (showPostMarketplaceDialog) {
        var itemTitle by remember { mutableStateOf("") }
        var itemPrice by remember { mutableStateOf("") }
        var itemCategory by remember { mutableStateOf("BOOKS") }
        var itemContact by remember { mutableStateOf("barnik.cse22@iiitkalyani.ac.in") }

        AlertDialog(
            onDismissRequest = { showPostMarketplaceDialog = false },
            title = {
                Text("List Item on Campus Marketplace", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = itemTitle,
                        onValueChange = { itemTitle = it },
                        label = { Text("Item Name & Edition") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = itemPrice,
                        onValueChange = { itemPrice = it },
                        label = { Text("Price (e.g. ₹500 or Free)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = itemContact,
                        onValueChange = { itemContact = it },
                        label = { Text("Seller Contact Email / Phone") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (itemTitle.isNotBlank()) {
                            viewModel.addMarketplaceItem(
                                MarketplaceItem(
                                    id = "mkt_${System.currentTimeMillis()}",
                                    title = itemTitle,
                                    price = if (itemPrice.isBlank()) "Free" else itemPrice,
                                    category = itemCategory,
                                    condition = "Good",
                                    sellerName = "Barnik Basu",
                                    sellerContact = itemContact,
                                    postedDate = "Just now"
                                )
                            )
                            showPostMarketplaceDialog = false
                            Toast.makeText(context, "Item posted to Campus Marketplace!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Please enter item title", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SarthiRoyalBlue)
                ) {
                    Text("Post Listing")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPostMarketplaceDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Application & RSVP confirmation dialogs
    if (showRsvpSuccessDialog != null) {
        AlertDialog(
            onDismissRequest = { showRsvpSuccessDialog = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = SarthiEmerald)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("RSVP Confirmed")
                }
            },
            text = {
                Text("You are confirmed for $showRsvpSuccessDialog! Your entry pass is linked to your IIITK Smart Credential.")
            },
            confirmButton = {
                Button(
                    onClick = { showRsvpSuccessDialog = null },
                    colors = ButtonDefaults.buttonColors(containerColor = SarthiRoyalBlue)
                ) {
                    Text("Great!")
                }
            }
        )
    }

    if (showApplicationDialog != null) {
        AlertDialog(
            onDismissRequest = { showApplicationDialog = null },
            title = {
                Text("Application Submitted")
            },
            text = {
                Text("Your profile and academic record (CSE/22014) have been forwarded to the conveners of $showApplicationDialog.")
            },
            confirmButton = {
                Button(
                    onClick = { showApplicationDialog = null },
                    colors = ButtonDefaults.buttonColors(containerColor = SarthiRoyalBlue)
                ) {
                    Text("OK")
                }
            }
        )
    }
}

// -------------------------------------------------------------
// ALL HIGHLIGHTS VIEW (Screenshot 1:1 Layout)
// -------------------------------------------------------------
@Composable
private fun AllHighlightsView(
    searchQuery: String,
    followedClubs: MutableMap<String, Boolean>,
    onRsvp: (String) -> Unit,
    onApply: (String) -> Unit,
    onBrowseFaculty: () -> Unit,
    context: Context
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // 1. CAMPUS SHOWSTOPPERS
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(SarthiAmber)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "CAMPUS SHOWSTOPPERS",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Text(
                        text = "Swipe for 2026 lineups",
                        style = MaterialTheme.typography.labelSmall,
                        color = SarthiRoyalBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Showstopper Card: StatusCode 2026
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF0F172A),
                    border = BorderStroke(1.dp, Color(0xFF1E293B))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = SarthiAmber
                            ) {
                                Text(
                                    text = "⚡ FLAGSHIP HACKATHON",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.Black,
                                    fontSize = 10.sp
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFF1E293B)
                            ) {
                                Text(
                                    text = "36h Non-stop",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF94A3B8),
                                    fontSize = 10.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "MAR 14 – 16, 2026",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF94A3B8)
                        )

                        Text(
                            text = "StatusCode 2026",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = null,
                                tint = SarthiAmber,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Main Auditorium Complex & Labs",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFCBD5E1)
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "National level multi-track hackathon powered by FreeScape & GDG IIITK. Over ₹1.5L in pooled bounties, hardware lab tracks and VC mentorship.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = CircleShape,
                                    color = SarthiRoyalBlue,
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text("FS", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                    }
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Surface(
                                    shape = CircleShape,
                                    color = SarthiEmerald,
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text("GD", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                    }
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "+310 Registered",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFFCBD5E1),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Button(
                                onClick = { onRsvp("StatusCode 2026") },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SarthiRoyalBlue,
                                    contentColor = Color.White
                                ),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                                modifier = Modifier.height(34.dp).testTag("rsvp_statuscode_btn")
                            ) {
                                Text(
                                    text = "RSVP Now",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // 2. CAMPUS SOCIETIES & CLUBS (11 Active)
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CAMPUS SOCIETIES & CLUBS",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFF1F5F9)
                    ) {
                        Text(
                            text = "11 Active",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF475569),
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "11 Official student chapters driving tech, culture & sports",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 2-Column Grid for Clubs
                val clubPairs = listOf(
                    Pair(
                        ClubItemData("FreeScape", "Open Source & Dev Guild", "412 Members", SarthiRoyalBlue),
                        ClubItemData("GDG Campus", "Google Dev Student Club", "580 Members", Color(0xFF4285F4))
                    ),
                    Pair(
                        ClubItemData("Spotlight", "Drama & Film Society", "188 Members", SarthiAmber),
                        ClubItemData("PiXeL", "Photography & Media", "215 Members", Color(0xFF8B5CF6))
                    ),
                    Pair(
                        ClubItemData("SEAL", "Hardware, Drones & IoT", "264 Members", Color(0xFF0EA5E9)),
                        ClubItemData("Udaan", "Literary & Debating Cell", "176 Members", Color(0xFFEC4899))
                    ),
                    Pair(
                        ClubItemData("Groovz", "Music & Dance Ensemble", "240 Members", Color(0xFFF97316)),
                        ClubItemData("IEEE SB", "Student Branch IIITK", "310 Members", Color(0xFF2563EB))
                    ),
                    Pair(
                        ClubItemData("Sports Club", "Athletics, Turf & Cricket", "390 Members", SarthiEmerald),
                        ClubItemData("E-Sports Club", "Valorant, BGMI & LAN", "288 Members", Color(0xFF6366F1))
                    )
                )

                clubPairs.forEach { (c1, c2) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ClubCard(
                            modifier = Modifier.weight(1f),
                            data = c1,
                            isFollowed = followedClubs[c1.name] ?: false,
                            onToggleFollow = { followedClubs[c1.name] = !(followedClubs[c1.name] ?: false) }
                        )

                        ClubCard(
                            modifier = Modifier.weight(1f),
                            data = c2,
                            isFollowed = followedClubs[c2.name] ?: false,
                            onToggleFollow = { followedClubs[c2.name] = !(followedClubs[c2.name] ?: false) }
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // 11th Club: SMC (Student Media Cell)
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = SarthiEmerald.copy(alpha = 0.15f),
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Apartment,
                                        contentDescription = null,
                                        tint = SarthiEmerald,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "SMC (Student Media Cell)",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Verified",
                                        tint = SarthiRoyalBlue,
                                        modifier = Modifier.size(13.dp)
                                    )
                                }
                                Text(
                                    text = "Press, Official Newsletters & PR Guild",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "145 Editorial Contributors",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        val isFollowed = followedClubs["SMC (Student Media Cell)"] ?: false
                        TextButton(
                            onClick = {
                                followedClubs["SMC (Student Media Cell)"] = !isFollowed
                            }
                        ) {
                            Text(
                                text = if (isFollowed) "Following" else "Follow",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isFollowed) SarthiEmerald else SarthiRoyalBlue
                            )
                        }
                    }
                }
            }
        }

        // 3. OPPORTUNITIES RADAR
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = SarthiEmerald,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "OPPORTUNITIES RADAR",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Text(
                        text = "3 Active Deadlines",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Opportunity 1: 5G & SHE Lab
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = SarthiEmeraldContainer
                            ) {
                                Text(
                                    text = "● FUNDED RESEARCH INTERN",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = SarthiEmerald,
                                    fontSize = 10.sp
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "₹8,000/mo",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "+ Course Credits",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Research Intern @ 5G & SHE Lab",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = "Mentor: Prof. Dr. Rabindranath Bera · 5G & SHE Lab",
                            style = MaterialTheme.typography.bodySmall,
                            color = SarthiRoyalBlue,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Focus: Next-gen signal processing & edge intelligence orchestration on FPGA boards. Open to 2nd & 3rd-year B.Tech students.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "⏱️ Deadline: Mar 08 (5 days left)",
                                style = MaterialTheme.typography.labelSmall,
                                color = SarthiAmber,
                                fontWeight = FontWeight.SemiBold
                            )

                            Button(
                                onClick = { onApply("Research Intern @ 5G & SHE Lab") },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SarthiRoyalBlue,
                                    contentColor = Color.White
                                ),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Text(
                                    text = "Apply Now",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Opportunity 2: SIH 2026
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = SarthiAmberContainer
                            ) {
                                Text(
                                    text = "🏆 HACKATHON SHORTLIST",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = SarthiAmber,
                                    fontSize = 10.sp
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "3 Teams",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "2 SW · 1 HW",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Smart India Hackathon (SIH 2026) Institute Round",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = "Internal Screening by IIITK Innovation Cell",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Mandatory internal campus pitch round for all interested teams. Final selected squads receive institutional travel funding and mentor pairing.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "👥 6 Members / Team (1 Female mandatory)",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )

                            Button(
                                onClick = { onApply("SIH 2026 Pitch Deck Form") },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFF1F5F9),
                                    contentColor = MaterialTheme.colorScheme.onSurface
                                ),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Text(
                                    text = "Pitch Deck Form",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // 4. CAMPUS FACILITIES RADAR
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Directions,
                            contentDescription = null,
                            tint = SarthiRoyalBlue,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "CAMPUS FACILITIES RADAR",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Text(
                        text = "Kalyani, WB",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Map Zone Card
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF1E293B),
                    border = BorderStroke(1.dp, Color(0xFF334155))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF0F172A)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = SarthiRoyalBlue
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Apartment,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Academic Block",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color(0xFF1E293B)
                                    ) {
                                        Text(
                                            text = "🔬 5G & SHE Labs",
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color(0xFFCBD5E1),
                                            fontSize = 10.sp
                                        )
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color(0xFF1E293B)
                                    ) {
                                        Text(
                                            text = "🍴 Canteen & Mess",
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color(0xFFCBD5E1),
                                            fontSize = 10.sp
                                        )
                                    }
                                }
                            }

                            Text(
                                text = "CURRENT CAMPUS ANCHOR\nIIIT Kalyani Campus Zone",
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(8.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // 2x2 Facilities Grid
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FacilityChip(
                                modifier = Modifier.weight(1f),
                                icon = Icons.Default.Apartment,
                                title = "Academic Block",
                                subtitle = "LH 1-6 · Labs 1-4"
                            )
                            FacilityChip(
                                modifier = Modifier.weight(1f),
                                icon = Icons.Default.Hotel,
                                title = "Hostels",
                                subtitle = "Block 1 & Block 2"
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FacilityChip(
                                modifier = Modifier.weight(1f),
                                icon = Icons.Default.Fastfood,
                                title = "Mess & Canteen",
                                subtitle = "Central Dining"
                            )
                            FacilityChip(
                                modifier = Modifier.weight(1f),
                                icon = Icons.Default.Science,
                                title = "5G & SHE Labs",
                                subtitle = "Research Wing"
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                try {
                                    val gmmIntentUri = Uri.parse("geo:22.9751,88.4344?q=IIIT+Kalyani")
                                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                    context.startActivity(mapIntent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Navigating to IIIT Kalyani Academic Block...", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(42.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SarthiRoyalBlue,
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Directions,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Get Turn-by-Turn Campus Directions",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // 5. FACULTY DIRECTORY & HOURS BANNER
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = SarthiBlueContainer,
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.People,
                                    contentDescription = null,
                                    tint = SarthiRoyalBlue,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "Faculty Directory & Hours",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Looking for cabin locations or slots with Professors?",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Button(
                        onClick = onBrowseFaculty,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF1F5F9),
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.height(34.dp).testTag("browse_faculty_btn")
                    ) {
                        Text(
                            text = "Browse (${CampusData.facultyList.size})",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// EVENTS DIRECTORY VIEW
// -------------------------------------------------------------
@Composable
private fun EventsDirectoryView(
    searchQuery: String,
    onSelectEvent: (CampusEvent) -> Unit,
    onRegister: (String) -> Unit
) {
    var selectedCategory by remember { mutableStateOf("ALL") }
    val categories = listOf("ALL", "HACKATHON", "FEST", "WORKSHOP", "CULTURAL")

    val filteredEvents = CampusData.eventsList.filter {
        (selectedCategory == "ALL" || it.category.equals(selectedCategory, ignoreCase = true)) &&
        (searchQuery.isBlank() || it.title.contains(searchQuery, ignoreCase = true) || it.description.contains(searchQuery, ignoreCase = true))
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat
                    Surface(
                        onClick = { selectedCategory = cat },
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) SarthiRoyalBlue else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = cat,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        items(filteredEvents, key = { it.id }) { event ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectEvent(event) },
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SarthiBlueContainer
                        ) {
                            Text(
                                text = event.category,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = SarthiRoyalBlue,
                                fontSize = 10.sp
                            )
                        }

                        Text(
                            text = event.date,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = event.tagline,
                        style = MaterialTheme.typography.bodySmall,
                        color = SarthiRoyalBlue,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = event.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "📍 ${event.venue}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Button(
                            onClick = { onRegister(event.title) },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SarthiRoyalBlue),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text("RSVP / Register", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// CLUBS DIRECTORY VIEW
// -------------------------------------------------------------
@Composable
private fun ClubsDirectoryView(
    searchQuery: String,
    followedClubs: MutableMap<String, Boolean>,
    context: Context
) {
    val clubs = CampusData.clubsList.filter {
        searchQuery.isBlank() || it.name.contains(searchQuery, ignoreCase = true) || it.description.contains(searchQuery, ignoreCase = true)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "11 OFFICIAL STUDENT CHAPTERS & SOCIETIES",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 0.8.sp
            )
        }

        items(clubs, key = { it.id }) { club ->
            val isFollowed = followedClubs[club.name] ?: false
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            SarthiAvatar(name = club.name, size = 38.dp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = club.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = SarthiRoyalBlue, modifier = Modifier.size(14.dp))
                                }
                                Text(text = club.category, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }

                        Button(
                            onClick = { followedClubs[club.name] = !isFollowed },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isFollowed) SarthiEmeraldContainer else SarthiBlueContainer,
                                contentColor = if (isFollowed) SarthiEmerald else SarthiRoyalBlue
                            ),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text(text = if (isFollowed) "Following ✓" else "Follow", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = club.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "Lead: ${club.lead} · ${club.contactEmail}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// OPPORTUNITIES VIEW
// -------------------------------------------------------------
@Composable
private fun OpportunitiesView(
    searchQuery: String,
    onApply: (String) -> Unit
) {
    val opportunities = CampusData.opportunitiesList.filter {
        searchQuery.isBlank() || it.title.contains(searchQuery, ignoreCase = true) || it.description.contains(searchQuery, ignoreCase = true)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(opportunities, key = { it.id }) { opp ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(shape = RoundedCornerShape(6.dp), color = SarthiEmeraldContainer) {
                            Text(text = opp.category, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = SarthiEmerald, fontSize = 10.sp)
                        }
                        Text(text = "Deadline: ${opp.deadline}", style = MaterialTheme.typography.labelSmall, color = SarthiAmber, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = opp.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(text = opp.organization, style = MaterialTheme.typography.bodySmall, color = SarthiRoyalBlue, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = opp.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "Eligibility: ${opp.eligibility}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { onApply(opp.title) },
                        modifier = Modifier.fillMaxWidth().height(36.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SarthiRoyalBlue)
                    ) {
                        Text("Apply Now", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MARKETPLACE VIEW (Student-to-Student Buy/Sell)
// -------------------------------------------------------------
@Composable
private fun MarketplaceView(
    searchQuery: String,
    onPostItem: () -> Unit,
    context: Context
) {
    var selectedCategory by remember { mutableStateOf("ALL") }
    val categories = listOf("ALL", "BOOKS", "CYCLES", "ELECTRONICS", "HOSTEL")

    val items = CampusData.marketplaceList.filter {
        (selectedCategory == "ALL" || it.category.equals(selectedCategory, ignoreCase = true)) &&
        (searchQuery.isBlank() || it.title.contains(searchQuery, ignoreCase = true))
    }

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
                    Text(text = "CAMPUS STUDENT MARKETPLACE", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 0.8.sp)
                    Text(text = "Verified student-to-student peer exchange", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Button(
                    onClick = onPostItem,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SarthiRoyalBlue),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.height(34.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Post Listing", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                }
            }
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat
                    Surface(
                        onClick = { selectedCategory = cat },
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) SarthiRoyalBlue else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = cat,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        items(items, key = { it.id }) { item ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = SarthiBlueContainer,
                        modifier = Modifier.size(46.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(imageVector = Icons.Default.ShoppingBag, contentDescription = null, tint = SarthiRoyalBlue, modifier = Modifier.size(24.dp))
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = item.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Text(text = "Condition: ${item.condition} · Posted ${item.postedDate}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = "Seller: ${item.sellerName}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp)
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = item.price, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold, color = SarthiEmerald)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("mailto:${item.sellerContact}")
                                    putExtra(Intent.EXTRA_SUBJECT, "Regarding your listing: ${item.title}")
                                }
                                context.startActivity(intent)
                            },
                            shape = RoundedCornerShape(6.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text("Contact", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// CAMPUS FACILITIES VIEW
// -------------------------------------------------------------
@Composable
private fun CampusFacilitiesView(context: Context) {
    val locations = CampusData.campusLocations

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(locations, key = { it.id }) { loc ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(shape = RoundedCornerShape(6.dp), color = SarthiBlueContainer) {
                            Text(text = loc.type, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = SarthiRoyalBlue, fontSize = 10.sp)
                        }
                        Text(text = "🕒 ${loc.timings}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = loc.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(text = "📍 Landmark: ${loc.landmark}", style = MaterialTheme.typography.bodySmall, color = SarthiRoyalBlue, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = loc.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            try {
                                val gmmIntentUri = Uri.parse("geo:22.9751,88.4344?q=${Uri.encode(loc.name)}")
                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                context.startActivity(mapIntent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "Locating ${loc.name}...", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(36.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SarthiRoyalBlue)
                    ) {
                        Icon(imageVector = Icons.Default.Directions, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Get Directions", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

data class ClubItemData(
    val name: String,
    val genre: String,
    val members: String,
    val themeColor: Color
)

@Composable
private fun ClubCard(
    modifier: Modifier = Modifier,
    data: ClubItemData,
    isFollowed: Boolean,
    onToggleFollow: () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = data.themeColor.copy(alpha = 0.15f),
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = data.name.take(2).uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = data.themeColor
                        )
                    }
                }

                TextButton(
                    onClick = onToggleFollow,
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
                    modifier = Modifier.height(28.dp)
                ) {
                    Text(
                        text = if (isFollowed) "Following" else "Follow",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isFollowed) SarthiEmerald else SarthiRoyalBlue,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = data.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(3.dp))
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Verified",
                    tint = SarthiRoyalBlue,
                    modifier = Modifier.size(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = data.genre,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.sp,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = data.members,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun FacilityChip(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    subtitle: String
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFF0F172A),
        border = BorderStroke(1.dp, Color(0xFF334155))
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = SarthiRoyalBlue,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF94A3B8),
                    fontSize = 10.sp
                )
            }
        }
    }
}
