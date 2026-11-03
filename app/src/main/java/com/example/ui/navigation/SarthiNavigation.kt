package com.example.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.res.painterResource
import com.example.R
import com.example.core.designsystem.SarthiBlueContainer
import com.example.core.designsystem.SarthiRoyalBlue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.designsystem.SarthiGold
import com.example.core.model.CampusEvent
import com.example.core.model.CampusNotice
import com.example.feature.academics.AcademicsScreen
import com.example.feature.campus.CampusScreen
import com.example.feature.common.EventDetailDialog
import com.example.feature.common.NoticeDetailDialog
import com.example.feature.discover.DiscoverScreen
import com.example.feature.emergency.EmergencyBottomSheet
import com.example.feature.helpdesk.HelpdeskDialog
import com.example.feature.home.HomeScreen
import com.example.feature.messaging.MessagingDialog
import com.example.feature.notifications.NotificationCenterDialog
import com.example.feature.profile.FullScreenIdDialog
import com.example.feature.profile.ProfileScreen
import com.example.feature.search.GlobalSearchDialog
import com.example.ui.viewmodel.SarthiAppViewModel

enum class SarthiDestination(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val tag: String
) {
    HOME("Home", Icons.Filled.Home, Icons.Outlined.Home, "nav_home"),
    CAMPUS("Campus", Icons.Filled.Groups, Icons.Outlined.Groups, "nav_campus"),
    ACADEMICS("Academics", Icons.Filled.School, Icons.Outlined.School, "nav_academics"),
    DISCOVER("Discover", Icons.Filled.Explore, Icons.Outlined.Explore, "nav_discover"),
    PROFILE("Profile", Icons.Filled.Person, Icons.Outlined.Person, "nav_profile")
}

@Composable
fun SarthiAppRoot(
    viewModel: SarthiAppViewModel,
    isDarkTheme: Boolean,
    onToggleDarkTheme: (Boolean) -> Unit
) {
    var currentDestination by remember { mutableStateOf(SarthiDestination.HOME) }
    var showHelpdeskDialog by remember { mutableStateOf(false) }
    var showFullScreenId by remember { mutableStateOf(false) }
    var showSearchDialog by remember { mutableStateOf(false) }
    var showNotificationsDialog by remember { mutableStateOf(false) }
    var showMessagingDialog by remember { mutableStateOf(false) }
    var showEmergencySheet by remember { mutableStateOf(false) }

    var selectedEventForDetail by remember { mutableStateOf<CampusEvent?>(null) }
    var selectedNoticeForDetail by remember { mutableStateOf<CampusNotice?>(null) }

    val userProfile by viewModel.userProfile.collectAsState()
    val events by viewModel.events.collectAsState()
    val notices by viewModel.notices.collectAsState()
    val notifications by viewModel.notifications.collectAsState()
    val conversations by viewModel.conversations.collectAsState()

    // Proper Android Back Handling
    BackHandler(
        enabled = currentDestination != SarthiDestination.HOME ||
                showHelpdeskDialog ||
                showFullScreenId ||
                showSearchDialog ||
                showNotificationsDialog ||
                showMessagingDialog ||
                showEmergencySheet ||
                selectedEventForDetail != null ||
                selectedNoticeForDetail != null
    ) {
        when {
            showFullScreenId -> showFullScreenId = false
            selectedNoticeForDetail != null -> selectedNoticeForDetail = null
            selectedEventForDetail != null -> selectedEventForDetail = null
            showHelpdeskDialog -> showHelpdeskDialog = false
            showSearchDialog -> showSearchDialog = false
            showNotificationsDialog -> showNotificationsDialog = false
            showMessagingDialog -> showMessagingDialog = false
            showEmergencySheet -> showEmergencySheet = false
            currentDestination != SarthiDestination.HOME -> currentDestination = SarthiDestination.HOME
        }
    }

    val outlineColor = MaterialTheme.colorScheme.outline

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        drawLine(
                            color = outlineColor,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                    },
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(38.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.padding(3.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.iiitk_crest),
                                    contentDescription = "IIIT Kalyani Crest",
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "IIIT KALYANI",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    letterSpacing = 0.5.sp
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = SarthiBlueContainer
                                ) {
                                    Text(
                                        text = "SARTHI",
                                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = SarthiRoyalBlue,
                                        fontSize = 9.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(com.example.core.designsystem.SarthiEmerald)
                                )
                            }
                            Text(
                                text = "भारतीय सूचना प्रौद्योगिकी संस्थान, कल्याणी",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 9.5.sp,
                                maxLines = 1
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // Persistent Search button
                        IconButton(
                            onClick = { showSearchDialog = true },
                            modifier = Modifier
                                .size(38.dp)
                                .testTag("top_search_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Campus",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Theme Toggle (Light / Dark mode matching website header)
                        IconButton(
                            onClick = { onToggleDarkTheme(!isDarkTheme) },
                            modifier = Modifier
                                .size(38.dp)
                                .testTag("top_theme_toggle_btn")
                        ) {
                            Icon(
                                imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = if (isDarkTheme) "Switch to Light Mode" else "Switch to Dark Mode",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Persistent Notifications button with unread count
                        IconButton(
                            onClick = { showNotificationsDialog = true },
                            modifier = Modifier
                                .size(38.dp)
                                .testTag("top_notifications_btn")
                        ) {
                            val unreadCount = notifications.count { !it.isRead }
                            BadgedBox(
                                badge = {
                                    if (unreadCount > 0) {
                                        Badge(
                                            containerColor = com.example.core.designsystem.SarthiRedSOS,
                                            contentColor = Color.White
                                        ) {
                                            Text("$unreadCount", fontWeight = FontWeight.Bold, fontSize = 9.sp)
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Notifications",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        // Red SOS Pill Button
                        androidx.compose.material3.Button(
                            onClick = { showEmergencySheet = true },
                            shape = RoundedCornerShape(20.dp),
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                containerColor = com.example.core.designsystem.SarthiRedSOS,
                                contentColor = Color.White
                            ),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            modifier = Modifier
                                .height(32.dp)
                                .testTag("top_sos_btn")
                        ) {
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Default.Phone,
                                contentDescription = "SOS",
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "SOS",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        drawLine(
                            color = outlineColor,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 1.dp.toPx()
                        )
                    },
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                windowInsets = NavigationBarDefaults.windowInsets
            ) {
                SarthiDestination.entries.forEach { destination ->
                    val isSelected = currentDestination == destination
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentDestination = destination },
                        modifier = Modifier.testTag(destination.tag),
                        icon = {
                            Icon(
                                imageVector = if (isSelected) destination.selectedIcon else destination.unselectedIcon,
                                contentDescription = destination.title,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                text = destination.title,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        },
        floatingActionButton = {
            if (currentDestination != SarthiDestination.PROFILE && currentDestination != SarthiDestination.CAMPUS) {
                FloatingActionButton(
                    onClick = { showHelpdeskDialog = true },
                    modifier = Modifier.testTag("fab_campus_helpdesk"),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Default.SupportAgent,
                        contentDescription = "Open Campus Helpdesk",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Crossfade(
                targetState = currentDestination,
                animationSpec = tween(220),
                label = "ScreenTransition"
            ) { destination ->
                when (destination) {
                    SarthiDestination.HOME -> HomeScreen(
                        viewModel = viewModel,
                        onNavigateToAcademics = { currentDestination = SarthiDestination.ACADEMICS },
                        onNavigateToDiscover = { currentDestination = SarthiDestination.DISCOVER },
                        onNavigateToCommunity = { currentDestination = SarthiDestination.CAMPUS },
                        onNavigateToProfile = { currentDestination = SarthiDestination.PROFILE },
                        onOpenDigitalId = { showFullScreenId = true },
                        onOpenHelpdesk = { showHelpdeskDialog = true },
                        onOpenEmergency = { showEmergencySheet = true },
                        onOpenSearch = { showSearchDialog = true },
                        onOpenNotifications = { showNotificationsDialog = true },
                        onSelectEvent = { selectedEventForDetail = it },
                        onSelectNotice = { selectedNoticeForDetail = it }
                    )
                    SarthiDestination.CAMPUS -> CampusScreen(
                        viewModel = viewModel,
                        onOpenSearch = { showSearchDialog = true },
                        onOpenNotifications = { showNotificationsDialog = true },
                        onOpenMessages = { showMessagingDialog = true }
                    )
                    SarthiDestination.ACADEMICS -> AcademicsScreen(
                        viewModel = viewModel
                    )
                    SarthiDestination.DISCOVER -> DiscoverScreen(
                        viewModel = viewModel,
                        onSelectEvent = { selectedEventForDetail = it }
                    )
                    SarthiDestination.PROFILE -> ProfileScreen(
                        viewModel = viewModel,
                        isDarkTheme = isDarkTheme,
                        onToggleDarkTheme = onToggleDarkTheme,
                        onOpenDigitalId = { showFullScreenId = true }
                    )
                }
            }
        }
    }

    if (showMessagingDialog) {
        MessagingDialog(
            viewModel = viewModel,
            onDismiss = { showMessagingDialog = false }
        )
    }

    if (showHelpdeskDialog) {
        HelpdeskDialog(
            viewModel = viewModel,
            onDismiss = { showHelpdeskDialog = false }
        )
    }

    if (showFullScreenId) {
        FullScreenIdDialog(
            userProfile = userProfile,
            onDismiss = { showFullScreenId = false }
        )
    }

    if (showSearchDialog) {
        GlobalSearchDialog(
            viewModel = viewModel,
            onDismiss = { showSearchDialog = false },
            onSelectNotice = { noticeId ->
                showSearchDialog = false
                val found = notices.firstOrNull { it.id == noticeId }
                if (found != null) {
                    selectedNoticeForDetail = found
                } else {
                    currentDestination = SarthiDestination.CAMPUS
                }
            },
            onSelectEvent = { eventId ->
                showSearchDialog = false
                val found = events.firstOrNull { it.id == eventId }
                if (found != null) {
                    selectedEventForDetail = found
                } else {
                    currentDestination = SarthiDestination.DISCOVER
                }
            }
        )
    }

    if (showNotificationsDialog) {
        NotificationCenterDialog(
            viewModel = viewModel,
            onDismiss = { showNotificationsDialog = false }
        )
    }

    if (showEmergencySheet) {
        EmergencyBottomSheet(
            onDismiss = { showEmergencySheet = false }
        )
    }

    selectedNoticeForDetail?.let { notice ->
        val currentNotice = notices.firstOrNull { it.id == notice.id } ?: notice
        NoticeDetailDialog(
            notice = currentNotice,
            isSaved = currentNotice.isSaved,
            onToggleSave = { viewModel.toggleNoticeSave(currentNotice.id) },
            onDismiss = { selectedNoticeForDetail = null }
        )
    }

    selectedEventForDetail?.let { event ->
        val currentEvent = events.firstOrNull { it.id == event.id } ?: event
        EventDetailDialog(
            event = currentEvent,
            isRegistered = currentEvent.isRegistered,
            isSaved = currentEvent.isSaved,
            onToggleRegister = { viewModel.toggleEventRegistration(currentEvent.id) },
            onToggleSave = { viewModel.toggleEventSave(currentEvent.id) },
            onDismiss = { selectedEventForDetail = null }
        )
    }
}
