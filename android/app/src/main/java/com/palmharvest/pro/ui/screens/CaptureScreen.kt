package com.palmharvest.pro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.palmharvest.pro.ui.theme.*

@Composable
fun CaptureScreen(
    onCapture: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray50)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Capture Harvest",
            style = MaterialTheme.typography.displaySmall,
            color = Gray900
        )
        Text(
            text = "Take a photo of the harvested bunches to start a new entry",
            style = MaterialTheme.typography.bodyMedium,
            color = Gray500,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Photo Capture Placeholder
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        modifier = Modifier.size(80.dp),
                        shape = CircleShape,
                        color = Primary50
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = null,
                            tint = Primary600,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onCapture,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary600)
                    ) {
                        Text("OPEN CAMERA", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Recent Activity
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Today's Harvests",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Gray900
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                // Mock List
                repeat(3) {
                    HarvestItem(
                        point = "har0${it + 1}",
                        bunches = (it + 1) * 12,
                        time = "10:2${it} AM"
                    )
                    if (it < 2) HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Gray100)
                }
            }
        }
    }
}

@Composable
fun HarvestItem(point: String, bunches: Int, time: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(12.dp),
                color = Primary50
            ) {
                Icon(
                    imageVector = Icons.Default.Inventory,
                    contentDescription = null,
                    tint = Primary600,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = point, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black)
                Text(text = time, style = MaterialTheme.typography.labelMedium, color = Gray400)
            }
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(text = "$bunches", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = Primary700)
            Text(text = "BUNCHES", style = MaterialTheme.typography.labelMedium, color = Gray400)
        }
    }
}
