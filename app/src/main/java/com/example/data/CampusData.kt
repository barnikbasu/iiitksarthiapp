package com.example.data

import com.example.core.model.AcademicSummary
import com.example.core.model.AttendanceRecord
import com.example.core.model.CampusEvent
import com.example.core.model.CampusLocation
import com.example.core.model.CampusNotice
import com.example.core.model.CampusTask
import com.example.core.model.ClassSession
import com.example.core.model.Club
import com.example.core.model.CourseGrade
import com.example.core.model.EmergencyContact
import com.example.core.model.FacultyMember
import com.example.core.model.MarketplaceItem
import com.example.core.model.Opportunity
import com.example.core.model.SemesterGradeSummary

object CampusData {

    // 16 Definitive Faculty Members
    val facultyList = listOf(
        FacultyMember(
            id = "fac_1",
            name = "Dr. Amit Ranjan Azad",
            designation = "Assistant Professor",
            department = "Electronics & Communication Engineering",
            email = "amitranjan@iiitkalyani.ac.in",
            researchInterests = listOf("RF & Microwave", "Antenna Design", "Electromagnetics"),
            cabin = "Cabin 108, Academic Block"
        ),
        FacultyMember(
            id = "fac_2",
            name = "Dr. Anirban Lakshman",
            designation = "Assistant Professor",
            department = "Mathematics & Computing",
            email = "anirban@iiitkalyani.ac.in",
            researchInterests = listOf("Operations Research", "Graph Theory", "Optimization"),
            cabin = "Cabin 105, Academic Block"
        ),
        FacultyMember(
            id = "fac_3",
            name = "Dr. Bhaskar Biswas",
            designation = "Assistant Professor",
            department = "Computer Science & Engineering",
            email = "bhaskar@iiitkalyani.ac.in",
            researchInterests = listOf("Wireless Networks", "IoT Protocols", "Mobile Computing"),
            cabin = "Cabin 202, Academic Block"
        ),
        FacultyMember(
            id = "fac_4",
            name = "Dr. Dalia Nandi",
            designation = "Assistant Professor",
            department = "Electronics & Communication Engineering",
            email = "dalia@iiitkalyani.ac.in",
            researchInterests = listOf("Signal Processing", "Communication Systems", "VLSI Design"),
            cabin = "Cabin 204, Academic Block"
        ),
        FacultyMember(
            id = "fac_5",
            name = "Dr. Debasish Bera",
            designation = "Assistant Professor",
            department = "Computer Science & Engineering",
            email = "debasish@iiitkalyani.ac.in",
            researchInterests = listOf("Data Structures", "Cryptography", "Network Security"),
            cabin = "Cabin 201, Academic Block"
        ),
        FacultyMember(
            id = "fac_6",
            name = "Dr. Imon Mukherjee",
            designation = "Assistant Professor",
            department = "Computer Science & Engineering",
            email = "imon@iiitkalyani.ac.in",
            researchInterests = listOf("Information Security", "Steganography", "Image Processing"),
            cabin = "Cabin 203, Academic Block"
        ),
        FacultyMember(
            id = "fac_7",
            name = "Dr. Oishila Bandyopadhyay",
            designation = "Assistant Professor",
            department = "Computer Science & Engineering",
            email = "oishila@iiitkalyani.ac.in",
            researchInterests = listOf("Computer Vision", "Medical Image Processing", "Machine Learning"),
            cabin = "Cabin 205, Academic Block"
        ),
        FacultyMember(
            id = "fac_8",
            name = "Dr. Pratik Chakraborty",
            designation = "Assistant Professor",
            department = "Electronics & Communication Engineering",
            email = "pratik@iiitkalyani.ac.in",
            researchInterests = listOf("Wireless Sensor Networks", "Signal Processing", "IoT Architecture"),
            cabin = "Cabin 206, Academic Block"
        ),
        FacultyMember(
            id = "fac_9",
            name = "Prof. Dr. Rabindranath Bera",
            designation = "Visiting Professor",
            department = "Electronics & Communication Engineering",
            email = "rnbera@iiitkalyani.ac.in",
            researchInterests = listOf("Radar Engineering", "5G/6G Networks", "Satellite Communications"),
            cabin = "Executive Suite 1, Academic Block"
        ),
        FacultyMember(
            id = "fac_10",
            name = "Dr. Rinky Sha",
            designation = "Assistant Professor",
            department = "Electronics & Communication Engineering",
            email = "rinky@iiitkalyani.ac.in",
            researchInterests = listOf("Semiconductor Devices", "Microfabrication", "Sensors"),
            cabin = "Cabin 109, Academic Block"
        ),
        FacultyMember(
            id = "fac_11",
            name = "Dr. Sanjay Chatterji",
            designation = "Assistant Professor",
            department = "Computer Science & Engineering",
            email = "sanjayc@iiitkalyani.ac.in",
            researchInterests = listOf("Natural Language Processing", "Machine Translation", "AI"),
            cabin = "Cabin 207, Academic Block"
        ),
        FacultyMember(
            id = "fac_12",
            name = "Dr. Sanjoy Pratihar",
            designation = "Assistant Professor",
            department = "Computer Science & Engineering",
            email = "sanjoy@iiitkalyani.ac.in",
            researchInterests = listOf("Computer Graphics", "Pattern Recognition", "Document Analysis"),
            cabin = "Cabin 208, Academic Block"
        ),
        FacultyMember(
            id = "fac_13",
            name = "Dr. SK Hafizul Islam",
            designation = "Associate Professor",
            department = "Computer Science & Engineering",
            email = "hafi@iiitkalyani.ac.in",
            researchInterests = listOf("Applied Cryptography", "Cyber-Physical Security", "Authentication"),
            cabin = "Cabin 210, Academic Block"
        ),
        FacultyMember(
            id = "fac_14",
            name = "Dr. Soumen Pandit",
            designation = "Assistant Professor",
            department = "Physics & Materials Science",
            email = "soumen@iiitkalyani.ac.in",
            researchInterests = listOf("Nanomaterials", "Energy Storage", "Quantum Devices"),
            cabin = "Cabin 106, Academic Block"
        ),
        FacultyMember(
            id = "fac_15",
            name = "Dr. Sudeshna Mondal",
            designation = "Assistant Professor",
            department = "Mathematics & Computing",
            email = "sudeshna@iiitkalyani.ac.in",
            researchInterests = listOf("Fluid Dynamics", "Numerical Simulation", "Differential Equations"),
            cabin = "Cabin 107, Academic Block"
        ),
        FacultyMember(
            id = "fac_16",
            name = "Dr. Uma Das",
            designation = "Assistant Professor",
            department = "Atmospheric & Space Science",
            email = "uma@iiitkalyani.ac.in",
            researchInterests = listOf("Atmospheric Dynamics", "Remote Sensing", "Satellite Meteorology"),
            cabin = "Cabin 110, Academic Block"
        )
    )

    // Director Special Profile
    const val DIRECTOR_NAME = "Prof. Suman Chakraborty"
    const val DIRECTOR_STATEMENT =
        "Prof. Suman Chakraborty has assumed the additional charge of Director, IIIT Kalyani, while continuing as Director of IIT Kharagpur."

    // 11 Definitive Clubs
    val clubsList = listOf(
        Club(
            id = "club_1",
            name = "Spotlight",
            category = "Cultural & Literary",
            lead = "Creative Arts & Drama Council",
            description = "The official dramatics, literary, public speaking, and theatrical society of IIIT Kalyani.",
            interests = listOf("Theatre", "Debating", "Creative Writing", "Poetry", "Open Mic"),
            upcomingActivities = listOf("Annual Mono-Act Playwright Showcase", "Inter-Year Parliamentary Debate"),
            contactEmail = "spotlight@iiitkalyani.ac.in"
        ),
        Club(
            id = "club_2",
            name = "Sports Club",
            category = "Sports & Fitness",
            lead = "Student Athletic Council",
            description = "Organizes intra-college tournaments, fitness bootcamps, and inter-IIIT sporting contingents.",
            interests = listOf("Cricket", "Football", "Badminton", "Table Tennis", "Chess", "Athletics"),
            upcomingActivities = listOf("IIITK Premier League (IPL) Cricket", "Annual Badminton Open"),
            contactEmail = "sports@iiitkalyani.ac.in"
        ),
        Club(
            id = "club_3",
            name = "SMC",
            category = "Media & Journalism",
            lead = "Student Media Cell",
            description = "The official student-led media, documentation, photography, and campus journalism collective.",
            interests = listOf("Campus Journalism", "Photography", "Videography", "Newsletter", "Social Media"),
            upcomingActivities = listOf("Semester Campus Chronicles Edition", "Kalyani Lens Exhibition"),
            contactEmail = "smc@iiitkalyani.ac.in"
        ),
        Club(
            id = "club_4",
            name = "PiXeL",
            category = "Design & Visual Arts",
            lead = "Creative Design Board",
            description = "Empowering visual thinking, digital illustration, graphic design, UI/UX aesthetics, and animation.",
            interests = listOf("UI/UX Design", "Figma", "Digital Painting", "Brand Identity", "Motion Graphics"),
            upcomingActivities = listOf("24-Hour UI/UX Redesign Sprint", "Design Systems Bootcamp"),
            contactEmail = "pixel@iiitkalyani.ac.in"
        ),
        Club(
            id = "club_5",
            name = "FreeScape",
            category = "Open Source & Systems",
            lead = "FOSS Community",
            description = "The open-source development guild cultivating Linux mastery, self-hosting, and kernel contributions.",
            interests = listOf("Linux", "Git", "Open Source", "Kernel Systems", "Rust", "Self-Hosting"),
            upcomingActivities = listOf("Install Fest & Linux Kernel 101", "Hacktoberfest Sprint"),
            contactEmail = "freescape@iiitkalyani.ac.in"
        ),
        Club(
            id = "club_6",
            name = "SEAL Robotics",
            category = "Robotics & Hardware",
            lead = "Hardware Engineering Group",
            description = "Hands-on robotics, microcontroller automation, PCB design, quadcopters, and IoT hardware solutions.",
            interests = listOf("Arduino", "ESP32", "ROS2", "CAD Modeling", "Autonomous Rovers", "Drone Tech"),
            upcomingActivities = listOf("Line Follower Challenge", "IoT Smart Campus Hackathon"),
            contactEmail = "seal@iiitkalyani.ac.in"
        ),
        Club(
            id = "club_7",
            name = "Udaan",
            category = "Social Outreach & Education",
            lead = "Community Action Wing",
            description = "Dedicated to social empowerment, digital literacy camps for underprivileged children, and environmental drives.",
            interests = listOf("Community Teaching", "Digital Literacy", "Eco Drives", "Cloth Donation", "Awareness"),
            upcomingActivities = listOf("Kalyani Primary School Tutoring Drive", "Campus Green Plantation Drive"),
            contactEmail = "udaan@iiitkalyani.ac.in"
        ),
        Club(
            id = "club_8",
            name = "Groovz",
            category = "Music & Performing Arts",
            lead = "Acoustic & Choreography Board",
            description = "Uniting vocalists, instrumentalists, bands, and dancers across diverse classical and modern genres.",
            interests = listOf("Live Band", "Classical Vocals", "Western Dance", "Beatboxing", "Acoustics"),
            upcomingActivities = listOf("Acoustic Jam Night", "Spring Serenade Flash Mob"),
            contactEmail = "groovz@iiitkalyani.ac.in"
        ),
        Club(
            id = "club_9",
            name = "IEEE Student Branch IIIT Kalyani",
            category = "Technical & Academic Society",
            lead = "IEEE Student Executive",
            description = "Fostering technological innovation, IEEE paper publications, technical workshops, and global chapter connects.",
            interests = listOf("Research Papers", "Microwave Systems", "IEEE Xplore", "Signal Processing", "Seminars"),
            upcomingActivities = listOf("Distinguished Lecturer Series on Quantum Info", "Paper Writing Masterclass"),
            contactEmail = "ieee@iiitkalyani.ac.in"
        ),
        Club(
            id = "club_10",
            name = "GDG on Campus Indian Institute of Information Technology - Kalyani, India",
            category = "Developer Community",
            lead = "Campus Student Lead",
            description = "Student developer chapter helping students bridge theory and software engineering through hands-on technical workshops and open-source hackathons.",
            interests = listOf("Android Jetpack", "Kotlin Multiplatform", "Cloud Infrastructure", "Flutter", "Web Development"),
            upcomingActivities = listOf("Cloud Systems Study Jams 2026", "StatusCode Hackathon Mentorship"),
            contactEmail = "gdg@iiitkalyani.ac.in"
        ),
        Club(
            id = "club_11",
            name = "E Sports",
            category = "Competitive Gaming",
            lead = "Esports Operations",
            description = "The premier gaming fraternity organizing collegiate tournaments, tactical scrims, and LAN events.",
            interests = listOf("Valorant", "BGMI", "Counter-Strike 2", "Rocket League", "Chess.com", "LAN Parties"),
            upcomingActivities = listOf("IIITK Winter LAN Showdown", "Inter-College Valorant Qualifier"),
            contactEmail = "esports@iiitkalyani.ac.in"
        )
    )

    // Emergency Contacts (Official & Verified)
    val emergencyContacts = listOf(
        EmergencyContact(
            title = "Medical Emergency",
            number = "033-2582-8562",
            subtitle = "Campus Health Centre & Kalyani Hospital Ambulance",
            type = "MEDICAL"
        ),
        EmergencyContact(
            title = "General Office & Contact Support",
            number = "033-2582-5003",
            subtitle = "Registrar & Administrative Reception, IIITK",
            type = "OFFICE"
        ),
        EmergencyContact(
            title = "National Anti-Ragging Helpline",
            number = "1800-180-5522",
            subtitle = "24x7 Toll-Free UGC National Helpline",
            type = "ANTI_RAGGING"
        )
    )

    // Definitive Events
    val eventsList = listOf(
        CampusEvent(
            id = "evt_1",
            title = "StatusCode 2026",
            tagline = "Annual 36-Hour National Flagship Hackathon",
            organizer = "GDG on Campus & FreeScape IIITK",
            category = "HACKATHON",
            date = "Oct 16 - 18, 2026",
            time = "06:00 PM onwards",
            venue = "Main Auditorium & Computing Complex",
            description = "The signature 36-hour code marathon bringing together 500+ builders across India to solve tough challenges in Systems, AI, and Fintech.",
            isRegistered = true,
            isSaved = true,
            tags = listOf("Hackathon", "Coding", "Prize: ₹2,00,000", "Flagship")
        ),
        CampusEvent(
            id = "evt_2",
            title = "Renesa 2026",
            tagline = "Annual Socio-Cultural Extravaganza",
            organizer = "Spotlight & Groovz IIITK",
            category = "FEST",
            date = "Nov 12 - 14, 2026",
            time = "10:00 AM - 10:00 PM",
            venue = "Open Air Amphitheatre & Campus Lawns",
            description = "Three days of theatrical plays, musical concerts, battle of the bands, classical rhythm, and celebrity band performances.",
            isRegistered = false,
            isSaved = true,
            tags = listOf("Cultural", "Fest", "Concerts", "Drama")
        ),
        CampusEvent(
            id = "evt_3",
            title = "Anukriti 2026",
            tagline = "Annual Technical Festival of IIIT Kalyani",
            organizer = "IEEE Student Branch & SEAL Robotics",
            category = "FEST",
            date = "Dec 04 - 06, 2026",
            time = "09:00 AM - 08:00 PM",
            venue = "Academic Block & Central Labs",
            description = "Robo-Wars, drone obstacle navigation, tech paper colloquium, and competitive exhibitions of cutting-edge hardware models.",
            isRegistered = false,
            isSaved = false,
            tags = listOf("Robotics", "Hardware", "Paper Presentation")
        ),
        CampusEvent(
            id = "evt_4",
            title = "Enigma 2026",
            tagline = "Flagship Cryptography & Algorithmic Contest",
            organizer = "FreeScape & CSE Department",
            category = "HACKATHON",
            date = "Oct 28, 2026",
            time = "08:00 PM - 11:30 PM",
            venue = "Online (Codeforces Platform)",
            description = "Rated ACM-ICPC style contest featuring tricky mathematical puzzles, dynamic programming challenges, and crypto exploits.",
            isRegistered = true,
            isSaved = true,
            tags = listOf("Competitive Programming", "Algorithms", "Prizes")
        ),
        CampusEvent(
            id = "evt_5",
            title = "Ekatra",
            tagline = "Inter-College Innovation & Research Symposium",
            organizer = "IIIT Kalyani Research Cell",
            category = "WORKSHOP",
            date = "Nov 25, 2026",
            time = "10:00 AM - 05:00 PM",
            venue = "Seminar Hall 1",
            description = "Bringing together industry researchers, faculty scholars, and student paper authors to present posters in distributed systems and AI.",
            isRegistered = false,
            isSaved = false,
            tags = listOf("Research", "Symposium", "Networking")
        ),
        CampusEvent(
            id = "evt_6",
            title = "Spring Serenade",
            tagline = "Annual Acoustic & Instrumental Sunset Evening",
            organizer = "Groovz",
            category = "CULTURAL",
            date = "Jan 22, 2027",
            time = "05:30 PM - 09:00 PM",
            venue = "Campus Courtyard",
            description = "Unplugged acoustic performances, indie song covers, classical fusion, and rhythmic storytelling under the stars.",
            isRegistered = false,
            isSaved = false,
            tags = listOf("Music", "Acoustic", "Chilled")
        ),
        CampusEvent(
            id = "evt_7",
            title = "Talent Hunt",
            tagline = "Freshers' Orientation & Creative Stage",
            organizer = "Spotlight",
            category = "CULTURAL",
            date = "Oct 05, 2026",
            time = "04:00 PM - 08:00 PM",
            venue = "Auditorium",
            description = "Platform for the newly inducted batch to showcase their unique artistic talents in mimicry, poetry, dance, and music.",
            isRegistered = false,
            isSaved = false,
            tags = listOf("Freshers", "Talent", "Fun")
        ),
        CampusEvent(
            id = "evt_8",
            title = "Alumni Meet",
            tagline = "Annual Homecoming & Mentorship Connect",
            organizer = "IIIT Kalyani Alumni Relations",
            category = "WORKSHOP",
            date = "Dec 19, 2026",
            time = "11:00 AM - 06:00 PM",
            venue = "Academic Block & Dining Hall",
            description = "Celebrating the accomplishments of IIITK alumni working in top tech, startups, and higher research across the globe.",
            isRegistered = false,
            isSaved = true,
            tags = listOf("Alumni", "Mentorship", "Careers")
        )
    )

    // Schedule Timeline
    val scheduleToday = listOf(
        ClassSession(
            id = "cs_1",
            courseCode = "CS301",
            courseName = "Data Structures & Algorithms",
            room = "Room 204, Academic Block",
            faculty = "Dr. Debasish Bera",
            startTime = "10:00 AM",
            endTime = "11:30 AM",
            dayOfWeek = "Today",
            status = "NOW" // Current / up next!
        ),
        ClassSession(
            id = "cs_2",
            courseCode = "CS303",
            courseName = "Computer Networks",
            room = "Room 102, Academic Block",
            faculty = "Dr. Bhaskar Biswas",
            startTime = "11:45 AM",
            endTime = "01:15 PM",
            dayOfWeek = "Today",
            status = "UPCOMING"
        ),
        ClassSession(
            id = "cs_3",
            courseCode = "MA301",
            courseName = "Discrete Mathematics",
            room = "Room 205, Academic Block",
            faculty = "Dr. Anirban Lakshman",
            startTime = "02:30 PM",
            endTime = "04:00 PM",
            dayOfWeek = "Today",
            status = "UPCOMING"
        ),
        ClassSession(
            id = "cs_4",
            courseCode = "CS311",
            courseName = "Software Engineering Lab",
            room = "Software Lab 2, 2nd Floor",
            faculty = "Dr. Sanjay Chatterji",
            startTime = "04:15 PM",
            endTime = "06:15 PM",
            dayOfWeek = "Today",
            status = "UPCOMING"
        )
    )

    // Attendance Records
    val attendanceRecords = listOf(
        AttendanceRecord(
            id = "att_1",
            courseCode = "CS301",
            courseName = "Data Structures & Algorithms",
            attended = 28,
            total = 32,
            threshold = 75
        ),
        AttendanceRecord(
            id = "att_2",
            courseCode = "CS303",
            courseName = "Computer Networks",
            attended = 24,
            total = 30,
            threshold = 75
        ),
        AttendanceRecord(
            id = "att_3",
            courseCode = "MA301",
            courseName = "Discrete Mathematics",
            attended = 19,
            total = 26,
            threshold = 75
        ),
        AttendanceRecord(
            id = "att_4",
            courseCode = "CS305",
            courseName = "Information Security",
            attended = 22,
            total = 25,
            threshold = 75
        ),
        AttendanceRecord(
            id = "att_5",
            courseCode = "CS311",
            courseName = "Software Engineering Lab",
            attended = 14,
            total = 14,
            threshold = 75
        )
    )

    // Academic Grades & Summary
    val academicSummary = AcademicSummary(
        cgpa = 6.41f,
        currentSgpa = 8.10f,
        totalCreditsCompleted = 114,
        semesters = listOf(
            SemesterGradeSummary("Sem 1 (Autumn 2023)", 8.25f, 8.25f, 22),
            SemesterGradeSummary("Sem 2 (Spring 2024)", 7.90f, 8.07f, 23),
            SemesterGradeSummary("Sem 3 (Autumn 2024)", 8.35f, 8.16f, 23),
            SemesterGradeSummary("Sem 4 (Spring 2025)", 7.80f, 8.06f, 23),
            SemesterGradeSummary("Sem 5 (Autumn 2025)", 8.10f, 8.07f, 23)
        )
    )

    val currentGrades = listOf(
        CourseGrade("g_1", "Semester 5", "CS301", "Data Structures & Algorithms", 4, "A", 8.0f),
        CourseGrade("g_2", "Semester 5", "CS303", "Computer Networks", 4, "A", 8.0f),
        CourseGrade("g_3", "Semester 5", "CS305", "Information Security", 3, "E", 9.0f),
        CourseGrade("g_4", "Semester 5", "MA301", "Discrete Mathematics", 4, "B", 7.0f),
        CourseGrade("g_5", "Semester 5", "CS311", "Software Engineering Lab", 2, "O", 10.0f)
    )

    // Campus Tasks / Deadlines
    val tasksList = listOf(
        CampusTask(
            id = "tsk_1",
            title = "B-Tree & Red-Black Tree Implementation",
            dueDate = "Tomorrow, 11:59 PM",
            course = "CS301 · Dr. Debasish Bera",
            priority = "HIGH",
            completed = false,
            category = "ASSIGNMENT"
        ),
        CampusTask(
            id = "tsk_2",
            title = "Subnetting & RIP Routing Packet Tracer Lab",
            dueDate = "Friday, 05:00 PM",
            course = "CS303 · Dr. Bhaskar Biswas",
            priority = "MEDIUM",
            completed = false,
            category = "ASSIGNMENT"
        ),
        CampusTask(
            id = "tsk_3",
            title = "Submit Team Registration for StatusCode 2026",
            dueDate = "Sunday, 08:00 PM",
            course = "GDG on Campus",
            priority = "HIGH",
            completed = true,
            category = "PROJECT"
        )
    )

    // Official Notices
    val noticesList = listOf(
        CampusNotice(
            id = "not_1",
            title = "Mid-Semester Examination Schedule - Autumn 2026",
            summary = "The detailed timetable and seating allotment for B.Tech Autumn Mid-Sem examinations.",
            content = "All undergraduate students are hereby notified that the Mid-Semester Examinations for Autumn 2026 will commence from October 12, 2026. The examination halls will be allocated per roll number. Standard institute examination codes and admit cards will be verified at the entrance.",
            category = "EXAMINATION",
            publishedDate = "27 Sep 2026",
            sourceName = "Office of the Controller of Examinations",
            isImportant = true,
            isRead = false,
            isSaved = true
        ),
        CampusNotice(
            id = "not_2",
            title = "Academic Fee Payment Deadline Notification",
            summary = "Last date for payment of tuition and hostel maintenance fees for the upcoming cycle.",
            content = "Students who have not completed the fee reconciliation through the SBI Collect institutional portal are requested to do so before October 15, 2026, to prevent registration holds.",
            category = "ACADEMIC",
            publishedDate = "25 Sep 2026",
            sourceName = "Accounts Department, IIIT Kalyani",
            isImportant = true,
            isRead = true,
            isSaved = false
        ),
        CampusNotice(
            id = "not_3",
            title = "Hostel Internet & WiFi Maintenance Window",
            summary = "Scheduled upgrade of campus optical fiber link and hostel AP controllers.",
            content = "The Computer Centre will carry out scheduled routing table updates and access point firmware upgrades on Saturday night between 01:00 AM and 04:00 AM.",
            category = "HOSTEL",
            publishedDate = "24 Sep 2026",
            sourceName = "Computer Centre, IIITK",
            isImportant = false,
            isRead = true,
            isSaved = false
        ),
        CampusNotice(
            id = "not_4",
            title = "Campus Placement Registration: Batch of 2027",
            summary = "Pre-placement training and registration portal opening for 3rd year students.",
            content = "Training and Placement Cell (T&P) invites eligible B.Tech students to complete profile updates on the institutional placement portal.",
            category = "PLACEMENT",
            publishedDate = "22 Sep 2026",
            sourceName = "Training & Placement Cell",
            isImportant = false,
            isRead = false,
            isSaved = true
        )
    )

    // Opportunities (Internships, Research, Scholarships)
    val opportunitiesList = listOf(
        Opportunity(
            id = "opp_1",
            title = "Open Source Summer Mentorship (GSoC) Track",
            organization = "FreeScape Open Source Collective",
            category = "INTERNSHIP",
            deadline = "15 Nov 2026",
            eligibility = "All B.Tech students with Git/Linux fundamentals",
            description = "Get 1-on-1 guidance from past GSoC scholars from IIITK on proposal drafting, selecting organizations, and making impactful open-source pull requests.",
            sourceUrl = "https://freescape.iiitkalyani.ac.in",
            isSaved = true
        ),
        Opportunity(
            id = "opp_2",
            title = "Undergraduate Research Fellowship in Applied Cryptography",
            organization = "IIIT Kalyani Cyber-Physical Security Lab",
            category = "RESEARCH",
            deadline = "10 Oct 2026",
            eligibility = "3rd & 4th year CSE with CGPA >= 7.5",
            description = "Research project under Dr. SK Hafizul Islam focusing on lightweight post-quantum authentication protocols for smart city IoT grids.",
            sourceUrl = "https://iiitkalyani.ac.in/research",
            isSaved = false
        ),
        Opportunity(
            id = "opp_3",
            title = "National Siemens Hackathon 2026",
            organization = "Siemens Digital Industries",
            category = "HACKATHON",
            deadline = "30 Oct 2026",
            eligibility = "Teams of 3-4 engineering undergraduates",
            description = "Build intelligent industrial automation models with cloud telemetry. Grand prize of ₹3,50,000 and direct interview tracks.",
            sourceUrl = "https://siemens.com/hackathon",
            isSaved = true
        )
    )

    // Marketplace Items (Student-to-student peer exchange)
    val marketplaceList = listOf(
        MarketplaceItem(
            id = "mkt_1",
            title = "Introduction to Algorithms (CLRS 3rd Edition)",
            price = "₹450",
            category = "BOOKS",
            condition = "Very Good · Highlighted notes",
            sellerName = "Aritra Sen (Final Year)",
            sellerContact = "aritra.cse21@iiitkalyani.ac.in",
            postedDate = "Yesterday"
        ),
        MarketplaceItem(
            id = "mkt_2",
            title = "Hero Sprint 21-Speed Mountain Gear Cycle",
            price = "₹2,800",
            category = "CYCLES",
            condition = "Good Condition · New brake pads",
            sellerName = "Rahul Das (Hostel 2)",
            sellerContact = "rahul.ece22@iiitkalyani.ac.in",
            postedDate = "2 days ago"
        ),
        MarketplaceItem(
            id = "mkt_3",
            title = "Raspberry Pi 4 Model B (4GB RAM) + Official Case",
            price = "₹3,200",
            category = "ELECTRONICS",
            condition = "Flawless · Used for 1 project",
            sellerName = "Sagnik Roy (SEAL Robotics)",
            sellerContact = "sagnik.seal@iiitkalyani.ac.in",
            postedDate = "3 days ago"
        ),
        MarketplaceItem(
            id = "mkt_4",
            title = "Study Table Lamp with USB Rechargeable Battery",
            price = "₹300",
            category = "HOSTEL",
            condition = "Like New",
            sellerName = "Pritam Ghosh",
            sellerContact = "pritam.cse23@iiitkalyani.ac.in",
            postedDate = "Sep 22"
        )
    )

    // Campus Facilities & Locations
    val campusLocations = listOf(
        CampusLocation(
            id = "loc_1",
            name = "Academic Block (Webel IT Park)",
            type = "ACADEMIC",
            landmark = "Near Kalyani Buddha Park",
            description = "Houses lecture theatres, faculty cabins, department offices, and executive meeting rooms.",
            timings = "08:00 AM - 08:00 PM"
        ),
        CampusLocation(
            id = "loc_2",
            name = "Central Computing Lab & Server Room",
            type = "LAB",
            landmark = "2nd Floor, Academic Block",
            description = "High-performance computing terminals, GPU clusters, and high-speed LAN for curriculum labs.",
            timings = "09:00 AM - 09:00 PM"
        ),
        CampusLocation(
            id = "loc_3",
            name = "IIITK Library & Digital Reading Room",
            type = "LIBRARY",
            landmark = "1st Floor, Academic Block",
            description = "Reference textbooks, IEEE Xplore terminals, quiet study carrels, and reprographic facility.",
            timings = "09:00 AM - 08:00 PM (Mon-Sat)"
        ),
        CampusLocation(
            id = "loc_4",
            name = "Student Residential Hostels",
            type = "HOSTEL",
            landmark = "Kalyani Township",
            description = "Separate multi-story accommodation for boys and girls with high-speed WiFi and common rooms.",
            timings = "24x7 (In-time: 10:00 PM)"
        ),
        CampusLocation(
            id = "loc_5",
            name = "Campus Dining & Mess Hall",
            type = "MESS",
            landmark = "Adjacent to Hostel Complexes",
            description = "Breakfast (07:30 - 09:30 AM), Lunch (12:30 - 02:30 PM), Dinner (08:00 - 10:00 PM).",
            timings = "Meal Windows Daily"
        ),
        CampusLocation(
            id = "loc_6",
            name = "Campus Health Centre & First-Aid Post",
            type = "MEDICAL",
            landmark = "Ground Floor, Hostel Wing",
            description = "Medical officer consultation, basic diagnostic essentials, and ambulance liaison.",
            timings = "24x7 Emergency Liaison (033-2582-8562)"
        )
    )
    // Campus Notifications
    val notificationsList = listOf(
        com.example.core.model.CampusNotification(
            id = "notif_1",
            title = "Mid-Sem Exam Timetable Published",
            description = "Controller of Examinations has released the schedule starting October 12, 2026.",
            time = "10m ago",
            category = "EXAMINATION",
            isRead = false,
            isImportant = true
        ),
        com.example.core.model.CampusNotification(
            id = "notif_2",
            title = "Next Class Starting Soon",
            description = "Data Structures & Algorithms in Room 204 with Dr. Debasish Bera.",
            time = "35m ago",
            category = "ACADEMIC",
            isRead = false,
            isImportant = false
        ),
        com.example.core.model.CampusNotification(
            id = "notif_3",
            title = "StatusCode 2026 Registrations Open",
            description = "Register your team for the flagship 36-hour hackathon with ₹2,00,000 in prizes.",
            time = "2h ago",
            category = "EVENT",
            isRead = true,
            isImportant = false
        ),
        com.example.core.model.CampusNotification(
            id = "notif_4",
            title = "SBI Collect Academic Fee Portal Live",
            description = "Pay semester fees before Oct 15 to avoid late fine.",
            time = "Yesterday",
            category = "ACADEMIC",
            isRead = true,
            isImportant = true
        )
    )

    // Academic Resources & Official Portals
    val academicResources = listOf(
        com.example.core.model.AcademicResource(
            id = "res_1",
            title = "SBI Collect Official Fee Payment Portal",
            category = "FEES",
            description = "Authorized institutional gateway for tuition, hostel, and mess fee transactions.",
            url = "https://www.onlinesbi.sbi/sbicollect/icollecthome.htm"
        ),
        com.example.core.model.AcademicResource(
            id = "res_2",
            title = "IIIT Kalyani Digital Library & IEEE Xplore",
            category = "LIBRARY",
            description = "Access e-journals, IEEE conference proceedings, ACM Digital Library, and textbook repositories.",
            url = "http://iiitkalyani.ac.in/library"
        ),
        com.example.core.model.AcademicResource(
            id = "res_3",
            title = "B.Tech Curriculum & Syllabi (CSE & ECE)",
            category = "SYLLABUS",
            description = "Official course structures, credit distributions, and elective catalogues approved by the Senate.",
            url = "http://iiitkalyani.ac.in/academics/curriculum"
        ),
        com.example.core.model.AcademicResource(
            id = "res_4",
            title = "Academic Ordinances & Code of Conduct",
            category = "REGULATION",
            description = "75% attendance policy, grading ordinances, repeat examination rules, and disciplinary statutes.",
            url = "http://iiitkalyani.ac.in/rules"
        ),
        com.example.core.model.AcademicResource(
            id = "res_5",
            title = "National Anti-Ragging Affidavit Portal",
            category = "PORTAL",
            description = "Mandatory annual UGC anti-ragging undertaking registration for enrolled students.",
            url = "https://antiragging.in"
        ),
        com.example.core.model.AcademicResource(
            id = "res_6",
            title = "IIIT Kalyani Official Institute Web Portal",
            category = "PORTAL",
            description = "Official announcements, faculty directory, tenders, press releases, and administration contacts.",
            url = "http://iiitkalyani.ac.in"
        )
    )
}
