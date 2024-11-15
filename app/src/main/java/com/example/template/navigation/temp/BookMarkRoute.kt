package com.example.template.navigation.temp

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object BookmarksRoute

fun NavController.navigateToBookmarks(navOptions: NavOptions) =
    navigate(route = BookmarksRoute, navOptions)

fun NavGraphBuilder.bookmarksScreen(
    onTopicClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable<BookmarksRoute> {
        LazyColumn {
            itemsIndexed(listOf(1,2,3,4,5,6,7,8,9,10)) { index, item ->
                Text(item.toString(),modifier = Modifier.height(130.dp))
            }
        }

    }
}
