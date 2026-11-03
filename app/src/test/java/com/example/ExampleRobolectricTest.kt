package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.core.model.AttendanceRecord
import com.example.data.CampusData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("IIITK Sarthi", appName)
    }

    @Test
    fun `verify definitive 16 faculty members and director leadership`() {
        assertEquals(16, CampusData.facultyList.size)
        assertTrue(CampusData.facultyList.any { it.name == "Dr. Debasish Bera" })
        assertTrue(CampusData.facultyList.any { it.name == "Dr. Amit Ranjan Azad" })
        assertTrue(CampusData.facultyList.any { it.name == "Dr. SK Hafizul Islam" })

        assertEquals("Prof. Suman Chakraborty", CampusData.DIRECTOR_NAME)
        assertTrue(
            CampusData.DIRECTOR_STATEMENT.contains("assumed the additional charge of Director, IIIT Kalyani")
        )
    }

    @Test
    fun `verify definitive 11 clubs list`() {
        assertEquals(11, CampusData.clubsList.size)
        val clubNames = CampusData.clubsList.map { it.name }
        assertTrue(clubNames.contains("Spotlight"))
        assertTrue(clubNames.contains("Sports Club"))
        assertTrue(clubNames.contains("SMC"))
        assertTrue(clubNames.contains("PiXeL"))
        assertTrue(clubNames.contains("FreeScape"))
        assertTrue(clubNames.contains("SEAL Robotics"))
        assertTrue(clubNames.contains("Udaan"))
        assertTrue(clubNames.contains("Groovz"))
        assertTrue(clubNames.contains("IEEE Student Branch IIIT Kalyani"))
        assertTrue(clubNames.any { it.contains("GDG on Campus") })
        assertTrue(clubNames.contains("E Sports"))

        // Ensure old/invented clubs are NOT present
        assertFalse(clubNames.contains("Tech Club"))
        assertFalse(clubNames.contains("CodeCubes"))
        assertFalse(clubNames.contains("Symphony"))
    }

    @Test
    fun `verify emergency contact phone numbers`() {
        val contacts = CampusData.emergencyContacts
        val medical = contacts.first { it.type == "MEDICAL" }
        assertEquals("033-2582-8562", medical.number)

        val office = contacts.first { it.type == "OFFICE" }
        assertEquals("033-2582-5003", office.number)

        val antiRagging = contacts.first { it.type == "ANTI_RAGGING" }
        assertEquals("1800-180-5522", antiRagging.number)
    }

    @Test
    fun `verify attendance 75 percent threshold calculation logic`() {
        val recordSafe = AttendanceRecord("1", "CS301", "DSA", attended = 28, total = 32, threshold = 75)
        assertTrue(recordSafe.isSafe)
        assertTrue(recordSafe.safeToBunk >= 2)
        assertEquals(0, recordSafe.neededToReachThreshold)

        val recordRisk = AttendanceRecord("2", "MA301", "Math", attended = 18, total = 26, threshold = 75)
        assertFalse(recordRisk.isSafe)
        assertEquals(0, recordRisk.safeToBunk)
        assertTrue(recordRisk.neededToReachThreshold >= 2)
    }

    @Test
    fun `verify campus notifications and official portals`() {
        assertTrue(CampusData.notificationsList.isNotEmpty())
        assertTrue(CampusData.academicResources.any { it.title.contains("SBI Collect") })
        assertTrue(CampusData.academicResources.any { it.title.contains("Digital Library") })
        assertTrue(CampusData.campusLocations.any { it.name.contains("Academic Block") })
    }

    @Test
    fun `verify repository state mutations and persistence`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val campusRepo = com.example.data.repository.CampusRepository(context)
        val userRepo = com.example.data.repository.UserRepository(context)

        // Task toggle
        val firstTask = campusRepo.tasks.value.first()
        val initialStatus = firstTask.completed
        campusRepo.toggleTask(firstTask.id)
        val updatedTask = campusRepo.tasks.value.first { it.id == firstTask.id }
        assertEquals(!initialStatus, updatedTask.completed)

        // Notice bookmark toggle
        val firstNotice = campusRepo.notices.value.first()
        val initialSaved = firstNotice.isSaved
        campusRepo.toggleNoticeSave(firstNotice.id)
        val updatedNotice = campusRepo.notices.value.first { it.id == firstNotice.id }
        assertEquals(!initialSaved, updatedNotice.isSaved)

        // User role switch
        userRepo.switchRole(com.example.core.model.UserRole.GUEST)
        assertEquals(com.example.core.model.UserRole.GUEST, userRepo.userProfile.value.role)
        assertEquals("Guest User", userRepo.userProfile.value.name)
        assertFalse(userRepo.userProfile.value.verified)

        // Switch back to Student
        userRepo.switchRole(com.example.core.model.UserRole.STUDENT)
        assertEquals(com.example.core.model.UserRole.STUDENT, userRepo.userProfile.value.role)
        assertTrue(userRepo.userProfile.value.verified)
    }

    @Test
    fun `verify helpdesk repository inquiry resolution`() {
        val helpdeskRepo = com.example.data.repository.HelpdeskRepository()
        assertEquals(1, helpdeskRepo.messages.value.size)

        helpdeskRepo.submitInquiry("When are the mid-sem exams?")
        assertEquals(3, helpdeskRepo.messages.value.size)
        val response = helpdeskRepo.messages.value.last()
        assertTrue(response.text.contains("October 12, 2026"))
        assertEquals("Controller of Examinations Notice", response.sourceTitle)
    }

    @Test
    fun `verify personalized student authentication and academic synchronization`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val userRepo = com.example.data.repository.UserRepository(context)
        val campusRepo = com.example.data.repository.CampusRepository(context)

        // Login as Debopam Saha
        userRepo.login(
            studentId = "CSE/22021",
            name = "Debopam Saha",
            department = "Computer Science & Engineering",
            semester = "6th Semester (Spring 2026)",
            cgpa = 8.42f,
            sgpa = 8.65f,
            attendancePct = 81.2f,
            email = "debopam.cse22@iiitkalyani.ac.in",
            bloodGroup = "B+"
        )
        campusRepo.syncStudentAcademicProfile(8.42f, 8.65f, 81.2f)

        assertTrue(userRepo.isLoggedIn.value)
        val profile = userRepo.userProfile.value
        assertEquals("Debopam Saha", profile.name)
        assertEquals("CSE/22021", profile.studentId)
        assertEquals(8.42f, profile.cgpa, 0.01f)
        assertEquals(8.65f, profile.sgpa, 0.01f)
        assertEquals(81.2f, profile.attendancePct, 0.01f)
        assertTrue(profile.verified)

        val academicSummary = campusRepo.academicSummary.value
        assertEquals(8.42f, academicSummary.cgpa, 0.01f)
        assertEquals(8.65f, academicSummary.currentSgpa, 0.01f)

        // Test Logout
        userRepo.logout()
        assertFalse(userRepo.isLoggedIn.value)
    }
}
