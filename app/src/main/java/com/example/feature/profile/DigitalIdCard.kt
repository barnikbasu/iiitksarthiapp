package com.example.feature.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.core.designsystem.SarthiGold
import com.example.core.designsystem.SarthiVerifiedGreen
import com.example.core.model.UserProfile
import com.example.core.model.UserRole

@Composable
fun DigitalIdCard(
    userProfile: UserProfile,
    modifier: Modifier = Modifier
) {
    val isVerifiedStudent = userProfile.role == UserRole.STUDENT && userProfile.verified
    val cardBorderColor = if (isVerifiedStudent) SarthiGold.copy(alpha = 0.6f) else Color(0xFF404040)

    // Premium Institutional Card with True Neutral Black Card Surface
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("digital_id_card"),
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFF0A0A0A),
        border = BorderStroke(1.2.dp, cardBorderColor),
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Institutional Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Official Crest
                Surface(
                    modifier = Modifier.size(46.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White,
                    border = BorderStroke(1.2.dp, if (isVerifiedStudent) SarthiGold else Color(0xFF666666))
                ) {
                    Box(
                        modifier = Modifier.padding(3.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.iiitk_crest),
                            contentDescription = "IIIT Kalyani Official Crest",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "भारतीय सूचना प्रौद्योगिकी संस्थान, कल्याणी",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isVerifiedStudent) SarthiGold else Color(0xFFE2E8F0),
                        fontSize = 9.sp,
                        letterSpacing = 0.3.sp
                    )
                    Text(
                        text = "INDIAN INSTITUTE OF INFORMATION TECHNOLOGY, KALYANI",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        fontSize = 10.5.sp,
                        lineHeight = 13.5.sp,
                        letterSpacing = 0.4.sp
                    )
                    Text(
                        text = "(An Institute of National Importance • MoE, Govt. of India)",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFA3A3A3),
                        fontSize = 8.5.sp,
                        letterSpacing = 0.6.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFF262626)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Photo and Key Info Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 3:4 Aspect Ratio Portrait Avatar
                Box(
                    modifier = Modifier
                        .width(82.dp)
                        .height(106.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF1A1A1A))
                        .border(1.dp, Color(0xFF333333), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Student Portrait",
                        tint = Color(0xFF888888),
                        modifier = Modifier.size(54.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = userProfile.name.uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            letterSpacing = 0.5.sp
                        )
                        if (isVerifiedStudent) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = "Verified Identity",
                                tint = SarthiVerifiedGreen,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = if (isVerifiedStudent) userProfile.department else "Campus Visitor / Guest Profile",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFCCCCCC),
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // ID Number - NEVER WRAPS
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFF171717),
                        border = BorderStroke(0.8.dp, if (isVerifiedStudent) SarthiGold.copy(alpha = 0.4f) else Color(0xFF444444))
                    ) {
                        Text(
                            text = if (isVerifiedStudent) userProfile.studentId else "GUEST-ACCESS",
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .testTag("digital_id_number_text"),
                            style = MaterialTheme.typography.labelMedium.copy(fontFamily = FontFamily.Monospace),
                            fontWeight = FontWeight.Bold,
                            color = if (isVerifiedStudent) SarthiGold else Color.White,
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Clip
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFF262626)
            )
            Spacer(modifier = Modifier.height(14.dp))

            // Metadata Row: DOB, Blood Group, Emergency, Barcode/QR
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "DOB",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF777777),
                        fontSize = 10.sp
                    )
                    Text(
                        text = userProfile.dob,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Column {
                    Text(
                        text = "BLOOD GRP",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF777777),
                        fontSize = 10.sp
                    )
                    Text(
                        text = userProfile.bloodGroup,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Column {
                    Text(
                        text = "EMERGENCY",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF777777),
                        fontSize = 10.sp
                    )
                    Text(
                        text = userProfile.emergencyContact,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Icon(
                    imageVector = Icons.Default.QrCode2,
                    contentDescription = "Identity QR Verification",
                    tint = if (isVerifiedStudent) SarthiGold else Color(0xFFA3A3A3),
                    modifier = Modifier.size(34.dp)
                )
            }
        }
    }
}
