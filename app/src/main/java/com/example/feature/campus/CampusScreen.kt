package com.example.feature.campus

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.HowToVote
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.core.designsystem.DarkBorderPrimary
import com.example.core.designsystem.DarkSurfaceCard
import com.example.core.designsystem.SarthiAvatar
import com.example.core.designsystem.SarthiCard
import com.example.core.designsystem.SarthiChip
import com.example.core.designsystem.SarthiEmptyState
import com.example.core.designsystem.SarthiGold
import com.example.core.designsystem.SarthiVerifiedGreen
import com.example.core.model.CampusPost
import com.example.core.model.CampusStoryGroup
import com.example.core.model.PollData
import com.example.core.model.PostType
import com.example.ui.viewmodel.SarthiAppViewModel

@Composable
fun CampusScreen(
    viewModel: SarthiAppViewModel,
    onOpenSearch: () -> Unit = {},
    onOpenNotifications: () -> Unit = {},
    onOpenMessages: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val posts by viewModel.posts.collectAsState()
    val stories by viewModel.stories.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) } // 0 = For You, 1 = Following, 2 = Official Updates
    val tabs = listOf("For You", "Following", "Official Updates")

    var selectedStoryGroupForViewing by remember { mutableStateOf<CampusStoryGroup?>(null) }
    var selectedPostForComments by remember { mutableStateOf<CampusPost?>(null) }
    var selectedMediaForViewing by remember { mutableStateOf<Pair<CampusPost, String>?>(null) }
    var showCreatePostSheet by remember { mutableStateOf(false) }

    val filteredPosts = when (selectedTab) {
        0 -> posts // For You: full campus feed
        1 -> posts.filter { !it.isOfficial || it.clubTag != null } // Following
        2 -> posts.filter { it.isOfficial } // Official Updates
        else -> posts
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("campus_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            // Top Stories Rail
            item {
                StoriesRail(
                    stories = stories,
                    onStoryClick = { group ->
                        selectedStoryGroupForViewing = group
                        viewModel.markStoryGroupViewed(group.authorId)
                    },
                    onAddStoryClick = {
                        showCreatePostSheet = true
                    }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            }

            // Quick Post Composer Pill
            item {
                Surface(
                    onClick = { showCreatePostSheet = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    shape = RoundedCornerShape(24.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SarthiAvatar(name = userProfile.name, size = 32.dp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Share with campus, ask a question, or post a poll...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Create",
                            tint = SarthiGold,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Feed Filter Tabs
            item {
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = SarthiGold,
                    edgePadding = 16.dp,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = SarthiGold,
                            height = 2.5.dp
                        )
                    },
                    divider = {
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Campus Posts
            if (filteredPosts.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        SarthiEmptyState(
                            title = "No updates in this stream",
                            message = "Follow verified student clubs or check 'For You' for campus discussions."
                        )
                    }
                }
            } else {
                items(filteredPosts, key = { it.id }) { post ->
                    CampusPostCard(
                        post = post,
                        onLikeClick = { viewModel.togglePostLike(post.id) },
                        onCommentClick = { selectedPostForComments = post },
                        onBookmarkClick = { viewModel.togglePostBookmark(post.id) },
                        onShareClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "${post.title ?: "Post by " + post.authorName}\n\n${post.content}\n\nShared via IIITK Sarthi"
                                )
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share Post"))
                        },
                        onPollVote = { optionId ->
                            viewModel.votePoll(post.id, optionId)
                        },
                        onMediaClick = { url ->
                            selectedMediaForViewing = Pair(post, url)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Floating Action Button to Compose
        FloatingActionButton(
            onClick = { showCreatePostSheet = true },
            containerColor = SarthiGold,
            contentColor = Color.Black,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .testTag("fab_create_post")
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Create Post",
                modifier = Modifier.size(26.dp)
            )
        }
    }

    // Story Viewer Dialog
    selectedStoryGroupForViewing?.let { group ->
        StoryViewerDialog(
            storyGroup = group,
            onDismiss = { selectedStoryGroupForViewing = null },
            onStoryCompleted = { selectedStoryGroupForViewing = null },
            onReplySubmitted = { replyText ->
                viewModel.sendDirectMessage("conv_gdg", replyText)
                selectedStoryGroupForViewing = null
            }
        )
    }

    // Media Viewer Dialog
    selectedMediaForViewing?.let { (post, mediaUrl) ->
        MediaViewerDialog(
            post = post,
            selectedMediaUrl = mediaUrl,
            onDismiss = { selectedMediaForViewing = null }
        )
    }

    // Comments Bottom Sheet
    selectedPostForComments?.let { post ->
        val currentPost = posts.firstOrNull { it.id == post.id } ?: post
        PostCommentsSheet(
            post = currentPost,
            onDismiss = { selectedPostForComments = null },
            onAddComment = { text ->
                viewModel.addPostComment(currentPost.id, text)
            }
        )
    }

    // Create Post Bottom Sheet
    if (showCreatePostSheet) {
        CreatePostSheet(
            userProfile = userProfile,
            onDismiss = { showCreatePostSheet = false },
            onPublish = { newPost ->
                viewModel.createPost(newPost)
                showCreatePostSheet = false
            }
        )
    }
}

@Composable
private fun StoriesRail(
    stories: List<CampusStoryGroup>,
    onStoryClick: (CampusStoryGroup) -> Unit,
    onAddStoryClick: () -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // "Your Story" item
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(68.dp)
                    .clickable(onClick = onAddStoryClick)
            ) {
                Box(modifier = Modifier.size(58.dp)) {
                    SarthiAvatar(name = "Your Story", size = 56.dp)
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.BottomEnd)
                            .background(SarthiGold, CircleShape)
                            .border(1.5.dp, MaterialTheme.colorScheme.background, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Story",
                            tint = Color.Black,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Your Story",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Campus Stories
        items(stories.filter { it.authorId != "user_me" }, key = { it.authorId }) { group ->
            val ringBrush = if (group.hasUnviewed) {
                Brush.sweepGradient(listOf(SarthiGold, SarthiVerifiedGreen, SarthiGold))
            } else {
                Brush.linearGradient(listOf(MaterialTheme.colorScheme.outline, MaterialTheme.colorScheme.outline))
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(70.dp)
                    .clickable { onStoryClick(group) }
            ) {
                Box(
                    modifier = Modifier
                        .size(58.dp)
                        .border(2.5.dp, ringBrush, CircleShape)
                        .padding(3.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SarthiAvatar(name = group.authorName, size = 48.dp)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = group.authorName,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = if (group.hasUnviewed) FontWeight.Bold else FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun CampusPostCard(
    post: CampusPost,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit,
    onPollVote: (String) -> Unit,
    onMediaClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SarthiCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        borderColor = MaterialTheme.colorScheme.outline
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Post Author Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SarthiAvatar(name = post.authorName, size = 42.dp)
                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = post.authorName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        if (post.isVerified) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = if (post.isOfficial) Icons.Default.Verified else Icons.Default.CheckCircle,
                                contentDescription = "Verified",
                                tint = if (post.isOfficial) SarthiGold else SarthiVerifiedGreen,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = post.authorRole,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "· ${post.timestamp}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Post Type Badge
                Surface(
                    color = when (post.postType) {
                        PostType.PROJECT_SHOWCASE -> SarthiGold.copy(alpha = 0.15f)
                        PostType.POLL -> Color(0xFF3B82F6).copy(alpha = 0.15f)
                        PostType.ACHIEVEMENT -> SarthiVerifiedGreen.copy(alpha = 0.15f)
                        PostType.LOST_AND_FOUND -> Color(0xFFEF4444).copy(alpha = 0.15f)
                        else -> MaterialTheme.colorScheme.surface
                    },
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Text(
                        text = post.postType.label,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = when (post.postType) {
                            PostType.PROJECT_SHOWCASE -> SarthiGold
                            PostType.POLL -> Color(0xFF60A5FA)
                            PostType.ACHIEVEMENT -> SarthiVerifiedGreen
                            PostType.LOST_AND_FOUND -> Color(0xFFF87171)
                            else -> MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Post Title if present
            if (!post.title.isNullOrBlank()) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            // Post Content Text
            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 22.sp
            )

            // Location Tag if present
            if (!post.location.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = "Location",
                        tint = SarthiGold,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = post.location,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Media Image Preview if present
            if (post.mediaUrls.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                val firstUrl = post.mediaUrls.first()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
                        .clickable { onMediaClick(firstUrl) }
                ) {
                    AsyncImage(
                        model = firstUrl,
                        contentDescription = post.title ?: "Post image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    if (post.mediaUrls.size > 1) {
                        Surface(
                            color = Color.Black.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "1/${post.mediaUrls.size}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            // Interactive Poll Card if present
            post.pollData?.let { poll ->
                Spacer(modifier = Modifier.height(14.dp))
                PollCardView(poll = poll, onVote = onPollVote)
            }

            // Tags row
            if (post.tags.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    post.tags.take(3).forEach { tag ->
                        Text(
                            text = "#$tag",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = SarthiGold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            Spacer(modifier = Modifier.height(6.dp))

            // Post Interaction Bar (Like, Comment, Bookmark, Share)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Like button with animation
                val scale by animateFloatAsState(
                    targetValue = if (post.isLiked) 1.2f else 1f,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                    label = "LikeScale"
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(onClick = onLikeClick)
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = if (post.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (post.isLiked) Color(0xFFEF4444) else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .size(20.dp)
                            .scale(scale)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = post.likesCount.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (post.isLiked) FontWeight.Bold else FontWeight.Medium,
                        color = if (post.isLiked) Color(0xFFEF4444) else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Comment button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(onClick = onCommentClick)
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = "Comment",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(19.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = post.commentsCount.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Bookmark button
                IconButton(
                    onClick = onBookmarkClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = if (post.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Save",
                        tint = if (post.isSaved) SarthiGold else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Share button
                IconButton(
                    onClick = onShareClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(19.dp)
                    )
                }
            }

            // Top comment teaser
            if (post.comments.isNotEmpty()) {
                val topComment = post.comments.first()
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onCommentClick)
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${topComment.authorName}: ",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = topComment.content,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun PollCardView(
    poll: PollData,
    onVote: (String) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.HowToVote,
                        contentDescription = "Poll",
                        tint = SarthiGold,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "CAMPUS POLL",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = SarthiGold
                    )
                }
                Text(
                    text = "${poll.totalVotes} votes",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = poll.question,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                poll.options.forEach { option ->
                    val percentage = if (poll.totalVotes > 0) {
                        (option.votes.toFloat() / poll.totalVotes)
                    } else 0f
                    val isVotedThis = poll.userVotedOptionId == option.id

                    Surface(
                        onClick = { onVote(option.id) },
                        shape = RoundedCornerShape(8.dp),
                        color = if (isVotedThis) SarthiGold.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant,
                        border = BorderStroke(
                            1.dp,
                            if (isVotedThis) SarthiGold else MaterialTheme.colorScheme.outline
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 10.dp)
                        ) {
                            // Background fill bar for percentage
                            LinearProgressIndicator(
                                progress = { percentage },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .matchParentSize()
                                    .clip(RoundedCornerShape(8.dp)),
                                color = if (isVotedThis) SarthiGold.copy(alpha = 0.25f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.35f),
                                trackColor = Color.Transparent
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    if (isVotedThis) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Voted",
                                            tint = SarthiGold,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                    }
                                    Text(
                                        text = option.text,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = if (isVotedThis) FontWeight.Bold else FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }

                                Text(
                                    text = "${(percentage * 100).toInt()}%",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isVotedThis) SarthiGold else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
