package com.plannersaas.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.plannersaas.R
import com.plannersaas.viewmodel.ProjectViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProjectScreen(viewModel: ProjectViewModel, onBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("Medium") }
    var status by remember { mutableStateOf("Active") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var dueDate by remember { mutableStateOf<Long?>(null) }
    
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    // Image Picker Launcher
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )

    // Date Picker State
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    dueDate = datePickerState.selectedDateMillis
                    showDatePicker = false
                }) { Text(stringResource(R.string.confirm)) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text(stringResource(R.string.cancel)) }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.new_project), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Image Selection Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(16.dp))
                    .clickable {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if (selectedImageUri != null) {
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = "Project Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.AddAPhoto, 
                            contentDescription = null, 
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(stringResource(R.string.add_image), color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.title_hint)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(stringResource(R.string.description_hint)) },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                shape = RoundedCornerShape(12.dp)
            )

            // Due Date Selector
            OutlinedCard(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.DateRange, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = if (dueDate != null) stringResource(R.string.due_date, dateFormatter.format(Date(dueDate!!))) 
                               else stringResource(R.string.select_due_date),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Text(stringResource(R.string.priority_label), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("High", "Medium", "Low").forEach { p ->
                    val priorityText = when(p) {
                        "High" -> stringResource(R.string.priority_high)
                        "Medium" -> stringResource(R.string.priority_medium)
                        "Low" -> stringResource(R.string.priority_low)
                        else -> p
                    }
                    FilterChip(
                        selected = priority == p,
                        onClick = { priority = p },
                        label = { Text(priorityText) },
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            Text(stringResource(R.string.status_selection_label), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Active", "Pending", "Completed").forEach { s ->
                    val statusText = when(s) {
                        "Active" -> stringResource(R.string.status_active)
                        "Pending" -> stringResource(R.string.status_pending)
                        "Completed" -> stringResource(R.string.status_completed)
                        else -> s
                    }
                    FilterChip(
                        selected = status == s,
                        onClick = { status = s },
                        label = { Text(statusText) },
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        viewModel.addProject(
                            title = title,
                            description = description,
                            priority = priority,
                            status = status,
                            imageUri = selectedImageUri?.toString(),
                            dueDate = dueDate
                        )
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                enabled = title.isNotBlank()
            ) {
                Text(stringResource(R.string.save_project), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = if (title.isNotBlank()) Color.White else Color.Gray)
            }
        }
    }
}
