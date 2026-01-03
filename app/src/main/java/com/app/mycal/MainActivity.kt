package com.app.mycal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.mycal.ui.theme.MycalTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MycalTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CalendarScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(calendarViewModel: CalendarViewModel = viewModel()) {
    val currentMonth by calendarViewModel.currentMonth.collectAsState()
    val selectedDate by calendarViewModel.selectedDate.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MyCal Calendar", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary)
            )
        }
    ) {\n        paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues) // Apply padding from Scaffold
        ) {
            MonthSelector(currentMonth) {
                calendarViewModel.setCurrentMonth(it)
            }
            Spacer(modifier = Modifier.height(16.dp))
            DayOfWeekHeader()
            Spacer(modifier = Modifier.height(8.dp))
            CalendarGrid(currentMonth, selectedDate) {
                calendarViewModel.setSelectedDate(it)
            }
        }
    }
}

@Composable
fun MonthSelector(currentMonth: LocalDate, onMonthChange: (LocalDate) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onMonthChange(currentMonth.minusMonths(1)) }) {
            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous Month")
        }
        Text(
            text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { onMonthChange(currentMonth.plusMonths(1)) }) {
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next Month")
        }
    }
}

@Composable
fun DayOfWeekHeader() {
    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    Row(modifier = Modifier.fillMaxWidth()) {
        daysOfWeek.forEach {
            Text(
                text = it,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun CalendarGrid(currentMonth: LocalDate, selectedDate: LocalDate?, onDateSelected: (LocalDate) -> Unit) {
    val firstDayOfMonth = currentMonth.with(TemporalAdjusters.firstDayOfMonth())
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // 0 for Monday, 6 for Sunday

    val calendarDays = mutableListOf<LocalDate?>()
    for (i in 0 until firstDayOfWeek) {
        calendarDays.add(null) // Fill leading empty spots
    }
    for (i in 1..daysInMonth) {
        calendarDays.add(firstDayOfMonth.withDayOfMonth(i))
    }

    LazyVerticalGrid(columns = GridCells.Fixed(7), modifier = Modifier.fillMaxWidth()) {
        items(calendarDays) {
            day ->
            if (day != null) {
                val isSelected = day == selectedDate
                val isToday = day == LocalDate.now()
                DayCell(day = day, isSelected = isSelected, isToday = isToday) {
                    onDateSelected(day)
                }
            } else {
                Spacer(Modifier.aspectRatio(1f))
            }
        }
    }
}

@Composable
fun DayCell(day: LocalDate, isSelected: Boolean, isToday: Boolean, onClick: () -> Unit) {
    val backgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.primary
        isToday -> MaterialTheme.colorScheme.secondaryContainer
        else -> Color.Transparent
    }
    val contentColor = when {
        isSelected -> MaterialTheme.colorScheme.onPrimary
        isToday -> MaterialTheme.colorScheme.onSecondaryContainer
        else -> MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .background(backgroundColor, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.dayOfMonth.toString(),
            color = contentColor,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal
        )
    }
}

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MycalTheme {
        CalendarScreen()
    }
}



