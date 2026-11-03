package com.example.feature.discover

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.designsystem.SarthiEmerald
import com.example.core.designsystem.SarthiRoyalBlue

data class StudyCircleGroup(
    val id: String,
    val topic: String,
    val courseCode: String,
    val hostStudent: String,
    val venue: String,
    val scheduleTime: String,
    val currentMembers: Int,
    val maxCapacity: Int,
    val focusDescription: String
)

val InitialStudyGroups = listOf(
    StudyCircleGroup(
        id = "sg_1",
        topic = "B-Trees & Dynamic Hash Indexing (DBMS)",
        courseCode = "CS-202",
        hostStudent = "Barnik Basu (CSE '26)",
        venue = "Library Silent Pod 3",
        scheduleTime = "Today, 05:00 PM",
        currentMembers = 3,
        maxCapacity = 5,
        focusDescription = "Working on mid-sem PYQ problem sets on multi-level indexing and B+ Tree node splitting."
    ),
    StudyCircleGroup(
        id = "sg_2",
        topic = "POSIX Thread Synchronization & Mutex Deadlocks",
        courseCode = "CS-214",
        hostStudent = "Aritra Sen (CSE '26)",
        venue = "Academic Block LHC-101",
        scheduleTime = "Tomorrow, 04:30 PM",
        currentMembers = 4,
        maxCapacity = 6,
        focusDescription = "Hands-on debugging session for kernel signals and producer-consumer pthread implementations."
    ),
    StudyCircleGroup(
        id = "sg_3",
        topic = "Discrete Probability & Generating Functions",
        courseCode = "MA-301",
        hostStudent = "Rhea Mukherjee (CSE '27)",
        venue = "Hostel 2 Common Study Room",
        scheduleTime = "Thursday, 06:00 PM",
        currentMembers = 2,
        maxCapacity = 4,
        focusDescription = "Solving recurrence relations with characteristic roots under Dr. Lakshman's syllabus."
    )
)

@Composable
fun StudyMatchmakerView(
    context: Context,
    modifier: Modifier = Modifier
) {
    var selectedSubjectFilter by remember { mutableStateOf("ALL") }
    val subjects = listOf("ALL", "CS-202 (DBMS)", "CS-214 (OS)", "MA-301 (Maths)", "CS-301 (DSA)")

    var showCreateDialog by remember { mutableStateOf(false) }
    var newTopicTitle by remember { mutableStateOf("") }
    var newTopicVenue by remember { mutableStateOf("") }

    val joinedGroups = remember {
        mutableStateMapOf("sg_1" to true, "sg_2" to false, "sg_3" to false)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("study_matchmaker_view"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Matchmaker Card
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = SarthiRoyalBlue.copy(alpha = 0.12f),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    tint = SarthiRoyalBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "PEER STUDY MATCHMAKER",
                                style = MaterialTheme.typography.labelSmall,
                                color = SarthiRoyalBlue,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            )
                            Text(
                                text = "Study Circles & Course Groups",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Connect with batchmates working on the same curriculum topics. Form focused revision pods in the Library or hostel common rooms.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { showCreateDialog = true },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SarthiRoyalBlue),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Create Study Circle", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Subject Filter Chips
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(subjects) { subj ->
                    val isSel = selectedSubjectFilter == subj
                    Surface(
                        onClick = { selectedSubjectFilter = subj },
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSel) SarthiRoyalBlue else MaterialTheme.colorScheme.surface,
                        border = BorderStroke(
                            1.dp,
                            if (isSel) SarthiRoyalBlue else MaterialTheme.colorScheme.outline
                        )
                    ) {
                        Text(
                            text = subj,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSel) Color.White else MaterialTheme.colorScheme.onSurface,
                            fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Study Groups List
        items(InitialStudyGroups) { group ->
            val isJoined = joinedGroups[group.id] ?: false

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SarthiRoyalBlue.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = group.courseCode,
                                style = MaterialTheme.typography.labelSmall,
                                color = SarthiRoyalBlue,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Group,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${group.currentMembers}/${group.maxCapacity} Seats",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = group.topic,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = group.focusDescription,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = SarthiRoyalBlue,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = group.venue,
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = group.hostStudent,
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            val curr = joinedGroups[group.id] ?: false
                            joinedGroups[group.id] = !curr
                            val msg = if (!curr) "Joined study circle for ${group.topic}!" else "Left study group."
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isJoined) SarthiEmerald else SarthiRoyalBlue
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = if (isJoined) Icons.Default.Check else Icons.Default.Group,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isJoined) "Joined (Seat Reserved)" else "Join Circle")
                    }
                }
            }
        }
    }

    if (showCreateDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = {
                Text("Create Peer Study Circle", fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "Set up a focused revision group with batchmates for mid-semesters or lab practicals.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    OutlinedTextField(
                        value = newTopicTitle,
                        onValueChange = { newTopicTitle = it },
                        label = { Text("Study Topic & Course") },
                        placeholder = { Text("e.g. CS204 OS Deadlocks") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newTopicVenue,
                        onValueChange = { newTopicVenue = it },
                        label = { Text("Proposed Venue & Time") },
                        placeholder = { Text("e.g. Library Silent Pod 2, 6 PM") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newTopicTitle.isNotBlank()) {
                            Toast.makeText(
                                context,
                                "Study circle '$newTopicTitle' created and broadcast to batchmates!",
                                Toast.LENGTH_SHORT
                            ).show()
                            showCreateDialog = false
                            newTopicTitle = ""
                            newTopicVenue = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SarthiRoyalBlue)
                ) {
                    Text("Broadcast Circle")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showCreateDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
