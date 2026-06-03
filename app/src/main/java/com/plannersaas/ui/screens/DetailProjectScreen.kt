package com.plannersaas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.plannersaas.R
import com.plannersaas.data.local.ProjectEntity
import com.plannersaas.viewmodel.ProjectViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailProjectScreen(
    projectId: Int,
    viewModel: ProjectViewModel,
    onBack: () -> Unit
) {
    var project by remember { mutableStateOf<ProjectEntity?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val dateFormatter = remember { SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()) }

    LaunchedEffect(projectId) {
        project = viewModel.getProjectById(projectId)
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(stringResource(R.string.delete_project)) },
            text = { Text(stringResource(R.string.delete_confirmation)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        project?.let { 
                            viewModel.deleteProject(it)
                            showDeleteDialog = false
                            onBack()
                        }
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) { Text(stringResource(R.string.delete)) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text(stringResource(R.string.cancel)) }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.project_details), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        project?.let { item ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                // Project Image Header
                if (item.imageUri != null) {
                    AsyncImage(
                        model = item.imageUri,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.title.firstOrNull()?.toString() ?: "P",
                            fontSize = 64.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Priority Badge
                        val priorityColor = when (item.priority) {
                            "High" -> Color(0xFFD32F2F)
                            "Medium" -> Color(0xFFF57C00)
                            else -> Color(0xFF388E3C)
                        }
                        val priorityText = when(item.priority) {
                            "High" -> stringResource(R.string.priority_high)
                            "Medium" -> stringResource(R.string.priority_medium)
                            "Low" -> stringResource(R.string.priority_low)
                            else -> item.priority
                        }
                        Surface(
                            color = priorityColor.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.PriorityHigh,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = priorityColor
                                )
                                Text(
                                    text = priorityText,
                                    color = priorityColor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Status Badge
                        val statusText = when(item.status) {
                            "Active" -> stringResource(R.string.status_active)
                            "Pending" -> stringResource(R.string.status_pending)
                            "Completed" -> stringResource(R.string.status_completed)
                            else -> item.status
                        }
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = statusText,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.CalendarMonth,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.created_at, dateFormatter.format(Date(item.createdAt))),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (item.dueDate != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.CalendarMonth,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(R.string.due_date, dateFormatter.format(Date(item.dueDate))),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(
                        text = stringResource(R.string.description_hint),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = item.description.ifBlank { stringResource(R.string.no_description) },
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge,
                            lineHeight = 24.sp
                        )
                    }
                }
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
