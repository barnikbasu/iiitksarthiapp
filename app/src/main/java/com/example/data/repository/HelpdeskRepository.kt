package com.example.data.repository

import com.example.core.model.HelpdeskMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HelpdeskRepository {
    private val _messages = MutableStateFlow(
        listOf(
            HelpdeskMessage(
                id = "msg_0",
                text = "Welcome to the IIIT Kalyani Student Helpdesk. Select an FAQ topic or enter your inquiry below regarding academic regulations, examinations, fee payment, hostel facilities, or campus administration.",
                isFromUser = false,
                timestamp = "Helpdesk Desk",
                sourceTitle = "Academic & Student Affairs Office",
                sourceNote = "Official Administrative Directory"
            )
        )
    )
    val messages: StateFlow<List<HelpdeskMessage>> = _messages.asStateFlow()

    fun submitInquiry(query: String) {
        val userMsg = HelpdeskMessage(
            id = "usr_${System.currentTimeMillis()}",
            text = query,
            isFromUser = true,
            timestamp = "Just now"
        )
        _messages.update { it + userMsg }

        val lower = query.lowercase()
        val (response, sourceTitle, sourceNote) = when {
            lower.contains("director") || lower.contains("head") || lower.contains("leadership") -> {
                Triple(
                    "Prof. Suman Chakraborty has assumed the additional charge of Director, IIIT Kalyani, while continuing as Director of IIT Kharagpur. The Director's Secretariat is located at the Administrative Complex.",
                    "Office of the Director",
                    "Administrative Secretariat, IIIT Kalyani"
                )
            }
            lower.contains("exam") || lower.contains("mid-sem") || lower.contains("timetable") || lower.contains("hall") -> {
                Triple(
                    "Mid-Semester Examinations for Autumn 2026 commence on October 12, 2026. Hall allotments and admit cards are released 5 days prior via the Examination Section notice board.",
                    "Controller of Examinations Notice",
                    "Examination Section, Published 27 Sep 2026"
                )
            }
            lower.contains("emergency") || lower.contains("ambulance") || lower.contains("medical") || lower.contains("doctor") || lower.contains("hospital") -> {
                Triple(
                    "Campus Medical & Emergency Numbers:\n• Campus Health Post & Ambulance: 033-2582-8562\n• Administrative Control Office: 033-2582-5003\n• National Anti-Ragging Helpline (24x7 Toll-Free): 1800-180-5522\n• Local Police Station (Kalyani): 033-2582-8256",
                    "Campus Safety & Emergency Protocol",
                    "Health Centre & Proctorial Board"
                )
            }
            lower.contains("ragging") || lower.contains("anti-ragging") || lower.contains("complaint") -> {
                Triple(
                    "IIIT Kalyani maintains a zero-tolerance policy towards ragging. Immediate confidential reporting channels:\n• Anti-Ragging Toll-Free Helpline: 1800-180-5522 (24x7)\n• Anti-Ragging Committee Email: antiragging@iiitkalyani.ac.in\n• Chief Warden / Proctor Office: Room 104, Admin Block",
                    "Anti-Ragging Committee Guidelines",
                    "Statutory UGC & Institute Regulations"
                )
            }
            lower.contains("fee") || lower.contains("tuition") || lower.contains("sbi") || lower.contains("payment") -> {
                Triple(
                    "Institute tuition and hostel fees must be paid through the official SBI Collect portal under 'West Bengal -> Educational Institutions -> IIIT Kalyani'. Retain the SBCollect e-receipt reference number for semester registration verification.",
                    "Finance & Accounts Section",
                    "Official SBI Collect Payment Protocol"
                )
            }
            lower.contains("data structures") || lower.contains("next class") || lower.contains("dr. debasish") || lower.contains("schedule") -> {
                Triple(
                    "Data Structures & Algorithms session (CS301) by Dr. Debasish Bera is scheduled in Room 204, Academic Block (10:00 AM – 11:30 AM).",
                    "Academic Time Table - CSE 3rd Sem",
                    "Department of Computer Science & Engineering"
                )
            }
            lower.contains("club") || lower.contains("societies") || lower.contains("activities") -> {
                Triple(
                    "Official 11 Student Clubs at IIIT Kalyani:\n1. Spotlight (Cultural & Drama)\n2. Sports Club (Athletics & Games)\n3. SMC (Student Media Cell)\n4. PiXeL (Design & Visual Arts)\n5. FreeScape (Open Source & Systems)\n6. SEAL Robotics (Robotics & Hardware)\n7. Udaan (Social Outreach & CSR)\n8. Groovz (Music & Performing Arts)\n9. IEEE Student Branch IIIT Kalyani\n10. GDG on Campus IIIT Kalyani\n11. E Sports",
                    "Student Affairs Council",
                    "Registered Campus Student Bodies"
                )
            }
            lower.contains("hackathon") || lower.contains("statuscode") || lower.contains("event") || lower.contains("fest") -> {
                Triple(
                    "Major Upcoming Campus Events:\n• StatusCode 2026: 36-hr National Flagship Hackathon on Oct 16–18, 2026\n• Enigma 2026: Algorithmic Contest on Oct 28, 2026\n• Renesa 2026: Annual Cultural Fest on Nov 12–14, 2026\n• Anukriti 2026: Annual Technical Fest on Dec 04–06, 2026",
                    "Academic & Cultural Activity Calendar",
                    "Dean of Student Affairs"
                )
            }
            lower.contains("attendance") || lower.contains("bunk") || lower.contains("threshold") || lower.contains("75") -> {
                Triple(
                    "Institute regulations mandate a strict minimum of 75% attendance in all registered courses to be eligible for end-semester examinations. Medical condonation up to 10% requires medical certificate submission to the HOD within 7 days of resumption.",
                    "Academic Ordinance Section 4.2",
                    "Senate Academic Regulations"
                )
            }
            lower.contains("wifi") || lower.contains("internet") || lower.contains("lan") || lower.contains("network") -> {
                Triple(
                    "Campus and hostel Wi-Fi network uses IEEE 802.1x authentication. Log in with your institutional credentials (username: roll number, password: registered portal password). For MAC registration issues, visit the Computer Centre, 2nd Floor, Academic Block.",
                    "Computer Centre & IT Services",
                    "Network Infrastructure Division"
                )
            }
            lower.contains("faculty") || lower.contains("professor") || lower.contains("teacher") -> {
                Triple(
                    "IIIT Kalyani faculty comprises 16 distinguished professors across CSE, ECE, Mathematics, Physics, and Space Science, led by Dr. Amit Ranjan Azad, Dr. Debasish Bera, Dr. Bhaskar Biswas, Dr. SK Hafizul Islam, and colleagues. Full office hours and cabin numbers are listed in the Faculty Directory under the Community tab.",
                    "Official Faculty Directory",
                    "Office of Academic Dean"
                )
            }
            lower.contains("library") || lower.contains("book") || lower.contains("journal") -> {
                Triple(
                    "Central Digital Library is accessible on campus and remotely through institutional VPN. Operating hours: Monday–Saturday, 9:00 AM – 9:00 PM. Book issue limit: 4 books per undergraduate student for a period of 21 days.",
                    "Central Library Protocol",
                    "Library Services Division"
                )
            }
            else -> {
                Triple(
                    "For inquiries regarding \"$query\", please consult the Academic Section at Room 107, Academic Block (Office hours: 9:30 AM – 5:30 PM, Monday–Friday) or email academic@iiitkalyani.ac.in.",
                    "Institutional Inquiry Desk",
                    "Administrative Secretariat, IIIT Kalyani"
                )
            }
        }

        val deskResponse = HelpdeskMessage(
            id = "desk_${System.currentTimeMillis()}",
            text = response,
            isFromUser = false,
            timestamp = "Official Helpdesk",
            sourceTitle = sourceTitle,
            sourceNote = sourceNote
        )
        _messages.update { it + deskResponse }
    }
}
