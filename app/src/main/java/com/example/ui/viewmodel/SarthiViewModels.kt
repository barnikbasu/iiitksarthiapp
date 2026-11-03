package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.core.model.AcademicSummary
import com.example.core.model.AttendanceRecord
import com.example.core.model.CampusEvent
import com.example.core.model.CampusNotice
import com.example.core.model.CampusNotification
import com.example.core.model.CampusPost
import com.example.core.model.CampusStoryGroup
import com.example.core.model.CampusTask
import com.example.core.model.ClassSession
import com.example.core.model.ConversationThread
import com.example.core.model.MarketplaceItem
import com.example.core.model.UserProfile
import com.example.core.model.UserRole
import com.example.data.repository.CampusRepository
import com.example.data.repository.HelpdeskRepository
import com.example.data.repository.SocialRepository
import com.example.data.repository.UserRepository
import kotlinx.coroutines.flow.StateFlow

class SarthiAppViewModel(
    application: Application
) : AndroidViewModel(application) {

    val campusRepository: CampusRepository = CampusRepository(application)
    val userRepository: UserRepository = UserRepository(application)
    val helpdeskRepository: HelpdeskRepository = HelpdeskRepository()
    val socialRepository: SocialRepository = SocialRepository(application)

    val isLoggedIn: StateFlow<Boolean> = userRepository.isLoggedIn
    val userProfile: StateFlow<UserProfile> = userRepository.userProfile
    val academicSummary: StateFlow<AcademicSummary> = campusRepository.academicSummary
    val events: StateFlow<List<CampusEvent>> = campusRepository.events
    val notices: StateFlow<List<CampusNotice>> = campusRepository.notices
    val tasks: StateFlow<List<CampusTask>> = campusRepository.tasks
    val schedule: StateFlow<List<ClassSession>> = campusRepository.schedule
    val attendance: StateFlow<List<AttendanceRecord>> = campusRepository.attendance
    val marketplace: StateFlow<List<MarketplaceItem>> = campusRepository.marketplace
    val notifications: StateFlow<List<CampusNotification>> = campusRepository.notifications

    // Social & Campus Feeds
    val stories: StateFlow<List<CampusStoryGroup>> = socialRepository.stories
    val posts: StateFlow<List<CampusPost>> = socialRepository.posts
    val conversations: StateFlow<List<ConversationThread>> = socialRepository.conversations

    fun login(
        studentId: String,
        name: String,
        department: String,
        semester: String,
        cgpa: Float,
        sgpa: Float,
        attendancePct: Float,
        email: String,
        bloodGroup: String = "O+",
        emergencyContact: String = "033-2582-8562"
    ) {
        userRepository.login(studentId, name, department, semester, cgpa, sgpa, attendancePct, email, bloodGroup, emergencyContact)
        campusRepository.syncStudentAcademicProfile(cgpa, sgpa, attendancePct)
    }

    fun loginAsGuest() {
        userRepository.loginAsGuest()
    }

    fun logout() {
        userRepository.logout()
    }

    fun toggleTask(taskId: String) {
        campusRepository.toggleTask(taskId)
    }

    fun markNotificationAsRead(id: String) {
        campusRepository.markNotificationAsRead(id)
    }

    fun markAllNotificationsRead() {
        campusRepository.markAllNotificationsRead()
    }

    fun toggleEventRegistration(eventId: String) {
        campusRepository.toggleEventRegistration(eventId)
    }

    fun toggleEventSave(eventId: String) {
        campusRepository.toggleEventSave(eventId)
    }

    fun toggleNoticeSave(noticeId: String) {
        campusRepository.toggleNoticeSave(noticeId)
    }

    fun addMarketplaceItem(item: MarketplaceItem) {
        campusRepository.addMarketplaceItem(item)
    }

    fun switchRole(newRole: UserRole) {
        userRepository.switchRole(newRole)
    }

    fun updateProfile(
        name: String,
        department: String,
        bloodGroup: String,
        emergencyContact: String
    ) {
        userRepository.updateProfile(name, department, bloodGroup, emergencyContact)
    }

    fun submitHelpdeskInquiry(query: String) {
        helpdeskRepository.submitInquiry(query)
    }

    // Social actions
    fun togglePostLike(postId: String) {
        socialRepository.togglePostLike(postId)
    }

    fun togglePostBookmark(postId: String) {
        socialRepository.togglePostBookmark(postId)
    }

    fun votePoll(postId: String, optionId: String) {
        socialRepository.votePoll(postId, optionId)
    }

    fun addPostComment(postId: String, commentText: String) {
        socialRepository.addPostComment(postId, commentText)
    }

    fun createPost(post: CampusPost) {
        socialRepository.createPost(post)
    }

    fun deletePost(postId: String) {
        socialRepository.deletePost(postId)
    }

    fun markStoryGroupViewed(authorId: String) {
        socialRepository.markStoryGroupViewed(authorId)
    }

    fun sendDirectMessage(conversationId: String, text: String) {
        socialRepository.sendDirectMessage(conversationId, text)
    }
}
