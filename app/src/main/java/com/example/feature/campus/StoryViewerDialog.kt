package com.example.feature.campus

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.core.designsystem.DarkBackground
import com.example.core.designsystem.SarthiAvatar
import com.example.core.designsystem.SarthiGold
import com.example.core.designsystem.SarthiVerifiedGreen
import com.example.core.model.CampusStoryGroup

@Composable
fun StoryViewerDialog(
    storyGroup: CampusStoryGroup,
    onDismiss: () -> Unit,
    onStoryCompleted: () -> Unit,
    onReplySubmitted: (String) -> Unit = {}
) {
    var currentStoryIndex by remember { mutableIntStateOf(0) }
    val stories = storyGroup.stories
    val activeStory = stories.getOrNull(currentStoryIndex) ?: stories.first()
    var replyText by remember { mutableStateOf("") }

    val progress = remember(currentStoryIndex) { Animatable(0f) }

    LaunchedEffect(currentStoryIndex) {
        progress.snapTo(0f)
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 5000, easing = LinearEasing)
        )
        if (currentStoryIndex < stories.size - 1) {
            currentStoryIndex++
        } else {
            onStoryCompleted()
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground)
                .testTag("story_viewer_dialog")
        ) {
            // Background gradient depending on story style
            val bgBrush = when (activeStory.mediaBackgroundGrad) {
                "GOLD" -> Brush.verticalGradient(
                    listOf(Color(0xFF2C1E05), Color(0xFF140F03), Color(0xFF000000))
                )
                "EMERALD" -> Brush.verticalGradient(
                    listOf(Color(0xFF06281E), Color(0xFF02130E), Color(0xFF000000))
                )
                "PURPLE" -> Brush.verticalGradient(
                    listOf(Color(0xFF260830), Color(0xFF130419), Color(0xFF000000))
                )
                else -> Brush.verticalGradient(
                    listOf(Color(0xFF1C1C1C), Color(0xFF0A0A0A), Color(0xFF000000))
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(bgBrush)
            )

            // Tap navigation zones (left 30% = prev, right 70% = next)
            Row(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            if (currentStoryIndex > 0) {
                                currentStoryIndex--
                            }
                        }
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(2f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            if (currentStoryIndex < stories.size - 1) {
                                currentStoryIndex++
                            } else {
                                onStoryCompleted()
                            }
                        }
                )
            }

            // Top Header: Progress bars & Author info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                // Segmented Progress Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    stories.forEachIndexed { index, _ ->
                        val segmentProgress = when {
                            index < currentStoryIndex -> 1f
                            index == currentStoryIndex -> progress.value
                            else -> 0f
                        }
                        LinearProgressIndicator(
                            progress = { segmentProgress },
                            modifier = Modifier
                                .weight(1f)
                                .height(3.dp)
                                .clip(RoundedCornerShape(2.dp)),
                            color = SarthiGold,
                            trackColor = Color.White.copy(alpha = 0.25f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Author Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SarthiAvatar(
                        name = storyGroup.authorName,
                        size = 38.dp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = storyGroup.authorName,
                                color = Color.White,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            if (storyGroup.isOfficial) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Official",
                                    tint = SarthiGold,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                        Text(
                            text = "${activeStory.authorRole} · ${activeStory.timestamp}",
                            color = Color.White.copy(alpha = 0.7f),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Story",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Center Content: Headline & Visual Caption Card
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 120.dp, top = 100.dp),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    color = Color.Black.copy(alpha = 0.55f),
                    shape = RoundedCornerShape(24.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.15f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (storyGroup.isClub) "CAMPUS SPOTLIGHT" else "STUDENT UPDATE",
                            color = SarthiGold,
                            style = MaterialTheme.typography.labelSmall,
                            letterSpacing = 2.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = activeStory.mediaCaption ?: "IIIT Kalyani Campus Stories",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            lineHeight = 28.sp
                        )
                    }
                }
            }

            // Bottom Reaction & Reply Bar
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .imePadding()
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                // Quick emoji reactions
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf("🔥", "❤️", "👏", "🚀", "💡").forEach { emoji ->
                        Text(
                            text = emoji,
                            fontSize = 24.sp,
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.12f))
                                .clickable {
                                    onReplySubmitted("Reacted with $emoji")
                                }
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }
                }

                // Reply input
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = replyText,
                        onValueChange = { replyText = it },
                        placeholder = {
                            Text(
                                "Reply to ${storyGroup.authorName}...",
                                color = Color.White.copy(alpha = 0.5f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(26.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = SarthiGold,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                            focusedContainerColor = Color.Black.copy(alpha = 0.6f),
                            unfocusedContainerColor = Color.Black.copy(alpha = 0.6f)
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            if (replyText.isNotBlank()) {
                                onReplySubmitted(replyText)
                                replyText = ""
                            }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(SarthiGold, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send Reply",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
