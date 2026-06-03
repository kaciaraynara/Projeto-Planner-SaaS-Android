package com.plannersaas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.SettingsBrightness
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.plannersaas.R
import com.plannersaas.ui.theme.*
import com.plannersaas.viewmodel.ProjectViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: ProjectViewModel,
    onBack: () -> Unit
) {
    val darkModePref by viewModel.darkMode.collectAsState()
    val themeColor by viewModel.themeColor.collectAsState()
    val dynamicColors by viewModel.dynamicColors.collectAsState()
    val language by viewModel.language.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings), fontWeight = FontWeight.Bold) },
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Theme Mode Section
            SettingsSection(title = stringResource(R.string.theme_mode), icon = Icons.Default.SettingsBrightness) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ThemeModeOption(stringResource(R.string.system), darkModePref == null) { viewModel.setDarkMode(null) }
                    ThemeModeOption(stringResource(R.string.light), darkModePref == false) { viewModel.setDarkMode(false) }
                    ThemeModeOption(stringResource(R.string.dark), darkModePref == true) { viewModel.setDarkMode(true) }
                }
            }

            // Language Section
            SettingsSection(title = stringResource(R.string.language), icon = Icons.Default.Language) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = language == "pt",
                        onClick = { viewModel.setLanguage("pt") },
                        label = { Text("Português") },
                        shape = RoundedCornerShape(12.dp)
                    )
                    FilterChip(
                        selected = language == "en",
                        onClick = { viewModel.setLanguage("en") },
                        label = { Text("English") },
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            // Color Palette Section
            SettingsSection(title = stringResource(R.string.color_palette), icon = Icons.Default.Palette) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    val colors = listOf(
                        "Blue" to BluePrimary,
                        "Purple" to PurplePrimary,
                        "Green" to GreenPrimary,
                        "Orange" to OrangePrimary,
                        "Red" to RedPrimary
                    )
                    
                    colors.forEach { (name, color) ->
                        ColorOption(
                            color = color,
                            isSelected = themeColor == name,
                            onClick = { viewModel.setThemeColor(name) }
                        )
                    }
                }
            }
            
            // Dynamic Colors (Android 12+)
            SettingsSection(title = stringResource(R.string.personalization), icon = Icons.Default.Palette) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(stringResource(R.string.dynamic_colors), fontWeight = FontWeight.Medium)
                        Text(stringResource(R.string.dynamic_colors_desc), style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(
                        checked = dynamicColors,
                        onCheckedChange = { viewModel.setDynamicColors(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsSection(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, content: @Composable () -> Unit) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))
        content()
    }
}

@Composable
fun ThemeModeOption(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
            if (isSelected) {
                Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun ColorOption(color: Color, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(color)
            .clickable { onClick() }
            .border(
                width = if (isSelected) 3.dp else 0.dp,
                color = if (isSelected) MaterialTheme.colorScheme.outline else Color.Transparent,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White)
        }
    }
}
