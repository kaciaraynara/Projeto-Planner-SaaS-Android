package com.plannersaas

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.plannersaas.ui.navigation.NavGraph
import com.plannersaas.ui.theme.PlannerSaaSTheme
import com.plannersaas.viewmodel.ProjectViewModel
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ProjectViewModel = viewModel(modelClass = ProjectViewModel::class.java)
            
            // Collect theme and language preferences
            val darkModePref by viewModel.darkMode.collectAsState()
            val themeColor by viewModel.themeColor.collectAsState()
            val dynamicColors by viewModel.dynamicColors.collectAsState()
            val language by viewModel.language.collectAsState()
            
            val isDarkTheme = when (darkModePref) {
                true -> true
                false -> false
                null -> isSystemInDarkTheme()
            }

            // Apply language/locale
            val context = LocalContext.current
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = Configuration(context.resources.configuration)
            config.setLocale(locale)
            val localizedContext = context.createConfigurationContext(config)

            CompositionLocalProvider(
                LocalContext provides localizedContext,
                LocalActivityResultRegistryOwner provides (context as ActivityResultRegistryOwner),
                LocalOnBackPressedDispatcherOwner provides (context as OnBackPressedDispatcherOwner)
            ) {
                PlannerSaaSTheme(
                    darkTheme = isDarkTheme,
                    themeColor = themeColor,
                    dynamicColor = dynamicColors
                ) {
                    val navController = rememberNavController()
                    NavGraph(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
