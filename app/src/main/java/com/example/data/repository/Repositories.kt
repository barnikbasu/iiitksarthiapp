package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.core.model.AcademicSummary
import com.example.core.model.AttendanceRecord
import com.example.core.model.CampusEvent
import com.example.core.model.CampusLocation
import com.example.core.model.CampusNotice
import com.example.core.model.CampusNotification
import com.example.core.model.CampusTask
import com.example.core.model.ClassSession
import com.example.core.model.Club
import com.example.core.model.EmergencyContact
import com.example.core.model.FacultyMember
import com.example.core.model.MarketplaceItem
import com.example.core.model.Opportunity
import com.example.core.model.UserProfile
import com.example.core.model.UserRole
import com.example.data.CampusData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Result sealed class for production error handling and future API integration.
 */
sealed class CampusResult<out T> {
    data class Success<T>(val data: T) : CampusResult<T>()
    data class Error(val exception: Throwable? = null, val message: String = "An unexpected error occurred") : CampusResult<Nothing>()
    data object Loading : CampusResult<Nothing>()
}

interface ICampusRepository {
    val events: StateFlow<List<CampusEvent>>
    val notices: StateFlow<List<CampusNotice>>
    val tasks: StateFlow<List<CampusTask>>
    val schedule: StateFlow<List<ClassSession>>
    val attendance: StateFlow<List<AttendanceRecord>>
    val marketplace: StateFlow<List<MarketplaceItem>>
    val notifications: StateFlow<List<CampusNotification>>

    fun toggleTask(taskId: String)
    fun toggleEventRegistration(eventId: String)
    fun toggleEventSave(eventId: String)
    fun toggleNoticeSave(noticeId: String)
    fun addMarketplaceItem(item: MarketplaceItem)
    fun markNotificationAsRead(id: String)
    fun markAllNotificationsRead()
}

class CampusRepository(private val context: Context? = null) : ICampusRepository {
    private val prefs: SharedPreferences? = context?.getSharedPreferences("sarthi_campus_prefs", Context.MODE_PRIVATE)

    private val _events = MutableStateFlow(loadInitialEvents())
    override val events: StateFlow<List<CampusEvent>> = _events.asStateFlow()

    private val _notices = MutableStateFlow(loadInitialNotices())
    override val notices: StateFlow<List<CampusNotice>> = _notices.asStateFlow()

    private val _tasks = MutableStateFlow(loadInitialTasks())
    override val tasks: StateFlow<List<CampusTask>> = _tasks.asStateFlow()

    private val _schedule = MutableStateFlow(CampusData.scheduleToday)
    override val schedule: StateFlow<List<ClassSession>> = _schedule.asStateFlow()

    private val _attendance = MutableStateFlow(CampusData.attendanceRecords)
    override val attendance: StateFlow<List<AttendanceRecord>> = _attendance.asStateFlow()

    private val _marketplace = MutableStateFlow(loadInitialMarketplace())
    override val marketplace: StateFlow<List<MarketplaceItem>> = _marketplace.asStateFlow()

    private val _notifications = MutableStateFlow(loadInitialNotifications())
    override val notifications: StateFlow<List<CampusNotification>> = _notifications.asStateFlow()

    private val _academicSummary = MutableStateFlow(CampusData.academicSummary)
    val academicSummary: StateFlow<AcademicSummary> = _academicSummary.asStateFlow()

    fun syncStudentAcademicProfile(cgpa: Float, sgpa: Float, attendancePct: Float) {
        _academicSummary.update { current ->
            current.copy(
                cgpa = cgpa,
                currentSgpa = sgpa
            )
        }
    }

    val faculties: List<FacultyMember> = CampusData.facultyList
    val clubs: List<Club> = CampusData.clubsList
    val opportunities: List<Opportunity> = CampusData.opportunitiesList
    val locations: List<CampusLocation> = CampusData.campusLocations
    val emergencyContacts: List<EmergencyContact> = CampusData.emergencyContacts
    val resources: List<com.example.core.model.AcademicResource> = CampusData.academicResources

    private fun loadInitialEvents(): List<CampusEvent> {
        val savedIds = prefs?.getStringSet("saved_events", emptySet()) ?: emptySet()
        val registeredIds = prefs?.getStringSet("registered_events", emptySet()) ?: emptySet()

        return CampusData.eventsList.map { event ->
            event.copy(
                isSaved = savedIds.contains(event.id) || event.isSaved,
                isRegistered = registeredIds.contains(event.id) || event.isRegistered
            )
        }
    }

    private fun loadInitialNotices(): List<CampusNotice> {
        val savedIds = prefs?.getStringSet("saved_notices", emptySet()) ?: emptySet()
        return CampusData.noticesList.map { notice ->
            notice.copy(
                isSaved = savedIds.contains(notice.id) || notice.isSaved
            )
        }
    }

    private fun loadInitialTasks(): List<CampusTask> {
        val completedIds = prefs?.getStringSet("completed_tasks", emptySet()) ?: emptySet()
        return CampusData.tasksList.map { task ->
            task.copy(
                completed = completedIds.contains(task.id) || task.completed
            )
        }
    }

    private fun loadInitialNotifications(): List<CampusNotification> {
        val readIds = prefs?.getStringSet("read_notifications", emptySet()) ?: emptySet()
        return CampusData.notificationsList.map { notif ->
            notif.copy(
                isRead = readIds.contains(notif.id) || notif.isRead
            )
        }
    }

    private fun loadInitialMarketplace(): List<MarketplaceItem> {
        // Return base marketplace items
        return CampusData.marketplaceList
    }

    override fun toggleTask(taskId: String) {
        _tasks.update { list ->
            val updated = list.map { if (it.id == taskId) it.copy(completed = !it.completed) else it }
            val completedSet = updated.filter { it.completed }.map { it.id }.toSet()
            prefs?.edit()?.putStringSet("completed_tasks", completedSet)?.apply()
            updated
        }
    }

    override fun toggleEventRegistration(eventId: String) {
        _events.update { list ->
            val updated = list.map { if (it.id == eventId) it.copy(isRegistered = !it.isRegistered) else it }
            val regSet = updated.filter { it.isRegistered }.map { it.id }.toSet()
            prefs?.edit()?.putStringSet("registered_events", regSet)?.apply()
            updated
        }
    }

    override fun toggleEventSave(eventId: String) {
        _events.update { list ->
            val updated = list.map { if (it.id == eventId) it.copy(isSaved = !it.isSaved) else it }
            val savedSet = updated.filter { it.isSaved }.map { it.id }.toSet()
            prefs?.edit()?.putStringSet("saved_events", savedSet)?.apply()
            updated
        }
    }

    override fun toggleNoticeSave(noticeId: String) {
        _notices.update { list ->
            val updated = list.map { if (it.id == noticeId) it.copy(isSaved = !it.isSaved) else it }
            val savedSet = updated.filter { it.isSaved }.map { it.id }.toSet()
            prefs?.edit()?.putStringSet("saved_notices", savedSet)?.apply()
            updated
        }
    }

    override fun addMarketplaceItem(item: MarketplaceItem) {
        _marketplace.update { listOf(item) + it }
    }

    override fun markNotificationAsRead(id: String) {
        _notifications.update { list ->
            val updated = list.map { if (it.id == id) it.copy(isRead = true) else it }
            val readSet = updated.filter { it.isRead }.map { it.id }.toSet()
            prefs?.edit()?.putStringSet("read_notifications", readSet)?.apply()
            updated
        }
    }

    override fun markAllNotificationsRead() {
        _notifications.update { list ->
            val updated = list.map { it.copy(isRead = true) }
            val readSet = updated.map { it.id }.toSet()
            prefs?.edit()?.putStringSet("read_notifications", readSet)?.apply()
            updated
        }
    }
}

class UserRepository(private val context: Context? = null) {
    private val prefs: SharedPreferences? = context?.getSharedPreferences("sarthi_user_prefs", Context.MODE_PRIVATE)

    private val _isLoggedIn = MutableStateFlow(prefs?.getBoolean("is_logged_in", false) ?: false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _userProfile = MutableStateFlow(loadInitialProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private fun loadInitialProfile(): UserProfile {
        val roleStr = prefs?.getString("role", UserRole.STUDENT.name) ?: UserRole.STUDENT.name
        val role = try { UserRole.valueOf(roleStr) } catch (_: Exception) { UserRole.STUDENT }
        val name = prefs?.getString("name", "Barnik Basu") ?: "Barnik Basu"
        val department = prefs?.getString("department", "Computer Science and Engineering") ?: "Computer Science and Engineering"
        val bloodGroup = prefs?.getString("blood_group", "O+") ?: "O+"
        val emergencyContact = prefs?.getString("emergency_contact", "033-2582-8562") ?: "033-2582-8562"
        val studentId = prefs?.getString("student_id", "CSE/25028/1428") ?: "CSE/25028/1428"
        val semester = prefs?.getString("semester", "6th Semester (Spring 2026)") ?: "6th Semester (Spring 2026)"
        val email = prefs?.getString("email", "barnik.cse22@iiitkalyani.ac.in") ?: "barnik.cse22@iiitkalyani.ac.in"
        val cgpa = prefs?.getFloat("cgpa", 8.84f) ?: 8.84f
        val sgpa = prefs?.getFloat("sgpa", 9.10f) ?: 9.10f
        val attendancePct = prefs?.getFloat("attendance_pct", 84.8f) ?: 84.8f
        val verified = if (role == UserRole.STUDENT) true else false

        return UserProfile(
            id = "user_001",
            name = if (role == UserRole.STUDENT) name else "Guest User",
            role = role,
            studentId = if (role == UserRole.STUDENT) studentId else "GUEST/LOCAL",
            department = if (role == UserRole.STUDENT) department else "Campus Explorer",
            semester = if (role == UserRole.STUDENT) semester else "Prospective / Visitor",
            dob = "14 Oct 2004",
            bloodGroup = bloodGroup,
            emergencyContact = emergencyContact,
            email = if (role == UserRole.STUDENT) email else "guest@visitor.iiitkalyani.ac.in",
            interests = listOf("Distributed Systems", "Robotics", "Web3", "Hackathons"),
            verified = verified,
            cgpa = cgpa,
            sgpa = sgpa,
            attendancePct = attendancePct
        )
    }

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
        prefs?.edit()
            ?.putBoolean("is_logged_in", true)
            ?.putString("role", UserRole.STUDENT.name)
            ?.putString("student_id", studentId)
            ?.putString("name", name)
            ?.putString("department", department)
            ?.putString("semester", semester)
            ?.putFloat("cgpa", cgpa)
            ?.putFloat("sgpa", sgpa)
            ?.putFloat("attendance_pct", attendancePct)
            ?.putString("email", email)
            ?.putString("blood_group", bloodGroup)
            ?.putString("emergency_contact", emergencyContact)
            ?.apply()

        _userProfile.update {
            it.copy(
                id = "user_${studentId.hashCode()}",
                name = name,
                role = UserRole.STUDENT,
                studentId = studentId,
                department = department,
                semester = semester,
                email = email,
                bloodGroup = bloodGroup,
                emergencyContact = emergencyContact,
                cgpa = cgpa,
                sgpa = sgpa,
                attendancePct = attendancePct,
                verified = true
            )
        }
        _isLoggedIn.value = true
    }

    fun loginAsGuest() {
        prefs?.edit()
            ?.putBoolean("is_logged_in", true)
            ?.putString("role", UserRole.GUEST.name)
            ?.apply()

        _userProfile.update {
            it.copy(
                id = "guest_001",
                name = "Guest Visitor",
                role = UserRole.GUEST,
                studentId = "GUEST/VISITOR",
                department = "Campus Explorer",
                semester = "Prospective / Visitor",
                email = "guest@visitor.iiitkalyani.ac.in",
                verified = false
            )
        }
        _isLoggedIn.value = true
    }

    fun logout() {
        prefs?.edit()?.putBoolean("is_logged_in", false)?.apply()
        _isLoggedIn.value = false
    }

    fun switchRole(newRole: UserRole) {
        prefs?.edit()?.putString("role", newRole.name)?.apply()

        _userProfile.update { current ->
            if (newRole == UserRole.GUEST) {
                current.copy(
                    role = UserRole.GUEST,
                    name = "Guest User",
                    studentId = "GUEST/LOCAL",
                    department = "Campus Explorer",
                    semester = "Prospective / Visitor",
                    verified = false
                )
            } else {
                val savedName = prefs?.getString("name", "Barnik Basu") ?: "Barnik Basu"
                val savedDept = prefs?.getString("department", "Computer Science and Engineering") ?: "Computer Science and Engineering"
                val savedBlood = prefs?.getString("blood_group", "O+") ?: "O+"
                val savedEmerg = prefs?.getString("emergency_contact", "033-2582-8562") ?: "033-2582-8562"
                val savedId = prefs?.getString("student_id", "CSE/25028/1428") ?: "CSE/25028/1428"
                current.copy(
                    role = UserRole.STUDENT,
                    name = savedName,
                    studentId = savedId,
                    department = savedDept,
                    semester = "6th Semester (Spring 2026)",
                    bloodGroup = savedBlood,
                    emergencyContact = savedEmerg,
                    verified = true
                )
            }
        }
    }

    fun updateProfile(
        name: String,
        department: String,
        bloodGroup: String,
        emergencyContact: String
    ) {
        val sanitizedName = name.trim().ifBlank { "Barnik Basu" }
        val sanitizedDept = department.trim().ifBlank { "Computer Science and Engineering" }
        val sanitizedBlood = bloodGroup.trim().ifBlank { "O+" }
        val sanitizedEmerg = emergencyContact.trim().ifBlank { "033-2582-8562" }

        prefs?.edit()
            ?.putString("name", sanitizedName)
            ?.putString("department", sanitizedDept)
            ?.putString("blood_group", sanitizedBlood)
            ?.putString("emergency_contact", sanitizedEmerg)
            ?.apply()

        _userProfile.update {
            it.copy(
                name = sanitizedName,
                department = sanitizedDept,
                bloodGroup = sanitizedBlood,
                emergencyContact = sanitizedEmerg
            )
        }
    }
}
