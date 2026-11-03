package com.example.core.model

enum class PostType(val label: String, val iconDescription: String) {
    TEXT("Text Update", "Text"),
    PHOTO("Photo", "Photo"),
    CAROUSEL("Carousel", "Multi-Photo"),
    SHORT_VIDEO("Short Video", "Video"),
    POLL("Poll", "Poll"),
    QUESTION_DISCUSSION("Discussion", "Question"),
    ACHIEVEMENT("Achievement", "Trophy"),
    PROJECT_SHOWCASE("Showcase", "Showcase"),
    HACKATHON_RECRUITMENT("Team Recruit", "Recruitment"),
    EVENT_ANNOUNCEMENT("Event", "Announcement"),
    CLUB_UPDATE("Club Post", "Club"),
    OPPORTUNITY_RESEARCH("Opportunity", "Research"),
    LOST_AND_FOUND("Lost & Found", "Lost and Found"),
    ACADEMIC_RESOURCE_SHARING("Resource", "Academic Resource")
}

data class PollOption(
    val id: String,
    val text: String,
    val votes: Int = 0
)

data class PollData(
    val id: String,
    val question: String,
    val options: List<PollOption>,
    val totalVotes: Int,
    val userVotedOptionId: String? = null
)

data class PostComment(
    val id: String,
    val authorName: String,
    val authorRole: String = "Student",
    val authorDepartment: String = "CSE",
    val content: String,
    val timestamp: String,
    val likes: Int = 0,
    val isLiked: Boolean = false,
    val replies: List<PostComment> = emptyList()
)

data class CampusPost(
    val id: String,
    val authorName: String,
    val authorAvatar: String? = null,
    val authorRole: String, // e.g. "Student - CSE 3rd Yr", "GDG on Campus", "SEAL Robotics"
    val isOfficial: Boolean = false,
    val isVerified: Boolean = true,
    val postType: PostType = PostType.TEXT,
    val title: String? = null,
    val content: String,
    val mediaUrls: List<String> = emptyList(),
    val pollData: PollData? = null,
    val tags: List<String> = emptyList(),
    val clubTag: String? = null,
    val timestamp: String,
    val likesCount: Int = 0,
    val isLiked: Boolean = false,
    val commentsCount: Int = 0,
    val isSaved: Boolean = false,
    val comments: List<PostComment> = emptyList(),
    val location: String? = null,
    val isAuthor: Boolean = false
)

data class StoryItem(
    val id: String,
    val authorId: String,
    val authorName: String,
    val authorAvatar: String? = null,
    val authorRole: String,
    val mediaCaption: String? = null,
    val mediaType: String = "IMAGE", // "IMAGE", "VIDEO"
    val mediaBackgroundGrad: String = "GOLD", // "GOLD", "DARK", "EMERALD", "PURPLE"
    val timestamp: String,
    val isViewed: Boolean = false,
    val isMyStory: Boolean = false
)

data class CampusStoryGroup(
    val authorId: String,
    val authorName: String,
    val authorRole: String,
    val isOfficial: Boolean = false,
    val isClub: Boolean = false,
    val stories: List<StoryItem>,
    val hasUnviewed: Boolean = true
)

data class DirectMessage(
    val id: String,
    val senderId: String,
    val senderName: String,
    val text: String,
    val timestamp: String,
    val isMe: Boolean = false
)

data class ConversationThread(
    val id: String,
    val participantName: String,
    val participantSubtitle: String,
    val lastMessage: String,
    val lastTimestamp: String,
    val unreadCount: Int = 0,
    val isClubChannel: Boolean = false,
    val messages: List<DirectMessage> = emptyList()
)
