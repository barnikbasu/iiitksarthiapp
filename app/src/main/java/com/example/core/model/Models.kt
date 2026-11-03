package com.example.core.model

enum class UserRole {
    GUEST,
    STUDENT,
    FACULTY,
    STAFF
}

enum class SourceType {
    OFFICIAL,
    AUTHORIZED,
    SARTHI_VERIFIED,
    COMMUNITY,
    USER
}

data class SourceMetadata(
    val sourceType: SourceType = SourceType.OFFICIAL,
    val sourceName: String = "IIIT Kalyani",
    val lastUpdated: String = "Today, 08:30 AM"
)

data class UserProfile(
    val id: String = "user_001",
    val name: String = "Barnik Basu",
    val role: UserRole = UserRole.STUDENT,
    val studentId: String = "CSE/25028/1428",
    val department: String = "Computer Science and Engineering",
    val semester: String = "6th Semester (Spring 2026)",
    val dob: String = "14 Oct 2004",
    val bloodGroup: String = "O+",
    val emergencyContact: String = "033-2582-8562",
    val email: String = "barnik.cse22@iiitkalyani.ac.in",
    val interests: List<String> = listOf("Distributed Systems", "Robotics", "Web3", "Hackathons"),
    val verified: Boolean = true,
    val cgpa: Float = 8.84f,
    val sgpa: Float = 9.10f,
    val attendancePct: Float = 84.8f,
    val totalCreditsEarned: Int = 84,
    val activeBacklogs: Int = 0
)

data class ClassSession(
    val id: String,
    val courseCode: String,
    val courseName: String,
    val room: String,
    val faculty: String,
    val startTime: String,
    val endTime: String,
    val dayOfWeek: String,
    val status: String // "UPCOMING", "NOW", "COMPLETED"
)

data class AttendanceRecord(
    val id: String,
    val courseCode: String,
    val courseName: String,
    val attended: Int,
    val total: Int,
    val threshold: Int = 75
) {
    val percentage: Float
        get() = if (total > 0) (attended.toFloat() / total) * 100f else 0f

    val isSafe: Boolean
        get() = percentage >= threshold

    val safeToBunk: Int
        get() {
            // max b = floor(attended / 0.75 - total)
            val maxAllowedTotal = (attended / 0.75f).toInt()
            val diff = maxAllowedTotal - total
            return if (diff > 0) diff else 0
        }

    val neededToReachThreshold: Int
        get() {
            if (percentage >= threshold) return 0
            // (attended + x) / (total + x) >= 0.75 => x >= (0.75*total - attended) / 0.25
            val needed = Math.ceil(((0.75 * total) - attended) / 0.25).toInt()
            return if (needed > 0) needed else 0
        }
}

data class CourseGrade(
    val id: String,
    val semester: String,
    val courseCode: String,
    val courseName: String,
    val credits: Int,
    val grade: String,
    val gradePoint: Float
)

data class SemesterGradeSummary(
    val semester: String,
    val sgpa: Float,
    val ygpa: Float,
    val credits: Int
)

data class AcademicSummary(
    val cgpa: Float = 6.41f,
    val currentSgpa: Float = 8.10f,
    val totalCreditsCompleted: Int = 114,
    val semesters: List<SemesterGradeSummary> = emptyList(),
    val source: SourceMetadata = SourceMetadata(SourceType.AUTHORIZED, "Sarthi Academic Sync", "Synced 28 Sep 2026")
)

data class CampusTask(
    val id: String,
    val title: String,
    val dueDate: String,
    val course: String,
    val priority: String, // "HIGH", "MEDIUM", "LOW"
    val completed: Boolean = false,
    val category: String // "ASSIGNMENT", "EXAM", "FEES", "PROJECT"
)

data class CampusNotice(
    val id: String,
    val title: String,
    val summary: String,
    val content: String,
    val category: String, // "ACADEMIC", "EXAMINATION", "ADMINISTRATION", "HOSTEL", "PLACEMENT", "CLUBS", "EMERGENCY"
    val publishedDate: String,
    val sourceName: String = "Registrar Office, IIITK",
    val isImportant: Boolean = false,
    val isRead: Boolean = false,
    val isSaved: Boolean = false
)

data class CampusEvent(
    val id: String,
    val title: String,
    val tagline: String,
    val organizer: String,
    val category: String, // "FEST", "HACKATHON", "WORKSHOP", "CULTURAL", "SPORTS"
    val date: String,
    val time: String,
    val venue: String,
    val description: String,
    val isRegistered: Boolean = false,
    val isSaved: Boolean = false,
    val tags: List<String> = emptyList()
)

data class Club(
    val id: String,
    val name: String,
    val category: String,
    val lead: String,
    val description: String,
    val interests: List<String>,
    val upcomingActivities: List<String>,
    val contactEmail: String,
    val official: Boolean = true
)

data class FacultyMember(
    val id: String,
    val name: String,
    val designation: String,
    val department: String,
    val email: String,
    val researchInterests: List<String>,
    val cabin: String,
    val officialUrl: String = "http://iiitkalyani.ac.in"
)

data class Opportunity(
    val id: String,
    val title: String,
    val organization: String,
    val category: String, // "INTERNSHIP", "HACKATHON", "RESEARCH", "SCHOLARSHIP", "FELLOWSHIP"
    val deadline: String,
    val eligibility: String,
    val description: String,
    val sourceUrl: String = "https://iiitkalyani.ac.in",
    val isSaved: Boolean = false
)

data class MarketplaceItem(
    val id: String,
    val title: String,
    val price: String,
    val category: String, // "BOOKS", "CYCLES", "ELECTRONICS", "HOSTEL", "ESSENTIALS"
    val condition: String,
    val sellerName: String,
    val sellerContact: String,
    val postedDate: String
)

data class CampusLocation(
    val id: String,
    val name: String,
    val type: String, // "ACADEMIC", "LAB", "HOSTEL", "MESS", "MEDICAL", "LIBRARY", "SPORTS"
    val landmark: String,
    val description: String,
    val timings: String
)

data class EmergencyContact(
    val title: String,
    val number: String,
    val subtitle: String,
    val type: String // "MEDICAL", "OFFICE", "ANTI_RAGGING", "SECURITY"
)

data class HelpdeskMessage(
    val id: String,
    val text: String,
    val isFromUser: Boolean,
    val timestamp: String,
    val sourceTitle: String? = null,
    val sourceNote: String? = null
)
