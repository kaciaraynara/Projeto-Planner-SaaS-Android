package com.plannersaas.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plannersaas.R
import com.plannersaas.data.local.ProjectEntity
import com.plannersaas.viewmodel.ProjectViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: ProjectViewModel,
    onAddProjectClick: () -> Unit,
    onProjectClick: (Int) -> Unit,
    onSettingsClick: () -> Unit
) {
    val projects by viewModel.projects.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        stringResource(R.string.home_title), 
                        fontWeight = FontWeight.Bold, 
                        color = MaterialTheme.colorScheme.primary 
                    ) 
                },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, contentDescription = stringResource(R.string.settings))
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddProjectClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.new_project))
            }
        }
    ) { padding ->
        if (projects.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding), 
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        stringResource(R.string.no_projects), 
                        color = MaterialTheme.colorScheme.onSurfaceVariant, 
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        stringResource(R.string.tap_to_add), 
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f), 
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(projects, key = { it.id }) { project ->
                    ProjectCard(
                        project = project, 
                        onClick = { onProjectClick(project.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun ProjectCard(project: ProjectEntity, onClick: () -> Unit) {
    val dateFormatter = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.96f else 1f, label = "scale")
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable(interactionSource = interactionSource, indication = LocalIndication.current) { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(), 
                horizontalArrangement = Arrangement.SpaceBetween, 
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Status Chip
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    val statusText = when(project.status) {
                        "Active" -> stringResource(R.string.status_active)
                        "Pending" -> stringResource(R.string.status_pending)
                        "Completed" -> stringResource(R.string.status_completed)
                        else -> project.status
                    }
                    Text(
                        text = statusText.uppercase(),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                // Priority Indicator
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val priorityColor = when (project.priority) {
                        "High" -> Color(0xFFD32F2F)
                        "Medium" -> Color(0xFFF57C00)
                        else -> Color(0xFF388E3C)
                    }
                    val priorityText = when(project.priority) {
                        "High" -> stringResource(R.string.priority_high)
                        "Medium" -> stringResource(R.string.priority_medium)
                        "Low" -> stringResource(R.string.priority_low)
                        else -> project.priority
                    }
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .padding(2.dp)
                    )
                    Text(
                        text = priorityText,
                        color = priorityColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = project.title, 
                fontSize = 22.sp, 
                fontWeight = FontWeight.ExtraBold, 
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = project.description, 
                color = MaterialTheme.colorScheme.onSurfaceVariant, 
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 0.5.dp)
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = stringResource(R.string.created_at, dateFormatter.format(Date(project.createdAt))),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                if (project.dueDate != null) {
                    Text(
                        text = stringResource(R.string.due_date, dateFormatter.format(Date(project.dueDate))),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}
