package com.plannersaas.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.plannersaas.ui.screens.AddProjectScreen
import com.plannersaas.ui.screens.DetailProjectScreen
import com.plannersaas.ui.screens.HomeScreen
import com.plannersaas.ui.screens.SettingsScreen
import com.plannersaas.viewmodel.ProjectViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddProject : Screen("add_project")
    object DetailProject : Screen("detail_project/{projectId}") {
        fun createRoute(projectId: Int) = "detail_project/$projectId"
    }
    object Settings : Screen("settings")
}

@Composable
fun NavGraph(navController: NavHostController, viewModel: ProjectViewModel) {
    NavHost(
        navController = navController, 
        startDestination = Screen.Home.route,
        enterTransition = { fadeIn(animationSpec = tween(400)) + slideInHorizontally(animationSpec = tween(400)) { it } },
        exitTransition = { fadeOut(animationSpec = tween(400)) + slideOutHorizontally(animationSpec = tween(400)) { -it } },
        popEnterTransition = { fadeIn(animationSpec = tween(400)) + slideInHorizontally(animationSpec = tween(400)) { -it } },
        popExitTransition = { fadeOut(animationSpec = tween(400)) + slideOutHorizontally(animationSpec = tween(400)) { it } }
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onAddProjectClick = { navController.navigate(Screen.AddProject.route) },
                onProjectClick = { projectId -> 
                    navController.navigate(Screen.DetailProject.createRoute(projectId)) 
                },
                onSettingsClick = { navController.navigate(Screen.Settings.route) }
            )
        }
        
        composable(Screen.AddProject.route) {
            AddProjectScreen(
                viewModel = viewModel, 
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = Screen.DetailProject.route,
            arguments = listOf(navArgument("projectId") { type = NavType.IntType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getInt("projectId") ?: 0
            DetailProjectScreen(
                projectId = projectId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Settings.route) {
            SettingsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
