/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.template.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.template.navigation.temp.ForYouBaseRoute
import com.example.template.navigation.temp.bookmarksScreen
import com.example.template.navigation.temp.forYouSection
import com.example.template.navigation.temp.interestsListDetailScreen
import com.example.template.navigation.temp.navigateToInterests
import com.example.template.ui.AppState

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun NiaNavHost(
    appState: AppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = ForYouBaseRoute,
        modifier = modifier,
    ) {
        composable(route = "foryou"){
            Text("foryou")
        }

        forYouSection(
            onTopicClick = {} //navController::navigateToTopic,
        ) {
//            topicScreen(
//                showBackButton = true,
//                onBackClick = navController::popBackStack,
//                onTopicClick = navController::navigateToTopic,
//            )
        }
        bookmarksScreen(
            onTopicClick = navController::navigateToInterests,
            onShowSnackbar = onShowSnackbar,
        )
//        searchScreen(
//            onBackClick = navController::popBackStack,
//            onInterestsClick = { appState.navigateToTopLevelDestination(INTERESTS) },
//            onTopicClick = navController::navigateToInterests,
//        )
        interestsListDetailScreen()
    }
}
