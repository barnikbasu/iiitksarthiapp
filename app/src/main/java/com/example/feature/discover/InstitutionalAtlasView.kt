package com.example.feature.discover

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.designsystem.SarthiEmerald
import com.example.core.designsystem.SarthiGold
import com.example.core.designsystem.SarthiRoyalBlue

data class AtlasPillar(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val badgeColor: Color,
    val charter: String,
    val subEntities: List<AtlasSubEntity>
)

data class AtlasSubEntity(
    val name: String,
    val description: String,
    val leadOffice: String
)

val InstitutionalAtlasPillars = listOf(
    AtlasPillar(
        id = "pillar_1",
        title = "1. Student Life",
        subtitle = "Clubs, Gymkhana & Student Governance",
        icon = Icons.Default.Group,
        badgeColor = SarthiRoyalBlue,
        charter = "Fostering leadership, creative expression, peer welfare, and vibrant campus extracurricular life.",
        subEntities = listOf(
            AtlasSubEntity("Student Affairs Council", "Apex representative body liaising between students and the Directorate.", "Dean of Student Affairs"),
            AtlasSubEntity("Clubs and Communities", "11 registered societies covering open-source, robotics, drama, music, media, and sports.", "Gymkhana Council"),
            AtlasSubEntity("Student Senate & Executive Council", "Elected batch representatives driving campus initiatives and student welfare policies.", "Senate Secretariat"),
            AtlasSubEntity("Student Welfare & Grievance Wing", "Confidential advocacy desk ensuring student health, hostel safety, and academic support.", "Welfare Cell"),
            AtlasSubEntity("Finance & Festival Secretariat", "Budget allocation and logistics management for Renesa, Anukriti, and campus leagues.", "Finance Committee")
        )
    ),
    AtlasPillar(
        id = "pillar_2",
        title = "2. Events & Festivals",
        subtitle = "Socio-Cultural, Technical & Hackathons",
        icon = Icons.Default.EmojiEvents,
        badgeColor = SarthiGold,
        charter = "Celebrating technological ingenuity, cultural performing arts, athletic prowess, and nationwide competitions.",
        subEntities = listOf(
            AtlasSubEntity("Renesa & Cultural Fests", "Annual 3-day flagship socio-cultural extravaganza featuring concerts, dramatics, and celebrity performances.", "Spotlight & Groovz"),
            AtlasSubEntity("StatusCode & National Hackathons", "36-hour code sprint hosting 500+ builders across India solving real-world challenges.", "FreeScape & GDG"),
            AtlasSubEntity("Competitions & SEAL Robotics", "Hardware hackathons, autonomous rover navigation, and inter-IIIT tech meet delegations.", "SEAL Robotics"),
            AtlasSubEntity("Workshops & Tech Bootcamps", "Hands-on masterclasses in FPGA design, Linux kernel, AI/ML, and cryptographic protocols.", "IEEE Student Branch"),
            AtlasSubEntity("Ekatra & Institutional Symposium", "Inter-college innovation forum bringing together academia and enterprise research.", "Research Cell")
        )
    ),
    AtlasPillar(
        id = "pillar_3",
        title = "3. Institutional Bodies",
        subtitle = "Statutory Governance & Administration",
        icon = Icons.Default.AccountBalance,
        badgeColor = Color(0xFF6366F1),
        charter = "Statutory oversight under the IIIT Act (PPP), upholding academic standards and administrative integrity.",
        subEntities = listOf(
            AtlasSubEntity("Board of Governors", "Apex governing authority steering strategic institutional policy and permanent campus development.", "Chaired by BoG"),
            AtlasSubEntity("Senate", "Principal academic authority supervising curriculum design, examinations, and degree conferrals.", "Director & Dean (Acad)"),
            AtlasSubEntity("Finance Committee", "Statutory audit and annual fiscal management oversight.", "Finance Officer"),
            AtlasSubEntity("Internal Complaints Committee (ICC)", "Zero-tolerance statutory cell safeguarding a safe and equitable gender environment.", "ICC Presiding Officer"),
            AtlasSubEntity("Anti-Ragging Committee & Squad", "24x7 surveillance and prompt enforcement of national anti-ragging mandates.", "Proctorial Board"),
            AtlasSubEntity("Grievance Redressal Cell", "Formal grievance escalation channel for students, scholars, and staff.", "Registrar's Office")
        )
    ),
    AtlasPillar(
        id = "pillar_4",
        title = "4. Research & Innovation",
        subtitle = "IIC, SRIC & Specialized Research Labs",
        icon = Icons.Default.Psychology,
        badgeColor = SarthiEmerald,
        charter = "Pioneering scholarly breakthroughs in Cyber-Physical Security, 5G/6G Networks, Computer Vision, and Materials Science.",
        subEntities = listOf(
            AtlasSubEntity("Institute Innovation Council (IIC)", "MoE-recognized startup incubator promoting ideathons, patent filings, and seed funding.", "IIC Coordinator"),
            AtlasSubEntity("Sponsored Research & Industrial Consultancy (SRIC)", "Facilitating external research grants from MeitY, DST, CSIR, and international consortia.", "SRIC Section"),
            AtlasSubEntity("IPR & Patent Facilitation Cell", "Assisting student and faculty inventors with intellectual property filing and commercialization.", "Dean (R&D)"),
            AtlasSubEntity("National Innovation & Startup Policy (NISP)", "Framework enabling students and faculty to launch technology enterprises.", "NISP Committee"),
            AtlasSubEntity("5G & SHE Lab", "Next-generation signal processing, smart healthcare, and embedded edge intelligence research.", "Prof. Dr. R. Bera")
        )
    ),
    AtlasPillar(
        id = "pillar_5",
        title = "5. Career & Placements",
        subtitle = "Training, Internships & Alumni Network",
        icon = Icons.Default.Work,
        badgeColor = Color(0xFF0284C7),
        charter = "Empowering students with industry immersion, premier campus placements, and global alumni connections.",
        subEntities = listOf(
            AtlasSubEntity("Training & Placement Cell (T&P)", "Leading corporate liaison, pre-placement talks, mock interviews, and final placement drives.", "Prof. In-Charge (T&P)"),
            AtlasSubEntity("Summer & Winter Internships", "Curated domestic and international research and industrial internship pathways.", "T&P Student Wing"),
            AtlasSubEntity("Prominent Recruiters", "Top multinational engineering recruiters in distributed systems, fintech, and semiconductor design.", "Industry Liaison"),
            AtlasSubEntity("Global Alumni Network", "Connect with IIITK graduates working across Fortune 500 tech companies and premier universities.", "Alumni Relations")
        )
    ),
    AtlasPillar(
        id = "pillar_6",
        title = "6. Campus & Infrastructure",
        subtitle = "Labs, Hostels, Library & Connectivity",
        icon = Icons.Default.LocationCity,
        badgeColor = Color(0xFFD97706),
        charter = "State-of-the-art infrastructure designed to inspire academic rigor and collaborative community living.",
        subEntities = listOf(
            AtlasSubEntity("Academic Block (Webel IT Park)", "Modern lecture halls with audio-visual equipment, tutorial rooms, and faculty chambers.", "Estate & Works"),
            AtlasSubEntity("Central Computing & Server Labs", "GPU clusters, high-speed Gigabit LAN, and specialized hardware test benches.", "Computer Centre"),
            AtlasSubEntity("Digital Library & OPAC", "Thousands of volumes, IEEE Xplore, ACM Digital Library, and quiet study pods.", "Library Services"),
            AtlasSubEntity("Student Hostels & Central Mess", "Residential wings with 24x7 Wi-Fi, nutritious catering, and recreation rooms.", "Hostel Wardens"),
            AtlasSubEntity("Campus Transport & Shuttle", "Transit connection between student residences, Academic Block, and Kalyani Railway Station.", "Administrative Desk")
        )
    ),
    AtlasPillar(
        id = "pillar_7",
        title = "7. Student Services",
        subtitle = "Portals, Regulations & Emergency Protocol",
        icon = Icons.Default.SupportAgent,
        badgeColor = Color(0xFFDC2626),
        charter = "Essential campus utilities, academic ordinances, verified portals, and round-the-clock emergency assistance.",
        subEntities = listOf(
            AtlasSubEntity("Notices & Administrative Circulars", "Real-time institutional directives, examination hall tickets, and fee notices.", "Registrar's Office"),
            AtlasSubEntity("Academic Ordinances & Rules", "B.Tech and Ph.D. curriculum rules, 75% attendance criteria, and grading standards.", "Senate Ordinances"),
            AtlasSubEntity("Official Institutional Portals", "SBI Collect fee gateway, Digital Library, and anti-ragging reporting.", "IT Division"),
            AtlasSubEntity("24/7 Campus Emergency Desk", "Direct rapid-dial assistance for Medical Post, Proctorial Board, and National Helpline.", "Campus Emergency")
        )
    ),
    AtlasPillar(
        id = "pillar_8",
        title = "8. Honors & Achievements",
        subtitle = "Student, Faculty & Institutional Laurels",
        icon = Icons.Default.School,
        badgeColor = SarthiGold,
        charter = "Showcasing student hackathon triumphs, faculty honors, and high-impact research publications.",
        subEntities = listOf(
            AtlasSubEntity("Student Laurels & Hackathon Victories", "Podium finishes at Smart Bengal Hackathon, SIH, Inter-IIIT Tech Meet, and ICPC Regionals.", "Student Council"),
            AtlasSubEntity("Faculty Research Citations", "High-impact publications in IEEE Transactions, ACM Journals, and sponsored research patents.", "Research Office"),
            AtlasSubEntity("Open-Source GSoC Scholars", "Multiple students selected for Google Summer of Code, Linux Foundation, and Apache Software Foundation.", "FreeScape Guild"),
            AtlasSubEntity("Inter-College Sports Championships", "Trophies in table tennis, badminton, cricket, and collegiate e-sports leagues.", "Sports Club")
        )
    )
)

@Composable
fun InstitutionalAtlasView(
    searchQuery: String,
    modifier: Modifier = Modifier
) {
    var expandedPillarId by remember { mutableStateOf<String?>(null) }

    val filteredPillars = InstitutionalAtlasPillars.filter { pillar ->
        searchQuery.isBlank() ||
                pillar.title.contains(searchQuery, ignoreCase = true) ||
                pillar.subtitle.contains(searchQuery, ignoreCase = true) ||
                pillar.subEntities.any { it.name.contains(searchQuery, ignoreCase = true) }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("institutional_atlas_list"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = SarthiRoyalBlue.copy(alpha = 0.12f),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.AccountBalance,
                                    contentDescription = null,
                                    tint = SarthiRoyalBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "INSTITUTIONAL ATLAS",
                                style = MaterialTheme.typography.labelSmall,
                                color = SarthiRoyalBlue,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            )
                            Text(
                                text = "8 Apex Governance & Campus Pillars",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "The comprehensive institutional ecosystem of IIIT Kalyani — structured governance, research facilities, student life, statutory bodies, and campus operations.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        items(filteredPillars) { pillar ->
            val isExpanded = expandedPillarId == pillar.id

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .clickable {
                        expandedPillarId = if (isExpanded) null else pillar.id
                    },
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(
                    1.dp,
                    if (isExpanded) pillar.badgeColor else MaterialTheme.colorScheme.outline
                )
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = pillar.badgeColor.copy(alpha = 0.12f),
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = pillar.icon,
                                        contentDescription = null,
                                        tint = pillar.badgeColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = pillar.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = pillar.subtitle,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandMore else Icons.Default.ChevronRight,
                            contentDescription = if (isExpanded) "Collapse" else "Expand",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    AnimatedVisibility(visible = isExpanded) {
                        Column(modifier = Modifier.padding(top = 14.dp)) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = pillar.charter,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(10.dp),
                                    fontSize = 12.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "DEPARTMENTS & BODIES (${pillar.subEntities.size})",
                                style = MaterialTheme.typography.labelSmall,
                                color = pillar.badgeColor,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            pillar.subEntities.forEach { entity ->
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    border = BorderStroke(0.6.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = entity.name,
                                                style = MaterialTheme.typography.titleSmall,
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 13.sp,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            Surface(
                                                shape = RoundedCornerShape(6.dp),
                                                color = pillar.badgeColor.copy(alpha = 0.15f)
                                            ) {
                                                Text(
                                                    text = entity.leadOffice,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = pillar.badgeColor,
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = entity.description,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            fontSize = 11.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
