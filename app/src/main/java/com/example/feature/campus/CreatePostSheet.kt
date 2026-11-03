package com.example.feature.campus

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.designsystem.SarthiAvatar
import com.example.core.designsystem.SarthiChip
import com.example.core.designsystem.SarthiGold
import com.example.core.model.CampusPost
import com.example.core.model.PollData
import com.example.core.model.PollOption
import com.example.core.model.PostType
import com.example.core.model.UserProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostSheet(
    userProfile: UserProfile,
    onDismiss: () -> Unit,
    onPublish: (CampusPost) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var selectedType by remember { mutableStateOf(PostType.TEXT) }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var audience by remember { mutableStateOf("Public Campus") }

    // Poll options if POLL type selected
    val pollOptions = remember { mutableStateListOf("Option 1", "Option 2") }

    val postTypes = listOf(
        PostType.TEXT,
        PostType.PROJECT_SHOWCASE,
        PostType.POLL,
        PostType.QUESTION_DISCUSSION,
        PostType.ACHIEVEMENT,
        PostType.LOST_AND_FOUND,
        PostType.ACADEMIC_RESOURCE_SHARING
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = null,
        modifier = Modifier
            .fillMaxHeight(0.92f)
            .testTag("create_post_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .imePadding()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cancel",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                Text(
                    text = "Create Campus Post",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Button(
                    onClick = {
                        if (content.isNotBlank()) {
                            val pollData = if (selectedType == PostType.POLL) {
                                PollData(
                                    id = "poll_${System.currentTimeMillis()}",
                                    question = title.ifBlank { "Campus Community Poll" },
                                    options = pollOptions.mapIndexed { idx, optText ->
                                        PollOption("opt_${idx + 1}", optText.ifBlank { "Option ${idx + 1}" }, 0)
                                    },
                                    totalVotes = 0
                                )
                            } else null

                            val newPost = CampusPost(
                                id = "post_${System.currentTimeMillis()}",
                                authorName = userProfile.name,
                                authorRole = "${userProfile.department} · ${userProfile.semester}",
                                isOfficial = false,
                                isVerified = userProfile.verified,
                                postType = selectedType,
                                title = title.ifBlank { null },
                                content = content.trim(),
                                pollData = pollData,
                                tags = listOf("IIITK", selectedType.label.replace(" ", "")),
                                timestamp = "Just now",
                                isAuthor = true
                            )
                            onPublish(newPost)
                            onDismiss()
                        }
                    },
                    enabled = content.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SarthiGold,
                        contentColor = Color.Black,
                        disabledContainerColor = MaterialTheme.colorScheme.outline
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.testTag("publish_post_button")
                ) {
                    Text(
                        text = "Publish",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outline)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // Author row & Audience tag
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SarthiAvatar(name = userProfile.name, size = 44.dp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = userProfile.name,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .clickable {
                                    audience = if (audience == "Public Campus") "CSE Batch '26" else "Public Campus"
                                }
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Public,
                                contentDescription = "Audience",
                                tint = SarthiGold,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = audience,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Post Type Chips
                Text(
                    text = "POST TYPE",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(postTypes) { type ->
                        val isSelected = selectedType == type
                        Surface(
                            onClick = { selectedType = type },
                            shape = RoundedCornerShape(16.dp),
                            color = if (isSelected) SarthiGold else MaterialTheme.colorScheme.surfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) SarthiGold else MaterialTheme.colorScheme.outline
                            )
                        ) {
                            Text(
                                text = type.label,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Optional Title input
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = {
                        Text(
                            text = when (selectedType) {
                                PostType.POLL -> "Poll Question (e.g. Workshop timing preference?)"
                                PostType.PROJECT_SHOWCASE -> "Project Title (e.g. Autonomous Drone SLAM)"
                                PostType.ACHIEVEMENT -> "Achievement Title (e.g. 1st Prize in ICPC Regionals)"
                                PostType.LOST_AND_FOUND -> "Lost / Found Item & Location"
                                else -> "Headline / Title (optional)"
                            },
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SarthiGold,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Content / Body input
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    placeholder = {
                        Text(
                            text = "What would you like to share with IIIT Kalyani? Use tags, share takeaways, links or opportunities...",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SarthiGold,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )

                // Poll Options Builder if POLL selected
                if (selectedType == PostType.POLL) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "POLL CHOICES",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    pollOptions.forEachIndexed { index, optionText ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = optionText,
                                onValueChange = { pollOptions[index] = it },
                                placeholder = { Text("Option ${index + 1}") },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = SarthiGold,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                                )
                            )

                            if (pollOptions.size > 2) {
                                IconButton(
                                    onClick = { pollOptions.removeAt(index) },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Remove option",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    if (pollOptions.size < 4) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    pollOptions.add("Option ${pollOptions.size + 1}")
                                }
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Choice",
                                tint = SarthiGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Add another option",
                                style = MaterialTheme.typography.labelMedium,
                                color = SarthiGold,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
