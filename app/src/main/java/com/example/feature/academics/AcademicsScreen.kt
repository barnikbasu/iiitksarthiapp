package com.example.feature.academics

import android.content.ClipData
import android.content.ClipboardManager
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import com.example.R
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
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.designsystem.SarthiAmber
import com.example.core.designsystem.SarthiAmberContainer
import com.example.core.designsystem.SarthiBlueContainer
import com.example.core.designsystem.SarthiEmerald
import com.example.core.designsystem.SarthiEmeraldContainer
import com.example.core.designsystem.SarthiRedContainer
import com.example.core.designsystem.SarthiRedSOS
import com.example.core.designsystem.SarthiRoyalBlue
import com.example.core.model.AcademicSummary
import com.example.core.model.AttendanceRecord
import com.example.core.model.UserProfile
import com.example.data.CampusData
import com.example.ui.viewmodel.SarthiAppViewModel

@Composable
fun AcademicsScreen(
    viewModel: SarthiAppViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val userProfile by viewModel.userProfile.collectAsState()
    val academicSummary by viewModel.academicSummary.collectAsState()
    var selectedAcademicView by remember { mutableIntStateOf(0) }
    val academicViews = listOf("Command Deck", "Timetable", "Attendance", "CGPA & Grades", "Academic Calendar", "Portals & Syllabi")

    var showSimulationDeck by remember { mutableStateOf(false) }
    var showSubmitDialog by remember { mutableStateOf<String?>(null) }
    var geoCheckedIn by remember { mutableStateOf(false) }

    // Interactive steppers delta map for Attendance Sandbox subjects
    val deltas = remember {
        mutableStateMapOf(
            "CS-204" to 0,
            "CS-202" to 0,
            "CS-206" to 0,
            "CS-208" to 0
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("academics_screen")
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Horizontal Segmented View Selector
        Surface(
            color = MaterialTheme.colorScheme.surface,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(academicViews.size) { index ->
                    val isSelected = selectedAcademicView == index
                    Surface(
                        onClick = { selectedAcademicView = index },
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) SarthiRoyalBlue else MaterialTheme.colorScheme.surfaceVariant,
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) SarthiRoyalBlue else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier.testTag("academic_view_tab_$index")
                    ) {
                        Text(
                            text = academicViews[index],
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        Crossfade(
            targetState = selectedAcademicView,
            animationSpec = tween(200),
            label = "AcademicsViewTransition"
        ) { viewIndex ->
            when (viewIndex) {
                0 -> {
                    // COMMAND DECK (Hero Layout from Screenshot)
                    CommandDeckView(
                        deltas = deltas,
                        geoCheckedIn = geoCheckedIn,
                        onGeoCheckIn = {
                            geoCheckedIn = true
                            Toast.makeText(context, "Geo-Checked in to LHC-Lab 3 via Campus Wi-Fi!", Toast.LENGTH_SHORT).show()
                        },
                        onOpenSimulationDeck = { showSimulationDeck = true },
                        onSubmitDeliverable = { title -> showSubmitDialog = title },
                        context = context,
                        userProfile = userProfile,
                        academicSummary = academicSummary
                    )
                }
                1 -> {
                    // TIMETABLE & SCHEDULE
                    TimetableView(userProfile = userProfile)
                }
                2 -> {
                    // DETAILED ATTENDANCE BREAKDOWN
                    DetailedAttendanceView(
                        userProfile = userProfile,
                        onOpenSimulationDeck = { showSimulationDeck = true }
                    )
                }
                3 -> {
                    // CGPA & GRADES CALCULATOR
                    CgpaCalculatorView(
                        academicSummary = academicSummary,
                        userProfile = userProfile
                    )
                }
                4 -> {
                    // ACADEMIC CALENDAR
                    AcademicCalendarView()
                }
                5 -> {
                    // PORTALS & SYLLABI
                    PortalsAndSyllabiView(context = context)
                }
            }
        }
    }

    // Direct Submit Dialog
    if (showSubmitDialog != null) {
        AlertDialog(
            onDismissRequest = { showSubmitDialog = null },
            title = {
                Text(
                    text = "Submit ${showSubmitDialog}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = "Upload your solution package (.zip or .pdf) to the exam portal. Submission timestamp will be digitally sealed.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Target Portal: IIITK Academics CMS\nDue: Tonight, 11:59 PM IST",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSubmitDialog = null
                        Toast.makeText(context, "Assignment submitted successfully to Exam Cell!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SarthiRoyalBlue)
                ) {
                    Text("Confirm Upload")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSubmitDialog = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Deep Simulation Deck Dialog (Leave Planner)
    if (showSimulationDeck) {
        AlertDialog(
            onDismissRequest = { showSimulationDeck = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = null,
                        tint = SarthiRoyalBlue
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Attendance Simulation Deck",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Leave & Conclave Forecaster",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Planning to attend StatusCode 2026 or sports tournament? Sarthi computes your exact projected margin across all registered courses.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        color = SarthiEmeraldContainer,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "✓ Safe to take 2 days of institutional leave without dropping below 75% threshold in any subject.",
                            modifier = Modifier.padding(10.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = SarthiEmerald,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSimulationDeck = false
                        Toast.makeText(context, "Leave Plan saved to Academic Planner", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SarthiRoyalBlue)
                ) {
                    Text("Apply to Schedule")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSimulationDeck = false }) {
                    Text("Close")
                }
            }
        )
    }
}

// -------------------------------------------------------------
// 1. COMMAND DECK VIEW (Exact Match to Screenshot 1:1)
// -------------------------------------------------------------
@Composable
private fun CommandDeckView(
    deltas: MutableMap<String, Int>,
    geoCheckedIn: Boolean,
    onGeoCheckIn: () -> Unit,
    onOpenSimulationDeck: () -> Unit,
    onSubmitDeliverable: (String) -> Unit,
    context: Context,
    userProfile: UserProfile,
    academicSummary: AcademicSummary
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. COMMAND DECK HEADER
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, SarthiRoyalBlue.copy(alpha = 0.3f))
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(2.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.iiitk_crest),
                                contentDescription = "IIIT Kalyani Crest",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = SarthiBlueContainer
                            ) {
                                Text(
                                    text = "COMMAND DECK",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SarthiRoyalBlue,
                                    fontSize = 10.5.sp
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFF3E8FF)
                            ) {
                                Text(
                                    text = "🏆 Dean's Honor Roll",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF7E22CE),
                                    fontSize = 10.5.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "भारतीय सूचना प्रौद्योगिकी संस्थान, कल्याणी • Indian Institute of Information Technology, Kalyani",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 9.sp,
                            maxLines = 1
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = userProfile.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Roll: ${userProfile.studentId}  ·  ${userProfile.semester}  ·  ${userProfile.department}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(SarthiRoyalBlue)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Spring '26 Term",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // 2. 3 STAT CARDS: CGPA, Sem III SGPA, Attendance
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // CGPA
                Surface(
                    modifier = Modifier.weight(1f),
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
                            Text(
                                text = "CGPA",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = "Verified",
                                tint = SarthiRoyalBlue,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = String.format(java.util.Locale.US, "%.2f", userProfile.cgpa),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = " / 10",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Exam Cell Validated",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 10.sp
                        )
                    }
                }

                // SGPA
                Surface(
                    modifier = Modifier.weight(1f),
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
                            Text(
                                text = "SGPA",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Icon(
                                imageVector = Icons.Default.TrendingUp,
                                contentDescription = "Trend Up",
                                tint = SarthiEmerald,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = String.format(java.util.Locale.US, "%.2f", userProfile.sgpa),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Semester Active",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = SarthiEmerald,
                            fontSize = 10.sp
                        )
                    }
                }

                // Attendance
                Surface(
                    modifier = Modifier.weight(1f),
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
                            Text(
                                text = "Attendance",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = "Attendance Safe",
                                tint = SarthiRoyalBlue,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${String.format(java.util.Locale.US, "%.1f", userProfile.attendancePct)}%",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (userProfile.attendancePct >= 75f) SarthiRoyalBlue else SarthiRedSOS
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (userProfile.attendancePct >= 75f) ">75% Safe Threshold" else "Shortage Warning",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Medium,
                            color = if (userProfile.attendancePct >= 75f) MaterialTheme.colorScheme.onSurfaceVariant else SarthiRedSOS,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }

        // 3. TODAY'S CLASS GRID
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = SarthiRoyalBlue,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "TODAY'S CLASS GRID",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Text(
                        text = "Tuesday · 4 Periods",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Period 1: Computer Networks
                ClassTimelineCard(
                    statusIcon = {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE2E8F0)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Attended",
                                tint = SarthiRoyalBlue,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    },
                    time = "09:30 - 10:25",
                    badge = "Attended · Logged",
                    badgeBg = Color(0xFFE2E8F0),
                    badgeColor = Color(0xFF334155),
                    title = "CS-206: Computer Networks",
                    details = "Dr. Bhaskar Biswas · LHC-101 · IPv6 Subnetting"
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Period 2: OS Lab (LIVE NOW)
                ClassTimelineCard(
                    statusIcon = {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(SarthiRoyalBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Wifi,
                                contentDescription = "Live",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    },
                    time = "10:30 - 12:25",
                    badge = "● LIVE NOW",
                    badgeBg = SarthiRoyalBlue,
                    badgeColor = Color.White,
                    title = "CS-214: OS Laboratory",
                    details = "Systems Group · LHC-Lab 3 · POSIX Mutexes",
                    actionRow = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = onGeoCheckIn,
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (geoCheckedIn) SarthiEmerald else SarthiRoyalBlue,
                                    contentColor = Color.White
                                ),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier.height(34.dp).testTag("geo_checkin_btn")
                            ) {
                                Icon(
                                    imageVector = if (geoCheckedIn) Icons.Default.CheckCircle else Icons.Default.LocationOn,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (geoCheckedIn) "Checked In" else "Geo-Check In",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Text(
                                text = "LHC-WiFi Connected",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Period 3: Database Systems (Upcoming)
                ClassTimelineCard(
                    statusIcon = {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE2E8F0)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = "Upcoming",
                                tint = Color(0xFF64748B),
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    },
                    time = "01:30 - 02:25",
                    badge = "Upcoming",
                    badgeBg = Color(0xFFF1F5F9),
                    badgeColor = Color(0xFF475569),
                    title = "CS-202: Database Systems",
                    details = "Core Theory · Room 204 · B-Trees & Hash Indexing"
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Period 4: Deep Work Slot
                ClassTimelineCard(
                    statusIcon = {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE2E8F0)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = "Self study",
                                tint = Color(0xFF64748B),
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    },
                    time = "03:00 - 04:30",
                    badge = "Deep Work Slot",
                    badgeBg = Color(0xFFF1F5F9),
                    badgeColor = Color(0xFF475569),
                    title = "Self-Study & Lab Report Prep",
                    details = "Central Library 2F · Silent Pod 4"
                )
            }
        }

        // 4. ATTENDANCE SANDBOX
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = null,
                            tint = SarthiRoyalBlue,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "ATTENDANCE SANDBOX",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFF1F5F9)
                    ) {
                        Text(
                            text = "Target: 75.0%",
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
                    text = "Simulate skip/attend impacts on 75% institute threshold with live safety calculations.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Subject 1: Operating Systems (CS-204)
                SandboxSubjectCard(
                    code = "CS-204 · CORE",
                    name = "Operating Systems",
                    initialAttended = 21,
                    initialDelivered = 25,
                    delta = deltas["CS-204"] ?: 0,
                    onDeltaChange = { deltas["CS-204"] = it },
                    statusPillText = "Safe Buffer",
                    statusColor = SarthiEmerald
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Subject 2: Database Systems (CS-202)
                SandboxSubjectCard(
                    code = "CS-202 · ATTENTION NEEDED",
                    name = "Database Systems",
                    initialAttended = 13,
                    initialDelivered = 17,
                    delta = deltas["CS-202"] ?: 0,
                    onDeltaChange = { deltas["CS-202"] = it },
                    statusPillText = "Borderline",
                    statusColor = SarthiRedSOS
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Subject 3: Computer Networks (CS-206)
                SandboxSubjectCard(
                    code = "CS-206 · CORE",
                    name = "Computer Networks",
                    initialAttended = 22,
                    initialDelivered = 25,
                    delta = deltas["CS-206"] ?: 0,
                    onDeltaChange = { deltas["CS-206"] = it },
                    statusPillText = "Resilient",
                    statusColor = SarthiRoyalBlue
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Subject 4: Design & Analysis of Algorithms (CS-208)
                SandboxSubjectCard(
                    code = "CS-208 · ADVANCED",
                    name = "Design & Analysis of Algorithms",
                    initialAttended = 31,
                    initialDelivered = 34,
                    delta = deltas["CS-208"] ?: 0,
                    onDeltaChange = { deltas["CS-208"] = it },
                    statusPillText = "Exemplary",
                    statusColor = Color(0xFF1E40AF)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onOpenSimulationDeck,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("launch_simulation_deck_btn"),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SarthiRoyalBlue,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.TrendingUp,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Launch Deep Simulation Deck (Leave Planner)",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // 5. DELIVERABLES PIPELINE
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = null,
                            tint = SarthiRoyalBlue,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "DELIVERABLES PIPELINE",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFF1F5F9)
                    ) {
                        Text(
                            text = "3 Active",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF475569),
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Deliverable 1: DBMS Assignment 3 (URGENT)
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
                                color = SarthiRedContainer
                            ) {
                                Text(
                                    text = "URGENT",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = SarthiRedSOS,
                                    fontSize = 10.sp
                                )
                            }
                            Text(
                                text = "Tonight, 11:59 PM",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = SarthiRedSOS
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "DBMS Assignment 3: Relational Algebra & SQL Views",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Submit compressed query log + ER schema diagram",
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
                                text = "Weightage:  10% End-Sem",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Button(
                                onClick = { onSubmitDeliverable("DBMS Assignment 3") },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SarthiRoyalBlue,
                                    contentColor = Color.White
                                ),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier.height(34.dp).testTag("direct_submit_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.UploadFile,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Direct Submit",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Deliverable 2: Wireshark Packet Trace
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
                                color = Color(0xFFF1F5F9)
                            ) {
                                Text(
                                    text = "DRAFTED",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF475569),
                                    fontSize = 10.sp
                                )
                            }
                            Text(
                                text = "Due Friday, 05:00 PM",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Wireshark Packet Trace: TCP Handshake Analysis",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Capture PCAP traces in LHC-101 and compute RTT delays",
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
                                text = "Status: 2 / 5 questions drafted",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            OutlinedButton(
                                onClick = {
                                    Toast.makeText(context, "Resumed Wireshark Lab draft", Toast.LENGTH_SHORT).show()
                                },
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Text(
                                    text = "Resume Draft",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Deliverable 3: OS Lab Report 4 (Evaluated)
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
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = SarthiEmerald,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "OS Lab Report 4: Kernel Signals",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Evaluated by Systems Group",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SarthiEmerald
                        ) {
                            Text(
                                text = "10 / 10",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        // 6. CURRICULUM ARCHIVES
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Folder,
                            contentDescription = null,
                            tint = SarthiRoyalBlue,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "CURRICULUM ARCHIVES",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Text(
                        text = "Exam Cell Verified",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Item 1: OS Lab Manual
                ArchiveDownloadCard(
                    formatTag = "PDF · 4.2 MB",
                    badge = "Official 2025",
                    title = "OS Lab Manual v4.2",
                    desc = "Full POSIX threading, shared memory & semaphores lab sheets.",
                    onDownload = {
                        Toast.makeText(context, "Downloading OS Lab Manual v4.2...", Toast.LENGTH_SHORT).show()
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Item 2: DBMS End-Sem PYQs
                ArchiveDownloadCard(
                    formatTag = "ZIP · 18 MB",
                    badge = "Exam Cell Solved",
                    title = "DBMS End-Sem PYQs (2021-2024)",
                    desc = "With verified model answer keys & schema normalization steps.",
                    onDownload = {
                        Toast.makeText(context, "Downloading DBMS End-Sem PYQs ZIP...", Toast.LENGTH_SHORT).show()
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Item 3: DAA Scribe Notes
                ArchiveDownloadCard(
                    formatTag = "PDF · 9.4 MB",
                    badge = "Scribe Notes",
                    title = "DAA Scribe Notes: Max Flow & Matching",
                    desc = "Ford-Fulkerson, Edmonds-Karp, and Dinic's algorithm notes.",
                    onDownload = {
                        Toast.makeText(context, "Downloading DAA Scribe Notes...", Toast.LENGTH_SHORT).show()
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // PEER REPOSITORIES: GitLab Curriculum Sync
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFFF1F5F9),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = SarthiBlueContainer
                            ) {
                                Text(
                                    text = "PEER REPOSITORIES",
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SarthiRoyalBlue,
                                    fontSize = 10.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "GitLab Curriculum Sync",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Clone verified lab templates & starter code straight to your terminal.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Button(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("GitLab SSH", "git@gitlab.iiitkalyani.ac.in:curriculum/cse-sem4.git")
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "SSH Key & Clone command copied to clipboard!", Toast.LENGTH_LONG).show()
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF0F172A),
                                contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                            modifier = Modifier.height(34.dp).testTag("gitlab_ssh_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Code,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "SSH Key",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 2. TIMETABLE & SCHEDULE VIEW
// -------------------------------------------------------------
@Composable
private fun TimetableView(
    userProfile: UserProfile
) {
    var selectedDay by remember { mutableStateOf("Tuesday") }
    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "WEEKLY LECTURE & LAB TIMETABLE",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${userProfile.department} · ${userProfile.semester} (LHC-101 / LHC-Labs)",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(daysOfWeek) { day ->
                    val isSelected = selectedDay == day
                    Surface(
                        onClick = { selectedDay = day },
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) SarthiRoyalBlue else MaterialTheme.colorScheme.surfaceVariant,
                        border = BorderStroke(1.dp, if (isSelected) SarthiRoyalBlue else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                    ) {
                        Text(
                            text = day,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        items(CampusData.scheduleToday, key = { it.id }) { session ->
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
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(72.dp)
                    ) {
                        Text(
                            text = session.startTime,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "to",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 10.sp
                        )
                        Text(
                            text = session.endTime,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = SarthiBlueContainer
                            ) {
                                Text(
                                    text = session.courseCode,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SarthiRoyalBlue,
                                    fontSize = 10.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            if (session.status == "NOW") {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = SarthiEmeraldContainer
                                ) {
                                    Text(
                                        text = "LIVE",
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = SarthiEmerald,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = session.courseName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "${session.faculty} · ${session.room}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 3. DETAILED ATTENDANCE VIEW
// -------------------------------------------------------------
@Composable
private fun DetailedAttendanceView(
    userProfile: UserProfile,
    onOpenSimulationDeck: () -> Unit
) {
    val records = CampusData.attendanceRecords
    val totalAttended = records.sumOf { it.attended }
    val totalDelivered = records.sumOf { it.total }
    val calculatedPercentage = if (totalDelivered > 0) (totalAttended.toFloat() / totalDelivered * 100f) else 0f
    val overallPercentage = if (userProfile.attendancePct > 0f) userProfile.attendancePct else calculatedPercentage

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = SarthiBlueContainer,
                border = BorderStroke(1.dp, SarthiRoyalBlue.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "INSTITUTIONAL AGGREGATE",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = SarthiRoyalBlue
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = String.format(java.util.Locale.US, "%.1f%%", overallPercentage),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = SarthiRoyalBlue
                        )
                        Text(
                            text = "$totalAttended of $totalDelivered sessions attended",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Button(
                        onClick = onOpenSimulationDeck,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SarthiRoyalBlue)
                    ) {
                        Text("Simulate Leave", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            Text(
                text = "SUBJECT-WISE REGULATORY AUDIT (75% RULE)",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 0.8.sp
            )
        }

        items(records, key = { it.id }) { rec ->
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
                        Column {
                            Text(
                                text = rec.courseCode,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = rec.courseName,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Text(
                            text = String.format("%.1f%%", rec.percentage),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (rec.isSafe) SarthiEmerald else SarthiRedSOS
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { (rec.percentage / 100f).coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = if (rec.isSafe) SarthiEmerald else SarthiRedSOS,
                        trackColor = Color(0xFFE2E8F0)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Delivered: ${rec.attended} / ${rec.total} sessions",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = if (rec.isSafe) "Safe to miss ${rec.safeToBunk} classes" else "Attend next ${rec.neededToReachThreshold} classes",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (rec.isSafe) SarthiEmerald else SarthiRedSOS
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 4. CGPA & GRADES CALCULATOR VIEW
// -------------------------------------------------------------
@Composable
private fun CgpaCalculatorView(
    academicSummary: AcademicSummary,
    userProfile: UserProfile
) {
    val summary = academicSummary.copy(
        cgpa = userProfile.cgpa,
        currentSgpa = userProfile.sgpa
    )
    val currentGrades = CampusData.currentGrades

    var simExpectedCredits by remember { mutableFloatStateOf(20f) }
    var simExpectedGradePoint by remember { mutableFloatStateOf(9.0f) }

    // What-if projected CGPA = (TotalCompletedCredits * CGPA + simCredits * simGradePoint) / (TotalCompletedCredits + simCredits)
    val projectedCgpa = (summary.totalCreditsCompleted * summary.cgpa + simExpectedCredits * simExpectedGradePoint) / (summary.totalCreditsCompleted + simExpectedCredits)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = SarthiBlueContainer,
                border = BorderStroke(1.dp, SarthiRoyalBlue.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "ACADEMIC TRANSCRIPT OVERVIEW",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = SarthiRoyalBlue
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = String.format(java.util.Locale.US, "%.2f", summary.cgpa),
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = SarthiRoyalBlue
                            )
                            Text(
                                text = "Cumulative Grade Point Average",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = String.format(java.util.Locale.US, "%.2f", summary.currentSgpa),
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = SarthiEmerald
                            )
                            Text(
                                text = "Latest SGPA",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "${summary.totalCreditsCompleted} Credits Done",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        // What-If CGPA Simulator Card
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "WHAT-IF CGPA SIMULATOR",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = SarthiRoyalBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Estimate final degree CGPA based on upcoming semester performance.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Target SGPA this sem: ${String.format("%.1f", simExpectedGradePoint)}", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                            Text("Credits: ${simExpectedCredits.toInt()}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            OutlinedButton(
                                onClick = { if (simExpectedGradePoint > 5f) simExpectedGradePoint -= 0.5f },
                                modifier = Modifier.size(32.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("-")
                            }
                            OutlinedButton(
                                onClick = { if (simExpectedGradePoint < 10f) simExpectedGradePoint += 0.5f },
                                modifier = Modifier.size(32.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("+")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = SarthiEmeraldContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Projected Cumulative CGPA:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = SarthiEmerald)
                            Text(String.format("%.2f", projectedCgpa), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold, color = SarthiEmerald)
                        }
                    }
                }
            }
        }

        // Semester Summaries
        item {
            Text(
                text = "SEMESTER HISTORICAL RECORD",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 0.8.sp
            )
        }

        items(summary.semesters) { sem ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = sem.semester, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Text(text = "${sem.credits} Credits Registered", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "SGPA", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp)
                            Text(text = "${sem.sgpa}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = SarthiRoyalBlue)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "YGPA", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp)
                            Text(text = "${sem.ygpa}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 5. ACADEMIC CALENDAR VIEW
// -------------------------------------------------------------
@Composable
private fun AcademicCalendarView() {
    val calendarEvents = listOf(
        Pair("15 Jul 2026", "Autumn Semester Registration & Course Opting"),
        Pair("18 Jul 2026", "Commencement of Lectures & Laboratories"),
        Pair("12 Oct — 17 Oct 2026", "Autumn Mid-Semester Theory & Practical Exams"),
        Pair("16 Oct — 18 Oct 2026", "StatusCode 2026: 36h National Flagship Hackathon"),
        Pair("12 Nov — 14 Nov 2026", "Renesa 2026: Annual Socio-Cultural Fest"),
        Pair("28 Nov 2026", "Instruction Closes for Autumn 2026"),
        Pair("01 Dec — 10 Dec 2026", "End-Semester Terminal Examinations"),
        Pair("15 Dec — 02 Jan 2027", "Winter Recess & Industry Internships"),
        Pair("05 Jan 2027", "Spring 2027 Term Commencement")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "SENATE APPROVED ACADEMIC CALENDAR",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Academic Year 2026 — 2027",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        items(calendarEvents) { (date, event) ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = SarthiBlueContainer,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = SarthiRoyalBlue,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = date,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = SarthiRoyalBlue
                        )
                        Text(
                            text = event,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 6. PORTALS & SYLLABI VIEW
// -------------------------------------------------------------
@Composable
private fun PortalsAndSyllabiView(context: Context) {
    val portals = CampusData.academicResources

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "OFFICIAL INSTITUTIONAL SERVICES",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 0.8.sp
            )
        }

        items(portals, key = { it.id }) { portal ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(portal.url))
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Could not open ${portal.title}", Toast.LENGTH_SHORT).show()
                        }
                    },
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = SarthiBlueContainer
                        ) {
                            Text(
                                text = portal.category,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = SarthiRoyalBlue,
                                fontSize = 10.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = portal.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = portal.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                        contentDescription = "Open",
                        tint = SarthiRoyalBlue,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// HELPER COMPOSABLES FOR TIMELINE & SANDBOX
// -------------------------------------------------------------
@Composable
private fun ClassTimelineCard(
    statusIcon: @Composable () -> Unit,
    time: String,
    badge: String,
    badgeBg: Color,
    badgeColor: Color,
    title: String,
    details: String,
    actionRow: (@Composable () -> Unit)? = null
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            statusIcon()

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = time,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = badgeBg
                    ) {
                        Text(
                            text = badge,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = badgeColor,
                            fontSize = 10.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = details,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (actionRow != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    actionRow()
                }
            }
        }
    }
}

@Composable
private fun SandboxSubjectCard(
    code: String,
    name: String,
    initialAttended: Int,
    initialDelivered: Int,
    delta: Int,
    onDeltaChange: (Int) -> Unit,
    statusPillText: String,
    statusColor: Color
) {
    val simAttended = (initialAttended + if (delta > 0) delta else 0).coerceAtLeast(0)
    val simDelivered = (initialDelivered + kotlin.math.abs(delta)).coerceAtLeast(1)
    val simPercentage = (simAttended.toFloat() / simDelivered.toFloat() * 100f)

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
                Text(
                    text = code,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (code.contains("ATTENTION")) SarthiRedSOS else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 10.sp
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = String.format("%.1f%%", simPercentage),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = statusColor.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = statusPillText,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = statusColor,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Delivered: $simAttended / $simDelivered sessions",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { (simPercentage / 100f).coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = if (simPercentage >= 75f) SarthiEmerald else SarthiRedSOS,
                trackColor = Color(0xFFE2E8F0)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val safeText = if (simPercentage >= 75f) {
                    val canSkip = ((simAttended - 0.75f * simDelivered) / 0.75f).toInt().coerceAtLeast(0)
                    if (canSkip > 0) "✓ Safe to skip $canSkip lecture${if (canSkip > 1) "s" else ""}" else "✓ Target 75% met exactly"
                } else {
                    val need = ((0.75f * simDelivered - simAttended) / 0.25f).toInt().coerceAtLeast(1)
                    "⚠️ Attend next $need classes to avoid debarment"
                }

                Text(
                    text = safeText,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = if (simPercentage >= 75f) SarthiEmerald else SarthiRedSOS,
                    fontSize = 11.sp,
                    modifier = Modifier.weight(1f)
                )

                // Steppers [- 0 +]
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Surface(
                        onClick = { onDeltaChange(delta - 1) },
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFFF1F5F9),
                        modifier = Modifier.size(28.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "Decrement",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    Text(
                        text = if (delta > 0) "+$delta" else "$delta",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    )

                    Surface(
                        onClick = { onDeltaChange(delta + 1) },
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFFF1F5F9),
                        modifier = Modifier.size(28.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Increment",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ArchiveDownloadCard(
    formatTag: String,
    badge: String,
    title: String,
    desc: String,
    onDownload: () -> Unit
) {
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = SarthiBlueContainer,
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null,
                        tint = SarthiRoyalBlue,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = formatTag,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 10.sp
                    )
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color(0xFFF1F5F9)
                    ) {
                        Text(
                            text = badge,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF475569),
                            fontSize = 9.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = onDownload) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = "Download",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
