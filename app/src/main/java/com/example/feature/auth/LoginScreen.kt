package com.example.feature.auth

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.core.designsystem.SarthiAmber
import com.example.core.designsystem.SarthiBlueContainer
import com.example.core.designsystem.SarthiEmerald
import com.example.core.designsystem.SarthiEmeraldContainer
import com.example.core.designsystem.SarthiGold
import com.example.core.designsystem.SarthiRoyalBlue
import com.example.ui.viewmodel.SarthiAppViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class VerifiedPresetStudent(
    val id: String,
    val name: String,
    val rollNumber: String,
    val department: String,
    val semester: String,
    val cgpa: Float,
    val sgpa: Float,
    val attendancePct: Float,
    val email: String,
    val bloodGroup: String,
    val emergencyContact: String,
    val roleBadge: String
)

private val PRESET_STUDENTS = listOf(
    VerifiedPresetStudent(
        id = "preset_barnik",
        name = "Barnik Basu",
        rollNumber = "CSE/22014",
        department = "Computer Science & Engineering",
        semester = "6th Semester (Spring 2026)",
        cgpa = 8.84f,
        sgpa = 9.10f,
        attendancePct = 84.8f,
        email = "barnik.cse22@iiitkalyani.ac.in",
        bloodGroup = "O+",
        emergencyContact = "033-2582-8562",
        roleBadge = "Dean's Honor Roll"
    ),
    VerifiedPresetStudent(
        id = "preset_debopam",
        name = "Debopam Saha",
        rollNumber = "CSE/22021",
        department = "Computer Science & Engineering",
        semester = "6th Semester (Spring 2026)",
        cgpa = 8.42f,
        sgpa = 8.65f,
        attendancePct = 81.2f,
        email = "debopam.cse22@iiitkalyani.ac.in",
        bloodGroup = "B+",
        emergencyContact = "033-2582-8562",
        roleBadge = "Tech Council Lead"
    ),
    VerifiedPresetStudent(
        id = "preset_ananya",
        name = "Ananya Sen",
        rollNumber = "ECE/22008",
        department = "Electronics & Communication Engg",
        semester = "6th Semester (Spring 2026)",
        cgpa = 9.12f,
        sgpa = 9.30f,
        attendancePct = 89.5f,
        email = "ananya.ece22@iiitkalyani.ac.in",
        bloodGroup = "A+",
        emergencyContact = "033-2582-8562",
        roleBadge = "Gold Medalist Candidate"
    )
)

@Composable
fun LoginScreen(
    viewModel: SarthiAppViewModel,
    isDarkTheme: Boolean,
    onToggleDarkTheme: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var selectedTab by remember { mutableIntStateOf(0) } // 0: Verified One-Tap Profiles, 1: Custom Student Login
    var isAuthenticating by remember { mutableStateOf(false) }
    var syncMessage by remember { mutableStateOf<String?>(null) }

    // Custom Form State
    var customRoll by remember { mutableStateOf("CSE/22014") }
    var customName by remember { mutableStateOf("Barnik Basu") }
    var customDept by remember { mutableStateOf("Computer Science & Engineering") }
    var customSemester by remember { mutableStateOf("6th Semester (Spring 2026)") }
    var customCgpa by remember { mutableStateOf("8.84") }
    var customSgpa by remember { mutableStateOf("9.10") }
    var customAttendance by remember { mutableStateOf("84.8") }
    var customEmail by remember { mutableStateOf("barnik.cse22@iiitkalyani.ac.in") }
    var customPassword by remember { mutableStateOf("••••••••") }
    var passwordVisible by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("login_screen")
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. TOP UTILITY BAR (Theme Toggle & Security Badge)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SarthiBlueContainer
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = "Secured",
                            tint = SarthiRoyalBlue,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "IIITK Verified ERP Auth",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = SarthiRoyalBlue,
                            fontSize = 10.5.sp
                        )
                    }
                }

                IconButton(
                    onClick = { onToggleDarkTheme(!isDarkTheme) },
                    modifier = Modifier.size(40.dp).testTag("login_theme_toggle")
                ) {
                    Icon(
                        imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = "Toggle Dark Mode",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        // 2. HERO INSTITUTIONAL CREST & IDENTITY
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.iiitk_header_banner),
                            contentDescription = "Indian Institute of Information Technology, Kalyani",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(74.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = SarthiBlueContainer,
                        border = BorderStroke(1.dp, SarthiRoyalBlue.copy(alpha = 0.3f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "SARTHI",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = SarthiRoyalBlue,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "• Personal Student ERP & Campus OS",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = SarthiRoyalBlue
                            )
                        }
                    }
                }
            }
        }

        // 3. AUTHENTICATION TABS
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
            ) {
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = SarthiRoyalBlue,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = SarthiRoyalBlue,
                            height = 3.dp
                        )
                    }
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = {
                            Text(
                                text = "Verified Student Profiles",
                                fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.5.sp
                            )
                        }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = {
                            Text(
                                text = "Custom ERP Sign-In",
                                fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.5.sp
                            )
                        }
                    )
                }
            }
        }

        // 4. TAB CONTENTS
        if (selectedTab == 0) {
            // VERIFIED ONE-TAP PROFILES
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "SELECT REGISTERED SCHOLAR PROFILE",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        letterSpacing = 0.8.sp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )

                    PRESET_STUDENTS.forEach { student ->
                        PresetStudentCard(
                            student = student,
                            isAuthenticating = isAuthenticating,
                            onSelect = {
                                coroutineScope.launch {
                                    isAuthenticating = true
                                    syncMessage = "Connecting to IIITK Academic Server for ${student.name}..."
                                    delay(400)
                                    syncMessage = "Syncing CGPA (${student.cgpa}), SGPA (${student.sgpa}) & Attendance (${student.attendancePct}%)..."
                                    delay(400)
                                    viewModel.login(
                                        studentId = student.rollNumber,
                                        name = student.name,
                                        department = student.department,
                                        semester = student.semester,
                                        cgpa = student.cgpa,
                                        sgpa = student.sgpa,
                                        attendancePct = student.attendancePct,
                                        email = student.email,
                                        bloodGroup = student.bloodGroup,
                                        emergencyContact = student.emergencyContact
                                    )
                                    isAuthenticating = false
                                    Toast.makeText(context, "Welcome back, ${student.name}!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    }
                }
            }
        } else {
            // CUSTOM STUDENT LOGIN FORM
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "INSTITUTIONAL CREDENTIALS",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = SarthiRoyalBlue,
                            letterSpacing = 0.8.sp
                        )

                        OutlinedTextField(
                            value = customName,
                            onValueChange = { customName = it },
                            label = { Text("Full Name") },
                            leadingIcon = {
                                Icon(Icons.Default.Person, contentDescription = null, tint = SarthiRoyalBlue)
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().testTag("login_name_input")
                        )

                        OutlinedTextField(
                            value = customRoll,
                            onValueChange = { customRoll = it },
                            label = { Text("Roll Number / Webmail ID") },
                            leadingIcon = {
                                Icon(Icons.Default.Badge, contentDescription = null, tint = SarthiRoyalBlue)
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().testTag("login_roll_input")
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = customCgpa,
                                onValueChange = { customCgpa = it },
                                label = { Text("CGPA (0-10)") },
                                leadingIcon = {
                                    Icon(Icons.Default.School, contentDescription = null, tint = SarthiRoyalBlue)
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                singleLine = true,
                                modifier = Modifier.weight(1f).testTag("login_cgpa_input")
                            )

                            OutlinedTextField(
                                value = customAttendance,
                                onValueChange = { customAttendance = it },
                                label = { Text("Attendance %") },
                                leadingIcon = {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SarthiEmerald)
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                singleLine = true,
                                modifier = Modifier.weight(1f).testTag("login_attendance_input")
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = customSgpa,
                                onValueChange = { customSgpa = it },
                                label = { Text("Last SGPA") },
                                leadingIcon = {
                                    Icon(Icons.Default.TrendingUp, contentDescription = null, tint = SarthiAmber)
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                singleLine = true,
                                modifier = Modifier.weight(1f)
                            )

                            OutlinedTextField(
                                value = customSemester,
                                onValueChange = { customSemester = it },
                                label = { Text("Semester") },
                                singleLine = true,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        OutlinedTextField(
                            value = customDept,
                            onValueChange = { customDept = it },
                            label = { Text("Department / Branch") },
                            leadingIcon = {
                                Icon(Icons.Default.School, contentDescription = null, tint = SarthiRoyalBlue)
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = customEmail,
                            onValueChange = { customEmail = it },
                            label = { Text("Institutional Email") },
                            leadingIcon = {
                                Icon(Icons.Default.Email, contentDescription = null, tint = SarthiRoyalBlue)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = customPassword,
                            onValueChange = { customPassword = it },
                            label = { Text("ERP Password / Security PIN") },
                            leadingIcon = {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = SarthiRoyalBlue)
                            },
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = "Toggle password visibility"
                                    )
                                }
                            },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    isAuthenticating = true
                                    syncMessage = "Authenticating $customName ($customRoll)..."
                                    delay(400)
                                    val cgpaVal = customCgpa.toFloatOrNull() ?: 8.84f
                                    val sgpaVal = customSgpa.toFloatOrNull() ?: 9.10f
                                    val attVal = customAttendance.toFloatOrNull() ?: 84.8f
                                    viewModel.login(
                                        studentId = customRoll.trim().ifBlank { "CSE/22014" },
                                        name = customName.trim().ifBlank { "Barnik Basu" },
                                        department = customDept.trim().ifBlank { "Computer Science & Engineering" },
                                        semester = customSemester.trim().ifBlank { "6th Semester (Spring 2026)" },
                                        cgpa = cgpaVal,
                                        sgpa = sgpaVal,
                                        attendancePct = attVal,
                                        email = customEmail.trim().ifBlank { "barnik.cse22@iiitkalyani.ac.in" }
                                    )
                                    isAuthenticating = false
                                    Toast.makeText(context, "Authenticated successfully as $customName", Toast.LENGTH_SHORT).show()
                                }
                            },
                            enabled = !isAuthenticating,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SarthiRoyalBlue),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("login_custom_submit_btn")
                        ) {
                            Icon(Icons.Default.Fingerprint, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Sign In & Sync Academic Profile",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // 5. ANIMATED SYNC / LOADING STATUS
        if (isAuthenticating && syncMessage != null) {
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SarthiBlueContainer,
                    border = BorderStroke(1.dp, SarthiRoyalBlue.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = SarthiRoyalBlue,
                            strokeWidth = 2.5.dp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = syncMessage ?: "",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = SarthiRoyalBlue
                        )
                    }
                }
            }
        }

        // 6. GUEST & VISITOR EXPLORATION
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                    Text(
                        text = " OR ",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedButton(
                    onClick = {
                        viewModel.loginAsGuest()
                        Toast.makeText(context, "Browsing in Campus Explorer & Visitor mode", Toast.LENGTH_SHORT).show()
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("login_guest_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Explore,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Continue as Guest / Campus Visitor",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Indian Institute of Information Technology Kalyani • All Rights Reserved",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun PresetStudentCard(
    student: VerifiedPresetStudent,
    isAuthenticating: Boolean,
    onSelect: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isAuthenticating) { onSelect() }
            .testTag("preset_student_${student.id}"),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)),
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Portrait Avatar Box
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(SarthiBlueContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = student.name.split(" ").mapNotNull { it.firstOrNull() }.joinToString("").take(2),
                        fontWeight = FontWeight.Bold,
                        color = SarthiRoyalBlue,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = student.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SarthiEmeraldContainer
                        ) {
                            Text(
                                text = student.roleBadge,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = SarthiEmerald,
                                fontSize = 10.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "${student.rollNumber} • ${student.department}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Stat Chips Row: CGPA, SGPA, Attendance
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = SarthiBlueContainer,
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "CGPA",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = SarthiRoyalBlue,
                            fontSize = 9.sp
                        )
                        Text(
                            text = String.format(java.util.Locale.US, "%.2f", student.cgpa),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = SarthiRoyalBlue
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFFEF3C7),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "SGPA",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB45309),
                            fontSize = 9.sp
                        )
                        Text(
                            text = String.format(java.util.Locale.US, "%.2f", student.sgpa),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFFB45309)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = SarthiEmeraldContainer,
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "ATTENDANCE",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = SarthiEmerald,
                            fontSize = 9.sp
                        )
                        Text(
                            text = String.format(java.util.Locale.US, "%.1f%%", student.attendancePct),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = SarthiEmerald
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sign in as ${student.name.split(" ").first()}",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = SarthiRoyalBlue
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = SarthiRoyalBlue,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}
