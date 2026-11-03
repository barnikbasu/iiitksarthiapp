package com.example.feature.discover

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.TheaterComedy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.designsystem.SarthiEmerald
import com.example.core.designsystem.SarthiGold
import com.example.core.designsystem.SarthiRoyalBlue

data class RenesaCompetition(
    val title: String,
    val category: String,
    val icon: ImageVector,
    val prizePool: String,
    val teamSize: String,
    val description: String,
    val isRegistered: Boolean
)

val RenesaCompetitionsList = listOf(
    RenesaCompetition(
        title = "Battle of the Bands (Rhythm Rumble)",
        category = "MUSIC",
        icon = Icons.Default.MusicNote,
        prizePool = "₹45,000",
        teamSize = "3 - 8 Members",
        description = "High-octane collegiate band faceoff covering rock, metal, indie fusion, and original compositions.",
        isRegistered = false
    ),
    RenesaCompetition(
        title = "Natyamanch: The Street Play & One-Act Theatrics",
        category = "DRAMA",
        icon = Icons.Default.TheaterComedy,
        prizePool = "₹35,000",
        teamSize = "8 - 20 Members",
        description = "Powerful social themes, acoustic percussion, synchronized vocal dynamics, and street drama.",
        isRegistered = true
    ),
    RenesaCompetition(
        title = "Step Up: Western & Classical Group Choreography",
        category = "DANCE",
        icon = Icons.Default.Celebration,
        prizePool = "₹30,000",
        teamSize = "4 - 15 Members",
        description = "Dynamic crew choreography with lighting cues, prop usage, and thematic storytelling.",
        isRegistered = false
    ),
    RenesaCompetition(
        title = "Voice of Kalyani (Western & Eastern Solo)",
        category = "VOCALS",
        icon = Icons.Default.Mic,
        prizePool = "₹15,000",
        teamSize = "Solo Performance",
        description = "Pitch-perfect vocal contest across semi-classical, ghazals, Bollywood retro, and English pop.",
        isRegistered = false
    ),
    RenesaCompetition(
        title = "LAN Dominance: Valorant & CS2 Arena",
        category = "ESPORTS",
        icon = Icons.Default.SportsEsports,
        prizePool = "₹25,000",
        teamSize = "5 Members",
        description = "5v5 tactical shooter tournament hosted on gigabit LAN with live auditorium casting.",
        isRegistered = true
    )
)

@Composable
fun RenesaFestivalView(
    context: Context,
    modifier: Modifier = Modifier
) {
    var selectedDayTab by remember { mutableIntStateOf(0) }
    var registeredMap by remember {
        mutableStateOf(RenesaCompetitionsList.associate { it.title to it.isRegistered }.toMutableMap())
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("renesa_festival_view"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Festival Card
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFF0F172A),
                border = BorderStroke(1.dp, Color(0xFF334155))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF1E3A8A).copy(alpha = 0.5f),
                                    Color(0xFF0F172A)
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = SarthiGold.copy(alpha = 0.2f),
                                border = BorderStroke(1.dp, SarthiGold.copy(alpha = 0.5f))
                            ) {
                                Text(
                                    text = "FLAGSHIP CULTURAL FEST",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SarthiGold,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Text(
                                text = "Nov 12 - 14, 2026",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFFCBD5E1),
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Renesa 2026",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            text = "Tech, Culture & The Nights · 7th Annual Edition",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF94A3B8)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = SarthiGold,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Amphitheatre & Main Lawns",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFFE2E8F0),
                                    fontSize = 12.sp
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Celebration,
                                    contentDescription = null,
                                    tint = SarthiEmerald,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "₹1.5L+ Prizes",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SarthiEmerald,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3-Day Itinerary Selector
        item {
            Text(
                text = "FESTIVAL ITINERARY",
                style = MaterialTheme.typography.labelSmall,
                color = SarthiRoyalBlue,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Day 1: Genesis", "Day 2: Zenith", "Day 3: Celeb Night").forEachIndexed { idx, label ->
                    val isSel = selectedDayTab == idx
                    Surface(
                        onClick = { selectedDayTab = idx },
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSel) SarthiRoyalBlue else MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, if (isSel) SarthiRoyalBlue else MaterialTheme.colorScheme.outline),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSel) Color.White else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }

        // Selected Day Schedule Card
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    val events = when (selectedDayTab) {
                        0 -> listOf(
                            "10:00 AM" to "Grand Inauguration by Director & Torch Relay",
                            "11:30 AM" to "Spotlight Street Play (Natyamanch) Preliminary Rounds",
                            "03:00 PM" to "Classical Rhythm & Solo Vocals (Voice of Kalyani)",
                            "06:30 PM" to "Battle of Bands (Rhythm Rumble) Day 1 Heats"
                        )
                        1 -> listOf(
                            "09:30 AM" to "LAN Arena: Valorant & CS2 Quarter Finals",
                            "01:00 PM" to "Western Solo & Duet Acoustic Sessions",
                            "04:00 PM" to "Step Up Choreography Finals (Main Stage)",
                            "07:30 PM" to "Groovz Music Ensemble: Fusion Rock Showcase"
                        )
                        else -> listOf(
                            "11:00 AM" to "Inter-College E-Sports Grand Finals",
                            "02:30 PM" to "Valedictory & Prize Distribution Ceremony",
                            "06:00 PM" to "Celebrity Headline Concert & Live Band",
                            "09:30 PM" to "Official Renesa After-Hours EDM Night"
                        )
                    }

                    events.forEach { (time, desc) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = time,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = SarthiRoyalBlue,
                                modifier = Modifier.width(72.dp)
                            )
                            Text(
                                text = desc,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        // Flagship Competitions & Rulebooks
        item {
            Text(
                text = "FLAGSHIP COMPETITIONS & RULEBOOKS",
                style = MaterialTheme.typography.labelSmall,
                color = SarthiRoyalBlue,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )
        }

        items(RenesaCompetitionsList.size) { idx ->
            val comp = RenesaCompetitionsList[idx]
            val isReg = registeredMap[comp.title] ?: false

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = SarthiRoyalBlue.copy(alpha = 0.12f),
                                modifier = Modifier.size(34.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = comp.icon,
                                        contentDescription = null,
                                        tint = SarthiRoyalBlue,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = comp.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${comp.category} · ${comp.teamSize}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SarthiEmerald.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = comp.prizePool,
                                style = MaterialTheme.typography.labelSmall,
                                color = SarthiEmerald,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = comp.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                Toast.makeText(
                                    context,
                                    "Downloading official rulebook for ${comp.title} (PDF)...",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Rulebook", fontSize = 11.sp)
                        }

                        Button(
                            onClick = {
                                val current = registeredMap[comp.title] ?: false
                                val newMap = registeredMap.toMutableMap()
                                newMap[comp.title] = !current
                                registeredMap = newMap
                                val msg = if (!current) "Registered for ${comp.title}!" else "Registration withdrawn."
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isReg) SarthiEmerald else SarthiRoyalBlue
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = if (isReg) Icons.Default.CheckCircle else Icons.Default.CalendarMonth,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(if (isReg) "Registered" else "Register", fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}
