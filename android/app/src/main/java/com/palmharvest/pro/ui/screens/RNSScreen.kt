package com.palmharvest.pro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RNSScreen(
    rnsService: RNSService,
    onBack: () -> Unit = {}
) {
    val rnsStatus by rnsService.status.collectAsState()
    var isSyncing by remember { mutableStateOf(false) }
    var syncProgress by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray50)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(White)
                    .padding(4.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Gray600)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "RNS Bridge",
                    style = MaterialTheme.typography.displaySmall,
                    color = Gray900
                )
                Text(
                    text = "Off-grid LoRa Sync",
                    style = MaterialTheme.typography.labelMedium,
                    color = Gray500
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Connection Status Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(48.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = if (rnsStatus.isConnected) Color(0xFFF0FDF4) else Gray50
                        ) {
                            Icon(
                                imageVector = if (rnsStatus.isConnected) Icons.Default.BluetoothConnected else Icons.Default.BluetoothDisabled,
                                contentDescription = null,
                                tint = if (rnsStatus.isConnected) Color(0xFF16A34A) else Gray400,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("RNode Connection", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black)
                            Text(
                                if (rnsStatus.isConnected) "Connected to ${rnsStatus.deviceName}" else "No RNode connected",
                                style = MaterialTheme.typography.labelMedium,
                                color = Gray500
                            )
                        }
                    }
                    Button(
                        onClick = { 
                            if (!rnsStatus.isConnected) {
                                // Mock address for demonstration, in real app would use a picker
                                rnsService.connectRNode("00:11:22:33:44:55")
                            }
                        },
                        modifier = Modifier.height(40.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (rnsStatus.isConnected) White else Primary600,
                            contentColor = if (rnsStatus.isConnected) Color(0xFFDC2626) else White
                        ),
                        border = if (rnsStatus.isConnected) androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFEE2E2)) else null
                    ) {
                        Text(if (rnsStatus.isConnected) "Disconnect" else "Connect BT", style = MaterialTheme.typography.labelLarge)
                    }
                }

                if (rnsStatus.isConnected) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Divider(color = Gray100)
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(if (rnsStatus.isRnsRunning) Color(0xFF22C55E) else Gray300)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("RNS STACK", style = MaterialTheme.typography.labelLarge, color = Gray700)
                        }
                        Button(
                            onClick = { rnsService.startRNS("PalmHarvest-User") },
                            modifier = Modifier.height(32.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Gray900),
                            enabled = !rnsStatus.isRnsRunning
                        ) {
                            Text(if (rnsStatus.isRnsRunning) "RUNNING" else "START STACK", style = MaterialTheme.typography.labelMedium)
                        }
                    }
                    
                    if (rnsStatus.localHash != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Local Hash: ${rnsStatus.localHash}",
                            style = MaterialTheme.typography.labelMedium,
                            color = Gray400,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // RNode Tuning Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFFF7ED)
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = null, tint = Color(0xFFEA580C), modifier = Modifier.padding(8.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("RNode Tuning", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black)
                        Text("LoRa Physical Layer Config", style = MaterialTheme.typography.labelMedium, color = Gray500)
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Frequency (Hz)", style = MaterialTheme.typography.labelLarge, color = Gray500)
                        Text("868.1 MHz", style = MaterialTheme.typography.labelLarge, color = Primary600, fontWeight = FontWeight.Black)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = "868100000",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Primary600,
                            unfocusedBorderColor = Gray100
                        )
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = { rnsService.injectRNode(868100000, 125000, 7, 7, 5) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    enabled = rnsStatus.isConnected,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEA580C))
                ) {
                    Text("Apply Tuning", style = MaterialTheme.typography.labelLarge)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // LXMF Sync Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFEFF6FF)
                    ) {
                        Icon(Icons.Default.Zap, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.padding(8.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("LXMF Sync", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black)
                        Text("Off-grid Data Exchange", style = MaterialTheme.typography.labelMedium, color = Gray500)
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Destination Hash (Hex)", style = MaterialTheme.typography.labelLarge, color = Gray500)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("e.g. 8b4f2c...", style = MaterialTheme.typography.labelLarge, color = Gray300) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = { Icon(Icons.Default.Send, contentDescription = null, tint = Gray400, modifier = Modifier.size(20.dp)) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Primary600,
                            unfocusedBorderColor = Gray100
                        )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = { rnsService.announce() },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        enabled = rnsStatus.isRnsRunning,
                        border = androidx.compose.foundation.BorderStroke(1.dp, Gray100)
                    ) {
                        Icon(Icons.Default.Activity, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Announce", style = MaterialTheme.typography.labelLarge)
                    }
                    Button(
                        onClick = { 
                            isSyncing = true
                            rnsService.sendText("8b4f2c...", "SYNC_RECORDS_MOCK")
                        },
                        modifier = Modifier.weight(2f).height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        enabled = rnsStatus.isRnsRunning && !isSyncing,
                        colors = ButtonDefaults.buttonColors(containerColor = Primary600)
                    ) {
                        Icon(Icons.Default.Zap, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Sync 12 Records", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }
}
