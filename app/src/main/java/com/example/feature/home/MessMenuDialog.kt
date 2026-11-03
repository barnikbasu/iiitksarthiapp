package com.example.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.designsystem.SarthiBlueContainer
import com.example.core.designsystem.SarthiEmerald
import com.example.core.designsystem.SarthiRoyalBlue

data class DayMenu(
    val day: String,
    val breakfast: String,
    val lunch: String,
    val snacks: String,
    val dinner: String
)

val weeklyMessSchedule = listOf(
    DayMenu(
        day = "Monday",
        breakfast = "Aloo Paratha, Curd, Pickle, Tea / Coffee",
        lunch = "Rice, Dal Makhani, Mixed Vegetable Curry, Roti, Salad",
        snacks = "Samosa (2 pcs), Green Chutney, Tea",
        dinner = "Jeera Rice, Paneer Butter Masala / Egg Curry, Tawa Roti, Kheer"
    ),
    DayMenu(
        day = "Tuesday",
        breakfast = "Idli, Vada, Sambhar, Coconut Chutney, Tea / Coffee",
        lunch = "Steamed Rice, Yellow Moong Dal, Shahi Paneer / Rohu Fish Curry, Begun Bhaja, Papad",
        snacks = "Veg Sandwich, Tomato Ketchup, Tea",
        dinner = "Tawa Roti, Aloo Gobi Matar, Chana Dal Tadka, Rice, Gulab Jamun"
    ),
    DayMenu(
        day = "Wednesday",
        breakfast = "Puri Sabji, Halwa, Boiled Egg / Banana, Tea / Coffee",
        lunch = "Rice, Rajma Masala, Dum Aloo, Phulka, Boondi Raita",
        snacks = "Poha with Roasted Peanuts, Tea",
        dinner = "Veg Fried Rice, Chilli Chicken / Chilli Paneer, Spring Roll, Ice Cream"
    ),
    DayMenu(
        day = "Thursday",
        breakfast = "Masala Dosa, Sambhar, Tomato & Mint Chutney, Tea",
        lunch = "Rice, Cholar Dal, Aloo Posto, Beguni, Chapati, Tomato Chutney",
        snacks = "Bhel Puri / Jhalmuri, Lemon Tea",
        dinner = "Paneer Do Pyaza / Chicken Korma, Rumali Roti, Pulao, Custard"
    ),
    DayMenu(
        day = "Friday",
        breakfast = "Uttapam, Sambhar, Coconut Chutney, Sprouts, Tea",
        lunch = "Rice, Sambar, Bhindi Do Pyaza, Katla Fish Curry / Matar Mushroom, Papad",
        snacks = "Veg Cutlet, Mustard Sauce, Tea",
        dinner = "Chicken Biryani / Hyderabadi Veg Dum Biryani, Mirchi Ka Salan, Raita"
    ),
    DayMenu(
        day = "Saturday",
        breakfast = "Chole Bhature, Onion Salad, Pickle, Tea / Coffee",
        lunch = "Khichdi, Labra (Mixed Veg), Begun Bhaja, Chutney, Papad",
        snacks = "Biscuits & Pakora, Masala Chai",
        dinner = "Laccha Paratha, Kadai Paneer / Mutton Curry (Special), Salad, Rasgulla"
    ),
    DayMenu(
        day = "Sunday",
        breakfast = "Bread Omelette / Veg Cheese Sandwich, Cornflakes with Milk, Tea",
        lunch = "Special Sunday Thali: Pulao, Paneer Pasanda / Murg Malai, Roti, Salad, Payesh",
        snacks = "Sweet Corn / Maggi, Cold Drink",
        dinner = "Roti, Dal Fry, Seasonal Subji, Jeera Rice, Rasmalai"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessMenuDialog(
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedDayIndex by remember { mutableIntStateOf(1) } // Tuesday default
    val currentDay = weeklyMessSchedule[selectedDayIndex]

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.testTag("mess_menu_dialog")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(SarthiBlueContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Restaurant,
                            contentDescription = null,
                            tint = SarthiRoyalBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Central Mess Weekly Menu",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Mess Committee Approved · Autumn Cycle",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Day Selector Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(weeklyMessSchedule.size) { index ->
                    val isSelected = selectedDayIndex == index
                    Surface(
                        onClick = { selectedDayIndex = index },
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) SarthiRoyalBlue else MaterialTheme.colorScheme.surface,
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) SarthiRoyalBlue else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                        )
                    ) {
                        Text(
                            text = weeklyMessSchedule[index].day,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    MealSlotCard(
                        slotName = "BREAKFAST",
                        timings = "07:30 AM — 09:30 AM",
                        menuItems = currentDay.breakfast,
                        badgeColor = SarthiEmerald
                    )
                }

                item {
                    MealSlotCard(
                        slotName = "LUNCH",
                        timings = "12:30 PM — 02:15 PM",
                        menuItems = currentDay.lunch,
                        badgeColor = SarthiRoyalBlue
                    )
                }

                item {
                    MealSlotCard(
                        slotName = "EVENING SNACKS",
                        timings = "05:00 PM — 06:15 PM",
                        menuItems = currentDay.snacks,
                        badgeColor = Color(0xFFD97706)
                    )
                }

                item {
                    MealSlotCard(
                        slotName = "DINNER",
                        timings = "08:00 PM — 09:45 PM",
                        menuItems = currentDay.dinner,
                        badgeColor = Color(0xFF7E22CE)
                    )
                }

                item {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Dietary Notes & Feedback",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Vegetarian, Non-Veg and Jain preparations are kept strictly separated. For rebate applications, submit through Sarthi at least 48 hours prior.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun MealSlotCard(
    slotName: String,
    timings: String,
    menuItems: String,
    badgeColor: Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = badgeColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = slotName,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = badgeColor,
                        fontSize = 10.sp
                    )
                }

                Text(
                    text = timings,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = menuItems,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 20.sp
            )
        }
    }
}
