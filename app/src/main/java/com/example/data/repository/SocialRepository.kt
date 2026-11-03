package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.core.model.CampusPost
import com.example.core.model.CampusStoryGroup
import com.example.core.model.ConversationThread
import com.example.core.model.DirectMessage
import com.example.core.model.PollData
import com.example.core.model.PollOption
import com.example.core.model.PostComment
import com.example.core.model.PostType
import com.example.core.model.StoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface ISocialRepository {
    val stories: StateFlow<List<CampusStoryGroup>>
    val posts: StateFlow<List<CampusPost>>
    val conversations: StateFlow<List<ConversationThread>>

    fun togglePostLike(postId: String)
    fun togglePostBookmark(postId: String)
    fun votePoll(postId: String, optionId: String)
    fun addPostComment(postId: String, commentText: String)
    fun createPost(post: CampusPost)
    fun deletePost(postId: String)
    fun markStoryGroupViewed(authorId: String)
    fun sendDirectMessage(conversationId: String, text: String)
}

class SocialRepository(private val context: Context? = null) : ISocialRepository {
    private val prefs: SharedPreferences? = context?.getSharedPreferences("sarthi_social_prefs", Context.MODE_PRIVATE)

    private val _stories = MutableStateFlow(loadInitialStories())
    override val stories: StateFlow<List<CampusStoryGroup>> = _stories.asStateFlow()

    private val _posts = MutableStateFlow(loadInitialPosts())
    override val posts: StateFlow<List<CampusPost>> = _posts.asStateFlow()

    private val _conversations = MutableStateFlow(loadInitialConversations())
    override val conversations: StateFlow<List<ConversationThread>> = _conversations.asStateFlow()

    private fun loadInitialStories(): List<CampusStoryGroup> {
        val viewedStoryAuthors = prefs?.getStringSet("viewed_stories", emptySet()) ?: emptySet()

        return listOf(
            CampusStoryGroup(
                authorId = "user_me",
                authorName = "Your Story",
                authorRole = "Student",
                isOfficial = false,
                isClub = false,
                stories = listOf(
                    StoryItem(
                        id = "story_me_1",
                        authorId = "user_me",
                        authorName = "Your Story",
                        authorRole = "CSE '26",
                        mediaCaption = "Late night hacking in Lab 2 for Smart Bengal Hackathon 🚀",
                        mediaType = "IMAGE",
                        mediaBackgroundGrad = "DARK",
                        timestamp = "2h ago",
                        isViewed = true,
                        isMyStory = true
                    )
                ),
                hasUnviewed = false
            ),
            CampusStoryGroup(
                authorId = "club_gdg",
                authorName = "GDG Kalyani",
                authorRole = "Official Club",
                isOfficial = true,
                isClub = true,
                stories = listOf(
                    StoryItem(
                        id = "story_gdg_1",
                        authorId = "club_gdg",
                        authorName = "GDG on Campus IIIT Kalyani",
                        authorRole = "Technical Society",
                        mediaCaption = "🎉 Announcing 36-Hr DevSprint! Registrations open on the portal. Swipe up for problem statements.",
                        mediaType = "IMAGE",
                        mediaBackgroundGrad = "GOLD",
                        timestamp = "1h ago",
                        isViewed = viewedStoryAuthors.contains("club_gdg")
                    ),
                    StoryItem(
                        id = "story_gdg_2",
                        authorId = "club_gdg",
                        authorName = "GDG on Campus IIIT Kalyani",
                        authorRole = "Technical Society",
                        mediaCaption = "Mentors from Google & top alumni joining for live code reviews!",
                        mediaType = "IMAGE",
                        mediaBackgroundGrad = "DARK",
                        timestamp = "45m ago",
                        isViewed = viewedStoryAuthors.contains("club_gdg")
                    )
                ),
                hasUnviewed = !viewedStoryAuthors.contains("club_gdg")
            ),
            CampusStoryGroup(
                authorId = "club_seal",
                authorName = "SEAL Robotics",
                authorRole = "Robotics Club",
                isOfficial = true,
                isClub = true,
                stories = listOf(
                    StoryItem(
                        id = "story_seal_1",
                        authorId = "club_seal",
                        authorName = "SEAL Robotics",
                        authorRole = "Technical Society",
                        mediaCaption = "Autonomous Rover obstacle detection test passed at Academic Block courtyard! 🤖",
                        mediaType = "VIDEO",
                        mediaBackgroundGrad = "EMERALD",
                        timestamp = "3h ago",
                        isViewed = viewedStoryAuthors.contains("club_seal")
                    )
                ),
                hasUnviewed = !viewedStoryAuthors.contains("club_seal")
            ),
            CampusStoryGroup(
                authorId = "club_spotlight",
                authorName = "Spotlight",
                authorRole = "Dramatics Club",
                isOfficial = true,
                isClub = true,
                stories = listOf(
                    StoryItem(
                        id = "story_spotlight_1",
                        authorId = "club_spotlight",
                        authorName = "Spotlight Dramatics",
                        authorRole = "Cultural Society",
                        mediaCaption = "Auditions for the Annual Street Play this Thursday at 5:30 PM! All batches welcome 🎭",
                        mediaType = "IMAGE",
                        mediaBackgroundGrad = "PURPLE",
                        timestamp = "5h ago",
                        isViewed = viewedStoryAuthors.contains("club_spotlight")
                    )
                ),
                hasUnviewed = !viewedStoryAuthors.contains("club_spotlight")
            ),
            CampusStoryGroup(
                authorId = "club_sports",
                authorName = "Sports Club",
                authorRole = "Sports Council",
                isOfficial = true,
                isClub = true,
                stories = listOf(
                    StoryItem(
                        id = "story_sports_1",
                        authorId = "club_sports",
                        authorName = "IIITK Sports Club",
                        authorRole = "Sports Council",
                        mediaCaption = "Inter-batch Badminton Championship semi-finals today at Kalyani Stadium Courts 🏸",
                        mediaType = "IMAGE",
                        mediaBackgroundGrad = "GOLD",
                        timestamp = "7h ago",
                        isViewed = viewedStoryAuthors.contains("club_sports")
                    )
                ),
                hasUnviewed = !viewedStoryAuthors.contains("club_sports")
            ),
            CampusStoryGroup(
                authorId = "admin_dean",
                authorName = "Academic Office",
                authorRole = "Institute Notice",
                isOfficial = true,
                isClub = false,
                stories = listOf(
                    StoryItem(
                        id = "story_admin_1",
                        authorId = "admin_dean",
                        authorName = "Office of Dean (Academics)",
                        authorRole = "Official Administration",
                        mediaCaption = "Notice: Mid-Semester Examination schedule published on portal. Check your course slots.",
                        mediaType = "IMAGE",
                        mediaBackgroundGrad = "DARK",
                        timestamp = "9h ago",
                        isViewed = viewedStoryAuthors.contains("admin_dean")
                    )
                ),
                hasUnviewed = !viewedStoryAuthors.contains("admin_dean")
            )
        )
    }

    private fun loadInitialPosts(): List<CampusPost> {
        val likedIds = prefs?.getStringSet("liked_posts", emptySet()) ?: emptySet()
        val savedIds = prefs?.getStringSet("saved_posts", emptySet()) ?: emptySet()
        val votedPolls = prefs?.getString("poll_votes", "") ?: ""
        // Format of votedPolls: "post_poll_1:opt_2,post_poll_2:opt_1"
        val voteMap = votedPolls.split(",")
            .filter { it.contains(":") }
            .associate {
                val parts = it.split(":")
                parts[0] to parts[1]
            }

        val basePosts = listOf(
            CampusPost(
                id = "post_seal_1",
                authorName = "SEAL Robotics IIITK",
                authorRole = "Verified Club · Robotics Society",
                isOfficial = true,
                isVerified = true,
                postType = PostType.PROJECT_SHOWCASE,
                title = "Autonomous Terrain Rover Prototype v2.4 Live Demo",
                content = "We have completed the outdoor trials for our autonomous rover prototype designed for the Inter-IIIT Tech Meet! Utilizing onboard LiDAR SLAM and ROS2 navigation stack, the rover successfully traversed the terrain near the Webel Academic Block without manual intervention.\n\nRecruiting 2nd & 3rd year CSE/ECE students for Computer Vision and Embedded Control. Comment below or reach out at seal@iiitkalyani.ac.in!",
                mediaUrls = listOf("https://images.unsplash.com/photo-1485827404703-89b55fcc595e?auto=format&fit=crop&w=1200&q=80"),
                tags = listOf("Robotics", "ROS2", "InterIIIT", "SEAL"),
                clubTag = "SEAL Robotics",
                timestamp = "2 hours ago",
                likesCount = 42,
                commentsCount = 8,
                comments = listOf(
                    PostComment(
                        id = "c1",
                        authorName = "Arjun Sen",
                        authorRole = "Student",
                        authorDepartment = "ECE '25",
                        content = "The LiDAR mapping precision looks super clean! What microcontroller are you using for the motor drive?",
                        timestamp = "1h ago",
                        likes = 3
                    ),
                    PostComment(
                        id = "c2",
                        authorName = "SEAL Robotics IIITK",
                        authorRole = "Club Lead",
                        authorDepartment = "SEAL Team",
                        content = "STM32F407 connected via CAN bus to the onboard Jetson Nano companion computer!",
                        timestamp = "45m ago",
                        likes = 5
                    )
                ),
                location = "Academic Block, Webel IT Park"
            ),
            CampusPost(
                id = "post_gdg_1",
                authorName = "GDG on Campus IIIT Kalyani",
                authorRole = "Verified Club · Google Developer Groups",
                isOfficial = true,
                isVerified = true,
                postType = PostType.EVENT_ANNOUNCEMENT,
                title = "DevSprint 2026: 36-Hour National Campus Hackathon",
                content = "Mark your calendars! GDG IIIT Kalyani is hosting DevSprint 2026 on Nov 4-5. Tracks include AI & Systems, FinTech, Open Campus Infra, and HealthTech. Cash pool of ₹1,50,000 + cloud credits for all participants!\n\nOpen to all students. Registration link live on the portal.",
                mediaUrls = listOf("https://images.unsplash.com/photo-1517245386807-bb43f82c33c4?auto=format&fit=crop&w=1200&q=80"),
                tags = listOf("Hackathon", "GDG", "DevSprint", "Code"),
                clubTag = "GDG on Campus",
                timestamp = "4 hours ago",
                likesCount = 89,
                commentsCount = 14,
                comments = listOf(
                    PostComment(
                        id = "c3",
                        authorName = "Rhea Mukherjee",
                        authorRole = "Student",
                        authorDepartment = "CSE '27",
                        content = "Can teams have members across different years?",
                        timestamp = "3h ago",
                        likes = 2
                    )
                )
            ),
            CampusPost(
                id = "post_poll_1",
                authorName = "IIITK Student Gymkhana",
                authorRole = "Official Council",
                isOfficial = true,
                isVerified = true,
                postType = PostType.POLL,
                title = "Campus Coding Workshop Timing Poll",
                content = "Gymkhana technical committee is organizing weekly peer-learning sessions on System Design & Data Structures. When works best for the majority of batches?",
                pollData = PollData(
                    id = "poll_time_1",
                    question = "Preferred day & timing for campus workshops:",
                    options = listOf(
                        PollOption("opt_1", "Saturday 4:00 PM - 6:00 PM", 64),
                        PollOption("opt_2", "Sunday 11:00 AM - 1:00 PM", 48),
                        PollOption("opt_3", "Friday 6:00 PM - 8:00 PM", 29),
                        PollOption("opt_4", "Wednesday Evening 5:30 PM", 12)
                    ),
                    totalVotes = 153,
                    userVotedOptionId = voteMap["post_poll_1"]
                ),
                tags = listOf("Poll", "Academics", "PeerLearning"),
                timestamp = "6 hours ago",
                likesCount = 56,
                commentsCount = 11
            ),
            CampusPost(
                id = "post_faculty_1",
                authorName = "Dr. Debasish Bera",
                authorRole = "Associate Professor · CSE Dept",
                isOfficial = true,
                isVerified = true,
                postType = PostType.OPPORTUNITY_RESEARCH,
                title = "Research Assistantship: Distributed Consensus & Zero-Knowledge Proofs",
                content = "Applications are invited from 3rd and 4th year B.Tech students with good programming background in Rust/C++ and understanding of distributed systems for an ongoing funded research initiative. Interested candidates may send their CV with academic transcript to debasish@iiitkalyani.ac.in with subject '[RA Application - 2026]'.",
                tags = listOf("Research", "DistributedSystems", "Cryptography", "CSE"),
                timestamp = "Yesterday",
                likesCount = 68,
                commentsCount = 9,
                location = "Faculty Cabin 304, Academic Block"
            ),
            CampusPost(
                id = "post_student_achieve",
                authorName = "Barnik Basu",
                authorRole = "Student · CSE 3rd Year",
                isOfficial = false,
                isVerified = true,
                postType = PostType.ACHIEVEMENT,
                title = "Runner-Up at Smart Bengal Hackathon 2026! 🥈",
                content = "Excited to share that our team (Barnik Basu, Debopam Saha, and Anirban) secured 2nd place among 120+ teams across West Bengal for building Sarthi Edge — a mesh-networked campus emergency dispatch tool that works even during mobile network blackouts!\n\nBig thanks to our faculty mentors at IIIT Kalyani for their continuous guidance.",
                mediaUrls = listOf("https://images.unsplash.com/photo-1522071820081-009f0129c71c?auto=format&fit=crop&w=1200&q=80"),
                tags = listOf("Hackathon", "Achievement", "IIITK", "Teamwork"),
                timestamp = "Yesterday",
                likesCount = 112,
                commentsCount = 22,
                isAuthor = true
            ),
            CampusPost(
                id = "post_lost_found",
                authorName = "Sneha Roy",
                authorRole = "Student · ECE 2nd Year",
                isOfficial = false,
                isVerified = true,
                postType = PostType.LOST_AND_FOUND,
                title = "Found: Casio FX-991EX Scientific Calculator in Room 104",
                content = "Found a black Casio ClassWiz fx-991EX calculator on the second bench of Lecture Room 104 after the CS301 (Operating Systems) lecture today at 1:15 PM. Has an orange sticker on the backside. Please DM me or collect from the student desk with proof.",
                tags = listOf("LostAndFound", "Calculator", "Room104"),
                timestamp = "1 day ago",
                likesCount = 18,
                commentsCount = 4
            ),
            CampusPost(
                id = "post_spotlight_1",
                authorName = "Spotlight Dramatics Club",
                authorRole = "Verified Club · Cultural Society",
                isOfficial = true,
                isVerified = true,
                postType = PostType.CLUB_UPDATE,
                title = "Annual Street Play Rehearsals & Open Mic Night",
                content = "Spotlight is hosting an Open Mic & Audition mixer this Friday at 6:00 PM at the Campus Amphitheatre space. Whether you act, write poetry, direct, or do stage lighting, we have a spot for you! Light refreshments provided.",
                mediaUrls = listOf("https://images.unsplash.com/photo-1469488865564-c2de10f69f96?auto=format&fit=crop&w=1200&q=80"),
                tags = listOf("Drama", "Culture", "Spotlight", "CampusLife"),
                clubTag = "Spotlight",
                timestamp = "2 days ago",
                likesCount = 74,
                commentsCount = 16
            ),
            CampusPost(
                id = "post_freescape_1",
                authorName = "FreeScape Open Source Club",
                authorRole = "Verified Club · FOSS Society",
                isOfficial = true,
                isVerified = true,
                postType = PostType.ACADEMIC_RESOURCE_SHARING,
                title = "Linux Kernel & Git Internals Hands-On Workshop Notes",
                content = "Slide decks, lab scripts, and container recipes from yesterday's Git plumbing session are now uploaded to the student repository. Covers reflog recovery, rebase workflows, and writing custom git hooks. Link available in the Academic Resources tab.",
                tags = listOf("OpenSource", "Linux", "Git", "FreeScape"),
                clubTag = "FreeScape",
                timestamp = "3 days ago",
                likesCount = 53,
                commentsCount = 6
            )
        )

        return basePosts.map { post ->
            val isLiked = likedIds.contains(post.id)
            val isSaved = savedIds.contains(post.id)
            val adjustedLikes = if (isLiked) post.likesCount + 1 else post.likesCount
            post.copy(
                isLiked = isLiked,
                isSaved = isSaved,
                likesCount = adjustedLikes
            )
        }
    }

    private fun loadInitialConversations(): List<ConversationThread> {
        return listOf(
            ConversationThread(
                id = "conv_gdg",
                participantName = "GDG DevSprint Core",
                participantSubtitle = "Club Group · 18 members",
                lastMessage = "Problem statements for the AI track have been finalized by mentors.",
                lastTimestamp = "10:45 AM",
                unreadCount = 2,
                isClubChannel = true,
                messages = listOf(
                    DirectMessage("m1", "gdg_lead", "GDG Lead", "Welcome everyone to the DevSprint organizing committee!", "09:30 AM"),
                    DirectMessage("m2", "gdg_mentor", "DevSprint Mentor", "We have verified the cloud sandbox keys for all participants.", "10:15 AM"),
                    DirectMessage("m3", "gdg_lead", "GDG Lead", "Problem statements for the AI track have been finalized by mentors.", "10:45 AM")
                )
            ),
            ConversationThread(
                id = "conv_seal",
                participantName = "SEAL Robotics Lab",
                participantSubtitle = "Club Group · 12 members",
                lastMessage = "LiDAR test results uploaded to drive. Check ground clearance.",
                lastTimestamp = "Yesterday",
                unreadCount = 0,
                isClubChannel = true,
                messages = listOf(
                    DirectMessage("m4", "seal_tech", "Rover Lead", "Obstacle avoidance firmware update ready.", "Yesterday"),
                    DirectMessage("m5", "user_me", "Barnik Basu", "I will run the calibration in Lab 2 after classes.", "Yesterday", isMe = true)
                )
            ),
            ConversationThread(
                id = "conv_arjun",
                participantName = "Arjun Sen",
                participantSubtitle = "ECE Batch '25 · Lab Partner",
                lastMessage = "Hey, are you joining the Embedded Systems lab early today?",
                lastTimestamp = "Yesterday",
                unreadCount = 1,
                isClubChannel = false,
                messages = listOf(
                    DirectMessage("m6", "arjun_sen", "Arjun Sen", "Hey, are you joining the Embedded Systems lab early today?", "Yesterday")
                )
            ),
            ConversationThread(
                id = "conv_rhea",
                participantName = "Rhea Mukherjee",
                participantSubtitle = "CSE Batch '27 · Hackathon Team",
                lastMessage = "Sounds good! Let's submit the project proposal before 5 PM.",
                lastTimestamp = "2 days ago",
                unreadCount = 0,
                isClubChannel = false,
                messages = listOf(
                    DirectMessage("m7", "rhea_m", "Rhea Mukherjee", "Did you review the system architecture diagram?", "2 days ago"),
                    DirectMessage("m8", "user_me", "Barnik Basu", "Yes, looks solid. Added the caching layer specs.", "2 days ago", isMe = true),
                    DirectMessage("m9", "rhea_m", "Rhea Mukherjee", "Sounds good! Let's submit the project proposal before 5 PM.", "2 days ago")
                )
            )
        )
    }

    override fun togglePostLike(postId: String) {
        _posts.update { list ->
            val updated = list.map { post ->
                if (post.id == postId) {
                    val newIsLiked = !post.isLiked
                    val newLikes = if (newIsLiked) post.likesCount + 1 else (post.likesCount - 1).coerceAtLeast(0)
                    post.copy(isLiked = newIsLiked, likesCount = newLikes)
                } else post
            }
            val likedSet = updated.filter { it.isLiked }.map { it.id }.toSet()
            prefs?.edit()?.putStringSet("liked_posts", likedSet)?.apply()
            updated
        }
    }

    override fun togglePostBookmark(postId: String) {
        _posts.update { list ->
            val updated = list.map { post ->
                if (post.id == postId) {
                    post.copy(isSaved = !post.isSaved)
                } else post
            }
            val savedSet = updated.filter { it.isSaved }.map { it.id }.toSet()
            prefs?.edit()?.putStringSet("saved_posts", savedSet)?.apply()
            updated
        }
    }

    override fun votePoll(postId: String, optionId: String) {
        _posts.update { list ->
            val updated = list.map { post ->
                if (post.id == postId && post.pollData != null) {
                    val currentPoll = post.pollData
                    val previousVote = currentPoll.userVotedOptionId

                    if (previousVote == optionId) {
                        // Already voted this option, keep as is
                        post
                    } else {
                        val updatedOptions = currentPoll.options.map { opt ->
                            when (opt.id) {
                                optionId -> opt.copy(votes = opt.votes + 1)
                                previousVote -> opt.copy(votes = (opt.votes - 1).coerceAtLeast(0))
                                else -> opt
                            }
                        }
                        val newTotalVotes = if (previousVote == null) currentPoll.totalVotes + 1 else currentPoll.totalVotes
                        val newPoll = currentPoll.copy(
                            options = updatedOptions,
                            totalVotes = newTotalVotes,
                            userVotedOptionId = optionId
                        )
                        post.copy(pollData = newPoll)
                    }
                } else post
            }

            // Persist poll vote
            val currentVotedStr = prefs?.getString("poll_votes", "") ?: ""
            val voteMap = currentVotedStr.split(",")
                .filter { it.contains(":") }
                .associate {
                    val parts = it.split(":")
                    parts[0] to parts[1]
                }.toMutableMap()
            voteMap[postId] = optionId
            val serialized = voteMap.entries.joinToString(",") { "${it.key}:${it.value}" }
            prefs?.edit()?.putString("poll_votes", serialized)?.apply()

            updated
        }
    }

    override fun addPostComment(postId: String, commentText: String) {
        if (commentText.isBlank()) return
        val newComment = PostComment(
            id = "comment_${System.currentTimeMillis()}",
            authorName = "Barnik Basu",
            authorRole = "Student",
            authorDepartment = "CSE '26",
            content = commentText.trim(),
            timestamp = "Just now"
        )

        _posts.update { list ->
            list.map { post ->
                if (post.id == postId) {
                    post.copy(
                        comments = listOf(newComment) + post.comments,
                        commentsCount = post.commentsCount + 1
                    )
                } else post
            }
        }
    }

    override fun createPost(post: CampusPost) {
        _posts.update { listOf(post) + it }
    }

    override fun deletePost(postId: String) {
        _posts.update { list -> list.filter { it.id != postId } }
    }

    override fun markStoryGroupViewed(authorId: String) {
        _stories.update { list ->
            list.map { group ->
                if (group.authorId == authorId) {
                    group.copy(
                        hasUnviewed = false,
                        stories = group.stories.map { it.copy(isViewed = true) }
                    )
                } else group
            }
        }
        val currentSet = prefs?.getStringSet("viewed_stories", emptySet())?.toMutableSet() ?: mutableSetOf()
        currentSet.add(authorId)
        prefs?.edit()?.putStringSet("viewed_stories", currentSet)?.apply()
    }

    override fun sendDirectMessage(conversationId: String, text: String) {
        if (text.isBlank()) return
        val newMsg = DirectMessage(
            id = "msg_${System.currentTimeMillis()}",
            senderId = "user_me",
            senderName = "Barnik Basu",
            text = text.trim(),
            timestamp = "Just now",
            isMe = true
        )

        _conversations.update { list ->
            list.map { conv ->
                if (conv.id == conversationId) {
                    conv.copy(
                        lastMessage = text.trim(),
                        lastTimestamp = "Just now",
                        messages = conv.messages + newMsg
                    )
                } else conv
            }
        }
    }
}
